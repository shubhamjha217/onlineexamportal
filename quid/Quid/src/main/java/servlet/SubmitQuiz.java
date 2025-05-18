package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SubmitQuiz")
public class SubmitQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try (Connection con = DBConnection.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");

            int total = 0;
            int correct = 0;

            while (rs.next()) {
                int qid = rs.getInt("id");
                String correctAnswer = rs.getString("correctOption").toLowerCase();
                String userAnswer = req.getParameter("q" + qid);

                if (userAnswer != null) {
                    total++;
                    if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        correct++;
                    }
                }
            }

            // Show result nicely with Logout and Home buttons
            out.println("<div style='font-family:sans-serif; background:#f3f3f3; padding:40px; text-align:center;'>");
            out.println("<h2 style='color:green;'>Quiz Submitted Successfully!</h2>");
            out.println("<h3>You scored " + correct + " out of " + total + "</h3>");
            
            out.println("<div style='margin-top:30px;'>");
            out.println("<a href='student.html'><button style='padding:10px 20px; margin:10px; background:#388e3c; color:white; border:none; border-radius:5px;'>Back to Student Home</button></a>");
            out.println("<a href='homepage.html'><button style='padding:10px 20px; margin:10px; background:#d32f2f; color:white; border:none; border-radius:5px;'>Logout</button></a>");
            out.println("</div>");
            out.println("</div>");
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}

