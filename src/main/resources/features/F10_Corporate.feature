Feature: corporates Feature

  Background:
    Given Logging in with superuser


  Rule:Non Zatca Subscribed
    Background:
      Given Select Property "P00958"
      And go to corporates page

    Scenario Outline: create new corporate
      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>"
      Then Check toast mesage contains text "<msg>"
      Examples:
        | name  | country | ignored | vat             | bNum | secBNum | msg                                                                  |
        |       | Saudi   | all     |                 |      |         | Name Is Requird                                                      |
        | zeko  |         | all     |                 |      |         | Country is required                                                  |
        | manga | Saudi   | all     |                 |      |         | Corporate Added Successfully                                         |
        | manga | Saudi   | all     |                 |      |         | Name exist before                                                    |
        | ghaly | Saudi   | all     | 564             |      |         | VAT Number Should be 15 Digits\nVAT Number Should Begin And End By 3 |
        | ghaly | Saudi   | all     | 987654123654987 |      |         | VAT Number Should Begin And End By 3                                 |

  Rule:Non Zatca Subscribed Reservation Popup
    Background:
      Given Select Property "P00958"
      And open reservations Page
      And Click on Add new Reservation
      And click on select corporate


    Scenario Outline: create new corporate
      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>"
      Then Check toast mesage contains text "<msg>"
      Examples:
        | name  | country | ignored | vat             | bNum | secBNum | msg                                                                  |
        |       | Saudi   | all     |                 |      |         | Name Is Requird                                                      |
        | zeko  |         | all     |                 |      |         | Country is required                                                  |
        | manga | Saudi   | all     |                 |      |         | Corporate Added Successfully                                         |
        | manga | Saudi   | all     |                 |      |         | Name exist before                                                    |
        | ghaly | Saudi   | all     | 564             |      |         | VAT Number Should be 15 Digits\nVAT Number Should Begin And End By 3 |
        | ghaly | Saudi   | all     | 987654123654987 |      |         | VAT Number Should Begin And End By 3                                 |
  #1-no duplicates names
  #2-country and name requiered
  #3-all other fields are not a nesisity
  #4- 3 erorr msgs 1*no country 2* no name 3* duplicate name


  Rule: Zatca Subscribed
    Background:
      Given Select Property "P00020"
      And go to corporates page


    Scenario Outline: create new corporate
      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>"
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

    Scenario Outline: edit corporate
      #When Creating a Corporate with name "automated1" and Country "Saudi" ignoredFields "" vat "369874512654893" bNumber "6548" secBNumber "9874"
      Given open edit mode for corporate "string"
      When  Edit Corporate name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>"
      Then Check toast mesage contains text "<msg>"
      Examples:
      #the last two case building number doesnt get deleted
        | name      | country | ignored    | vat | bNum | secBNum | msg                                                                                       |
        | manga     | Saudi   |            |     | 654  | 13      | Building Number Max Length is 4 digits\nAdditional Building Number Max Length is 4 digits |
        | manga     | Saudi   | district   |     | 6548 | 6548    | District Is Required                                                                      |
        | manga     | Saudi   | street     |     | 6548 | 6549    | Street Is Required                                                                        |
        | test new  | Saudi   |            |     | 6548 | 8456    | Name exist before                                                                         |
        | automoto2 | Saudi   |            |     | 6548 | 8456    | Updated Successfully                                                                      |
        | manga     | Saudi   | city       |     | 6548 | 3215    | Please Select City                                                                        |
        | manga     | Saudi   | postalcode |     | 9874 | 9874    | Postal Code Is Required                                                                   |
        | manga     | Saudi   |            |     |      |         | Building Number Is Required\nAdditional Building Number Is Required                       |



    Scenario: Check Zatca Logo on Requiered Fields
      Given open the new Corporate Page
      Then Check Zatca Logo is in Required Corporate Fields


  Rule:Zatca Subscribed Reservation Popup
    Background:
      Given Select Property "P00020"
      And open reservations Page
      And Click on Add new Reservation
      And click on select corporate


    Scenario Outline: create new corporate
      When Creating a Corporate with name "<name>" and Country "<country>" ignoredFields "<ignored>" vat "<vat>" bNumber "<bNum>" secBNumber "<secBNum>"
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

#TODO Corporates setup  Both in stand alone and reservations
