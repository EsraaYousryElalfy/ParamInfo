package StepClasses;

import CommonClasses.AbstractPageObject;
import PageMethods.HomePageMethods;
import PageMethods.PackagesPageMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class PackagesPageSteps extends AbstractPageObject {

    @When("^The user redirected to Package page$")
    public void TheUserRedirectedToPackagePage() {
        PackagesPageMethods packagesPageMethods = new PackagesPageMethods();
        Assert.assertTrue(packagesPageMethods.SummaryIsDisplayed());
    }

    @And("^The total cost is in (.*) currency$")
    public void TheTotalCostIsInCurrency(String currency) {
        PackagesPageMethods packagesPageMethods = new PackagesPageMethods();
        Assert.assertTrue(packagesPageMethods.GetTotalPackageCostTextValue().contains(currency));
    }

    @And("^The Package price is in (.*) currency$")
    public void ThePackagePriceIsInCurrency(String currency) {
        PackagesPageMethods packagesPageMethods = new PackagesPageMethods();
        Assert.assertTrue(packagesPageMethods.GetPackageCostTextValue().contains(currency));
    }

}
