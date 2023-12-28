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
    And Check toast mesage contains text "Saved Successfully"
    ## Todo : assertion needs filtering
   ## And check unit card in the card grid with number "RANDOM"


  Scenario: add group of units
    Given open the add group of units popup
    And enter the required data with number of units 5
    Then  submit adding group of units
    And Check toast mesage contains text "Units Added Successfully"
    ## Todo : assertion needs filtering
    ##Then check the newly added units


  Scenario: edit Group of Units
    Given  open the edit group of units popup
    When  Select units to be edited criteria
    And select all units
    Then  edit all the features related to the selected units and save
    Then Check toast mesage contains text "Updated Successfully"


  Scenario: delete a unit
    When clicking delete button for unit "RANDOM"
    Then Check the deleted unit number matches the selected unit number
    When clicking the confirm delete button and Check toast mesage contains text "Saved Successfully"

  Scenario: filter units by Activity State
    Given clicking onthe filter button to open filter menue
    And Selecting Status "RANDOM" and Filtring
    Then Check all visible units card have the status "RANDOM"

  Scenario: filter units with unit number
    Given clicking onthe filter button to open filter menue
    And enter the unit number 1 and filter
    Then check all units visible contains  number 1

Scenario: filter units with unit type
  Given clicking onthe filter button to open filter menue
  And Select Type "RANDOM" and filter
  Then check all visible units have type "RANDOM"