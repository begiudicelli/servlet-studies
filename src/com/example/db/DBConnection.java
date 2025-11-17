package com.example.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DBConnection {

    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/xdb");
        } catch (NamingException e) {
            throw new RuntimeException("Falha ao localizar DataSource jdbc/xdb: " + e.getMessage(), e);
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
