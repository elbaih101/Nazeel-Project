@FinancialSettings @Group3
Feature: Property Financials settings

  Background:  login and choose property
    Given Logging in with superuser
    And Select Property "P01405"
  Rule: cost Centers
    Background:go to cost Centers Page
      Given open cost Centers Page

    Scenario Outline: adding cost Center
      When adding new Cost Center with name "<name>" category "<cat>"
      Then Check toast mesage contains text "<msg>"
      And Check msg "<msg>" and the costCenter
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

    Scenario Outline: create new discount type
      When Create new Discount Type "<type>" description "<desc>"
      Then Check toast mesage contains text "Discount Type is updated successfully"
      And Check the Discount "<type>" in the grid with state "Active" is "present"
      And the type "<type>" is no more in the addition list
      Examples:
        | type     | desc                         |
        | Trainees | added trainees discount type |

    Scenario: deactivating and Reactivate discount type
      When dactivating discount "Random"
      Then Check toast mesage contains text "Discount Type is updated successfully"
      Then Check the Discount "Random" in the grid with state "Inactive" is "present"
      When reactivating discount type "Random"
      Then Check toast mesage contains text "Discount Type is updated successfully"
      Then Check the Discount "Random" in the grid with state "Active" is "present"

    Scenario: delete discount with no related data
      When  deleting discount "Trainees" without related data
      Then Check toast mesage contains text "Discount Type is deleted successfully"
      And Check the Discount "Trainees" in the grid with state "Active" is "deleted"

    Scenario: Reorder the discounts list
      When replacing the order of the first record with the last
      Then Check toast mesage contains text "Discount Type is updated successfully"
      And check the order is changed

  Rule:Currencies
    Background:go to currencies page
      Given go to Currencies Page

    Scenario Outline: adding new currency
      When adding new Currency "<curr>" with exchange Rate "<exRate>" and default "<isDef>"
      Then check msg "<msg>" and the new currency is added in the grid
      Examples:
        | curr | isDef | exRate | msg                                       |
        | EUR  | false |        | Exchange rate is required                 |
        | SAR  | false | 5.3    | Currency already exists for that property |
        | EuR  | true  | 0.6    | Added Successfully                        |


    Scenario Outline: edit currencies
      When editing Currency "<oCurr>" to "<nCurr>" and exchangeRate "<exRate>" and state "<state>" and default "<isDef>"
      Then check msg "<msg>" and the new currency is added in the grid
      Examples:
        | oCurr | nCurr | state  | isDef | exRate | msg                                       |
        | EUR   | SAR   | Active | false | 1.5    | Currency already exists for that property |
        | SAR   |       |        | true  |        | Updated Successfully                      |
        | EUR   |       |        |       | non    | Exchange rate is required                 |


    Scenario Outline: Delete currency
      When deleting "<curr>" currency
      Then Check "<msg>" and the currency is deleted
      Examples:
        | curr    | msg                                                      |
        | default | At least one currency should be the default              |
        | EGP     | Sorry, this item has related data and can not be deleted |
        | EUR     | Deleted Successfully                                     |