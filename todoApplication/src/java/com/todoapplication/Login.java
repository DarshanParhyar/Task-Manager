package com.todoapplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                out.println("Error: MySQL JDBC Driver not found");
                return;
            }

            String url = "jdbc:mysql://localhost:3306/myDB1";
            String username = "root";
            String dbPassword = "1234567890";

            try (Connection conn = DriverManager.getConnection(url, username, dbPassword)) {
                String query = "SELECT * FROM users WHERE email = ? AND password = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {

                            String username1 = resultSet.getString("username");

                            Cookie sessionCookie = new Cookie("username", username1);
                            sessionCookie.setMaxAge(30 * 60);
                            response.addCookie(sessionCookie);

                            response.sendRedirect("read.html");
                        } else {

                            out.println("<div style='text-align: center;'>");
                            out.println("<h1>Login Failed</h1>");
                            out.println("<p>Invalid email or password</p>");
                            out.println("</div>");
                            request.getRequestDispatcher("login.html").include(request, response);
                        }
                    }
                }
            } catch (SQLException e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}
