@Regression @Sprint38
Feature: Vouchers

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P00020"
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

    Scenario: check the voucers edit mode after drop cash actions
      Given successfully create a voucher of type "SD" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "28112023"
      Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "28112023"
      And create a drop cash action to date "28112023"
      And go to Receipt Vouchers Page
      Then Check "receipt" Voucher with state "CashDrop" edit mode

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
      And Choose Reservation Status as "Canceled"
      When go to Receipt Vouchers Page
      Then Check "Receipt" Voucher with state "ended" edit mode
# Todo : Switch some of the scenarios as no show
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

    Scenario: check edit mode for draft voucher on ended reservation
      Given successfully create a voucher of type "Draft" amount "less than balance" payment Method "Cash" maturity Date "15122025" and Creatian Date ""
      And Choose Reservation Status as "Checked-Out"
      When go to Draft Vouchers Page
      Then Check "Draft" Voucher with state "ended" edit mode

    Scenario: Check edit mode for payed digital vouchers on a reservation
      Given create PayTabs link for receipt Voucher with "ReceiptVoucher" and generate link with "PayTabs" amount 200 and purpose ""
      And open the link and pay it successfully
      And Close the open popup
      Then Check "GenReceipt" Voucher with state "Generated" edit mode

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

    Scenario: Check edit mode for auto Generated Vucher after successfull Paytabs operation
      Given succesfully create a stand alone voucher with "PayTabs" amount 200 purpose "Checking auto generated receipts" Guest "RANDOM"
      And open the link and pay it successfully
      And Close the open popup
      When go to Receipt Vouchers Page
      Then Check "GenReceipt" Voucher with state "Generated" edit mode


  Rule: Drop Cash Actions
    Background:
      Given create a drop cash action to date "29112023"
    Scenario: Can't Create a Cash Voucher with Date Before Last DropCash Date
      Given go to Receipt Vouchers Page
      Then successfully create a voucher of type "SAReceipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "28112023"
    Scenario: Can't edit a cash voucher to a date before last drop cash Date
      Given go to Receipt Vouchers Page
      And successfully create a voucher of type "SAReceipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date ""
      When editing the Created Voucher's  amount "" payment Method "" maturity Date "" and Creatian Date "28112023"
      Then Check toast mesage contains text "issue date/ time of cash vouchers could not be changed"


 # TODO : REceipts and Refunds on wlakin Orders