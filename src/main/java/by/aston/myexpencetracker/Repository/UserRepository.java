package by.aston.myexpencetracker.Repository;

import by.aston.myexpencetracker.Entity.User;
import by.aston.myexpencetracker.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static UserRepository userRepository;

    private UserRepository() {}
    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public void save(User user) {
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "INSERT INTO users (name,password,balance) VALUES (?,?,?)");) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBigDecimal(3,user.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet=preparedStatement.executeQuery();) {

            while(resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setBalance(resultSet.getBigDecimal("balance"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public Optional<User> findById(int id) {
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM users WHERE id=? ");) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setBalance(resultSet.getBigDecimal("balance"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void delete(int id) {
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "DELETE FROM users WHERE id=?");) {

            preparedStatement.setInt(1, id);
            int executeUpdate=preparedStatement.executeUpdate();
            if(executeUpdate==0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user,int id) {
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "UPDATE users SET name=?,password=?,balance=? WHERE id=?");) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBigDecimal(3,user.getBalance());
            preparedStatement.setInt(4, id);

            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate == 0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}