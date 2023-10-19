Feature: Blocks And Floors
  Scenario: Check default Floor
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    Then Check Default Block name to be "1" and Description to be "Default Block"


  Scenario: Add new Block with empty floors
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    When click on new block Button
    And Fill the Block name "2" and discription "added floor 2"
    Then Check toast mesage contains text "Saved Successfully"
    Then check new block is created with name "2" and discription "added floor 2"

    ##ToDo:: DElete Block

    # #ToDo:: edit block

    # #ToDo:: filter with name


    # #ToDo:: filter with discription
