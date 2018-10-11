package org.wso2.finance.open.banking.conformance.mgt.dao;

import com.google.gson.Gson;
import org.wso2.finance.open.banking.conformance.mgt.db.DBConnector;
import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPlanDAOImpl implements TestPlanDAO{

    @Override
    public void storeTestPlan(String userID, String uuid, TestPlan testPlan) {
        Gson gson = new Gson();
        Connection conn = DBConnector.getConnection();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);


        String testPlanJson = gson.toJson(testPlan);
        PreparedStatement stmt = null;
        try {
            // Execute query
            String sql =  "INSERT INTO TestPlan VALUES  (?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, uuid);
            stmt.setString(2, userID);
            stmt.setString(3, testPlanJson);
            stmt.setString(4, currentTime);
            stmt.executeUpdate();
            // System.out.println("Data Added to DB........");

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
        // System.out.println("Exit from storeTestPlan");
    }

    @Override
    public TestPlan getTestPlan(String userID, String uuid) {
        Gson gson = new Gson();
        TestPlan testPlan = new TestPlan();
        Connection conn = DBConnector.getConnection();
        Statement stmt = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            String sql =  "SELECT * FROM TestPlan WHERE userID='"+userID+"' AND testID='"+uuid+"'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String testPlanJson = rs.getString("testConfig");
                String creationTime = rs.getString("creationTime");
                testPlan = gson.fromJson(testPlanJson, TestPlan.class);

            }
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
        // System.out.println("Exit from getTestPlan");

        return testPlan;

    }

    @Override
    public Map<String, TestPlanDTO> getTestPlans(String userID) {
        Gson gson = new Gson();
        Map<String, TestPlanDTO> testPlans = new HashMap<String, TestPlanDTO>();
        Connection conn = DBConnector.getConnection();
        ReportDAO reportDAO = new ReportDAOImpl();
        List<Report> reports = null;
        Statement stmt = null;

        try {
            // Execute query
            stmt = conn.createStatement();
            String sql =  "SELECT * FROM TestPlan";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String uuid = rs.getString("testID");
                String testPlanJson = rs.getString("testConfig");
                String creationTime = rs.getString("creationTime");

                TestPlan testPlan = gson.fromJson(testPlanJson, TestPlan.class);
                reports = reportDAO.getReports("adminx", uuid);
                // System.out.println(reports.toString());
                TestPlanDTO testPlanDTO = new TestPlanDTO(uuid, testPlan, reports);
                testPlans.put(uuid, testPlanDTO);
               // System.out.println(testPlans.toString());
            }
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
        // System.out.println("Exit from getTestPlans");

        return testPlans;
    }
}
