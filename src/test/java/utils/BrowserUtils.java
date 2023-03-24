package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BrowserUtils {
    private BrowserUtils(){};
    private static WebDriver driver = null;

    public static WebDriver getDriver(){
        if (driver == null)
        {
            if (ConfigReader.readProperty("config.properties","runInSaucelabs")
                    .equalsIgnoreCase("true"))
            {
                getRemoteDriver();
            }
            else
            {
                initializeDriver("chrome");
            }
        }
        return driver;
    }

    public static void closeDriver(){
        driver.close();
        driver = null;
    }
    public static void quitDriver()
    {
        if (driver != null)
        {
            driver.quit();
            driver = null;
        }
    }

    private static void initializeDriver(String browser){
        switch(browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfigReader.readProperty("config.properties","url"));
    }

    private static void getRemoteDriver()
    {
        String sauceKey = "f067bea4-9e79-4389-bf6a-cdfb2f0e97fa";
        String sauceUsername = "oauth-paulppath-5a30d";
        String url = "https://" + sauceUsername + ":" + sauceKey + "@ondemand.us-west-1.saucelabs.com:443/wd/hub";

        try
        {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("name", "Home Scenario");
            capabilities.setCapability("build", "Home Build");
            capabilities.setCapability("version", "111");
            capabilities.setCapability("platform", "Windows 11");
            driver = new RemoteWebDriver(new URL(url), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(ConfigReader.readProperty("config.properties","url"));
    }

    public static void switchToNewWindow(){
        for(String each: driver.getWindowHandles()){
            if(!each.equalsIgnoreCase(driver.getWindowHandle())) {
                System.out.println(driver.getTitle());
                System.out.println(driver.getCurrentUrl());
                driver.switchTo().window(each);
            }
        }
    }
    public static void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 2; i++) {
            try {
                if (i % 2 == 0) {
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black;" +
                            "border: 3px solid red; background: yellow");
                    //TODO: apply report screenshot here
                    sleep(200);
                    CucumberLogUtils.logInfo("clicked on " +  element.toString(), false);
                } else {
                    sleep(600);
                    js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void waitForElementClickability(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public static void waitForElementVisibility(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void moveIntoView(WebElement element){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static void sendKeys(WebElement element, String inputText){
        //TODO: apply report -> logInfo("Entered the text ", element);
        waitForElementVisibility(element);
        moveIntoView(element);
        highlightElement(element);
        element.sendKeys(inputText);
    }
    public static String getText(WebElement element){
        //TODO: apply report -> logInfo("Retrieved the text ", element);
        waitForElementVisibility(element);
        moveIntoView(element);
        highlightElement(element);
        return element.getText();
    }
    public static void click(WebElement element){
        //TODO: apply report -> logInfo("clicked the button ", element);
        waitForElementClickability(element);
        moveIntoView(element);
        highlightElement(element);
        element.click();
    }
    public static void assertEquals(String actual, String expected){
        //TODO: apply report -> logInfo("Actual: " + actual);
        CucumberLogUtils.logInfo("Actual: " + actual, false);
        //TODO: apply report -> logInfo("Expected: " + expected);
        CucumberLogUtils.logInfo("Expected: " + expected, false);
        Assert.assertEquals(expected, actual);
    }
    public static void assertFalse(boolean result){
        //TODO: apply report -> logInfo("Expected: " + result);
        CucumberLogUtils.logInfo("Expected " + result, false);
        Assert.assertFalse(result);
    }
    public static void assertTrue(boolean result){
        //TODO: apply report -> logInfo("Expected: " + result);
        CucumberLogUtils.logInfo("Expected " + result, false);
        Assert.assertTrue(result);
    }
    public static void isDisplayed(WebElement element){
        waitForElementVisibility(element);
        moveIntoView(element);
        highlightElement(element);
        Assert.assertTrue(element.isDisplayed());
    }
    public static boolean isEnabled(WebElement element){
        waitForElementClickability(element);
        moveIntoView(element);
        highlightElement(element);
        return element.isEnabled();
    }
    public static boolean isDisabled(WebElement element){
        moveIntoView(element);
        highlightElement(element);

        if(element.isEnabled()){
            return false;
        }else {
            return true;
        }
    }

    public static void selectByVisibleText(WebElement element, String text){
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }
}
