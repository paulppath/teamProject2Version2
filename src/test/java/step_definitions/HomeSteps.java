package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.HomePage;
import pages.LogInPage;
import utils.BrowserUtils;
import utils.ConfigReader;

public class HomeSteps
{
    HomePage page;
    LogInPage loginPage;
    public HomeSteps()
    {
        page = new HomePage();
        loginPage = new LogInPage();
    }

    @Given("I enter {string}")
    public void i_enter_in(String inputString)
    {
        switch (inputString.toLowerCase())
        {
            case "username":
                BrowserUtils.sendKeys(loginPage.usernameField,
                        ConfigReader.readProperty("config.properties", inputString.toLowerCase()));
                break;
            case "password":
                BrowserUtils.sendKeys(loginPage.passwordField,
                        ConfigReader.readProperty("config.properties", inputString.toLowerCase()));
                break;
            default:
                Assert.fail("Invalid Field!");
        }
    }
    @When("I click on {string} button")
    public void i_click_on_button(String btn)
    {
        switch(btn.toLowerCase())
        {
            case "login":
                BrowserUtils.click(loginPage.loginBtn);
                break;
            default:
                Assert.fail("Invalid Button!");
        }

    }
    @Then("Verify that {string} is the title of the page")
    public void verify_that_is_the_title_of_the_page(String title)
    {
        BrowserUtils.assertEquals(BrowserUtils.getDriver().getTitle(), title);
    }


}
