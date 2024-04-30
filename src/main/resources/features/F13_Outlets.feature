@Outlets
Feature: Outlets Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01385"

  Rule: Outlets Setup
    Background: go to outlets Setup Page
      Given go to outlets Setup Page

    Scenario Outline: creating outlets
      When creating outlet with opState "<opState>" and code "<code>" and name "<name>" description "<desc>"
      Then Check msg "<msg>" and the outlet in the grid
      Examples:
        | opState | code | name | desc | msg                                                                 |
        | Open    | 1    |      | 1    | Name is required                                                    |
        |         | 1    | 1    | 1    | Operating status is required                                        |
        | Closed  |      | 1    | 1    | Outlet code is required                                             |
        | Open    | 1    | 1    | 1    | Added Successfully                                                  |
        | Open    | 1    | 2    | 2    | Repeated outlet code detected, each outlet must has it unique code. |
        | Open    | 2    | 1    | 2    | Name exist before                                                   |
        | Closed  | 2    | 2    | 2    | Added Successfully                                                  |

    Scenario Outline: Filter Outlets
      When Filtering With "<filter>" as "<value>"
      Then check all visible records "<filter>" as "<value>"
      Examples:
        | filter   | value  |
        | status   | Active |
        | name     | 2      |
        | code     | 2      |
        | opStatus | Closed |

    Scenario Outline:editing Outlets
      When editing Outlet "<oName>" opState "<opState>" and code "<code>" and name "<nName>" description "<desc>" state "<state>"
      Then Check msg "<msg>" and the outlet in the grid
      Examples:
        | oName | opState | code | nName | state    | desc | msg                                                                 |
        | 2     |         |      | non   |          |      | Name is required                                                    |
        | 2     | non     |      |       |          |      | Operating status is required                                        |
        | 2     |         | non  |       |          |      | Outlet code is required                                             |
        | 2     |         | 1    |       |          |      | Repeated outlet code detected, each outlet must has it unique code. |
        | 2     |         |      | 1     |          |      | Name exist before                                                   |
        | 2     | Closed  | 4    | 4     | Inactive | 4    | Updated Successfully                                                |

    Scenario: deleting non related Data outlet
      When deleting outlet "4"
      Then Check msg "Deleted Successfully" and outlet "4" is deleted

  Rule:Outlet Categories
    Background:goto Outlets Categories Page
      Given go to categories Page
    Scenario Outline:
      When creating category "<name>" on outlet "<outlet>" with NTMP Categ as "<ntmp>" and description "<desc>"
      Then Check msg "<msg>" and Categorey
      Examples:
        | name    | outlet | ntmp    | desc                        | msg                       |
        |         | 1      | Other   | added categ on outlet 2     | English Name Is required  |
        | categ 1 |        | Other   | added categ on outlet 2     | Outlet is required        |
        | categ 1 | 1      |         | added categ on outlet 2     | NTMP Category is required |
        | categ 1 | 1      | Other   | added categ on outlet 2     | Added Successfully        |
        | categ 1 | 1      | Laundry | dublicate categ on outlet 2 | Name exist before         |
        | categ 2 | 1      | Laundry |                             | Added Successfully        |

    Scenario Outline: Filter Categories
      When Filtering categs With "<filter>" as "<value>"
      Then check all visible categs records "<filter>" as "<value>"
      Examples:
        | filter | value   |
        | status | Active  |
        | name   | categ 2 |
        | outlet | 1       |
        | ntmp   | Other   |

    Scenario Outline:editing Categories
      When editing Category "<oName>" outlet "<outlet>" and ntmp "<ntmp>" and name "<nName>" description "<desc>" state "<state>"
      Then Check msg "<msg>" and Categorey
      Examples:
        | oName   | outlet | ntmp                             | desc           | nName   | state    | msg                       |
        | categ 2 |        |                                  |                | non     |          | English Name Is required  |
        | categ 2 | non    |                                  |                |         |          | Outlet is required        |
        | categ 2 |        | non                              |                |         |          | NTMP Category is required |
        | categ 2 |        |                                  |                | categ 2 |          | Name exist before         |
        | categ 2 | 1      | Pick & Drop (Transport Services) | Edited Categ 3 |         | inactive | Updated Successfully      |

    Scenario: delete non related data category
      When deleting category "categ 2"
      Then Check msg "Deleted Successfully" and category "categ 2"
        #Can not delete this category, it has related items

  Rule: Outlet Items
    Background: go to items setup
      Given  go to items setup

    Scenario Outline: creating outlet Item
      When creating item with name "<name>" and type "<type>" and outlet "<outlet>" and category "<categ>" description "<desc>" price "<price>" taxstate "<tax>"
      Then Check msg "<msg>" and the item
      Examples:
        | name   | type    | outlet | categ   | desc                    | price       | tax      | msg                    |
        |        | Product | 1      | categ 1 | item 1 categ 2 outlet 2 | 15          | applied  | Name is required       |
        | item 1 |         | 1      | categ 1 | item 1 categ 2 outlet 2 | userdefined |          | Item Type is required  |
        | item 1 | Service |        | categ 1 | item 1 categ 2 outlet 2 | free        |          | Outlet is required     |
        | item 1 | Service | 1      |         | item 1 categ 2 outlet 2 | free        |          | Category is required   |
        | item 1 | Product | 1      | categ 1 | item 1 categ 2 outlet 2 |             | exempted | Item Price is required |
        | item 1 | Service | 1      | categ 1 | item 1 categ 2 outlet 2 | 15          | applied  | Added Successfully     |
        | item 1 | Product | 1      | categ 1 | item 1 categ 2 outlet 2 | 20          |          | Name exist before      |
        | item 2 | Service | 1      | categ 1 | item 2 categ 2 outlet 2 | userdefined | exempted | Added Successfully     |

    Scenario Outline: edit Item
      When editing item "<oName>" name "<nName>" and type "<type>" and outlet "<outlet>" and category "<categ>" description "<desc>" price "<price>" taxstate "<tax>" state "<state>"
      Then Check msg "<msg>" and the item
      Examples:
        | oName  | nName  | type    | outlet | categ | desc              | price | tax     | state    | msg                    |
        | item 2 | non    |         |        |       |                   |       |         |          | Name is required       |
        | item 2 |        | non     |        |       |                   |       |         |          | Item Type is required  |
        | item 2 |        |         | non    |       |                   |       |         |          | Outlet is required     |
        | item 2 |        |         |        | non   |                   |       |         |          | Category is required   |
        | item 2 |        |         |        |       | non               |       |         |          | Item Price is required |
        | item 2 | item 1 |         |        |       |                   |       |         |          | Name exist before      |
        | item 2 | item 3 | product |        |       | item3 from item 2 | 50    | applied | Inactive | Updated Successfully   |

    Scenario Outline: Filter Items
      When Filter Items With "<filter>" as "<value>"
      Then Check all items records "<filter>" as "<value>"
      Examples:
        | filter   | value       |
        | status   | inactive    |
        | Outlet   | 2           |
        | name     | 3           |
        | price    | 0           |
        | Category | 2 - Categ 2 |

    Scenario: delete item
      When deleting item "item 3"
      Then Check msg "Deleted Successfully" and item "item 3"


  Rule:Outlet Categories2
    Background:goto Outlets Categories Page
      Given go to categories Page
    Scenario: cant delete  related data category
      When deleting category "categ 1"
      Then Check msg "Can not delete this category, it has related items" and category "categ 1"

  Rule: Outlets Setup2
    Background: go to outlets Setup Page
      Given go to outlets Setup Page
    Scenario: can't delete  related Data outlet
      When deleting outlet "1"
      Then Check msg "Outlet Related To Catogries Could not be Deleted" and outlet "1" is deleted


  Rule: outlet Orders

  Background:go to outlet Orders Page
    Given navigate to outlet orders Page

  Scenario: Check the Discount and Tax Calculation
    When creating an order for item "item 1" from outlet "1"
    Then Check the Tax and Discount Calculations