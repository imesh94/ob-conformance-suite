package org.wso2.finance.open.banking.conformance.api.dao;

import org.wso2.finance.open.banking.conformance.mgt.dao.ReportDAO;
import org.wso2.finance.open.banking.conformance.mgt.models.Report;

import java.util.List;

public class H2ReportDAO implements ReportDAO {

    @Override
    public void storeReport(String userID, String uuid, Report report) {

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
