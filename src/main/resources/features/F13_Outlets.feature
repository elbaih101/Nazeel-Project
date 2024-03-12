Feature: Outlets Feature

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P00020"

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
        | Open    | 2    | 2    | 2    | Added Successfully                                                  |
        | Open    | 2    | 3    | 3    | Repeated outlet code detected, each outlet must has it unique code. |
        | Open    | 3    | 2    | 3    | Name exist before                                                   |
        | Closed  | 3    | 3    | 3    | Added Successfully                                                  |

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
        | 2     |         | 3    |       |          |      | Repeated outlet code detected, each outlet must has it unique code. |
        | 2     |         |      | 3     |          |      | Name exist before                                                   |
        | 3     | Closed  | 4    | 4     | Inactive | 4    | Updated Successfully                                                |

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
        |         | 2      | Other   | added categ on outlet 2     | English Name Is required  |
        | categ 2 |        | Other   | added categ on outlet 2     | Outlet is required        |
        | categ 2 | 2      |         | added categ on outlet 2     | NTMP Category is required |
        | categ 2 | 2      | Other   | added categ on outlet 2     | Added Successfully        |
        | categ 2 |        | Laundry | dublicate categ on outlet 2 | Name exist before         |
        | categ 3 | 2      | Laundry |                             | Added Successfully        |

    Scenario Outline: Filter Categories
      When Filtering categs With "<filter>" as "<value>"
      Then check all visible categs records "<filter>" as "<value>"
      Examples:
        | filter | value   |
        | status | Active  |
        | name   | categ 2 |
        | outlet | 2       |
        | ntmp   | Other   |

    Scenario Outline:editing Categories
      When editing Category "<oName>" outlet "<outlet>" and ntmp "<ntmp>" and name "<nName>" description "<desc>" state "<state>"
      Then Check msg "<msg>" and the outlet in the grid
      Examples:
        | oName   | outlet | ntmp                             | desc           | nName | state    | msg                       |
        | categ 2 |        |                                  |                | non   |          | English Name Is required  |
        | categ 2 | non    |                                  |                |       |          | Outlet is required        |
        | categ 2 |        | non                              |                |       |          | NTMP Category is required |
        | categ 3 |        |                                  |                |       | categ 2  | Name exist before         |
        | categ 3 | 2      | Pick & Drop (Transport Services) | Edited Categ 3 |       | inactive | Updated Successfully      |

      Scenario: delete non related data category
        When deleting category "categ3"
        Then Check msg "Deleted Successfully" and category "categ3" is deleted
        #Can not delete this category, it has related items