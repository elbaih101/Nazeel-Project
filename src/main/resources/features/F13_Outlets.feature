@Outlets @Group3
Feature: Outlets Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01405"

  Rule: Outlets Setup
    Background: go to outlets Setup Page
      Given go to outlets Setup Page

    Scenario: creating outlets
      When Checking the validity of required fields of Outlet creation
        | opState | code | name                | desc | msg                                                                 |
        | Open    | 1    |                     | 1    | Name is required                                                    |
        | Open    | 2    | data Related Outlet |      | Name exist before                                                   |
        |         | 1    | 1                   | 1    | Operating status is required                                        |
        | Closed  |      | 1                   | 1    | Outlet code is required                                             |
        | Open    | 999  | 2                   | 2    | Repeated outlet code detected, each outlet must has it unique code. |
        | Open    | 1    | 1                   | 1    | Added Successfully                                                  |
      Then Check validity of Outlet fields and outlet

    Scenario: Filter Outlets
      When Filtering With the below table Check the filtered Criteria for Outlets
        | filter   | value               |
        | status   | Active              |
        | name     | data Related Outlet |
        | code     | 999                 |
        | opStatus | Closed              |
        | status   | Inactive            |
      Then assert all filtering success

    Scenario:editing Outlets
      When Checkig thevalidity of required fields of Outlet editing
        | oName | opState | code | nName               | state    | desc          | msg                                                                 |
        | 1     | open    | 2    | non                 | Active   |               | Name is required                                                    |
        | 1     | non     | 2    | 2                   | Active   |               | Operating status is required                                        |
        | 1     | open    | non  | 2                   | Active   |               | Outlet code is required                                             |
        | 1     | open    | 999  | 2                   | Active   |               | Repeated outlet code detected, each outlet must has it unique code. |
        | 1     | open    |      | data Related Outlet | Active   |               | Name exist before                                                   |
        | 1     | Closed  | 2    | 2                   | Inactive | edited outlet | Updated Successfully                                                |
      Then Check validity of Outlet fields and outlet

    Scenario: deleting Outlets Constrains
      When deleting Outlets Check Constrains
        | outlet              | msg                                              |
        | data Related Outlet | Outlet Related To Catogries Could not be Deleted |
        | 2                   | Deleted Successfully                             |
      Then Check Outlet deletion validation

  Rule:Outlet Categories
    Background:goto Outlets Categories Page
      Given go to categories Page

    Scenario:Category Creation
      When Checking the validity of required fields of Category "creation"
        | name               | outlet              | ntmp    | desc            | msg                       |
        |                    | data Related Outlet | Other   | added categ     | English Name Is required  |
        | data Related Categ | data Related Outlet | Laundry | dublicate categ | Name exist before         |
        | categ 1            |                     | Other   | added categ     | Outlet is required        |
        | categ 1            | data Related Outlet |         | added categ     | NTMP Category is required |
        | categ 1            | data Related Outlet | Other   | added categ     | Added Successfully        |
      Then Check validity of Category fields and Category

    Scenario:editing Categories
      When Checking the validity of required fields of Category "Editing"
        | oName   | outlet              | ntmp                             | desc           | nName              | state    | msg                       |
        | categ 1 | data Related Outlet | Other                            | edited desc    | non                | Active   | English Name Is required  |
        | categ 1 | non                 | Other                            | edited desc    | categ 1            | Active   | Outlet is required        |
        | categ 1 | data Related Outlet | non                              | edited desc    | categ 1            | Active   | NTMP Category is required |
        | categ 1 | data Related Outlet | Other                            | edited desc    | data Related Categ | Active   | Name exist before         |
        | categ 1 | closed outlet       | Pick & Drop (Transport Services) | Edited Categ 3 | categ 1            | inactive | Updated Successfully      |
      Then Check validity of Category fields and Category

    Scenario: Filter Categories
      When filtering ctegories with below table Check the filtered criteria for Categories
        | filter | value         |
        | status | Active        |
        | name   | categ 1       |
        | outlet | closed outlet |
        | ntmp   | Other         |
      Then check the Search Criteria Validity


    Scenario: delete non related data category
      When deleting Categs and check cnstarins
        | categ              | msg                                                |
        | data Related Categ | Can not delete this category, it has related items |
        | categ 1            | Deleted Successfully                               |
      Then  Check categs deletion Constrains validity

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
