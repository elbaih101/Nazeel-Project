Feature: Property Financials settings

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P00020"
  Rule: cost Centers
    Background:go to cost Centers Page
      Given open cost Centers Page

    Scenario Outline: adding cost Center
      When adding new Cost Center with name "<name>" category "<cat>"
      Then Check toast mesage contains text "<msg>"
      And Check the newly added costCenter is added
      Examples:
        | name        | cat    | msg                               |
        |             | Random | Name is required                  |
        | any name    |        | Select category                   |
        | costCenter1 | Random | Cost Center is added successfully |


    Scenario Outline: edit cost centers
      Given edit cost Center "<oName>" name "<nName>" categ "<categ>" status "<stat>"
      Then Check toast mesage contains text "<msg>"
      And Check the updated cost Center
      Examples:
        | oName       | nName       | categ  | stat     | msg                                 |
        | costCenter1 | non         |        |          | Name is required                    |
        | costCenter1 |             | non    |          | Select category                     |
        | costCenter1 | cost edited | Random | Inactive | Cost Center is updated successfully |
        | cost edited | costCenter1 | Random | Active   | Cost Center is updated successfully |

    Scenario Outline: Delete Cost Centers
      Given  delete cost center "<name>"
      Then  Check message "<msg>" and the cost center after delete action
      Examples:
        | name        | msg                                                      |
        | Hospitality | Sorry, this item has related data and can not be deleted |
        | costCenter1 | Cost Center is deleted successfully                      |

    Scenario Outline: Filter cost Centers
      Given filter by "<by>" "<data>"
      Then  Check the shown records "<by>" to contains "<data>"
      Examples:
        | by    | data        |
        | name  | Hos         |
        | stat  | Active      |
        | categ | Hospitality |



  Rule:Discount Types
  Background: go to discount Types page
    Given go to discount Types page

    Scenario: create new discount type

