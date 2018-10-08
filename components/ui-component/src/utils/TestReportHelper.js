/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * ClassName: TestReportHelper
 *
 * Responsible for calculating result summary and statistics when
 * a report is given.
 *
 */
export default class TestReportHelper {
    /**
     * Gives the Pass/Fail count of the whole test plan
     * @param {object} testData - JSON containing results
     * @returns {object} results - JSON with passed/failed count and pass-rate
     */
    getTestSummary(testData) {
        const results = { passed: 0, failed: 0, rate: 0 };
        let curResult;
        for (const api of Object.keys(testData)) {
            curResult = this.getAPIResult(testData[api]);
            results.passed += curResult.passed;
            results.failed += curResult.failed;
        }
        if (results.passed + results.failed === 0) {
            results.rate = 0;
        } else {
            results.rate = (100 * (results.passed / (results.passed + results.failed))).toFixed(2);
        }
        return results;
    }

    /**
     * Gives the Pass/Fail feature count of the API
     * @param {object} api - JSON containing api results
     * @returns {object} results - JSON with passed/failed feature count
     */
    getAPIResult(api) {
        const results = { passed: 0, failed: 0 };
        let curResult;
        api.forEach((feature) => {
            curResult = this.getFeatureResult(feature);
            results.passed += (curResult.failed === 0) * 1;
            results.failed += (curResult.failed > 0) * 1;
        });
        return results;
    }

    /**
     * Gives the Pass/Fail scenario count of the feature
     * @param {object} feature - JSON containing feature results
     * @returns {object} results - JSON with passed/failed scenario count
     */
    getFeatureResult(feature) {
        const results = { passed: 0, failed: 0 };
        feature.elements.forEach((scenario) => {
            if (this.getScenarioResult(scenario)) {
                results.passed += 1;
            } else {
                results.failed += 1;
            }
        });
        return results;
    }

    /**
     * Gives the Pass/Fail status of the scenario (based on step results)
     * @param {object} scenario - JSON containing scenario results
     * @returns {object} results - JSON with passed/failed step count
     */
    getScenarioResult(scenario) {
        let status = true;
        scenario.steps.forEach((step) => {
            status = status && (this.getStepResult(step) === 'passed');
        });
        return status;
    }

    /**
     * Gives the Pass/Fail status of a single step
     * @param {object} step - JSON containing step
     * @returns {string} status - 'passed' or 'failed'
     */
    getStepResult(step) {
        return step.result.status;
    }

    /**
     * Gives the Pass/Fail status of a single feature (based on scenario results)
     * @param {object} feature - JSON containing feature results
     * @returns {string} status - 'Passed' or 'Failed'
     */
    getFeatureResultStatus(feature) {
        const result = this.getFeatureResult(feature);
        if (result.failed === 0) {
            return { class: 'passed-feature', status: 'Passed' };
        }
        return { class: 'failed-feature', status: 'Failed' };
    }

    /* Number of features to be tested in the test plan */
    /**
     * Gives the Number of features to be tested in the test plan
     * @param {object} testPlan - JSON containing the test plan
     * @returns {number} count - Number of features
     */
    getFeatureCount(testPlan) {
        let count = 0;
        for (const api of Object.keys(testPlan.specifications)) {
            count += Object.keys(testPlan.specifications[api].features).length;
        }
        return count;
    }
}
