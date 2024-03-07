Feature: Property Rules

  Background: logging in
    Given Logging in with superuser
    And Select Property "P00020"

  Rule: Reservations
    Background:opeening ReservationsRules
      Given go to Reservation Rules Page

    Scenario Outline: Change Reservation View
      Given choose "<view>" view and save
      Then check "<view>" view is selecetd
      Examples:
        | view     |
        | List     |
        | Units    |
        | Calender |

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
        | enablemonthly         | off   |
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
        When  changing the auto cancel reason to "Customer changed the dates"
        Then Check toast mesage contains text "Saved Successfully"
        And cancel reasons are "Customer changed the dates"
