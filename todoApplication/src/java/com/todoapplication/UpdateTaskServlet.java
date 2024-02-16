package com.todoapplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTaskServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String taskId = request.getParameter("taskId");

            if (taskId == null || taskId.isEmpty()) {
                out.println("Task ID is required.");
                return;
            }

            try {

                int taskIdInt = Integer.parseInt(taskId);

                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Database connection details
                String url = "jdbc:mysql://localhost:3306/myDB1";
                String username = "root";
                String password = "1234567890";

                try (Connection conn = DriverManager.getConnection(url, username, password)) {

                    String query = "UPDATE tasks SET task_status = 'completed' WHERE task_id = ?";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                        preparedStatement.setInt(1, taskIdInt);
                        int rowsUpdated = preparedStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            out.println("Task updated successfully.");
                        } else {
                            out.println("Failed to update task.");
                        }
                    }

                }
            } catch (NumberFormatException e) {
                out.println("Invalid task ID format.");
            } catch (ClassNotFoundException | SQLException e) {
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
        return "Update Task Servlet";
    }
}
