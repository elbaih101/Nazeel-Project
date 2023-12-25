@Regression @Sprint36
Feature: Unit Setup

  Background: going to the Unit setup page
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to unit Setup page

  Scenario: add a unit for newly added unit type
    Given open the new unit page
    And  enter unit required data with room number "Au1"
    When  click on the add unit button
    Then Check toast mesage contains text "saved successfully"
    And check unit card in the card grid with number "Au1"


  Scenario: view and edit a unit
    When open the view mode for a unit "RANDOM"
    Then check the url contains "view" click on the edit button to enter edit mode and check the url contains "edit"
    And  enter unit required data with room number "RANDOM"
    And save the edited unit
    ## Todo : assertion needs filtering
    And check unit card in the card grid with number "RANDOM"


  Scenario: add group of units
    Given open the add group of units popup
    And enter the required data with number of units 5
    Then  submit adding group of units
    And Check toast mesage contains text "Units Added Successfully"
    ## Todo : assertion needs filtering
    Then check the newly added units
    # #Todo : delete a unit
  Scenario: delete a unit
    When clicking delete button for a unit
    # #Todo :edit group of units
