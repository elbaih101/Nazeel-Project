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
    Then check new block with name "2" and discription "added floor 2"


    # #ToDo:: edit block
  Scenario: edit Block from the view Button for Block name "2"
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And click on the view Button for Block name "2"
    When click on edit Button
    And enter new name "EditedName" and new discriptn "EditedBlockDiscription"
    And click on save button for block edit mood
    Then check new block with name "EditedName" and discription "EditedBlockDiscription"




    # #ToDo:: filter with name
  Scenario: filter block list with name
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And click on filter button
    And enter the name of block "EditedName" in search criteria
    And click filter search Button
    Then check filtered blocks cotains name "EditedName"

    # #ToDo:: filter with discription
  Scenario: filter block list with description
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And click on filter button
    And click filter search Button
    And enter discription of the block "EditedBlockDiscription"
    And click filter search Button
    Then check filtered blocks cotains description "EditedBlockDiscription"
    # #ToDo:: DElete Block

  Scenario: Delete a block
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And clicking on block's more menue button for block "EditedName"
    And click on block's delete button
    And click on confirmation messsage yes button
    Then Check toast mesage contains text "Deleted Successfully !"