package com.todoapplication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ViewTaskServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Tasks</title>");
            out.println("<style>");
            out.println("body {");
            out.println("    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;");
            out.println("    background-color: #f9f9f9;");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("}");
            out.println("");
            out.println("h2 {");
            out.println("    text-align: center;");
            out.println("    margin-top: 30px;");
            out.println("    color: #333;");
            out.println("}");
            out.println("");
            out.println("table {");
            out.println("    width: 100%;");
            out.println("    border-collapse: collapse;");
            out.println("    margin-top: 20px;");
            out.println("}");
            out.println("");
            out.println("th, td {");
            out.println("    border: 1px solid #ddd;");
            out.println("    padding: 8px;");
            out.println("    text-align: left;");
            out.println("}");
            out.println("");
            out.println("th {");
            out.println("    background-color: #f2f2f2;");
            out.println("}");
            out.println("");
            out.println("tr:nth-child(even) {");
            out.println("    background-color: #f2f2f2;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            try {

                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://localhost:3306/myDB1";
                String username = "root";
                String password = "1234567890";

                try (Connection conn = DriverManager.getConnection(url, username, password)) {
                    String query = "SELECT * FROM tasks";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                        ResultSet resultSet = preparedStatement.executeQuery();

                        out.println("<h2>Tasks</h2>");
                        out.println("<table>");
                        out.println("<tr>");
                        out.println("<th>Task Name</th>");
                        out.println("<th>Description</th>");
                        out.println("<th>Status</th>");
                        out.println("<th>Due Date</th>");
                        out.println("</tr>");

                        while (resultSet.next()) {
                            out.println("<tr>");
                            out.println("<td>" + resultSet.getString("task_name") + "</td>");
                            out.println("<td>" + resultSet.getString("task_description") + "</td>");
                            out.println("<td>" + resultSet.getString("task_status") + "</td>");
                            out.println("<td>" + resultSet.getString("task_due_date") + "</td>");
                            out.println("</tr>");
                        }

                        out.println("</table>");
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            }

            out.println("</body>");
            out.println("</html>");
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
        return "View Task Servlet";
    }
}
