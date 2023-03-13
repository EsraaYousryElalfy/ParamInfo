$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/java/Features/PurshaseWindows.feature");
formatter.feature({
  "line": 1,
  "name": "PurchaseWindows",
  "description": "",
  "id": "purchasewindows",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 3,
  "name": "Register a new valid user",
  "description": "",
  "id": "purchasewindows;register-a-new-valid-user",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 4,
  "name": "The user launched the Application",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "The user navigates to the Register page",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "The user registers new User Successfully",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "The User Registered Successfully",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "The user clicks on continue button",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "The user navigates to the Login page",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "The User enters login credentials",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "The user logged in successfully",
  "keyword": "Then "
});
formatter.step({
  "line": 12,
  "name": "The user searches about Product as \"\u003cProduct\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "The user selects the search suggestion no \"\u003cSuggestion\u003e\"",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "The user adds the \"\u003cProduct\u003e\" in the cart",
  "keyword": "And "
});
formatter.step({
  "line": 15,
  "name": "The user complete Checkout for \"\u003cItemsNo\u003e\"",
  "keyword": "And "
});
formatter.examples({
  "comments": [
    {
      "line": 16,
      "value": "#    Then  The purchasing processing is done successfully"
    }
  ],
  "line": 17,
  "name": "",
  "description": "",
  "id": "purchasewindows;register-a-new-valid-user;",
  "rows": [
    {
      "cells": [
        "Product",
        "Suggestion",
        "ItemsNo"
      ],
      "line": 18,
      "id": "purchasewindows;register-a-new-valid-user;;1"
    },
    {
      "cells": [
        "Windows 8 Pro",
        "1",
        "1"
      ],
      "line": 19,
      "id": "purchasewindows;register-a-new-valid-user;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 494700,
  "status": "passed"
});
formatter.scenario({
  "line": 19,
  "name": "Register a new valid user",
  "description": "",
  "id": "purchasewindows;register-a-new-valid-user;;2",
  "type": "scenario",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 4,
  "name": "The user launched the Application",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "The user navigates to the Register page",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "The user registers new User Successfully",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "The User Registered Successfully",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "The user clicks on continue button",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "The user navigates to the Login page",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "The User enters login credentials",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "The user logged in successfully",
  "keyword": "Then "
});
formatter.step({
  "line": 12,
  "name": "The user searches about Product as \"Windows 8 Pro\"",
  "matchedColumns": [
    0
  ],
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "The user selects the search suggestion no \"1\"",
  "matchedColumns": [
    1
  ],
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "The user adds the \"Windows 8 Pro\" in the cart",
  "matchedColumns": [
    0
  ],
  "keyword": "And "
});
formatter.step({
  "line": 15,
  "name": "The user complete Checkout for \"1\"",
  "matchedColumns": [
    2
  ],
  "keyword": "And "
});
formatter.match({
  "location": "PageNavigationStepClasses.theUserHasLaunchedTheApplication()"
});
formatter.result({
  "duration": 11909875500,
  "status": "passed"
});
formatter.match({
  "location": "HomePageSteps.TheUserNavigatesToTheRegisterPage()"
});
formatter.result({
  "duration": 695434700,
  "status": "passed"
});
formatter.match({
  "location": "RegisterPageSteps.TheUserRegistersNewUserSuccessfully()"
});
formatter.result({
  "duration": 3059072600,
  "status": "passed"
});
formatter.match({
  "location": "RegisterPageSteps.TheUserRegisteredSuccessfully()"
});
formatter.result({
  "duration": 100570000,
  "status": "passed"
});
formatter.match({
  "location": "RegisterPageSteps.TheUserClicksOnContinueButton()"
});
formatter.result({
  "duration": 578114400,
  "status": "passed"
});
formatter.match({
  "location": "HomePageSteps.TheUserNavigatesToTheLoginPage()"
});
formatter.result({
  "duration": 524601800,
  "status": "passed"
});
formatter.match({
  "location": "LoginPageSteps.TheUserEntersLoginCredentials()"
});
formatter.result({
  "duration": 1871528000,
  "status": "passed"
});
formatter.match({
  "location": "HomePageSteps.TheUserLoggedInSuccessfully()"
});
formatter.result({
  "duration": 46196800,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Windows 8 Pro",
      "offset": 36
    }
  ],
  "location": "HomePageSteps.TheUserSearchesAboutProductAs(String)"
});
formatter.result({
  "duration": 901871900,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 43
    }
  ],
  "location": "SearchPageSteps.TheUserSelectsTheSearchSuggestionNo(int)"
});
formatter.result({
  "duration": 1050855300,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "Windows 8 Pro",
      "offset": 19
    }
  ],
  "location": "ProductPageSteps.TheUserAddsTheInTheCart(String)"
});
formatter.result({
  "duration": 983869000,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1",
      "offset": 32
    }
  ],
  "location": "CheckoutPageSteps.TheUserCompleteCheckout(int)"
});
formatter.result({
  "duration": 3086934200,
  "status": "passed"
});
formatter.after({
  "duration": 811548400,
  "status": "passed"
});
});