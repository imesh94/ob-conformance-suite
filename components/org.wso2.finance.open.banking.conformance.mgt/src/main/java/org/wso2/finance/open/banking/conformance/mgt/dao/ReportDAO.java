package org.wso2.finance.open.banking.conformance.api.dao;

import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.util.List;

public interface ReportDAO {
    public void storeReport(String userID, String uuid, Report report);
    public Report getReport(String userID, String uuid, Integer reportID);
    public List<Report> getReports(String userID, String uuid);
}
