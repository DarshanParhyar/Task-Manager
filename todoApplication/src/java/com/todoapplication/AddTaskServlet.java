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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTaskServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String taskName = request.getParameter("taskName");
            String taskDescription = request.getParameter("taskDescription");
            String taskStatus = request.getParameter("taskStatus");
            String taskDueDate = request.getParameter("taskDueDate");

            String url = "jdbc:mysql://localhost:3306/myDB1";
            String username = "root";
            String password = "1234567890";

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String query = "INSERT INTO tasks (user_id, task_name, task_description, task_status, task_due_date) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                    int userId = 1;
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setString(2, taskName);
                    preparedStatement.setString(3, taskDescription);
                    preparedStatement.setString(4, taskStatus);
                    preparedStatement.setString(5, taskDueDate);
                    preparedStatement.executeUpdate();

                    out.println("<h2>Task Added Successfully</h2>");
                    out.println("<p>Task Name: " + taskName + "</p>");
                    out.println("<p>Task Description: " + taskDescription + "</p>");
                    out.println("<p>Task Status: " + taskStatus + "</p>");
                    out.println("<p>Task Due Date: " + taskDueDate + "</p>");
                }
            } catch (SQLException e) {
                out.println("<h2>Error Adding Task</h2>");
                out.println("<p>" + e.getMessage() + "</p>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Add Task Servlet";
    }
}
