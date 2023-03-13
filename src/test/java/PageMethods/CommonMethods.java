package PageMethods;

import CommonClasses.AbstractPageObject;
import CommonClasses.BaseMethods;
import CommonClasses.Property;
import com.cucumber.listener.Reporter;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.net.InetAddress;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class CommonMethods extends AbstractPageObject {

    // Locators
    private static By errorMessage = By.xpath("//div//div[contains(text(),'Error')]//parent::div/following::div[contains(@class,'message')]");
    public static By warningMessage = By.xpath("//div//div[contains(text(),'Warning')]//parent::div/following::div[contains(@class,'message')]");
    public static By warningAlertMessage = By.xpath("(//div[contains(text(),'Outstanding errors or warnings exist. Statement Signing cannot be progressed')])");
    private static By successMessage = By.xpath("//div//div[contains(text(),'Success')]//parent::div/following::div[contains(@class,'message')]");
    private static By saveHeaderSuccessMessage = By.id("save-statement-header-success");
    private static By notificationCloseButton = By.xpath("//div[contains(@class,'notification')]//button[@class='close']");
    private static By dxcLoading = By.xpath("//div[contains(@class,'loading')]");
    private static By fieldLoading = By.cssSelector(".field_loading");
    private static final By _headerLogoSelector = By.xpath("//*[@class='header__logo']");
    private static By _alertMenu = By.xpath("//dxc-alert-menu");
    private static By _alertsErrors = By.xpath("//div[contains(text(), 'Error')]//parent::div//div[contains(@class,'message')]");


    // Elements
    public static List<WebElement> AlertsErrors() {
        return driver.findElements(_alertsErrors);
    }

    public static WebElement ErrorMessage() {
        return driver.findElement(errorMessage);
    }

    public static WebElement SuccessMessage() {
        return driver.findElement(successMessage);
    }

    public static WebElement WarningMessage() {
        return driver.findElement(warningMessage);
    }
    public static WebElement HeaderLogo() {
        return driver.findElement(_headerLogoSelector);
    }

    public static void TheUserHasLaunchedTheNopCommerceApplication() throws Exception {
        BaseMethods.launchBrowser();
        System.out.println("Base URL " + Property.getProperty("BaseURL"));
        for (int i = 0; i < 10; i++) {
            try {
                driver.get(Property.getProperty("BaseURL"));
                HomePageMethods homePageMethods = new HomePageMethods();
                BaseMethods.WaitForElementVisible(homePageMethods.PageHeaderLogo());
            }
            catch (TimeoutException | NoSuchElementException e)
            {
                System.out.println("Site loading issue (local network) " + e.getMessage());
            }
        }
        BaseMethods.WaitUntilPageDisplayed();
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("ActualTitle is:  " + driver.getTitle());
        String computername = InetAddress.getLocalHost().getHostName();
        System.out.println("computername is:  " + computername);
    }

    public static void AddLastErrorFromAlertMenuToStepLog() {
        try {
            if (AlertsErrors().size() > 0)
                Reporter.addStepLog("Last Error From Alert Menu: " + BaseMethods.GetElementText(AlertsErrors().get(AlertsErrors().size() - 1)));
        } catch (NullPointerException e) {
            System.out.println("No Report exist");
        }
    }

}



