package step_definitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.BrowserUtils;
import utils.CucumberLogUtils;

public class Hooks
{
    @Before
    public void setUp(Scenario scenario)
    {
//        System.out.println("----setting up browser");
        BrowserUtils.getDriver();
        CucumberLogUtils.initScenario(scenario);
    }

    @After
    public void tearDown(Scenario scenario)
    {
        if(scenario.isFailed())
        {
            CucumberLogUtils.logFail(scenario.getName(),true);
        }
//        System.out.println("----closing the browser");
        BrowserUtils.quitDriver();
    }
}
