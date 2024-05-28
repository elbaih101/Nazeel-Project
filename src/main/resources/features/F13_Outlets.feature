@Outlets @Group3
Feature: Outlets Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01405"

  Rule: Outlets Setup
    Background: go to outlets Setup Page
      Given go to outlets Setup Page

    Scenario Outline: creating outlets
      When creating outlet with opState "<opState>" and code "<code>" and name "<name>" description "<desc>"
      Then Check msg "<msg>" and the outlet in the grid
      Examples:
        | opState | code | name                | desc | msg                                                                 |
        | Open    | 1    |                     | 1    | Name is required                                                    |
        | Open    | 2    | data Related Outlet |      | Name exist before                                                   |
        |         | 1    | 1                   | 1    | Operating status is required                                        |
        | Closed  |      | 1                   | 1    | Outlet code is required                                             |
        | Open    | 1    | 1                   | 1    | Added Successfully                                                  |
        | Open    | 999  | 2                   | 2    | Repeated outlet code detected, each outlet must has it unique code. |

    Scenario Outline: Filter Outlets
      When Filtering With "<filter>" as "<value>"
      Then check all visible records "<filter>" as "<value>"
      Examples:
        | filter   | value               |
        | status   | Active              |
        | name     | data Related Outlet |
        | code     | 999                 |
        | opStatus | Closed              |

    Scenario Outline:editing Outlets
      When editing Outlet "<oName>" opState "<opState>" and code "<code>" and name "<nName>" description "<desc>" state "<state>"
      Then Check msg "<msg>" and the outlet in the grid
      Examples:
        | oName | opState | code | nName               | state    | desc          | msg                                                                 |
        | 1     |         |      | non                 |          |               | Name is required                                                    |
        | 1     | non     |      |                     |          |               | Operating status is required                                        |
        | 1     |         | non  |                     |          |               | Outlet code is required                                             |
        | 1     |         | 999  |                     |          |               | Repeated outlet code detected, each outlet must has it unique code. |
        | 1     |         |      | data Related Outlet |          |               | Name exist before                                                   |
        | 1     | Closed  | 2    | 2                   | Inactive | edited outlet | Updated Successfully                                                |

    Scenario Outline: deleting non related Data outlet
      When deleting outlet "<outlet>"
      Then Check msg "<msg>" and outlet "<outlet>" is deleted
      Examples:
        | outlet              | msg                                              |
        | 2                   | Deleted Successfully                             |
        | data Related Outlet | Outlet Related To Catogries Could not be Deleted |

  Rule:Outlet Categories
    Background:goto Outlets Categories Page
      Given go to categories Page
    Scenario Outline:
      When creating category "<name>" on outlet "<outlet>" with NTMP Categ as "<ntmp>" and description "<desc>"
      Then Check msg "<msg>" and Categorey
      Examples:
        | name               | outlet              | ntmp    | desc            | msg                       |
        |                    | data Related Outlet | Other   | added categ     | English Name Is required  |
        | data Related Categ | data Related Outlet | Laundry | dublicate categ | Name exist before         |
        | categ 1            |                     | Other   | added categ     | Outlet is required        |
        | categ 1            | data Related Outlet |         | added categ     | NTMP Category is required |
        | categ 1            | data Related Outlet | Other   | added categ     | Added Successfully        |

    Scenario Outline: Filter Categories
      When Filtering categs With "<filter>" as "<value>"
      Then check all visible categs records "<filter>" as "<value>"
      Examples:
        | filter | value         |
        | status | Active        |
        | name   | categ 1       |
        | outlet | closed outlet |
        | ntmp   | Other         |

    Scenario Outline:editing Categories
      When editing Category "<oName>" outlet "<outlet>" and ntmp "<ntmp>" and name "<nName>" description "<desc>" state "<state>"
      Then Check msg "<msg>" and Categorey
      Examples:
        | oName   | outlet        | ntmp                             | desc           | nName              | state    | msg                       |
        | categ 1 |               |                                  |                | non                |          | English Name Is required  |
        | categ 1 | non           |                                  |                |                    |          | Outlet is required        |
        | categ 1 |               | non                              |                |                    |          | NTMP Category is required |
        | categ 1 |               |                                  |                | data Related Categ |          | Name exist before         |
        | categ 1 | closed outlet | Pick & Drop (Transport Services) | Edited Categ 3 |                    | inactive | Updated Successfully      |

    Scenario Outline: delete non related data category
      When deleting category "<categ>"
      Then Check msg "<msg>" and category "<categ>"
      Examples:
        | categ              | msg                                                |
        | categ 1            | Deleted Successfully                               |
        | data Related Categ | Can not delete this category, it has related items |

        #Can not delete this category, it has related items

  Rule: Outlet Items
    Background: go to items setup
      Given  go to items setup

    @Outlet_Items  @Item_suggested_price
    Scenario Outline: creating outlet Item
      When creating item with name "<name>" and type "<type>" and outlet "<outlet>" and category "<categ>" description "<desc>" price "<price>" taxstate "<tax>"
      Then Check msg "<msg>" and the item
      Examples:
        | name   | type    | outlet              | categ              | desc                    | price       | tax      | msg                    |
        |        | Product | data Related Outlet | data Related Categ | item 1 categ 2 outlet 2 | 15          | applied  | Name is required       |
        | item 1 |         | data Related Outlet | data Related Categ | item 1 categ 2 outlet 2 | userdefined |          | Item Type is required  |
        | item 1 | Service |                     | data Related Categ | item 1 categ 2 outlet 2 | free        |          | Outlet is required     |
        | item 1 | Service | data Related Outlet |                    | item 1 categ 2 outlet 2 | free        |          | Category is required   |
        | item 1 | Product | data Related Outlet | data Related Categ | item 1 categ 2 outlet 2 |             | exempted | Item Price is required |
        | item 1 | Service | data Related Outlet | data Related Categ | item 1 categ 2 outlet 2 | 15          | applied  | Added Successfully     |
        | item 1 | Product | data Related Outlet | data Related Categ | item 1 categ 2 outlet 2 | 20          |          | Name exist before      |
        | item 2 | Service | data Related Outlet | data Related Categ | item 2 categ 2 outlet 2 | userdefined | exempted | Added Successfully     |


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
        | filter   | value              |
        | status   | inactive           |
        | Outlet   | 2                  |
        | name     | 3                  |
        | price    | 0                  |
        | Category | data Related Categ |

    Scenario: delete item
      When deleting item "item 3"
      Then Check msg "Deleted Successfully" and item "item 3"


  Rule: outlet Orders

    Background:go to outlet Orders Page
      Given navigate to outlet orders Page

  #Checks the tax discount calculations on Exclusive after discount property
    Scenario: Check the Discount and Tax Calculation
      When creating an order for item "data Related Item" from outlet "data Related Outlet"
      Then Check the Tax and Discount Calculations

    @corporate_vouchers,orders
    Scenario: outlet order for a corporate
      When creating an order for item "data Related Item" from outlet "data Related Outlet"
      And submiting the order as "walk in" for a "corporate" issue date ""
      Then  Check "walk in" order is created



    @Walk-inOrders_IssueDate
    Scenario: cant create a walk in order with future date
      When creating an order for item "data Related Item" from outlet "data Related Outlet"
      Then check the issue date validation

    @Item_suggested_price
    Scenario: check userDefined item Price is rewritable
      When selecting item "user defined" from outlet "data Related Outlet"
      Then  check item price is rewritable

    @Tax_Exempted_Item
    Scenario: check taxes on tax exempted item
      When creating an order for item "tax exempted" from outlet "data Related Outlet"
      Then Check the order Tax amount to be 0.0

    Scenario: order can only be transferred to non ended reservations
      When creating an order for item "data Related Item" from outlet "data Related Outlet"
      And open Transfere to reservation pop up and Check can't be transfered to ended reservaion

    Scenario: can't create order for reservation without the same tax settings
      When creating an order for item "data Related Item" from outlet "data Related Outlet"
      And open Transfere to reservation pop up and Check can't be transfered to reservation of diffrent tax type


 # TODO : REceipts and Refunds on wlakin Orders
