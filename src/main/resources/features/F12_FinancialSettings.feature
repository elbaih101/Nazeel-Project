Feature: Property Financials settings

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P00020"
  Rule: cost Centers
    Background:go to cost Centers Page
      Given open cost Centers Page

    Scenario Outline: adding cost Center
      When adding new Cost Center with name "<name>" category "<cat>"
      Then Check toast mesage contains text "<msg>"
      And Check the newly added costCenter is added
      Examples:
        | name        | cat    | msg                               |
        |             | Random | Name is required                  |
        | any name    |        | Select category                   |
        | costCenter1 | Random | Cost Center is added successfully |