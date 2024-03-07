import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import dao.AccountDAO;
import util.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@WebListener
public class AppStartupListener implements ServletContextListener {

    private AccountDAO accountDAO;

    private PasswordUtil passwordUtil;

    String hashedPassword = "";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        accountDAO = new AccountDAO();
        ensureAdminAccount();
        ensureUserAccount();
    }

    private void ensureAdminAccount() {
        String username = "admin";
        String defaultPassword = "123456";
        String role = "admin";
        try {
            hashedPassword = passwordUtil.generateStrongPasswordHash(defaultPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        if (accountDAO.existsByUsername(username)) {
            accountDAO.updatePassword(username, hashedPassword, role);
        } else {
            accountDAO.createAccount(username, hashedPassword, role);
        }
    }

    private void ensureUserAccount() {
        String username = "user";
        String defaultPassword = "123456";
        String role = "user";
        try {
            hashedPassword = passwordUtil.generateStrongPasswordHash(defaultPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        if (accountDAO.existsByUsername(username)) {
            accountDAO.updatePassword(username, hashedPassword, role);
        } else {
            accountDAO.createAccount(username, hashedPassword, role);
        }
    }





}