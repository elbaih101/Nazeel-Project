@Guests @SideSp
Feature: Vendors Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P00020"
    And go to Guests page

  Scenario: Check default vendor
    Then check default vendor in the grid

  Scenario Outline: Create a Guest
    When create Guest firstname "<fName>" last name "<lName>"  phone "<phone>" nationality "<nat>" id type "<idType>" id number "<idNumber>" ignored Fields "<ign>" invailed Fields "<inv>"
    Then Check msg "<msg>" and Guest is "added"
    Examples:
      | fName | lName | phone     | nat   | idType      | idNumber | ign                             | inv | msg                                                                                                                                                                                                                                                                     |
      |       |       |           |       |             |          | bDate,Gender,GuestType,idSerial |     | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nInvalid mobile number\nDate of birth is required\ngender is missing\nThe mobile number must be a maximum of 9 digits without the first zero |
      |       |       | non       |       |             |          | bDate,Gender,GuestType,idSerial |     | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nCountry dial code is required\nInvalid mobile number\nDate of birth is required\ngender is missing                                          |
      | zaky  | zeko2 | 236548795 | Egypt | National Id | 5565872  |                                 |     | Guest added successfully                                                                                                                                                                                                                                                |
      | Zaky  | Zeko2 | 236548795 | Egypt | National Id | 5565872  |                                 |     | Inserted Id was used before for other guest number                                                                                                                                                                                                                      |
      | Zaky  | Zeko3 | 236548795 | Egypt | National Id | 5565873  |                                 |     | Inserted Id was used before for other guest number                                                                                                                                                                                                                      |


  Scenario Outline: edit  guest details
    When edit Guest with id number "<oIdNum>" firstname "<fName>" last name "<lName>"  phone "<phone>" nationality "<nat>" id type "<idType>" id number "<nIdNum>" status "<state>" ignored Fields "<ign>" invailed Fields "<inv>"
    Then Check msg "<msg>" and Guest is "added"
    Examples:
      | oIdNum  | fName | lName | phone     | nat   | idType      | nIdNum  | state    | ign                             | inv | msg                                                                                                                                                                                                                                                                     |
      | 5565873 | non   | non   | dialOnly  | non   | non         | non     |          | bDate,Gender,GuestType,idSerial |     | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nInvalid mobile number\nDate of birth is required\ngender is missing\nThe mobile number must be a maximum of 9 digits without the first zero |
      | 5565873 | non   | non   | non       | non   | non         | non     |          | bDate,Gender,GuestType,idSerial |     | Please select guest ID type\nId number is required\nFirst name is required\nLast name is required\nNationality is required\nCountry dial code is required\nInvalid mobile number\nDate of birth is required\ngender is missing                                          |
      |         | Zaky  | Zeko2 | 236548795 | Egypt | National Id | 5565873 |          |                                 |     | Inserted Id was used before for other guest number                                                                                                                                                                                                                      |
      |         | zaky  | zeko3 | 236548795 | Egypt | National Id | 5565872 | inActive |                                 |     | Guest added successfully                                                                                                                                                                                                                                                |

    #Todo Scenario: add guest Notes
    #Todo Scenario: edit guest Notes
    # Todo Scenario: delete Guest Note
   #Todo Scenario:add guest documents
  #TODO Scenario: remove guest Document

  Scenario: delete guest
    When delete Guest with id number "idNum"
    Then Check msg "Guest deleted successfully" and Guest is "deleted"