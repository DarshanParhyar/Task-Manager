package com.todoapplication;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTaskServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String taskId = request.getParameter("taskId");

            String url = "jdbc:mysql://localhost:3306/myDB1";
            String username = "root";
            String password = "1234567890";

            try {

                Class.forName("com.mysql.jdbc.Driver");

                Connection conn = DriverManager.getConnection(url, username, password);

                String sql = "DELETE FROM tasks WHERE task_id=?";

                PreparedStatement pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, taskId);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {

                    out.println("<div style=\"text-align: center;\">");
                    out.println("<b>Task deleted successfully.</b>");
                    out.println("</div>");

                } else {

                    out.println("<div style=\"text-align: center;\">");
                    out.println("<b>No task found with the provided ID.</b>");
                    out.println("</div>");

                }

                pstmt.close();
                conn.close();

            } catch (ClassNotFoundException | SQLException e) {
                out.println("<p>An error occurred: " + e.getMessage() + "</p>");
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
        return "Delete Task Servlet";
    }

}
