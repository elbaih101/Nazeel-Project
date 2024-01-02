@Regression @Sprint36
Feature: unit Type Customization
  Background: selecting property
    Given Logging in with superuser
    And Select Property "Corkery, Bins and Huel"
  Rule: customization
    Background: going to the customization page
      And go to unit type customization page

    Scenario: Check paginations
      Then check unit Type pagination

    Scenario:can't add new unit type with ore than 10 photos
      When click on new type button
      And select type "Single Room" and enter description "the new unit type"
      And upload photos "src/main/resources/Images" 11 of the unit
      And click on the submit button
      Then Check toast mesage contains text "Maximum photos number is 10"

    Scenario: add new unit type
      When click on new type button
      And select type "Single Room" and enter description "the new unit type"
      And upload photos "src/main/resources/Images" 1 of the unit
      And click on the submit button
      Then check the room type "Single Room" is added
      And Check toast mesage contains text "Saved Successed"

    Scenario: edit unit type and description
      When click on edit Button for the unit Type "Single Room"
      And select type "Two Rooms" and enter description "the edited unit type"
      And upload photos "src/main/resources/Images" 1 of the unit
      And click on the submit button
      Then check the room type "Two Rooms" is added
      And Check toast mesage contains text "Saved Successed"

    Scenario: can't add duplicated uni types
      When click on new type button
      And select type "duplicate" and enter description "duplicate type"
      And click on the submit button
      Then Check toast mesage contains text "This unit type already exists"

    Scenario: can't edit type to an existing type
      When click on edit Button for the unit Type "Two Rooms"
      And select type "duplicate" and enter description "the duplicate edited room type"
      And click on the submit button
      Then Check toast mesage contains text "This unit type already exists"

    Scenario: delete a unit type
      When click on more menu for unit type "Two Rooms" and click delete button
      Then  check del unit type dialog contains selected unit type and description
      When click on del unit type dilaog's confirm button
      Then Check toast mesage contains text "Unit Type Customization Deleted Successfully"
      And Check the room type "Single Room" isn't visible in the grid