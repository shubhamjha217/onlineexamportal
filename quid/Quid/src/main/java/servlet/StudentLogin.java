package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentLogin")
public class StudentLogin extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            if (rs.next()) {
                // Login successful → redirect to quiz page
                res.sendRedirect("studentquiz.jsp");
            } else {
                // Login failed → show message and link back
                out.println("<div style='text-align:center; font-family:sans-serif; padding:50px;'>");
                out.println("<h2 style='color:red;'>Invalid Email or Password</h2>");
                out.println("<a href='student_login.html'><button style='padding:10px 20px; background:#558b2f; color:white; border:none; border-radius:5px;'>Try Again</button></a>");
                out.println("</div>");
            }

        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
