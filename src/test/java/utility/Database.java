package utility;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private Connection conn;

    //Refine the method for setting parameters to make it more versatile for testing various data types.
    private void setPreparedStatementParameters(PreparedStatement pstmt, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            pstmt.setObject(i + 1, params.get(i));
        }
    }


    // Method for connecting to the database
    public void connect(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method for disconnecting from the database
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method for executing a SELECT statement
    public ResultSet select(String sql) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    // Method for executing a SELECT prepared statement
    public ResultSet select(String sql, List<Object> params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i) instanceof String) {
                pstmt.setString(i + 1, (String) params.get(i));
            } else if (params.get(i) instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) params.get(i));
            }
        }
        return pstmt.executeQuery();
    }

    // Method for executing an INSERT, UPDATE, or DELETE statement
    public int execute(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    // Method for executing an INSERT, UPDATE, or DELETE prepared statement
    public int execute(String sql, List<Object> params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i) instanceof String) {
                pstmt.setString(i + 1, (String) params.get(i));
            } else if (params.get(i) instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) params.get(i));
            }
        }
        return pstmt.executeUpdate();
    }

    //Methods to verify the data in the database. This might involve fetching data based
    // on certain criteria to ensure that the application is correctly manipulating the data.
    public boolean verifyData(String sql, List<Object> expectedValues) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false; // No Rows
            }
            int i = 1;
            for (Object expected : expectedValues) {
                Object actual = rs.getObject(i++);
                if (!expected.equals(actual)) {
                    return false; // Mismatch
                }
            }
            return !rs.next(); // Passes if exactly one row matches
        }
    }

    //Useful for tests that need to assert the state of a single row in the database.
    public Map<String, Object> fetchSingleRow(String sql) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                return row;
            }
            return null; // Or throw if a row is expected
        }
    }

    //Utilities to insert test data before test execution. This could be an extension of your existing
    // execute method but designed to insert multiple rows easily, which can be useful for setting up
    // complex test scenarios.
    public void insertTestData(String tableName, List<Map<String, Object>> rows) throws SQLException {
        if (rows.isEmpty()) return;

        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder placeholders = new StringBuilder(" VALUES (");
        Map<String, Object> firstRow = rows.get(0);
        for (String column : firstRow.keySet()) {
            sql.append(column).append(",");
            placeholders.append("?,");
        }
        sql.deleteCharAt(sql.length() - 1).append(")");
        placeholders.deleteCharAt(placeholders.length() - 1).append(")");

        sql.append(placeholders);

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (Map<String, Object> row : rows) {
                int i = 1;
                for (Object value : row.values()) {
                    pstmt.setObject(i++, value);
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }



}
