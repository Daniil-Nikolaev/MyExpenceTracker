package by.aston.myexpencetracker.Repository;

import by.aston.myexpencetracker.Entity.Category;
import by.aston.myexpencetracker.Entity.Transaction;
import by.aston.myexpencetracker.Utils.DBConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class TransactionRepository {
    private static TransactionRepository transactionRepository;

    private TransactionRepository() {};
    public static TransactionRepository getInstance() {
        if (transactionRepository == null) {
            transactionRepository = new TransactionRepository();
        }
        return transactionRepository;
    }

    public void save(Transaction transaction, int userId) {
        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO transactions (user_id, description, amount, date) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, transaction.getDescription());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.setDate(4, Date.valueOf(transaction.getDate()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int transactionId = rs.getInt(1);
                insertTransactionCategories(connection, transactionId, transaction.getCategories());
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> findAll(int userId) {
        Map<Integer, Transaction> transactionMap = new LinkedHashMap<>();
        String query = """
            SELECT t.*, c.id AS cat_id, c.name AS cat_name
            FROM transactions t
            LEFT JOIN transaction_category tc ON t.id = tc.transaction_id
            LEFT JOIN categories c ON c.id = tc.category_id
            WHERE t.user_id = ?
            ORDER BY t.id
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");

                Transaction transaction = transactionMap.computeIfAbsent(id, i -> {
                    Transaction t = new Transaction();
                    t.setId(i);
                    try {
                        t.setDescription(rs.getString("description"));
                        t.setAmount(rs.getBigDecimal("amount"));
                        t.setDate(rs.getDate("date").toLocalDate());
                        t.setCategories(new ArrayList<>());
                        return t;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                int catId = rs.getInt("cat_id");
                if (catId != 0) {
                    Category category = new Category(catId, rs.getString("cat_name"));
                    transaction.getCategories().add(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(transactionMap.values());
    }

    public Optional<Transaction> findByTransactionId(int transactionId, int userId) {
        String query = """
            SELECT t.*, c.id AS cat_id, c.name AS cat_name
            FROM transactions t
            LEFT JOIN transaction_category tc ON t.id = tc.transaction_id
            LEFT JOIN categories c ON c.id = tc.category_id
            WHERE t.id = ? AND t.user_id = ?
        """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, transactionId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            Transaction transaction = null;

            while (rs.next()) {
                if (transaction == null) {
                    transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setDescription(rs.getString("description"));
                    transaction.setAmount(rs.getBigDecimal("amount"));
                    transaction.setDate(rs.getDate("date").toLocalDate());
                    transaction.setCategories(new ArrayList<>());
                }
                int catId = rs.getInt("cat_id");
                if (catId != 0) {
                    transaction.getCategories().add(new Category(catId, rs.getString("cat_name")));
                }
            }
            return Optional.ofNullable(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int transactionId, int userId) {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps1 = connection.prepareStatement(
                    "DELETE FROM transaction_category WHERE transaction_id = ?");
                 PreparedStatement ps2 = connection.prepareStatement(
                         "DELETE FROM transactions WHERE id = ? AND user_id = ?")) {

                ps1.setInt(1, transactionId);
                ps1.executeUpdate();

                ps2.setInt(1, transactionId);
                ps2.setInt(2, userId);
                int updated = ps2.executeUpdate();

                if (updated == 0) throw new RuntimeException("Transaction not found");

                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Transaction transaction, int transactionId, int userId) {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE transactions SET description = ?, amount = ?, date = ? WHERE id = ? AND user_id = ?");
            ps.setString(1, transaction.getDescription());
            ps.setBigDecimal(2, transaction.getAmount());
            ps.setDate(3, Date.valueOf(transaction.getDate()));
            ps.setInt(4, transactionId);
            ps.setInt(5, userId);

            int updated = ps.executeUpdate();
            if (updated == 0) throw new RuntimeException("Transaction not found");

            try (PreparedStatement deleteLinks = connection.prepareStatement(
                    "DELETE FROM transaction_category WHERE transaction_id = ?")) {
                deleteLinks.setInt(1, transactionId);
                deleteLinks.executeUpdate();
            }

            insertTransactionCategories(connection, transactionId, transaction.getCategories());

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertTransactionCategories(Connection connection, int transactionId, List<Category> categories) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO transaction_category (transaction_id, category_id) VALUES (?, ?)")) {
            for (Category category : categories) {
                ps.setInt(1, transactionId);
                ps.setInt(2, category.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }



}
