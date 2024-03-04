Feature: Property Taxes and Fees setup

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01357"

  Rule:Taxes and Fees
    Background: go to taxes page
      Given open Taxes and Fees Page

    Scenario Outline: create new Tax/Fee
      Given create new type "<type>" with name "<name>" and method "<method>" and amount "<amount>" applied on "<aplied>" and start date "<sDate>" end date "<eDate>" Charged on "<chargedOn>"
      Then Check toast mesage contains text "<msg>"
      And Check the type "<type>"  with name "<name>" and method "<method>" and amount "<amount>" applied on "<aplied>" and start date "<sDate>" end date "<eDate>" Charged on "<chargedOn>" status "<stat>"
      And Check the tax "<name>" is applied on the reservations
      Examples:
        | type | name    | method                 | amount | aplied | sDate      | eDate      | chargedOn | stat   | msg                                               |
        | Fee  | Lodging | Percentage             | non    |        | 20/12/2023 | 21/12/2023 |           | Active | Enter an amount                                   |
        | Fee  | Tourism | non                    | 15     |        | 20/12/2023 | 21/12/2023 |           | Active | Select the method                                 |
        | Fee  | Tourism | Percentage             | 15     |        | non        | 21/12/2023 |           | Active | enter Valid Start Date Time                       |
        | Fee  | Tourism | Percentage             | 15     |        | 20/12/2023 | 19/12/2023 |           | Active | Start Date/Time must be before Than End Date/Time |
        | Fee  | Lodging | Percentage             | 2.5    |        | 20/12/2023 | 21/12/2023 |           | Active |                                                   |
        | Tax  | VAT     | Percentage             | 15     | non    | 20/12/2023 | 21/12/2023 | Lodging   | Active | choose at least one Applied For                   |
        | Fee  | Tourism | Amount Per Reservation | 20     |        | 20/12/2023 | 21/12/2023 |           | Active |                                                   |
        | Tax  | VAT     | Percentage             | 15     | All    | 20/12/2023 | 21/12/2023 | Lodging   | Active |                                                   |

    Scenario Outline:edit customization
      Given  edit customization "<name>" method "<method>" amount "<amount>" applied on "<aplied>" startDate "<sDate>" endDate "<eDate>" Charged on "<chargedOn>" status "<stat>"
      Then Check toast mesage contains text "<msg>"
      And Check the type "<type>"  with name "<name>" and method "<method>" and amount "<amount>" applied on "<aplied>" and start date "<sDate>" end date "<eDate>" Charged on "<chargedOn>" status "<stat>"
      Examples:
        | type | name    | method           | amount | aplied | sDate      | eDate      | chargedOn | stat     | msg                                               |
        |      | VAT     | non              |        |        |            |            |           |          | Select the method                                 |
        |      | VAT     |                  | non    |        |            |            |           |          | Enter an amount                                   |
        |      | VAT     |                  |        | non    |            |            |           |          | choose at least one Applied For                   |
        |      | VAT     |                  |        |        | non        |            |           |          | enter Valid Start Date Time                       |
        |      | Tourism |                  |        |        | 22/12/2023 | 21/12/2023 |           |          | Start Date/Time must be before Than End Date/Time |
        |      | Tourism | Amount Per Night | 200    |        | 20/12/2023 | 21/12/2023 |           | inactive | Saved Successfully                                |

    Scenario Outline: deleting tax customization
      Given delete the customizatiin "<tax>"
      Then  Check toast mesage contains text "<msg>"
      And  Check msg "<msg>" and the tax "<tax>" is not visible on grid
      Examples:
        | tax     | msg                                                      |
        | Tourism | Tax customization deleted successfully                   |
        | VAT     | Sorry, this item has related data and can not be deleted |

    Scenario Outline: Change tax calculation type
      Given change the calculation to "<calc>"
      Then Check toast mesage contains text "Taxes Common Settings Updated Successfully"
      And Check the taxes are with the "<calc>" type
      Examples:
        | calc      |
        | Inclusive |
        | Exclusive |