package util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy session
        HttpSession session = httpRequest.getSession(false);

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        // Lấy URL hiện tại
        String requestURI = httpRequest.getRequestURI();

        // Kiểm tra quyền truy cập dựa trên URL và vai trò của người dùng
        if (isPublicResource(requestURI) || isLoggedIn && hasPermission(requestURI, session)) {
            // Nếu tài nguyên là công khai hoặc người dùng đã đăng nhập và có quyền truy cập, cho phép truy cập
            chain.doFilter(request, response);
        } else {
            // Ngược lại, chuyển hướng người dùng đến trang báo lỗi hoặc trang đăng nhập
            httpResponse.sendRedirect("AccessDenied.jsp");
        }
    }

    private boolean isPublicResource(String requestURI) {
        // Các tài nguyên công khai, không cần phân quyền
        return true;
    }

    private boolean hasPermission(String requestURI, HttpSession session) {
        // Kiểm tra quyền truy cập dựa trên vai trò của người dùng
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            String role = loggedInUser.getRole();
            if ("admin".equals(role)){
                return true; // Cho phép truy cập các tài nguyên dành cho admin
            } else if ("user".equals(role)) {
                return true; // Cho phép truy cập các tài nguyên dành cho người dùng
            }
        }
        return false; // Từ chối truy cập nếu không có quyền
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }
}
