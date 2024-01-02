Feature: PayTabs integration

  Background:
    Given Logging in with superuser
    And Select Property "Boyle-Adams"


  Rule: Receipt Vouchers

    Background:
      Given go to Receipt Vouchers Page
      And open the digital payment popup
    Scenario: create a stand alone PayTabs link for receipt Voucher with
      When open the generate pay-link tab and generate link with "PayTabs" amount 200 and purpose "creating receipt voucher" and open guest sellection
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
      Given go to Reservatios Page
      And create Checked-in Reservation
      And go to Reservation Financial Page

  Scenario: create PayTabs link for receipt Voucher with

  ##TODO :: reservation vouchers

  ##TODO :: Draft collection

  ##TODO :: report actions
  ##TODO ::
   ##TODO:  Scenario: check created stand alone voucher is logged in the paytabs log

