package PageMethods;

import CommonClasses.AbstractPageObject;
import CommonClasses.BaseMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class HomePageMethods extends AbstractPageObject {

    private By _CountryCurrencyButtonLocator = By.cssSelector("div.head-links div.country-current");
    private By _CountriesCurrencyNameListLocator = By.xpath("//a[@class='country ']//div[@class='flag']/parent::a//span[2]");
    private By _Logo = By.id("#jawwy-logo-web");
    private By _TranslationBuuttonLocator = By.id("#translation-btn");
    private By _SelectButtonLocator = By.xpath("//a[text()='Select']");
    private By _TrialCostLocator = By.cssSelector("div.trial-cost");

    public WebElement GetCountryCurrencyButton() {
        return BaseMethods.findElement(_CountryCurrencyButtonLocator);
    }

    public void ClickOnCountryCurrencyButton() {
        BaseMethods.Click_Element(GetCountryCurrencyButton());
    }

    public WebElement GetTranslationButton() {
        return BaseMethods.findElement(_TranslationBuuttonLocator);
    }

    public void ClickOnTranslationButton() {
        BaseMethods.Click_Element(GetTranslationButton());
    }

    public WebElement PageHeaderLogo() {
        return BaseMethods.findElement(_Logo);
    }

    public Boolean LogoIsDisplayed() {
        if (PageHeaderLogo().isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public List<WebElement> GetCountriesName() {
        return BaseMethods.findElements(_CountriesCurrencyNameListLocator);
    }

    public void SelectCountryCurrencyByName(String countryName) {
        for (WebElement we : GetCountriesName()) {
            if (we.getText().trim()==countryName) {
                BaseMethods.Click_Element(we);
            }
        }
    }


    public WebElement GetSelectButton() {
        return BaseMethods.findElement(_SelectButtonLocator);
    }

    public void ClickOnSelectButton() { BaseMethods.Click_Element(GetSelectButton()); }

    public WebElement GetTrialCost() { return BaseMethods.findElement(_TrialCostLocator); }

    public String GetTrialCostTextValue() { return GetTrialCost().getText().trim(); }
}
