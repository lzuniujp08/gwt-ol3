/*******************************************************************************
 * Copyright 2014, 2018 gwt-ol3
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ol;

import com.github.desjardins.gwt.junit.client.BaseTestCase;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tino Desjardins
 *
 */
public abstract class GwtOLBaseTestCase extends BaseTestCase {

    private static Set<String> loadedScriptUrls = new HashSet<>();

    public GwtOLBaseTestCase() {
        super("", "ol.GwtOLTest", 10000);
    }

    @Override
    protected void injectUrlAndTest(TestWithInjection testWithInjection) {

        if (scriptAlreadyLoaded()) {
            testWithInjection.test();
        } else {

            this.delayTestFinish(this.testDelay);


            ScriptInjector.fromUrl(this.getJsUrl()).setWindow(ScriptInjector.TOP_WINDOW).setCallback(new Callback<Void, Exception>() {

                @Override
                public void onFailure(Exception reason) {
                    assertNotNull(reason);
                    fail("Injection failed: " + reason.toString());
                }

                @Override
                public void onSuccess(Void result) {
                    loadedScriptUrls.add(scriptUrl);
                    testWithInjection.test();
                    finishTest();
                }

            }).inject();

        }

    }

    private String getJsUrl() {
        return GWT.getModuleBaseURL() + "ol.js";
    }

}
