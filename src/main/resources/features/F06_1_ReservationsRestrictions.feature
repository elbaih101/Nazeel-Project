@Group2
Feature:restrictions on reservations
  Rule:privilages for user auttoemp1
    Background: selecting property
      Given Logging in with end user "auttoemp1" "Aa@123456" "01329"
      And Select Property "P01406"
      And open reservations Page
    Scenario: Check user can't add past reservations or change min rate
      When Click on Add new Reservation
      And fill "single" Reservation Data with Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "02/01/2021" endDate "03/01/2021"
      When Change Rate of the reservation to "1"
      And Save reservation as "confirmed"
      Then check toast messages from the list and the reservation notcreatd
        | Rate is less than the minimum rate                     |
        | You do not have the privilege to add past reservations |

    Scenario: can't Cehcek in future reservations
      When Click on Add new Reservation
      And fill "single" Reservation Data with Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "02/01/2030" endDate "03/01/2030"
      And Save reservation as "CheckedIn"
      Then Check the failedPopUp with msg "'Save resevation failed\n-\nYou do not have the privilege to add or modify future reservations"

    Scenario Outline: change check in and out times privilages
      Given open a reservation of state "<resState>"
      Then Check the disabled date time fields to be "<disabled>"
      Examples:
        | resState  | disabled         |
        | Confirmed | CheckIn          |
        | CheckedIn | CheckIn,CheckOut |

  Rule: genral reserctions
    Background: selecting property
      Given Logging in with superuser
      And Select Property "P01406"
      And open reservations Page

    Scenario: can't change from date of checkedin reservation to future
      Given open a reservation of state "Checked In"
      When editing check in date to be tomorrow and saving
      Then Check toast mesage contains text "From Date must be today or less than today"

    Scenario:late check out
      Given  Create "single" "Checked In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "01/12/2021" endDate "03/12/2021"
      And Change the checkout Time to now and save
      Then checking out with today should not emerge the penalty tab


    Scenario: monthly Reservations Change unit restrictions
      Given Create a "Checked In" monthly Reservation from "lastmonth" for "3" months
      And Change the unit on the reservation
      Then Check cant change callender type and start date is disabled

    Scenario: cant select main guest on the group reervation as dependent
      When Click on Add new Reservation
      And fill "single" Reservation Data with Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "" endDate ""
      Then add dependent to single reservtion "auto guest 1"
      Then Check toast mesage contains text "This User is Selected already As Owner"

    Scenario: adding a unit by type to a group reservation changes all units to be by type
      When Click on Add new Reservation
      And fill "group" Reservation Data with Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "" startDate "" endDate ""
      And open unit selection Popup
      And select a unit "byType"
      Then Check all selectedunits are by type


Scenario: can't add dpenedent on a room when the dependent is checkedout but can undo chekout
 Given  Create "group" "Checked In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "01/12/2021" endDate "03/12/2021"
  And  add dependent "Random" to the the room "Random" and save
  When  checking out dependent "Random"
  Then adding dependent "Random" again Check toast message "This user is selected already as dependent" and the dependet has undo chek out button
  And Choose Reservation Status as "Cancelled"


