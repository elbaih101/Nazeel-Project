@Regression @Sprint36
Feature: Blocks And Floors
  Background: selecting property
    Given Logging in with superuser
    And Select Property "P00020"
  Rule: blocks
    Background: going to blocks Page
      And go to Blocks Page

#    Scenario: Check pagination
#      Then Check blocks pagination

    Scenario: Check default block
      Then Check Default Block name to be "1" and Description to be "Default Block"

    Scenario: Add new Block with empty floors
      When click on new block Button
      And Fill the Block name "2" and discription "added block 2"
      Then Check toast mesage contains text "Saved Successfully"
      Then check new block with name "2" and discription "added block 2"


    Scenario: edit Block from the view Button for Block name "2"
      And click on the view Button for Block name "2"
      When click on edit Button
      And enter new name "EditedName" and new discriptn "EditedBlockDiscription"
      And click on save button for block edit mood
      Then check new block with name "EditedName" and discription "EditedBlockDiscription"


    Scenario: filter block list with name
      And click on filter button
      And enter the name of block "EditedName" in search criteria
      And click filter search Button
      Then check filtered blocks cotains name "EditedName"


    Scenario: filter block list with description
      And click on filter button
      And click filter search Button
      And enter discription of the block "EditedBlockDiscription"
      And click filter search Button
      Then check filtered blocks cotains description "EditedBlockDiscription"

    Scenario: Delete a block
      And clicking on block's more menue button for block "EditedName"
      And click on block's delete button
      And click on confirmation messsage yes button
      Then Check toast mesage contains text "Deleted Successfully !"

    Scenario: check number of floors is incremented when adding new floor to the block
      And note the number of floors in the grid for block "1"
      Then go to Floors Page
      And click on new floor button
      And Fill the floor name "added floor" and discription "added floor for the default block "
      Then Check toast mesage contains text "Saved Successfully"
      Then check new floor with name "added floor" and discription "added floor for the default block"
      And go to Blocks Page
      And check the number of floors is incremented by 1 for block "1"

    Scenario: check number of floors is decreases when adding new floor to the block
      And note the number of floors in the grid for block "1"
      Then go to Floors Page
      When click on floor with name "added floor" more menu button
      And click on floor's delete button
      Then Check toast mesage contains text "Deleted Successfully !"
      And go to Blocks Page
      And check the number of floors is decreased by 1 for block "1"


###########################################################################################
    ######################### Floors ###################################################
  Rule:floors
    Background:going to floors page
      And go to Floors Page

#    Scenario: check paginations
#      Then check floors pagination
#
    Scenario: check Default Floor name and
      Then Check Default Floor name to be "1" and Description to be "Default Floor"

    Scenario: add a floor to the default block
      And click on new floor button
      And Fill the floor name "2" and discription "added floor 2"
      Then Check toast mesage contains text "Saved Successfully"
      Then check new floor with name "2" and discription "added floor 2"

    Scenario: edit floor
      And click on the view Button for Floor name "2"
      When click on floor edit Button
      And enter new floor name "EditedName" and new discriptn "EditedFloorDiscription"
      And click on save button for floor edit mood
      Then Check toast mesage contains text "Saved Successfully"
      Then check new floor with name "EditedName" and discription "EditedFloorDiscription"

    Scenario: filter floors list by block
      And click on filter button
      And click on the block drop list and select block "1"
      And click filter search Button
      Then  check the grid contaiing only floors with block name "1"

    Scenario: filter floors by floor name
      And click on filter button
      When enter name of floor "EditedName" in search criteria
      And click filter search Button
      Then Check filtered floors contains only name of "EditedName"

    Scenario: filter floors by floor order
      And click on filter button
      When enter order of floor "2" in search criteria
      And click filter search Button
      Then Check filtered floors contains only order of "2"

    Scenario: filter floors by floor description
      And click on filter button
      When enter description of floor "EditedFloorDiscription" in search criteria
      And click filter search Button
      Then Check filtered floors contains only description of "EditedFloorDiscription"


    Scenario: cant delete floors before last floor
      When click on floor with order "1" more menu button
      And click on floor's delete button
      Then Check toast mesage contains text "You must delete floors in order starting with the greatest order then previous one and so on"

    Scenario: delete the top  floor with no units
      When click on floor with order "topfloor" more menu button
      And click on floor's delete button
      Then Check toast mesage contains text "Deleted Successfully !"