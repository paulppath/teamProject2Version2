package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BrowserUtils;

public class LogInPage
{
    public LogInPage()
    {
        PageFactory.initElements(BrowserUtils.getDriver(), this);
    }
    @FindBy(name = "email")
    public WebElement usernameField;

    @FindBy(name = "password")
    public WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement loginBtn;
}
