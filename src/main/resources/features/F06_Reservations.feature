@Group2
Feature: Making Reservation with Nazeel PMS (web application)

  Background: selecting property
    Given Logging in with superuser
    And Select Property "P01406"

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
    Given  Create "confirmed" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "" endDate ""
    And Choose Reservation Status as "Checked-In"
    And verify toast message appears with text "Saved Successfully" and the reservation status to be "Checked In"

  Scenario: Check out a reservation
    Given  Create "Checked-In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "" endDate ""
    Then Choose Reservation Status as "Checked-Out"

  @LoadMoreAction @Sprint40
  Scenario: check load more action
    When Click on Add new Reservation
    And open unit selection Popup
    Then check the cards count to be 12 and by loading more each time 12 new cards are displayed


  Scenario Outline: Search criteria and filter
    When filtering with "<filter>" as "<value>"
    And choose page size as "100"
    And open a reservation and return to reservations page
    Then check all reservations records "<filter>" as "<value>"
    And Check page size equal to "100"
    When refresh page
    Then check the search criteria is reset
    Examples:
      | filter   | value                                       |
      | resType  | Single                                      |
      | rentType | Monthly                                     |
      | resState | Confirmed                                   |
      | resState | In-House Guests                             |
      | resState | On Arrival Reservations (Not Checked-In)    |
      | resState | On Departure Reservations (Not Checked-Out) |


  @filter_Reservation_by_corporate @28509
  Scenario: Check Search With Corporate feature
    When filtering with "corporate" as "corp data related"
    Then check all reservations records "corporate" as "corp data related"
    When filtering with "corporate" as "شركة متعلقة ببيانات"
    Then check all reservations records "corporate" as "corp data related"
 ## Discount and Taxes Calc

  Scenario: Check Discount and Tax Calculations on reservtion
    When Click on Add new Reservation
    And fill Reservation Data with Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM" startDate "" endDate ""
    And ge applied Taxes on reservation
    And go to Reservation Financial Page
    Then Check all Discounts types against Taxes Calculations and Balnce

