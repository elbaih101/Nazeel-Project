Feature: Vouchers

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
  Rule:Reservations Related Receipt Vouchers
    Background:
      Given open reservations Page
      And create a successfull reservation Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
      And go to Reservation Financial Page

    Scenario: Create a draft Voucher
      Given click on the add voucher button
      And select voucher "Draft" tab
      And enter maturity Date "01122024" and amount "20"
      Then submit the voucher and check success message prefix "Draft Voucher Number. " postfix " Generated successfully!"
#todo : continue vouchers creation and cycles
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
      Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      Given open Refund Vouchers tab
      And click on the add voucher button
      And select voucher "Refund" tab
      And select Payment Method "Cash" and enter amount "50"
      Then submit the voucher and check success message prefix "Refund Voucher Number. " postfix " Generated successfully!"

    Scenario: Create SD Refund Voucher
      Given successfully create a voucher of type "SD" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      Given open Refund Vouchers tab
      And click on the add voucher button
      And select voucher "SDRefund" tab
      And select Payment Method "Cash" and enter amount "50"
      Then submit the voucher and check success message prefix "Security Deposit Refund Voucher Number. " postfix " Generated successfully!"
#  todo : keep trying for failure >> (D01_MakingReservation.java:136)
  Rule:ended Reservations Vouchers
    Background:
      Given open reservations Page
      And  Create "Checked-In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
      And go to Reservation Financial Page

    Scenario: Check edit mode for receipt voucher for ended reservation
      Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      And Choose Reservation Status as "Checked-Out"
      When go to Receipt Vouchers Page
      Then Check "receipt" Voucher with state "ended" edit mode

    Scenario: Check edit mode for SD receipt Voucher for ended reservation
      Given successfully create a voucher of type "SD" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      And Choose Reservation Status as "Checked-Out"
      When go to Receipt Vouchers Page
      Then Check "Receipt" Voucher with state "ended" edit mode

    Scenario: Check edit mode for Refund receipt Voucher for ended reservation
      Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      Given open Refund Vouchers tab
      And successfully create a voucher of type "Refund" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      And Choose Reservation Status as "Checked-Out"
      When go to Payment Vouchers Page
      Then Check "Refund" Voucher with state "ended" edit mode

    Scenario: Check edit mode for SDRefund receipt Voucher for ended reservation
      Given successfully create a voucher of type "SD" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      Given open Refund Vouchers tab
      And successfully create a voucher of type "SDRefund" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      And Choose Reservation Status as "Checked-Out"
      When go to Payment Vouchers Page
      Then Check "Refund" Voucher with state "ended" edit mode

  Rule: Drafts Collection

    Background:
      Given go to Draft Vouchers Page
      And successfully create a voucher of type "SADraft" amount "200" payment Method "Cash" maturity Date "25012024" and Creatian Date ""

    Scenario: Check edit mode for Receipt Voucher for Collected Draft
      And click on draft more menu and choose collect by "Normal" payment
      When finish Draft Normal collecting process with amount "200" PaymentMethod "Cash"
      When go to Receipt Vouchers Page
      Then Check "receipt" Voucher with state "Collected" edit mode

#Todo : Check the pay tabs operations and receipts and Complete it
  Rule: Digital Payment auto generated Vouchers
    Background:
      Given go to Receipt Vouchers Page
      And open the digital payment popup
#Todo Check the code Works (Run and See)
    Scenario: Check edit mode for auto Generated Vucher after successfull Paytabs operation
      Given succesfully create a stand alone voucher with "PayTabs" amount 200 purpose "Checking auto generated receipts" Guest "RANDOM"
      And open the link and pay it successfully
      And Close the open popup
      When go to Receipt Vouchers Page
      Then Check "receipt" Voucher with state "Generated" edit mode


  Rule: Drop Cash ACtions
    Background:
      Given open reservations Page
      And create a successfull reservation Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
      And go to Reservation Financial Page

    Scenario: check the voucers edit mode after drop cash actions
      Given successfully create a voucher of type "SD" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "28112023"
      Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "28112023"
      And create a drop cash action to date "28112023"
      And go to Receipt Vouchers Page
      Then Check "receipt" Voucher with state "CashDrop" edit mode
