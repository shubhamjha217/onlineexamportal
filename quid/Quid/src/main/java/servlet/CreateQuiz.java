package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreateQuiz")
public class CreateQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");
        try (Connection con = DBConnection.getConnection()) {
            String question = req.getParameter("question");
            String a = req.getParameter("a");
            String b = req.getParameter("b");
            String c = req.getParameter("c");
            String d = req.getParameter("d");
            String correct = req.getParameter("correct");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO quizzes (question, optionA, optionB, optionC, optionD, correctOption) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, question);
            ps.setString(2, a);
            ps.setString(3, b);
            ps.setString(4, c);
            ps.setString(5, d);
            ps.setString(6, correct);
            ps.executeUpdate();

            // Success HTML with styled message and buttons
            String html = """
                <html>
                <head>
                  <title>Quiz Saved</title>
                </head>
                <body style="font-family:sans-serif; background:#f9f9f9; text-align:center; padding:50px;">
                  <div style="display:inline-block; padding:30px; border-radius:10px; background:#d4edda; color:#155724; box-shadow: 0 0 10px #28a745a0; margin-bottom:30px;">
                    <h2>🎉 Quiz saved successfully!</h2>
                  </div>
                  
                  <div style="display:flex; justify-content:center; gap:20px;">
                    <a href="quizmaker.html" 
                       style="text-decoration:none; background:#007bff; color:white; padding:15px 30px; border-radius:8px; font-weight:bold; box-shadow:0 4px 8px rgba(0,0,0,0.2); transition: background 0.3s;">
                       Create Another Quiz
                    </a>
                    
                    <a href="homepage.html" 
                       style="text-decoration:none; background:#dc3545; color:white; padding:15px 30px; border-radius:8px; font-weight:bold; box-shadow:0 4px 8px rgba(0,0,0,0.2); transition: background 0.3s;">
                       Logout
                    </a>
                  </div>
                </body>
                </html>
                """;

            res.getWriter().println(html);
        } catch (Exception e) {
            res.getWriter().println("<p style='color:red; font-weight:bold;'>Error: " + e.getMessage() + "</p>");
        }
    }
}

