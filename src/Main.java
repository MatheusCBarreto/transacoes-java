import db.DB;
import db.DBException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        Connection connection = null;
        Statement st = null;

        try {
            connection = DB.getConnection();

            connection.setAutoCommit(false);

            st = connection.createStatement();

            int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE departmentId = 1");

            int x = 1;
            if(x < 2) {
                throw new SQLException("Fake error");
            }

            int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE departmentId = 2");

            connection.commit();

            System.out.println("Rows1 " + rows1);
            System.out.println("Rows2 " + rows2);


        } catch(SQLException e) {
            try {
                connection.rollback();
                throw new DBException("Transaction rolled back! Caused by " + e.getMessage());
            } catch (SQLException e1) {
                throw new DBException("Error trying to rollback! Caused by " + e1.getMessage());
            }

        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }

    }
}