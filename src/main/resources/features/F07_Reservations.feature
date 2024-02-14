Feature: Making Reservation with Nazeel PMS (web application)

  Background:
#   Given Logging in with end user "elbaih" "Aa@123456" '00726'
    Given Logging in with superuser
    And Select Property "P00020"
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
    Given create a successfull reservation Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
    Then click on save Reservation button
    And when reservation Summary dialouge appears click on save reservatuon Button
    And Choose Reservation Status as "Checked-In"
    And verify toast message appears with text "Saved Successfully" and the reservation status to be "Checked In"

  Scenario: Check out a reservation
    Given  Create "Checked-In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
    Then Choose Reservation Status as "Checked-Out"
