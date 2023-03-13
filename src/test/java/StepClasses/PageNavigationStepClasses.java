package StepClasses;

import PageMethods.CommonMethods;
import PageMethods.HomePageMethods;
import cucumber.api.java.en.Given;
import org.junit.Assert;

public class PageNavigationStepClasses {

    @Given("^The user launched the Application$")
    public static void theUserHasLaunchedTheApplication() throws Exception {
        CommonMethods.TheUserHasLaunchedTheNopCommerceApplication();
        HomePageMethods homePageMethods = new HomePageMethods();
        Assert.assertTrue(homePageMethods.LogoIsDisplayed());
    }

}
