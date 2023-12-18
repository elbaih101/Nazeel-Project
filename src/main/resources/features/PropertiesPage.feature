@essential
Feature: property Page
  Scenario: Create new Property

    Given Logging in with superuser
    And Select Property "baihhotel1"
    And Go to Properties Page
    When click on new propery button
    And fill property Data
    And fill Location Data
    And fill Owner Data
    And fill sms Data
    And Fill Subscription Data
    And Finish the Summary
    Then Check new property is created

