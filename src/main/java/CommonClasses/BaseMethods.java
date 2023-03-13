package CommonClasses;

import cucumber.api.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static org.openqa.selenium.logging.LogType.BROWSER;

//import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;
//import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;

public class BaseMethods extends AbstractPageObject {
    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    static String timeStamp = null;

    public static WebDriver launchBrowser() throws Exception {
        System.out.println("BaseMethods.launchBrowser: Start");
        String Browser_Config = Property.getProperty("Browser");

        try {
            if (Browser_Config.equalsIgnoreCase("chrome")) {
                driver = getLocalWebDriver();
            } else {
                throw new Exception("enter valid browser name");
            }
            System.out.println("BaseMethods.launchBrowser: End");
            return driver;
        } catch (Exception e) {
            System.out.println("ERROR: BaseMethods.launchBrowser: Error: Failed to open browser throwing exception !");
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private static WebDriver getLocalWebDriver() throws UnknownHostException {
        //        browser logs
        LoggingPreferences preferences = new LoggingPreferences();
        preferences.enable(LogType.PERFORMANCE, Level.ALL);
        preferences.enable(LogType.BROWSER, Level.ALL);

        System.out.println("BaseMethods.getLocalWebDriver: Start");

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //        browser logs
        options.setCapability(CapabilityType.LOGGING_PREFS, preferences);
        options.setCapability("goog:loggingPrefs", preferences);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--start-maximized");
        options.addArguments("enable-automation");
//        options.addArguments("--headless");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS).pageLoadTimeout(30, TimeUnit.SECONDS).setScriptTimeout(5, TimeUnit.MINUTES);
        System.out.println("BaseMethods.getLocalWebDriver: End");
        return driver;
    }

    public static void PageRefresh() {
        WaitUntilPageDisplayed();
        driver.navigate().refresh();
    }

    public static void DoubleClick(WebElement element) {
        Actions act = new Actions(driver);
        act.doubleClick(element).perform();
    }

    public static boolean ElementIsEnabled(WebElement element) {
        Boolean IsEnabled;
        IsEnabled = element.isEnabled();
        return IsEnabled;
    }

    public static void WaitForPresenceOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void WaitForElementClickable(WebElement visibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(visibleElement));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("WaitForElementClickable exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static void WaitForElementNotClickable(WebElement visibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(visibleElement)));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("WaitForElementVisible exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static void WaitForElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void WaitForinvisibilityOfElementLocated(By locator) throws InterruptedException {
        System.out.println("WaitForinvisibilityOfElementLocated: Start");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30L))
                .pollingEvery(Duration.ofSeconds(3L))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        System.out.println("WaitForinvisibilityOfElementLocated: End");
    }

    public static void WaitForvisibilityOfElementLocated(By locator) {
        System.out.println("WaitForvisibilityOfElementLocated: Start");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30L))
                .pollingEvery(Duration.ofSeconds(3L))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        System.out.println("WaitForvisibilityOfElementLocated: End");
    }

    public static void WaitForNumberOfElementsToBe(By locator, int num) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, num));
    }

    public static boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                e.printStackTrace();
            }
            attempts++;
        }
        return result;
    }

    public static void WaitUntilPageDisplayed() {
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static void ClickOnPageBody() {
        DoubleClick(driver.findElement(By.tagName("Body")));
    }

    public static void CheckBrowserAlertIsDisplayed() {
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("BaseMethods.CheckBrowserAlertIsDisplayed: " + alert.getText() + " :Alert is Displayed");
        } catch (NoAlertPresentException ex) {
            System.out.println("BaseMethods.CheckBrowserAlertIsDisplayed: Alert is NOT Displayed");
        }
    }

    public static void CloseBrowserAlert() throws InterruptedException {
        System.out.println("BaseMethods.closeBrowserAlert: Start: " + System.nanoTime());
        Thread.sleep(3000);
        driver.switchTo().alert().accept();
        System.out.println("BaseMethods.closeBrowserAlert: End: " + System.nanoTime());
    }

    //this handle close the browser instead of driver.close as it's make a force close
    public static void handleCloseTheBrowserToCheckBrowserAlerts() throws AWTException {
        System.out.println("BaseMethods.handleCloseTheBrowserToCheckBrowserAlerts: Start");
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_W);
        rb.keyRelease(KeyEvent.VK_W);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        System.out.println("BaseMethods.handleCloseTheBrowserToCheckBrowserAlerts: End");
    }

    public static void CloseBrowserAlertIfAppeared() {
        UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMin"));
        try {
            WaitForAlertIsPresent();
            CloseBrowserAlert();
        } catch (Exception e) {
            System.out.println("alert did not appear " + e);
        }
        UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMax"));

    }

    public static void WaitUntilTextOfElementIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(AbstractPageObject.driver, 30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='statement-status' and contains(@ng-reflect-model, 'Work in Progress')]"))));
    }

    public static void WaitUntilTextOfElementIsForAgreementDisplayed() {
        WebDriverWait wait = new WebDriverWait(AbstractPageObject.driver, 30);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//li[contains(text(), 'For Agreement')]"))));
    }

    public static void WaitPresenceOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void WaitStalenessOf(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
    }

    public static void WaitForAlertIsPresent() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public static void WaitForElementVisible(WebElement VisibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.visibilityOf(VisibleElement));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("WaitForElementVisible exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static void WaitForElementPresent(By VisibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(VisibleElement));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("WaitForElementVisible exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static void WaitForElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("WaitForElementVisible exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static void WaitForNumberOfElementsToBeZero(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, 0));
    }

    public static void WaitForElementDisabled(WebElement Element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeContains(Element, "disabled", "true"));
    }

    public static void WaitForElementEnabled(WebElement Element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.attributeContains(Element, "disabled", "false"));
    }

    public static void WaitForElementRefreshedClickable(WebElement VisibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(VisibleElement));
    }

    public static void WaitForElementRefreshedVisible(WebElement VisibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(VisibleElement));
    }

    public static void WaitForInvisibilityOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("element is already hidden");
        }
    }

    public static void WaitForRefreshedInvisibilityOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void WaitForVisibilityOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static void WaitForRefreshedVisibilityOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static void WaitForLoadingIcon() {
        BaseMethods.WaitForRefreshedVisibilityOfElementLocated(By.xpath("//div[@class='loading__icon']"));
        BaseMethods.WaitForRefreshedInvisibilityOfElementLocated(By.xpath("//div[@class='loading__icon']"));
    }

    public static void WaitForElementText(By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBe(locator, text));
    }

    public static void WaitForTextToBePresentInElement(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static void WaitForTextToBePresentInElementValue(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
    }

    public static void WaitForURLContains(String urlKeyword) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.urlContains(urlKeyword));
    }

    public static void WaitForElementInVisible(WebElement InVisibleElement) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOf(InVisibleElement));
    }

    public static void WaitUntilTextElementCalculated(WebElement VisibleElement) {
        (new WebDriverWait(driver, 3)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return VisibleElement.getText().length() != 0;
            }
        });
    }

    public static void UpdateImplicitlyWait(String seconds) {
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(seconds), TimeUnit.SECONDS);
        System.out.println("UpdateImplicitlyWait" + seconds);
    }

    public static void UpdateImplicitlyWaitToMin() {
        UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMin"));
    }

    public static void UpdateImplicitlyWaitToMax() {
        UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMax"));
    }

    public static void loadingWait(WebElement loader) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(loader)); // wait for loader to appear
        wait.until(ExpectedConditions.invisibilityOf(loader)); // wait for loader to disappear
    }

    public static boolean ElementIsDisplayed(By locator) {
        try {
            driver.findElement(locator);
            return true;

        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public static boolean ElementIsVisable(WebElement locator) {
        try {
            locator.isDisplayed();
            return true;

        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public static void Click_Element(By element_locator) {
        for (int i = 0; i < 5; i++) {
            try {
                WaitUntilPageDisplayed();
                WaitForElementVisible(driver.findElement(element_locator));
                WaitForElementClickable(driver.findElement(element_locator));
                WebElement element = driver.findElement(element_locator);
                element.click();
                System.out.println("Click on : " + element_locator);
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                System.out.println("Click_Element exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static void Click_Element(WebElement element) {
        for (int i = 0; i < 5; i++) {
            try {
                WaitUntilPageDisplayed();
                WaitForElementVisible(element);
                WaitForElementClickable(element);
                element.click();
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                System.out.println("Click_Element exception: " + i + " " + e.getMessage());
            }
        }
        System.out.println("Click on : " + element);
    }

    public static void Click_Element_JS(By element_locator) {
        for (int i = 0; i < 5; i++) {
            try {
                WaitUntilPageDisplayed();
                WaitForElementVisible(element_locator);
                WaitForElementClickable(element_locator);
                WebElement element = driver.findElement(element_locator);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                System.out.println("Click_Element_JS exception : " + i + " " + e.getMessage());
            }
        }
        System.out.println("Click on : " + element_locator);
    }

    public static void Click_Element_JS(WebElement element) {
        for (int i = 0; i < 5; i++) {
            try {
                WaitUntilPageDisplayed();
                WaitForElementVisible(element);
                WaitForElementClickable(element);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                System.out.println("Click_Element_JS exception : " + i + " " + e.getMessage());
            }
        }
        System.out.println("Click on : " + element);
    }

    public static void ClickElement_ActionsClass(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click().build().perform();
    }

    public static void Type(By element_locator, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(driver.findElement(element_locator));
        WaitForElementClickable(driver.findElement(element_locator));
        driver.findElement(element_locator).clear();
        driver.findElement(element_locator).sendKeys(value);
        driver.findElement(element_locator).sendKeys(Keys.TAB);
        System.out.println("SendKeys: " + "Element: " + element_locator + " value: " + value);
    }

    public static void BackToPreviousPage() {
        driver.navigate().back();
    }

    public static void Type(WebElement element, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(element);
        WaitForElementClickable(element);
        element.clear();
        element.sendKeys(value);
        element.sendKeys(Keys.TAB);
        System.out.println("SendKeys: " + "Element: " + element + " value: " + value);

    }

    public static void enterValue(WebElement element, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(element);
        WaitForElementClickable(element);
        element.clear();
        element.sendKeys(value);
        System.out.println("SendKeys: " + "Element: " + element + " value: " + value);
    }


    public static void TypeIntoDropDown(WebElement element, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(element);
        WaitForElementClickable(element);
        element.sendKeys(value);
    }

    public static void SelectFromDropDown(WebElement element, String Option) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(element);
        WaitForElementClickable(element);
        Click_Element(element);
        Select value = new Select(element);
        value.selectByVisibleText(Option);
    }

    public static void TypeUsingJS(By element_locator, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(driver.findElement(element_locator));
        WaitForElementClickable(driver.findElement(element_locator));
        driver.findElement(element_locator).clear();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", driver.findElement(element_locator));
        driver.findElement(element_locator).sendKeys(Keys.TAB);
        System.out.println("SendKeys: " + "Locator: " + element_locator + " value: " + value);

    }

    public static void TypeUsingJS(WebElement element, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(element);
        WaitForElementClickable(element);
        element.clear();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", element);
        element.sendKeys(Keys.TAB);
        System.out.println("SendKeys: " + "Element: " + element + " value: " + value);
    }

    public static void EnterTabButton() {
        Actions act = new Actions(driver);
        act.sendKeys(Keys.TAB).build().perform();
        System.out.println("EnterTabButton ");
    }

    public static void EnterTabButton(WebElement element) {
        element.sendKeys(Keys.TAB);
        System.out.println("EnterTabButton ");
    }

    public static void EnterEnterButton() {
        Actions act = new Actions(driver);
        act.sendKeys(Keys.ENTER).build().perform();
        System.out.println("EnterEnterButton ");
    }

    public static void EnterEnterButtonUsingRobot() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void EnterEnterButtonUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        element.sendKeys(Keys.ENTER);
    }

    public static void EnterEnterButton(WebElement element) {
        element.sendKeys(Keys.ENTER);
        System.out.println("EnterEnterButton " + element);
    }

    public static void DeleteElementValue(By element_locator) {
        WaitForElementVisible(driver.findElement(element_locator));
        driver.findElement(element_locator).sendKeys(Keys.CONTROL + "a");
        driver.findElement(element_locator).sendKeys(Keys.DELETE);
        driver.findElement(element_locator).sendKeys(Keys.TAB);
    }

    public static void DeleteElementValue(WebElement element) {
        WaitForElementVisible(element);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.TAB);
    }

    public static void clearElementValueWithoutClickingTabBtn(WebElement element) {
        WaitForElementVisible(element);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    public static void DeleteElementValueUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '';", element);
    }

    public static String GetInputFieldValue(WebElement element) {
        String value = element.getAttribute("value");
        System.out.println("GetInputFieldValue: " + "Element: " + element + " value: " + value);
        return value;
    }

    public static String GetElementTitle(WebElement element) {
        String value = element.getAttribute("title");
        System.out.println("GetElementTitle: " + "Element: " + element + " title: " + value);
        return value;
    }

    public static String GetElementText(WebElement element) {
        String text = "";
        for (int i = 0; i < 5; i++) {
            try {
                text = element.getText();
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("GetElementText exception: " + i + " " + e.getMessage());
            }
        }

        System.out.println("GetElementText: " + "Element: " + element + " text: " + text);
        return text;
    }

    public static String GetInputFieldPlaceholder(WebElement element) {
        String placeholder = element.getAttribute("placeholder");
        System.out.println("GetInputFieldPlaceholder: " + "Element: " + element + " placeholder: " + placeholder);
        return placeholder;
    }


    public static void SendKeysUsingJS(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", element);
        element.sendKeys(Keys.TAB);
        System.out.println("SendKeys: " + "Element: " + element + " value: " + value);

    }

    public static void SendKeys(WebElement element, String value) {
        WaitUntilPageDisplayed();
        WaitForElementVisible(element);
        WaitForElementClickable(element);
        element.sendKeys(value);
        System.out.println("SendKeys: " + "Element: " + element + " value: " + value);
    }

    public static void ScrollToBottomOfPage() {
        System.out.println("ScrollToBottomOfPage: Start");
        BaseMethods.UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMin"));
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, document.body.scrollHeight)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseMethods.UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMax"));
        System.out.println("ScrollToBottomOfPage: End");
    }

    public static void ScrollToUp() {
        System.out.println("ScrollToUpOfPage: Start");
        BaseMethods.UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMin"));
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("window.scrollTo(0, -350)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseMethods.UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMax"));
        System.out.println("ScrollToUpOfPage: End");
    }

    public static void ScrolltoElement(WebElement element) throws InterruptedException {
        UpdateImplicitlyWaitToMin();
        for (int i = 0; i < 5; i++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                Thread.sleep(500);
                break;
            } catch (InterruptedException | StaleElementReferenceException e) {
                System.out.println("ScrolltoElement exception: " + i + " " + e.getMessage());
            }
        }
        UpdateImplicitlyWaitToMax();
    }

    public static void ScrolltoElement(By locator) throws InterruptedException {
        UpdateImplicitlyWaitToMin();
        for (int i = 0; i < 5; i++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
                Thread.sleep(500);
                break;
            } catch (NoSuchElementException | InterruptedException | StaleElementReferenceException e) {
                System.out.println("ScrolltoElement exception: " + i + " " + e.getMessage());
            }
        }
        UpdateImplicitlyWaitToMax();
    }

    // Drag and drop Method
    public static void DragAndDrop(WebElement sourceLocator, WebElement targetLocator) {
        Actions action = new Actions(driver);
        action.dragAndDrop(sourceLocator, targetLocator).build().perform();
    }

    public static String getCurrentDay() {
        // Create a Calendar Object
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        // Get Current Day as a number
        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        // Integer to String Conversion
        String todayStr = Integer.toString(todayInt);
        return todayStr;
    }

    /// get current date with format dd-MM-yyyy
    public static String GetCurrentDate_format() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        String date = dtf.format(localDate);
        return date;
    }

    /// get current date with format dd-MM-yyyy HH:MM:SS
    public static String GetCurrentDateTime_format() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String CurrentDateAndTime_inUK = now.format(DATE_TIME_FORMATTER);
        return CurrentDateAndTime_inUK;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        String strDate = formDate.format(timestamp);
        return strDate;
    }

    public static String GetLastDayInTheYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String CurrentYear = String.valueOf(year);
        String LastDayInTheYearString = "31/12/" + CurrentYear;
        return LastDayInTheYearString;
    }

    /// get current date with format dd-MM-yyyy HH:MM:SS
    public static OffsetDateTime GetCurrentDateTime_formatUTC() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return now;
    }

    public static int presentationDate(String PresDate) {
        int date;
        switch (PresDate) {
            case "TodayDate":
                date = 0;
                break;
            case "YesterdayDate":
                date = -1;
                break;
            case "LessthanSixCurrentDate":
                date = -7;
                break;
            case "Lessthan14CurrentDate":
                date = -15;
                break;
            case "FutureDate":
                date = 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + PresDate);
        }
        return date;
    }

    //to Exclude the weekends
    private static Date DateCalc(int number) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, number);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.DATE, -2);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DATE, -1);
        } else {
            cal.add(Calendar.DATE, -0);
        }
        return cal.getTime();
    }

    public static String GetDateString(String presDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(DateCalc(presentationDate(presDate)));
    }

    public static String GetDateString(int number) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(DateCalc(number));
        return date;
    }

    public static String GetTodayDate() {
        String date = GetDateString(0);
        return date;
    }

    public static String getCurrentYear() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();
    }

    public static String GetTheLastDayInThisYear() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.with(lastDayOfYear());
        String lastDayInYear = dtf.format(lastDay);
        System.out.println(lastDayInYear);
        return lastDayInYear;
    }

    public static void SelectDateOfToday(WebElement Calender_Xpath) {
        String today;
        today = BaseMethods.getCurrentDay();
        int todayInt = Integer.parseInt(today);
        int value = 0;
        List<WebElement> columns = Calender_Xpath.findElements(By.tagName("td"));
        int size = columns.size();
        for (int i = 0; i < size; i++) {
            if (columns.get(i).getText().equals(today) && (todayInt > 24)) {
                value = i;
            } else if (columns.get(i).getText().equals(today) && (todayInt <= 24)) {
                value = i;
                break;
            }
        }
        columns.get(value).click();
    }

    public static boolean isStringUpperCase(String str) {
        //convert String to char array
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            //if any character is not in upper case, return false
            if (!Character.isUpperCase(charArray[i]))
                return false;
        }
        return true;
    }

    public static void progStream(Scenario scenario) throws FileNotFoundException {
        Path dir = null;
        try {
            // create dir path
            dir = Paths.get(FileSystems.getDefault().getPath("").toAbsolutePath() + "/target/cucumberReports/logs/");
            if (!Files.exists(dir))
                Files.createDirectories(dir);
            System.out.println("scenario " + scenario.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception Msg : " + e.getMessage());
        }
        File file = new File(dir + "/" + scenario.getName() + System.currentTimeMillis() + ".txt");
        PrintStream console = new PrintStream(new FileOutputStream(file));
        System.out.println("Writing to " + file.getPath());
        System.setOut(console);
//        Format fmt = new Format();
//        fmt.console.println(getEntries());
    }

    public static List<LogEntry> getEntries() {
        return driver.manage().logs().get(BROWSER).getAll();
    }

    public static String getComputerName() throws UnknownHostException {
        String computerName = InetAddress.getLocalHost().getHostName();
        return computerName;
    }

    public static void ScrollUpOrDown(int i) {
        BaseMethods.UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMin"));
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,'" + i + "')");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BaseMethods.UpdateImplicitlyWait(Property.getProperty("ImplicitlyWaitMax"));
    }

    public static void CloseDriver() {
        System.out.println("BaseMethods.CloseDriver: Start");
        try {
            driver.close();
            driver.quit();
            System.out.println("Driver should be closed successfully");
            if (driver.toString().contains("null")) {
                System.out.println("All Browser windows are closed ");
            } else {
                System.out.println("Browser windows seems not closed ");
            }
        } catch (Exception e) {
            System.out.println("ERROR: BaseMethods.CloseDriver: Close driver exception " + e);
        }
        System.out.println("BaseMethods.CloseDriver: End");
    }

    public static void SwitchToChildWindow() {
        // windowHandles will store all the windows in the screen parent and child
        Set<String> windowHandles = driver.getWindowHandles();
        System.out.println(windowHandles);
        // iterator will iterate throught the set (parent and child windows)
        Iterator<String> iterator = windowHandles.iterator();
        //get the window handle for the parent window
        String parentWindow = iterator.next();
        //get the window handle for the child window
        String childWindow = iterator.next();
        driver.switchTo().window(childWindow);
        driver.switchTo().defaultContent();
    }

    public static String GetBrowserTitle() {
        return driver.getTitle();
    }

    public static void ScrollRightOnGrid(WebElement element) {
        element.click();
        Actions move = new Actions(driver);
        move.moveToElement(element).clickAndHold();
        move.moveByOffset(300, 0);
        move.release();
        move.perform();
    }

    public static void ScrollRightOnGrid(By locator) {
        driver.findElement(locator).click();
        Actions move = new Actions(driver);
        move.moveToElement(driver.findElement(locator)).clickAndHold();
        move.moveByOffset(300, 0);
        move.release();
        move.perform();
    }

    public static void ScrollRightOnGridToEnd(By scrollAreaLocator) {
        WebElement scrollArea = driver.findElement(scrollAreaLocator);
        // Scroll to div's most right:
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollArea);
    }

    public static void ScrollLeftOnGridToEnd(By scrollAreaLocator) {
        WebElement scrollArea = driver.findElement(scrollAreaLocator);
        // Scroll to div's most left:
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollright = arguments[0].offsetWidth", scrollArea);
    }

    public static void ScrollLeftOnGrid(WebElement element) {
        element.click();
        Actions move = new Actions(driver);
        move.moveToElement(element).clickAndHold();
        move.moveByOffset(0, 300);
        move.release();
        move.perform();
    }

    public static Boolean InputFieldValueIsBlank(WebElement element) {
        System.out.println(GetInputFieldValue(element));
        if (GetInputFieldValue(element).equals(""))
            return true;
        else return false;
    }

    public static void ClickCheckBox(By locator) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread.sleep(500);
            try {
                driver.findElement(locator).click();
                break;
            } catch (Exception e) {
                System.out.println("ClickCheckBox exception: " + i + " " + e.getMessage());
            }
        }
    }

    public static WebElement findElement(By locator) {
        WebElement element = null;
        for (int i = 0; i < 10; i++) {
            try {
                element = driver.findElement(locator);
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("findElement exception: " + i + " " + e.getMessage());
            }
        }
        return element;
    }

    public static List<WebElement> findElements(By locator) {
        UpdateImplicitlyWaitToMin();
        List<WebElement> webElementList  = driver.findElements(locator);
        UpdateImplicitlyWaitToMax();
        return webElementList;
    }
}