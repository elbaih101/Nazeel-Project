Feature: Making Reservation with Nazeel PMS (web application)

  Background: selecting property
    Given Logging in with superuser
    And Select Property "P00166"

  @Reservations
  Rule: Reservations
  #noinspection GherkinMisplacedBackground
  Background:
    And open reservations Page

  Scenario:Create confirmed Reservation
    When Click on Add new Reservation
    And Select Reservation source "RANDOM" and visit purpose "RANDOM"
    And open unit selection Popup
    And select a unit "RANDOM"
    And click on selectguest now button
    And Select Guest "RANDOM" or ID "" or Mobile ""
    Then click on save Reservation button
    And when reservation Summary dialouge appears click on save reservatuon Button
    And verify toast message appears with text "Saved Successfully" and the reservation status to be "Confirmed"

  Scenario: Create Checked in Reservation
    Given create a successfull reservation Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" state "confirmed"
#    Then click on save Reservation button
#    And when reservation Summary dialouge appears click on save reservatuon Button
    And Choose Reservation Status as "Checked-In"
    And verify toast message appears with text "Saved Successfully" and the reservation status to be "Checked In"

  Scenario: Check out a reservation
    Given  Create "Checked-In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
    Then Choose Reservation Status as "Checked-Out"

  @LoadMoreAction @Sprint40
  Scenario: check load more action
    When Click on Add new Reservation
    And open unit selection Popup
    Then check the cards count to be 12 and by loading more each time 12 new cards are displayed


    # TODO Scenario Outline: Search criteria and filter


  @Penalties
  Rule:Penalties
  #noinspection GherkinMisplacedBackground
  Background:go to penalties page
    Given go to penalties Page

  Scenario Outline: adding new penalty
    When creating penalty with name "<name>" ctegory "<categ>" type "<type>" amount "<amount>" calculatedOF "<calcOf>" Description "<desc>"
    Then Check msg "<msg>" and the penalty
    Examples:
      | name      | categ          | type       | amount    | calcOf      | desc                                | msg                       |
      |           | User Defined   | Amount     | 15        |             | userdefined penalty with amount     | Name Is Requird           |
      | penalty 1 | User Defined   | Amount     |           |             | userdefined penalty with amount     | Please enter amount value |
      | penalty 1 | User Defined   |            | 15        |             | userdefined penalty with amount     | Please Select Amount Type |
      | penalty 1 |                | Amount     | 15        |             | userdefined penalty with amount     | Please Select Category    |
      | penalty 1 | User Defined   | Amount     | 15        |             | userdefined penalty with amount     | Saved Successfully        |
      | penalty 1 | User Defined   |            | undefined |             | userdefined penalty with amount     | duplicated Penality name  |
      | penalty 2 | User Defined   | Percentage | 15        |             | userdefined penalty with percentage | Calculated Of is required |
      | penalty 2 | Early Check-In | Percentage | 15        | First Night | userdefined penalty with percentage | Saved Successfully        |

  Scenario Outline: Filter Penalties
    When Filtering penalties With "<filter>" as "<value>"
    Then  Check all visible records "<filter>" as "<value>"
    Examples:
      | filter | value          |
      | name   | penalty 1      |
      | amount | 15             |
      | type   | percentage     |
      | calcOf | First Night    |
      | categ  | Early Check-In |
      | state  | Active         |


  Scenario Outline: edit penalty
    When editing penalty "<oName>" name "<nName>" ctegory "<categ>" type "<type>" amount "<amount>" calculatedOF "<calcOf>" Description "<desc>" and state "<state>"
    Then Check msg "<msg>" and the penalty
    Examples:
      | oName     | nName              | categ                      | type   | amount | calcOf | desc                             | state    | msg                       |
      | penalty 2 | non                |                            |        |        |        |                                  |          | Name Is Requird           |
      | penalty 2 |                    | non                        |        |        |        |                                  |          | Please Select Category    |
      | penalty 2 |                    |                            |        | non    |        |                                  |          | Please enter amount value |
      | penalty 2 |                    |                            |        |        | non    |                                  |          | Calculated Of is required |
      | penalty 2 | penalty 1          |                            |        |        |        |                                  |          | duplicated Penality name  |
      | penalty 2 | penalty 2 (Edited) | Cancel/No-Show Reservation | Amount | 50     |        | the edited desc of the penalty 2 | Inactive | Saved Successfully        |


  Scenario: can delete a penalty with no related data
    When deleting penalty "penalty 2 (Edited)"
    Then Check msg "Deleted Successfully" and penalty deletion

