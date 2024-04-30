Feature: Property Rules

  Background: logging in
    Given Logging in with superuser
    And Select Property "P01385"

  @Reservation_Rules
  Rule: Reservations Rules
  #noinspection GherkinMisplacedBackground
  Background:opeening ReservationsRules
    Given go to Reservation Rules Page

  Scenario Outline: Change Times
    When set Checkin time "<in>" and Check out time "<out>" grace Period "<grace>"
      #And  switch "<switch>" SWitch "<state>"
    And set auto extend to be "<extend>"
    Then save and check the msg "<msg>" and fields after edit
    And Check the inTime "<in>" and out time "<out>" and auto etend after "<extend>"
    Examples:
      | in       | out      | grace | extend   | msg                                                                                       |
      | 12:15 AM | 05:00 PM | 12    |          | Normal check-out time value + Grace period Time must be <= 23:59                          |
      | 12:15 AM | 05:00 PM | 1     | 05:05 PM | Normal check-out time value + Grace period value must be less than Auto extend time value |
      | 12:15 AM | 05:00 PM | 3     | 08:01 PM | Saved Successfully                                                                        |

  Scenario Outline: Change Reservation View
    Given choose "<view>" view and save
    Then check "<view>" view is selecetd
    Examples:
      | view     |
      | Calender |
      | Units    |
      | List     |

  Scenario Outline: switches
    When  switch "<switch>" SWitch "<state>"
    Then save and check switch "<switch>" is "<state>"
    Examples:
      | switch                | state |
      | previousdayclac       | on    |
      | autoextenddaily       | on    |
      | autoextendformonthly  | off   |
      | restrictchangeunit    | off   |
      | reasonsrequire        | on    |
      | enableunconfirmed     | on    |
      | enablemonthly         | on    |
      | autonoshow            | off   |
      | autorejectota         | off   |
      | mandatorycheckintoday | on    |
      | skipbalancepay        | on    |
      | resetsequence         | off   |

  Scenario: change unit allowance period
    When changing the unit allowance period "3"
    Then Check the period to be "3"

  Scenario Outline: Change auto now show time after and reasons
    When  changing the in time to "<in>" the auto no show time to "<noShow>" and reasons "<reas>"
    Then Check the msg "<msg>" the time to be  "<noShow>" and reasons "<reas>"
    Examples:
      | in       | noShow   | reas                       | msg                                                              |
      | 12:01 AM | 12:00 AM |                            | Auto 'No Show' Time value must be greater than 'Check in ' value |
      | 12:00 AM | 12:00 AM |                            | Auto 'No Show' Time value must be greater than 'Check in ' value |
      | 12:00 AM | 12:01 AM | Customer changed the dates | Saved Successfully                                               |


  Scenario: Change auto cancel Reasons
    #FixMe add validation over if property has channel manager subscription
    When  changing the auto cancel reason to "Customer changed the dates"
    Then Check toast mesage contains text "Saved Successfully"
    And cancel reasons are "Customer changed the dates"


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
