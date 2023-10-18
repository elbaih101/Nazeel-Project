Feature: Making Reservation with Nazeel PMS (web application)

  Scenario:Successful_Reservation

    Given Logging in using The Login TestData username "Mostafa Hamed" password "123456&Mh" ACC "00720"
    And clicking on later to bypass user verification
    Then click on Reservation link to open reservation
    And Click on Add new Reservation
    And Select Reservation source
    And  Select Visit purpose
    And Select Unit Now Span
    And hover on any unit card then click confirm
    And click on selectguest now button
    When select guest dialouge appears click on name to search by name
    And  enter the guest name "mostafa" in the Name field
    #record button is replaced with confirm button
    And click on Record After the guest Appears
    Then click on check in button
    And when reservation Summary dialouge appears click on save reservatuon Button
    And verify toast message appears with text "Saved Successfully" and the reservation status to be "Confirmed"

