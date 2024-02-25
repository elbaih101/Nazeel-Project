@Regression @Sprint38

Feature: printing options page

  Background:
    Given Logging in with superuser
    And Select Property "P00020"
    And go to printing options page

  Scenario: Check the default options
    Then Check the default contract view is single language and default paper type is blank papaer

  Scenario: check the two selection buttons for paper
    When selcting all reports and clicking " Letter Head Paper " button and check the checkboxes are selected
    Then Check toast mesage contains text "Saved Successfully"
    When selcting all reports and clicking " Blank Paper " button and check the checkboxes are selected
    Then Check toast mesage contains text "Saved Successfully"


  Scenario: changing paper type for individual reports
    When select "cashier" paper for "Walk-in" and check the box is selected
    Then Check toast mesage contains text "Saved Successfully"

  Scenario: select contract template
    When selecting the "double" Language template and check its selected
    Then Check toast mesage contains text "Saved Successfully"

    #todo : assert changes made for individual reports
    # PS :>>> dependent on CRs for print previewer and contract design
