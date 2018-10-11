package org.wso2.finance.open.banking.conformance.mgt.dao;

import com.google.gson.Gson;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReportDAOImpl implements ReportDAO{

    @Override
    public void storeReport(String userID, String uuid, int reportID, Report report) {
        Gson gson = new Gson();
        Connection conn = DBConnector.getConnection();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);


        String reportJson = gson.toJson(report);
        PreparedStatement stmt = null;
        try {
            // Execute query
            String sql =  "INSERT INTO Report VALUES  (?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reportID);
            stmt.setString(2, uuid);
            stmt.setString(3, userID);
            stmt.setString(4, reportJson);
            stmt.setString(5, currentTime);
            stmt.executeUpdate();
            System.out.println("Report data Added to DB........");

            // Clean-up
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        System.out.println("Goodbye from REPORT-DAO!");

    }

    @Override
    public Report getReport(String userID, String uuid, Integer reportID) {
        return null;
    }

    @Override
    public List<Report> getReports(String userID, String uuid) {
        return null;
    }
}
