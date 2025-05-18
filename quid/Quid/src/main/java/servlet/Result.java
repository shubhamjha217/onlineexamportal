package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/Result")
public class Result extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try (Connection con = DBConnection.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");

            int score = 0;
            while (rs.next()) {
                String selected = req.getParameter("q" + rs.getInt("id"));
                if (selected != null && selected.equals(rs.getString("correctOption"))) {
                    score++;
                }
            }

            res.getWriter().println("Your Score: " + score);
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
