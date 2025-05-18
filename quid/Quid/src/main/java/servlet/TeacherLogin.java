package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/TeacherLogin")
public class TeacherLogin extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM teachers WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("teacherName", rs.getString("name"));
                res.sendRedirect("quizmaker.html");
            } else {
                res.getWriter().println("<h3 style='color:red; text-align:center;'>Invalid email or password</h3>");
            }
        } catch (Exception e) {
            res.getWriter().println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
