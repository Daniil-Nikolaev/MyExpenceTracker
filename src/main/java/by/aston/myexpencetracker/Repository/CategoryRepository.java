package by.aston.myexpencetracker.Repository;

import by.aston.myexpencetracker.Entity.Category;
import by.aston.myexpencetracker.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private static CategoryRepository categoryRepository;

    private CategoryRepository() {}
    public static CategoryRepository getInstance() {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }

    public void save(Category category) {
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "INSERT INTO categories (name) VALUES (?)");) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM categories");
            ResultSet resultSet=preparedStatement.executeQuery();) {

            while(resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public void delete(int id) {
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(
                    "DELETE FROM categories WHERE id=?");) {

            preparedStatement.setInt(1, id);
            int executeUpdate=preparedStatement.executeUpdate();
            if(executeUpdate==0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
