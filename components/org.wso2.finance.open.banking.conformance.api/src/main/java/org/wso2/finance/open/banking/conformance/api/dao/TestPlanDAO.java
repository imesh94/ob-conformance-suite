package org.wso2.finance.open.banking.conformance.api.dao;

import org.wso2.finance.open.banking.conformance.api.dto.TestPlanDTO;
import org.wso2.finance.open.banking.conformance.mgt.models.TestPlan;

import java.util.Map;

public interface TestPlanDAO {
    public void storeTestPlan(String userID, String uuid, TestPlan testPlan);
    public TestPlan getTestPlan(String userID, String uuid);
    public Map<String, TestPlanDTO> getTestPlans(String userID);
}
