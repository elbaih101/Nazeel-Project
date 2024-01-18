Feature: Vouchers

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
  Rule:Reservations Related Receipt Vouchers
    Background:
      Given create a successfull reservation Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
      And go to Reservation Financial Page

    Scenario: Create a draft Voucher
      Given click on the add voucher button
      And select voucher "Draft" tab
      And eneter maturity Date "01122024" and amount "20"
      Then submit the voucher and check success message prefix "Draft Voucher Number. " postfix " Generated successfully!"
#todo : containue vouchers creation and cycles
    Scenario: Create Receipt Voucher
      Given click on the add voucher button
      And select voucher "Receipt" tab
      And select Payment Method "Cash" and enter amount "50"
      Then submit the voucher and check success message prefix "Receipt Number. " postfix " Generated successfully!"

    Scenario: Create SDReceipt Voucher
      Given click on the add voucher button
      And select voucher "SD" tab
      And select Payment Method "Cash" and enter amount "50"
      Then submit the voucher and check success message prefix "Security Deposit Receipt Voucher Number. " postfix " Generated successfully!"

    Scenario: Create Refund Voucher
      Given open Refund Vouchers tab
      And click on the add voucher button
      And select voucher "Refund" tab
      And select Payment Method "Cash" and enter amount "50"
      Then submit the voucher and check success message prefix "Refund Voucher Number. " postfix " Generated successfully!"

    Scenario: Create SD Refund Voucher
      Given open Refund Vouchers tab
      And click on the add voucher button
      And select voucher "SDRefund" tab
      And select Payment Method "Cash" and enter amount "50"
      Then submit the voucher and check success message prefix "Security Deposit Refund Voucher Number. " postfix " Generated successfully!"

      Scenario: Check edit mode for receipt voucher for ended reservation
        Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date ""
        And Choose Reservation Status as "Checked-In"
        And verify toast message appears with text "Saved Successfully" and the reservation status to be "Checked In"
        Then Choose Reservation Status as "Checked-Out"

