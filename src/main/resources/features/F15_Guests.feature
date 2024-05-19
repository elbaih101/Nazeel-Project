@Guests @SideSp @Group4
Feature: Guest Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01404"
    And go to Guests page

  Scenario Outline: Create a Guest
    When create Guest firstname "<fName>" last name "<lName>"  phone "<phone>" nationality "<nat>" Guest Type "<gType>" id type "<idType>" id number "<idNumber>" ignored Fields "<ign>" invailed Fields "<inv>"
    Then Check msg "<msg>" and Guest is "added"
    Examples:
      | fName | lName | phone     | nat     | gType | idType      | idNumber | ign                   | inv | msg                                                                                                                                                                                                                                                                     |
      |       |       |           |         |       |             |          | bDate,Gender,idSerial |     | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nInvalid mobile number\nDate of birth is required\ngender is missing\nThe mobile number must be a maximum of 9 digits without the first zero |
      |       |       | non       |         |       |             |          | bDate,Gender,idSerial |     | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nCountry dial code is required\nInvalid mobile number\nDate of birth is required\ngender is missing                                          |
      | zaky  | new   | 236548795 | Egypt   | any   | National Id | 5565872  |                       |     | Guest added successfully                                                                                                                                                                                                                                                |
      | Zaky  | new   | 236548795 | Unknown | any   | National Id | 65549    |                       |     | Inserted Id was used before for other guest number                                                                                                                                                                                                                      |


  Scenario Outline: edit  guest details
    When edit Guest with id number "<oIdNum>" firstname "<fName>" last name "<lName>"  phone "<phone>" nationality "<nat>" Guest Type "<gType>" id type "<idType>" id number "<nIdNum>" status "<state>" ignored Fields "<ign>" invailed Fields "<inv>"
    Then Check msg "<msg>" and Guest is "added"
    Examples:
      | oIdNum  | fName | lName | phone     | nat     | gType | idType      | nIdNum  | state    | ign | inv                   | msg                                                                                                                                                                                                                                                                                 |
      | 5565872 | non   | non   | dialOnly  | non     | non   | non         | non     |          |     | bDate,Gender,idSerial | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nInvalid mobile number\ndate of birth must be less than today\ngender is missing\nThe mobile number must be a maximum of 9 digits without the first zero |
      | 5565872 | non   | non   | non       | non     | non   | non         | non     |          |     | bDate,Gender,idSerial | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nCountry dial code is required\nInvalid mobile number\ndate of birth must be less than today\ngender is missing                                          |
      | 5565872 | Zaky  | Zeko2 | 236548795 | Unknown | any   | National Id | 5565872 |          |     |                       | Inserted Id was used before for other guest number                                                                                                                                                                                                                                  |
      | 5565872 | zaky  | zeko3 | 236548795 | Unknown | any   | Family Id   | 5565878 | inActive |     |                       | Guest Updated Successfully                                                                                                                                                                                                                                                          |

  Scenario: add guest Notes
    When adding a company note "guest is a naughty" and property ote "the guest is fantastic" to guest with id "5565878"
    Then check the note is added with the name of the user who created it and the property


  Scenario: edit guest Notes
    When editing a company note "guest is a naughty" to be "u are not real" to guest with id "5565878"
    Then Check the Edited Note

  Scenario: delete Guest Note
    When deleting guest Note "u are not real" to guest with id "5565878"
    Then Check the success msg and the note is deleted


  Scenario: guest documents
    When adding a Document to guest with id "5565878" naming it "id"
    Then Check the added document visible with the name "id"
    When deleting the document
    Then Check the document no more exist

  Scenario: delete guest
    When delete Guest with id number "5565878"
    Then Check msg "Guest deleted successfully" and Guest is "deleted"

  Scenario Outline: filter guests
    When Filtering guest "<filter>" as "<value>"
    Then Check all guests shown "<filter>" as "<value>"
    Examples:
      | filter      | value        |
      | status      | inactive     |
      | name        | auto         |
      | nationality | Egypt        |
      | class       | auto class 1 |
      | idtype      | National ID  |
      | idnum       | 65549      |