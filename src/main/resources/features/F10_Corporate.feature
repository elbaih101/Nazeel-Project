@CorporateSetup @Group4
Feature: corporates Feature

  Background:
    Given Logging in with superuser


  Rule:Non Zatca Subscribed
    Background:
      Given Select Property "P01404"
      And go to corporates page
# FIXME the AZTCA LOGO
#    Scenario: Check Zatca Logo on Requiered Fields
#      Given open the new Corporate Page
#      Then Check Zatca Logo is in Required Corporate Fields

    Scenario: create new corporate
      When Checking the validation over the required Fields(name, Country ,VAT,first and second building numbers,) in corporate Creation
        | name              | country     | empty | vat             | bNum | secBNum | invalid       | msg                                                                    |
        | zeko              |             | all   |                 |      |         |               | Country is required                                                    |
        | corp data related |             |       | 332166498745613 | 6541 | 5236    |               | Name exist before                                                      |
        |                   | Saudi       | all   |                 |      |         |               | Name Is Requird                                                        |
        | ghaly             | Saudi       | all   | 564             |      |         |               | vat regestration no should be 15 digits that begins and ends with 3    |
        | ghaly             | Saudi       | all   | 987654123654987 |      |         |               | vat regestration no should be 15 digits that begins and ends with 3    |
        | ghaly             | Saudi       | non   | 332166498745613 | 9876 | 6597    | email         |                                                                        |
        | ghaly             | Saudi       | non   | 332166498745613 | 9876 | 6597    | cpersonnumber | The mobile number must be a maximum of 9 digits without the first zero |
        | shenga            | Afghanistan | non   | 654             | 6541 | 5236    |               | Corporate Added Successfully                                           |
      Then assert Validity of Fields
#      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>" invalid ""


  Rule:Non Zatca Subscribed Reservation Popup
    Background:
      Given Select Property "P01404"
      And open reservations Page
      And Click on Add new Reservation
      And click on select corporate

    Scenario: create new corporate
      When Checking the validation over the required Fields(name, Country ,VAT,first and second building numbers,) in corporate Creation
        | name              | country | empty | vat             | bNum | secBNum | invalid | msg                                                                 |
        | zeko              |         | all   |                 |      |         |         | Country is required                                                 |
        | corp data related |         |       | 332166498745613 | 6541 | 5236    |         | Name exist before                                                   |
        |                   | Saudi   | all   |                 |      |         |         | Name Is Requird                                                     |
        | ghaly             | Saudi   | all   | 564             |      |         |         | vat regestration no should be 15 digits that begins and ends with 3 |
        | ghaly             | Saudi   | all   | 987654123654987 |      |         |         | vat regestration no should be 15 digits that begins and ends with 3 |
      Then assert Validity of Fields

#    Scenario Outline: create new corporate reservation page
#      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>" invalid ""
#      Then Check toast mesage contains text "<msg>"
#      Examples:
#        | name              | country | ignored | vat             | bNum | secBNum | msg                                                                 |
#        |                   | Saudi   | all     |                 |      |         | Name Is Requird                                                     |
#        | zeko              |         | all     |                 |      |         | Country is required                                                 |
#        | corp data related | Saudi   |         | 332166498745613 | 6541 | 5236    | Name exist before                                                   |
#        | ghaly             | Saudi   | all     | 564             |      |         | vat regestration no should be 15 digits that begins and ends with 3 |
#        | ghaly             | Saudi   | all     | 987654123654987 |      |         | VAT Number Should Begin And End By 3                                |

    Scenario Outline: delete corporate
      Given delete corporate "<name>"
      Then Check toast mesage contains text "<msg>"
      Examples:
        | name              | msg                                                      |
        | corp data related | Sorry, this item has related data and can not be deleted |
        | shenga            | Deleted Successfully                                     |

  Rule: Zatca Subscribed
    Background:
      Given Select Property "P00020"
      And go to corporates page

    Scenario: create new corporate related to zatca
      When Checking the validation over the required Fields(name, Country ,VAT,first and second building numbers,) in corporate Creation
        | name   | country | empty      | invalid    | vat | bNum | secBNum | msg                                                                                                                                                        |
        | maanga | Saudi   | all        |            |     |      |         | Please Select City\nDistrict Is Required\nStreet Is Required\nPostal Code Is Required\nBuilding Number Is Required\nAdditional Building Number Is Required |
        | manga  | Saudi   |            |            |     |      |         | Building Number Is Required\nAdditional Building Number Is Required                                                                                        |
        | manga  | Saudi   | non        |            |     | 654  | 13      | Building Number Max Length is 4 digits                                                                                                                     |
        | manga  | Saudi   | postalcode |            |     | 9874 | 9874    | Postal Code Is Required                                                                                                                                    |
        | manga  | Saudi   |            | postalcode |     | 9874 | 9874    | Postal Code Length is 5 digits                                                                                                                             |
        | manga  | Saudi   | city       |            |     | 6548 | 3215    | Please Select City                                                                                                                                         |
        | manga  | Saudi   | district   |            |     | 6548 | 6548    | District Is Required                                                                                                                                       |
        | manga  | Saudi   | street     |            |     | 6548 | 6549    | Street Is Required                                                                                                                                         |
      Then assert Validity of Fields

    Scenario: edit corporate zatca connected
      #When Creating a Corporate with name "automated1" and Country "Saudi" ignoredFields "" vat "369874512654893" bNumber "6548" secBNumber "9874"
      Given open edit mode for corporate "string"
      When Checking the validation over the required Fields(name, Country ,VAT,first and second building numbers,) in corporate Edit
        | name        | country | empty      | vat | bNum | secBNum | msg                                                                 |
        | ayclam fady | Saudi   |            |     | 654  | 13      | Building Number Max Length is 4 digits                              |
        | ayclam fady | Saudi   | district   |     | 6548 | 6548    | District Is Required                                                |
        | ayclam fady | Saudi   | street     |     | 6548 | 6549    | Street Is Required                                                  |
        | ayclam fady | Saudi   | city       |     | 6548 | 3215    | Please Select City                                                  |
        | ayclam fady | Saudi   | postalcode |     | 9874 | 9874    | Postal Code Is Required                                             |
        | ayclam fady | Saudi   | non        |     | non  | non     | Building Number Is Required\nAdditional Building Number Is Required |
        | Zest new    | Saudi   |            |     | 6548 | 8456    | Name exist before                                                   |
      Then assert Validity of Fields
#      When  Edit Corporate name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>"
#      Then Check toast mesage contains text "<msg>"

      #the last two case building number doesnt get deleted



  Rule:Zatca Subscribed Reservation Popup
    Background:
      Given Select Property "P00020"
      And open reservations Page
      And Click on Add new Reservation
      And click on select corporate


    Scenario Outline: create new corporate
      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>" invalid ""
      Then Check toast mesage contains text "<msg>"
      Examples:
        | name   | country | ignored    | vat | bNum | secBNum | msg                                                                                                                                                        |
        | maanga | Saudi   | all        |     |      |         | Please Select City\nDistrict Is Required\nStreet Is Required\nPostal Code Is Required\nBuilding Number Is Required\nAdditional Building Number Is Required |
        | manga  | Saudi   |            |     |      |         | Building Number Is Required\nAdditional Building Number Is Required                                                                                        |
        | manga  | Saudi   | postalcode |     | 9874 | 9874    | Postal Code Is Required                                                                                                                                    |
        | manga  | Saudi   |            |     | 654  | 13      | Building Number Max Length is 4 digits\nAdditional Building Number Max Length is 4 digits                                                                  |
        | manga  | Saudi   | city       |     | 6548 | 3215    | Please Select City                                                                                                                                         |
        | manga  | Saudi   | district   |     | 6548 | 6548    | District Is Required                                                                                                                                       |
        | manga  | Saudi   | street     |     | 6548 | 6549    | Street Is Required                                                                                                                                         |


