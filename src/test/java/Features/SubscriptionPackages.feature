Feature: PurchaseWindows

  Scenario Outline: Register a new valid user
    Given The user launched the Application
    When The user clicks on translation button
    Then The language is '<Language>'
    When The User Selects The Country As '<Country>'
    Then The Cost for the Trial Package is in '<Currency>' currency
    And  The user clicks on select the custom package
    Then The user redirected to Package page
    And  The Package price is in '<Currency>' currency
    And  The total cost is in '<Currency>' currency
    Examples:
      | Country | Language | Currency       |
      | UAE     | English  | AED            |
      | EGP     | English  | Egyptian pound |
