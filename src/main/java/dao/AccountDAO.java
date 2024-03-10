package dao;

import model.Account;
import util.DBConnect;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public Account get(int id) {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Account WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt("id"));
                    account.setUsername(resultSet.getString("username"));
                    account.setPassword(resultSet.getString("password"));
                    account.setRole(resultSet.getString("role"));
                    account.setImg(resultSet.getString("img"));
                    account.setCreatedDate(resultSet.getTimestamp("createdDate"));
                    account.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                    return account;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void changePassword(int parseInt, String hashPassword) {
        if (connection != null) {
            try {
                String sql = "UPDATE Account SET password = ?, updatedDate = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, hashPassword);
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, parseInt);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateAvatar(int parseInt, String relativePath) {
        if (relativePath == null || relativePath.isEmpty() || relativePath.isBlank() || relativePath.equals("")) {
            return true;
        }
        if (connection != null) {
            try {
                String sql = "UPDATE Account SET img = ?, updatedDate = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, relativePath);
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                preparedStatement.setInt(3, parseInt);
                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Account> getAll() {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Account WHERE deleted = 0";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    List<Account> accounts = new java.util.ArrayList<>();
                    do {
                        Account account = new Account();
                        account.setId(resultSet.getInt("id"));
                        account.setUsername(resultSet.getString("username"));
                        account.setPassword(resultSet.getString("password"));
                        account.setRole(resultSet.getString("role"));
                        account.setImg(resultSet.getString("img"));
                        account.setCreatedDate(resultSet.getTimestamp("createdDate"));
                        account.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                        accounts.add(account);
                    } while (resultSet.next());
                    return accounts;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void delete(String id) {
        if (connection != null) {
            try {
                String sql = "UPDATE Account SET deleted = 1 WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(id));
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Account> search(String search) {
        if (connection != null) {
            try {
                String sql = "SELECT * FROM Account WHERE username LIKE ? AND deleted = 0";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "%" + search + "%");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    List<Account> accounts = new java.util.ArrayList<>();
                    do {
                        Account account = new Account();
                        account.setId(resultSet.getInt("id"));
                        account.setUsername(resultSet.getString("username"));
                        account.setPassword(resultSet.getString("password"));
                        account.setRole(resultSet.getString("role"));
                        account.setImg(resultSet.getString("img"));
                        account.setCreatedDate(resultSet.getTimestamp("createdDate"));
                        account.setUpdatedDate(resultSet.getTimestamp("updatedDate"));
                        accounts.add(account);
                    } while (resultSet.next());
                    return accounts;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
