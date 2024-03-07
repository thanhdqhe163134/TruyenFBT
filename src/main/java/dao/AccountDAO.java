package dao;

import model.Account;
import util.DBConnect;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

    Connection connection = new DBConnect().connection;

    public boolean existsByUsername(String username) {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Account WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                return preparedStatement.executeQuery().next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updatePassword(String username, String password, String role) {
        if (connection != null) {
            try {
                String sql = "UPDATE Account SET password = ?, role = ? WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, role);
                preparedStatement.setString(3, username);
                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean createAccount(String username, String password, String role) {
        if (connection != null) {
            try {
                String sql = "INSERT INTO Account (username, password, role, createdDate) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, role);
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public Account login(String username, String password) {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Account WHERE username = ? AND deleted = 0";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (PasswordUtil.validatePassword(password, storedPassword)) {
                        Account account = new Account();
                        account.setId(resultSet.getInt("id"));
                        account.setUsername(resultSet.getString("username"));
                        account.setPassword(resultSet.getString("password"));
                        account.setRole(resultSet.getString("role"));
                        account.setImg(resultSet.getString("img"));
                        return account;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
