package servlet;

//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//@WebServlet("/TeacherRegister")
//public class TeacherRegister extends HttpServlet {
//    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        try (Connection con = DBConnection.getConnection()) {
//            String name = req.getParameter("name");
//            String email = req.getParameter("email");
//            String password = req.getParameter("password");
//
//            PreparedStatement ps = con.prepareStatement("INSERT INTO teachers (name, email, password) VALUES (?, ?, ?)");
//            ps.setString(1, name);
//            ps.setString(2, email);
//            ps.setString(3, password);
//            ps.executeUpdate();
//
//            res.getWriter().println("Teacher registered successfully.");
//        } catch (Exception e) {
//            res.getWriter().println("Error: " + e.getMessage());
//        }
//    }
//}



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TeacherRegister")
public class TeacherRegister extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/shubham", "root", "manager");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO teachers(name, email, password) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            int result = ps.executeUpdate();
            ps.close();
            con.close();

            if (result > 0) {
                response.sendRedirect("quizmaker.html");
            } else {
                response.getWriter().println("Registration failed. Try again.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
