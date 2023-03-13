package StepClasses;

import CommonClasses.AbstractPageObject;
import CommonClasses.BaseMethods;
import CommonClasses.Helper;
import PageMethods.CommonMethods;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class StepsHooks extends AbstractPageObject {
    private static boolean prevScenarioFailed = false;
    private static String featureName = "null";

    @Before
    public void beforeScenario(Scenario scenario) throws UnknownHostException {
        System.out.println("StepsHooks.beforeScenario: Start " + scenario.getName());

        // Skip the rest of feature file scenarios in case one failed
        if (!featureName.equals(getFeatureFileNameFromScenarioId(scenario))) {
            prevScenarioFailed = false;
            featureName = getFeatureFileNameFromScenarioId(scenario);
        }
        if (prevScenarioFailed && !featureName.contains("Yogesh")) {
            throw new RuntimeException("Cucumber wants to quit.");
        }
        System.setProperty("build.profile.id","testEnv");
        System.out.println("StepsHooks.beforeScenario: End " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) throws IOException {
        System.out.println("StepsHooks.afterScenario: Start" + scenario.getName());
        prevScenarioFailed = scenario.isFailed();
        Helper.CreateScreenShotsDirectory();
        if (!"passed".equals(scenario.getStatus())) {
            if (!driver.toString().contains("null")) {
                CommonMethods.AddLastErrorFromAlertMenuToStepLog();
                Helper.PrintBrowserLogs();
                String SceenshotName = scenario.toString();
                Helper.CaptureScreenShot(SceenshotName);
                System.out.println("Page URL: " + driver.getCurrentUrl());
                System.out.println("Page Title:  " + driver.getTitle());
            } else {
                System.out.println("Driver is not available for CaptureScreenShot");
            }
        }
        BaseMethods.CloseDriver();

        System.out.println("Result: " + scenario.getStatus());


        System.out.println("StepsHooks.afterScenario: End: " + scenario.getName());

    }

    private String getFeatureFileNameFromScenarioId(Scenario scenario) {
        String featureName = "Feature ";
        String rawFeatureName = scenario.getId().split(";")[0].replace("-", " ");
        featureName = featureName + rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
        return featureName;
    }

}