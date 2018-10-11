package org.wso2.finance.open.banking.conformance.mgt.dao;

import org.wso2.finance.open.banking.conformance.mgt.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.testconfig.TestPlan;

import java.util.Map;

public interface TestPlanDAO {
    public void storeTestPlan(String userID, String uuid, TestPlan testPlan);
    public org.wso2.finance.open.banking.conformance.mgt.models.TestPlan getTestPlan(String userID, String uuid);
    public Map<String, TestPlanDTO> getTestPlans(String userID);
}
