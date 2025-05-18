<%@ page import="java.sql.*, java.util.*, servlet.DBConnection" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Quiz</title>
</head>
<body style="font-family:sans-serif; background:#e8f5e9; padding:40px;">
    <div style="max-width:800px; margin:auto; background:white; padding:30px; border-radius:10px; box-shadow:0 4px 10px rgba(0,0,0,0.1);">
        <h2 style="color:#2e7d32;">Welcome to the Quiz</h2>
        <form action="SubmitQuiz" method="post">
            <%
                try {
                    Connection con = DBConnection.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");
                    int qno = 1;
                    while (rs.next()) {
            %>
                <div style="margin-bottom:20px;">
                    <p><strong>Q<%= qno %>:</strong> <%= rs.getString("question") %></p>
                    <input type="radio" name="q<%= rs.getInt("id") %>" value="a" required> <%= rs.getString("optionA") %><br>
                    <input type="radio" name="q<%= rs.getInt("id") %>" value="b"> <%= rs.getString("optionB") %><br>
                    <input type="radio" name="q<%= rs.getInt("id") %>" value="c"> <%= rs.getString("optionC") %><br>
                    <input type="radio" name="q<%= rs.getInt("id") %>" value="d"> <%= rs.getString("optionD") %><br>
                </div>
            <%
                        qno++;
                    }
                    con.close();
                } catch (Exception e) {
                    out.println("<p>Error loading quiz: " + e.getMessage() + "</p>");
                }
            %>
            <button type="submit" style="padding:10px 20px; background:#388e3c; color:white; border:none; border-radius:5px; font-size:16px;">Submit Quiz</button>
        </form>
    </div>
</body>
</html>
