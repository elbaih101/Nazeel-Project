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
      And  switch "<switch>" SWitch "<state>"
      And set auto extend to be "<extend>"
      Then save and check the msg and fields after edit
      Examples:
        | in | out | grace | extend |
        |    |     |       |        |
#Todo :: containue above