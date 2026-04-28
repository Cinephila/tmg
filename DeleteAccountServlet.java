/* File: DeleteAccountServlet.java
   Package: com.tmg.servlets
   Note: configure JDBC datasource in your environment and update DB constants below.
*/
package com.tmg.servlets;

import java.io.IOException;
import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

@WebServlet("/delete-account")
public class DeleteAccountServlet extends HttpServlet {
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
        String username = req.getParameter("username");
        String reason = req.getParameter("reason");
        String confirm = req.getParameter("confirm");

        if (name == null || email == null || confirm == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
            return;
        }

        String insertSql = "INSERT INTO account_deletion_requests (name, email, username, reason, requested_at, status) VALUES (?, ?, ?, ?, NOW(), ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, username);
            ps.setString(4, reason);
            ps.setString(5, "PENDING");
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new ServletException("Database error", ex);
        }

        // Redirect to a confirmation page (simple approach)
        resp.sendRedirect(req.getContextPath() + "/delete-confirmation.html");
    }
}
