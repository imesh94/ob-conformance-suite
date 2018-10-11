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

import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import SpecificationSelectView from './views/SpecificationSelectView';
import TestConfigurationView from './views/TestConfigurationView';
import TestHistoryView from './views/TestHistoryView';
import TestReportView from './views/TestReportView';
import LoginView from './views/LoginView';
import rootReducer from './reducers';
import CommonDataLoader from './components/CommonDataLoader';

const store = createStore(rootReducer);

const App = () => (
    <Provider store={store}>
        <CommonDataLoader>
            <Switch>
                <Route exact path='/' component={LoginView} />
                <Route exact path='/dashboard' component={TestHistoryView} />
                <Route exact path='/tests/new' component={SpecificationSelectView} />
                <Route exact path='/tests/new/configure' component={TestConfigurationView} />
                <Route exact path='/tests/report/:uuid/:revision' component={TestReportView} />
            </Switch>
        </CommonDataLoader>
    </Provider>
);

export default App;
