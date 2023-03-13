package StepClasses;

import CommonClasses.AbstractPageObject;
import PageMethods.HomePageMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.Locale;

public class HomePageSteps extends AbstractPageObject {

    @When("^The user clicks on translation button$")
    public void TheUserClicksOnTranslationButton() {
        HomePageMethods homePageMethods = new HomePageMethods();
        homePageMethods.ClickOnTranslationButton();
    }

    @Then("The language is '(.*)'")
    public void TheLanguageIS(String lan) {
        HomePageMethods homePageMethods = new HomePageMethods();
        Assert.assertTrue(homePageMethods.GetTranslationButton().getText().trim() != lan);
    }

    @When("^The User Selects The Country As '(.*)'$")
    public void TheUserSelectsTheCountryAs(String currencyName) {
        HomePageMethods homePageMethods = new HomePageMethods();
        homePageMethods.SelectCountryCurrencyByName(currencyName);
    }

    @And("^The user clicks on select the custom package$")
    public void TheUserClicksOnSelectTheCustomPackage() {
        HomePageMethods homePageMethods = new HomePageMethods();
        homePageMethods.ClickOnSelectButton();
    }

    @Then("^The Cost for the Trial Package is in (.*) currency$")
    public void TheCostForTheTrialPackageIsInCurrency(String currency) {
        HomePageMethods homePageMethods = new HomePageMethods();
        Assert.assertTrue(homePageMethods.GetTrialCostTextValue().contains(currency));
    }
}
