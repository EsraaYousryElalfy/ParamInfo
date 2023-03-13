package PageMethods;

import CommonClasses.AbstractPageObject;
import CommonClasses.BaseMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PackagesPageMethods extends AbstractPageObject {

    private By _SummaryHeaderLocator = By.xpath("//h3[text()='Order summary']");
    private By _PackageCostLocator = By.xpath("//span[@id='order-tier-price']//b");
    private By _TotalPackageCostLocator = By.xpath("//span[@id='order-total-price']");

    public WebElement SummaryHeader() {
        return BaseMethods.findElement(_SummaryHeaderLocator);
    }

    public Boolean SummaryIsDisplayed() {
        if (SummaryHeader().isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public WebElement GetPackageCost() { return BaseMethods.findElement(_PackageCostLocator); }

    public String GetPackageCostTextValue() { return GetPackageCost().getText().trim(); }

    public WebElement GetTotalPackageCost() { return BaseMethods.findElement(_TotalPackageCostLocator); }

    public String GetTotalPackageCostTextValue() { return GetTotalPackageCost().getText().trim(); }
}
