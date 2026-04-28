/* File: ContactSubmitServlet.java
   Package: com.tmg.servlets
   Note: This stores contact messages and could send notification emails via configured SMTP.
*/
package com.tmg.servlets;

import java.io.IOException;
import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/contact-submit")
public class ContactSubmitServlet extends HttpServlet {
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/tmgdb");
        } catch (NamingException e) {
            throw new ServletException("Unable to find DataSource", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        if (name == null || email == null || subject == null || message == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
            return;
        }

        String insertSql = "INSERT INTO contact_messages (name, email, subject, message, submitted_at, status) VALUES (?, ?, ?, ?, NOW(), ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, subject);
            ps.setString(4, message);
            ps.setString(5, "NEW");
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ServletException("Database error", ex);
        }

        resp.sendRedirect(req.getContextPath() + "/contact-confirmation.html");
    }
}
