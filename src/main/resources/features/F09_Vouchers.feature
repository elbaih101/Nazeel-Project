@Regression @Sprint38 @Vouchers
Feature: Vouchers

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01385"
    # TODO NO show Scenario
  @corporate_vouchers,orders
  Rule:Stand corporate Vouchers
    #noinspection GherkinMisplacedBackground
  Background: going to receipt vouchers page
      Given go to "Receipt" Vouchers Page
    Scenario: create SA receipt Voucher for a corporate
      And successfully create a voucher of type "SAReceipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "" for a "corporate"
      Then check the created voucher owner to be the selected corporate



  Rule:ended Reservations Vouchers
    Background: creating checked in reservation
      Given open reservations Page
      And  Create "Checked-In" Reservation withSource "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
      And go to Reservation Financial Page

    Scenario: check the voucers edit mode after drop cash actions
      Given successfully create a voucher of type "SD" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "29/04/2024" for a "guest"
      Given successfully create a voucher of type "Receipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "29/04/2024" for a "guest"
      And create a drop cash action to date "29/04/2024"
      And go to "Receipt" Vouchers Page
      Then Check "receipt" Voucher with state "CashDrop" edit mode

    Scenario: Create Vouchers onReservation and check its edit mode
      Given create all vouchers Types
      And Choose Reservation Status as "Checked-Out"
      Then Check all Created Vouchers edit mode and edit payment method to "mada" and check msg "Updated Successfully"

    Scenario: Check edit mode for payed digital vouchers on a reservation
      Given create PayTabs link for receipt Voucher with "ReceiptVoucher" and generate link with "PayTabs" amount 200 and purpose ""
      And open the link and pay it successfully
      And Close the open popup
      Then Check "GenReceipt" Voucher with state "Generated" edit mode

  Rule: Drafts Collection

    Background:
      Given go to Draft Vouchers Page
      And successfully create a voucher of type "SADraft" amount "200" payment Method "Cash" maturity Date "25012024" and Creatian Date "" for a "guest"

    Scenario: Check edit mode for Receipt Voucher for Collected Draft
      And click on draft more menu and choose collect by "Normal" payment
      When finish Draft Normal collecting process with amount "200" PaymentMethod "Cash"
      When go to "Receipt" Vouchers Page
      Then Check "receipt" Voucher with state "Collected" edit mode

#    Scenario Outline: Check edit mode for Receipt vouchers
#      Given successfully create a voucher of type "<type>" amount "<amount>" payment Method "<method>" maturity Date "<mDate>" and Creatian Date "<cDate>"
#      And Choose Reservation Status as "<rState>"
#      #change the behaviour to open as draft or receipts
#      When go to "<type>" Vouchers Page
#      Then Check "<type>" Voucher with state "<vState>" edit mode
#      Examples:
#        | type    | amount            | method | mDate    | cDate | vState | rState      |
#        | Receipt | 200               | Cash   |          |       | ended  | Checked-Out |
#        | SD      | 200               | Cash   |          |       | ended  | Canceled    |
#        | Draft   | less than balance | Cash   | 15122025 |       | ended  | Checked-Out |

#    Scenario Outline: Check Receipt Vouchers full cycle to ended mode
#      Given successfully create a voucher of type "<type>" amount "<amount>" payment Method "<method>" maturity Date "<mDate>" and Creatian Date "<cDate>"
#      And Choose Reservation Status as "<rState>"
#      When go to "receipt" Vouchers Page
#      Then Check "<type>" Voucher with state "<vState>" edit mode
#      And edit "<type>" Voucher with state "<vState>" Payment method to "<newMethod>" and check msg "Updated Successfully"
#      Examples:
#        | type    | amount            | method | mDate    | cDate | vState | newMethod   | rState      |
#        | Receipt | 200               | Cash   |          |       | ended  | mada        | Checked-Out |
#        | SD      | 200               | Cash   |          |       | ended  | cheque      | Canceled    |
#        | Draft   | less than balance | Cash   | 15122025 |       | ended  | Checked-Out |             |



#    Scenario Outline: Check edit mode for Refund Vouchers
#      Given successfully create a voucher of type "<vType>" amount "<amount>" payment Method "<method>" maturity Date "<mDate>" and Creatian Date "<cDate>"
#      Given open Refund Vouchers tab
#      Given successfully create a voucher of type "<rvType>" amount "<amount>" payment Method "<method>" maturity Date "<mDate>" and Creatian Date "<cDate>"
#      And Choose Reservation Status as "<rState>"
#      When go to Payment Vouchers Page
#      Then Check "<rvType>" Voucher with state "<rvState>" edit mode
#      Examples:
#        | vType   | amount | method | mDate | cDate | rvType   | rvState | rState      |
#        | Receipt | 200    | Cash   |       |       | Refund   | ended   | Checked-Out |
#        | SD      | 200    | Cash   |       |       | SDRefund | ended   | Canceled    |
#
#    Scenario Outline: Check Refund Vouchers full cucle to ended mode
#      Given successfully create a voucher of type "<vType>" amount "<amount>" payment Method "<method>" maturity Date "<mDate>" and Creatian Date "<cDate>"
#      Given open Refund Vouchers tab
#      Given successfully create a voucher of type "<rvType>" amount "<amount>" payment Method "<method>" maturity Date "<mDate>" and Creatian Date "<cDate>"
#      And Choose Reservation Status as "<rState>"
#      When go to Payment Vouchers Page
#      Then Check "<rvType>" Voucher with state "<rvState>" edit mode
#      And edit "<rvType>" Voucher with state "<rvState>" Payment method to "<newMethod>" and check msg "Updated Successfully"
#      Examples:
#        | vType   | amount | method | mDate | cDate | rvType   | rvState | newMethod | rState      |
#        | Receipt | 200    | Cash   |       |       | Refund   | ended   | mada      | Checked-Out |
#        | SD      | 200    | Cash   |       |       | SDRefund | ended   | cheque    | Canceled    |
#



#  Rule: Digital Payment auto generated Vouchers
#    Background:
#      Given go to "Receipt" Vouchers Page
#      And open the digital payment popup

#    Scenario: Check edit mode for auto Generated Vucher after successfull Paytabs operation
#      Given succesfully create a stand alone voucher with "PayTabs" amount 200 purpose "Checking auto generated receipts" Guest "RANDOM"
#      And open the link and pay it successfully
#      And Close the open popup
#      When go to "Receipt" Vouchers Page
#      Then Check "GenReceipt" Voucher with state "Generated" edit mode


  Rule: Drop Cash Actions
    Background:
      Given create a drop cash action to date "29112023"

    Scenario: Can't Create a Cash Voucher with Date Before Last DropCash Date
      Given go to "Receipt" Vouchers Page
      Then successfully create a voucher of type "SAReceipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "28112023" for a "guest"

    Scenario: Can't edit a cash voucher to a date before last drop cash Date
      Given go to "Receipt" Vouchers Page
      And successfully create a voucher of type "SAReceipt" amount "200" payment Method "Cash" maturity Date "" and Creatian Date "" for a "guest"
      When editing the Created Voucher's  amount "" payment Method "" maturity Date "" and Creatian Date "28112023"
      Then Check toast mesage contains text "issue date/ time of cash vouchers could not be changed"



  Scenario: create a SA Voucher for a corporate


 # TODO : REceipts and Refunds on wlakin Orders