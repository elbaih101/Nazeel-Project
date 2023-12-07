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
    And Fill the Block name "2" and discription "added block 2"
    Then Check toast mesage contains text "Saved Successfully"
    Then check new block with name "2" and discription "added block 2"


  Scenario: edit Block from the view Button for Block name "2"
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And click on the view Button for Block name "2"
    When click on edit Button
    And enter new name "EditedName" and new discriptn "EditedBlockDiscription"
    And click on save button for block edit mood
    Then check new block with name "EditedName" and discription "EditedBlockDiscription"


  Scenario: filter block list with name
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And click on filter button
    And enter the name of block "EditedName" in search criteria
    And click filter search Button
    Then check filtered blocks cotains name "EditedName"


  Scenario: filter block list with description
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And click on filter button
    And click filter search Button
    And enter discription of the block "EditedBlockDiscription"
    And click filter search Button
    Then check filtered blocks cotains description "EditedBlockDiscription"

  Scenario: Delete a block
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Blocks Page
    And clicking on block's more menue button for block "EditedName"
    And click on block's delete button
    And click on confirmation messsage yes button
    Then Check toast mesage contains text "Deleted Successfully !"

###########################################################################################
    ######################### Floors ###################################################

  Scenario: check Default Floor name and
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    Then Check Default Floor name to be "1" and Description to be "Default Floor"

  Scenario: add a floor to the default block
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    And click on new floor button
    And Fill the floor name "2" and discription "added floor 2"
    Then Check toast mesage contains text "Saved Successfully"
    Then check new floor with name "2" and discription "added floor 2"

  Scenario: edit floor
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    And click on the view Button for Floor name "2"
    When click on floor edit Button
    And enter new floor name "EditedName" and new discriptn "EditedFloorDiscription"
    And click on save button for floor edit mood
    Then Check toast mesage contains text "Saved Successfully"
    Then check new floor with name "EditedName" and discription "EditedFloorDiscription"

  Scenario: filter floors list by block
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    And click on filter button
    And click on the block drop list and select block "1"
    And click filter search Button
    Then  check the grid contaiing only floors with block name "1"

  Scenario: filter floors by floor name
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    And click on filter button
    When enter name of floor "2" in search criteria
    And click filter search Button
    Then Check filtered floors contains only name of "2"

  Scenario: filter floors by floor order
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    And click on filter button
    When enter order of floor "2" in search criteria
    And click filter search Button
    Then Check filtered floors contains only order of "2"

  Scenario: filter floors by floor description
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    And click on filter button
    When enter description of floor "2" in search criteria
    And click filter search Button
    Then Check filtered floors contains only description of "2"


  Scenario: cant delete floors before last floor
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    When click on floor with order "1" more menu button
    And click on floor's delete button
    Then Check toast mesage contains text "You must delete floors in order starting with the greatest order then previous one and so on"

  Scenario: delete the top  floor with no units
    Given Logging in with superuser
    And Select Property "Boyle-Adams"
    And go to Floors Page
    When click on floor with order "topfloor" more menu button
    And click on floor's delete button
## Todo :: check the code works