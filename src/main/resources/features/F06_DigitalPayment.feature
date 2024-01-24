Feature: PayTabs integration

  Background:
    Given Logging in with superuser
    And Select Property "Boyle-Adams"


  Rule: Receipt Vouchers

    Background:
      Given go to Receipt Vouchers Page
      And open the digital payment popup
    Scenario: create a stand alone PayTabs link for receipt Voucher with
      When open the generate pay-link tab and select "" and generate link with "PayTabs" amount 200 and purpose "creating receipt voucher"
      And open guest selection
      And Select Guest "RANDOM" or ID "" or Mobile ""
      Then click generate Link and check Toast message "Saved Successfully"
      And Check the link is generated in the link field

    Scenario: check the digital Payment dialog send via whattsapp button
      Given succesfully create a stand alone voucher with "PayTabs" amount 200 purpose "Checking whattsapp button" Guest "RANDOM"
      When click on send via Whattsapp Button
      Then check the opened Whattsapp page contains message body guest name", Please follow the link below to pay " the amount " SAR required by"hotelname Link

    Scenario: check the digital Payment dialog send via sms button
      Given succesfully create a stand alone voucher with "PayTabs" amount 200 purpose "Checking SMS button" Guest "RANDOM"
      When  clicking the send via SMS and check toast msg "Messages have been added successfully to sending queue"
      Then go to sms log page and check the msg body guest name", Please follow the link below to pay " the amount " SAR required by"hotelname Link

#    Scenario: check the copy button
#      Given succesfully create a stand alone voucher
#      When clicking the copy button and pasting the copied link
#      Then check the copied link is the same as the generated link

  Rule:Reservations
    Background:
      Given create a successfull reservation Source "RANDOM" purpose "RANDOM" Unit "RANDOM" Guest "RANDOM"
      And go to Reservation Financial Page
      And open the digital payment popup
    Scenario: create PayTabs link for receipt Voucher
      When open the generate pay-link tab and select "ReceiptVoucher" and generate link with "PayTabs" amount 200 and purpose ""
      And Check the data matches the reservation data
      And Check purpose field contains "Rent fees for units"
      Then click generate Link and check Toast message "Saved Successfully"
      And Check the link is generated in the link field

    Scenario: create Link for SD Voucher
      When open the generate pay-link tab and select "SD" and generate link with "PayTabs" amount 200 and purpose ""
      And Check the data matches the reservation data
      And Check purpose field contains "Security Deposit fees for units"
      Then click generate Link and check Toast message "Saved Successfully"
      And Check the link is generated in the link field

    Scenario: Check a generated receipt is logged in the log tab
      Given create PayTabs link for receipt Voucher with "ReceiptVoucher" and generate link with "PayTabs" amount 200 and purpose ""
      When open the log tab and select "paytabs"
      Then check the generated link is present in the grid with state "pending" and Voucher type "Receipt Voucher"

    Scenario: successfully pay a link generated on reservation
      Given create PayTabs link for receipt Voucher with "ReceiptVoucher" and generate link with "PayTabs" amount 200 and purpose ""
      And open the link and pay it successfully
      When open the log tab and select "paytabs"
      Then check the generated link is present in the grid with state "Paid" and Voucher type "Receipt Voucher"

    Scenario: Check a generated SD is logged in the log tab
      Given create PayTabs link for receipt Voucher with "SD" and generate link with "PayTabs" amount 200 and purpose ""
      When open the log tab and select "paytabs"
      Then check the generated link is present in the grid with state "pending" and Voucher type "Security Deposit Receipt Voucher"

    Scenario: Check paid  paytabs Receipt state is  Paid
      Given create PayTabs link for receipt Voucher with "ReceiptVoucher" and generate link with "PayTabs" amount 200 and purpose ""
      When oppen the link and pay it successfully
      When open the log tab and select "paytabs"
      Then check the generated link is present in the grid with state "pending" and Voucher type "Receipt Voucher"

  Rule: Drafts
    Background:
      Given go to Draft Vouchers Page
      And click on draft more menu and choose collect by "digital" payment

    Scenario:succefully create a paytabs draftcollection link with amount less than remaining amount
      When succefully create a paytabs draftcollection link with amount "less than" remaining amount
      Then Check purpose field contains "Collection of draft voucher no"
      And open the log tab and select "paytabs"
      And check the generated link is present in the grid with state "pending" and Voucher type "Draft"

  ##TODO :: report actions

  ##TODO ::
   ##TODO:  Scenario: check created stand alone voucher is logged in the paytabs log

