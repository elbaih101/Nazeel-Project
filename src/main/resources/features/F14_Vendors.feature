@Vendors @Sprint40
Feature: Vendors Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P00020"
    And go to Vendors page

  Scenario: Check default vendor
    Then check default vendor in the grid

  Scenario Outline: Create a vendor
    When create vendor name "<name>" phone "<phone>" email "<email>" VAT "<vat>" CRNo "<cR>" PostalCode "<pCode>" description "<desc>" address "<address>"
    Then Check msg "<msg>" and vendor is "added"
    Examples:
      | name  | phone    | email           | vat             | cR     | pCode  | desc                 | address | msg                                                                 |
      |       |          |                 |                 |        |        | no name              |         | Name Is Requird                                                     |
      | manga |          | manga           |                 |        |        | invalid email Format |         | Not Valid Email                                                     |
      | manga | dialonly |                 |                 |        |        | dial only            | Random  | Phone Number required                                               |
      | manga | nodial   |                 |                 |        |        | no dial code         | Random  | Country dial code required                                          |
      | manga |          |                 | 323             |        |        | wrong vat count      |         | VAT Regestration No Should be 15 Digits That Begins and Ends With 3 |
      | manga | Random   | Random          | 654987256489752 | Random | Random | wrong vat format     | Random  | VAT Regestration No Should be 15 Digits That Begins and Ends With 3 |
      | zekwa | 3265415  | any.any@any.com | 369852147854123 | 6541   | 9874   | valid Random entries | Random  | Saved Successfully                                                  |
      | zekwa |          |                 |                 |        |        |                      |         | Name exist before                                                   |


  Scenario Outline: edit vendor
    When  eidting vendor "<oName>"  name "<nName>" phone "<phone>" email "<email>" VAT "<vat>" status "<state>"
    Then Check msg "<msg>" and vendor is "added"
    Examples:
      | oName | nName  | phone    | email | vat             | state | msg                                                                 |
      | zekwa |        | dialonly |       |                 |       | Phone Number required                                               |
      | zekwa |        | nodial   |       |                 |       | Country dial code required                                          |
      | zekwa |        |          | manga |                 |       | Not Valid Email                                                     |
      | zekwa |        |          |       | 323             |       | VAT Regestration No Should be 15 Digits That Begins and Ends With 3 |
      | zekwa |        |          |       | 654987256489752 |       | VAT Regestration No Should be 15 Digits That Begins and Ends With 3 |
      | zekwa | non    |          |       |                 |       | Name Is Requird                                                     |
      | zekwa | zekwa2 | non      | non   | non             | non   | Saved Successfully                                                  |

  Scenario Outline: delete vendor
    When deleting vendor "<name>"
    Then Check msg "<msg>" and vendor is "deleted"
    Examples:
      | name           | msg                                                      |
      | zekwa2         | Vendor deleted successfully                              |
      | Default vendor | Sorry, this item has related data and can not be deleted |

  Scenario Outline: filter vendors
    When filtering vendors "<filter>" as "<value>"
    Then Check Records shown "<filter>" as "<value>"
    Examples:
      | filter | value           |
      | name   | vendor          |
      | status | inActive        |
      | vat    | 312345678912343 |
      | crno   | 98898           |