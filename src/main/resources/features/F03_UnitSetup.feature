@Regression @Sprint36
Feature: Unit Setup

  Background: selecting property
    Given Logging in with superuser
    And Select Property "P01385"
# masterdata unit Type Customization ##
  Rule:masterdata unit Type Customization
    Background: going to units master data
      Given go to units master Data

    Scenario Outline:filter units types
      Given click on filter button and enter name of type "<name>" and status "<status>" and click search
      Then Check the grid contains only types with names "<name>" and statues "<status>"
      Examples:
        | name   | status |
        | single |        |
        |        | active |

#    Scenario: filter units types with name
#      Given click on filter button and enter name of type "single" and status "" and click search
#      Then Check the grid contains only types with names "single" and statues ""
#
#    Scenario:filter units types with status
#      Given click on filter button and enter name of type "" and status "active" and click search
#      Then Check the grid contains only types with names "" and statues "active"

    Scenario: add new unit type
      When add anew unit type with name "zeka"
      Then Check the type "zeka" is visible in the grid
  # TODO :: add full cycle to create unit from created type

    Scenario: edit type  status
      Given click on edit button for type "zeka" and change status to "inactive"
      Then Check the grid contains only types with names "zeka" and statues "inactive"

    Scenario: edit type name
      Given click on edit button for type "zeka" and change name to "zeko"
      Then Check the type "zeko" is visible in the grid

    Scenario: can't delete unit type associated with units
      Given click on delete button for unit type associated with units
      Then Check toast mesage contains text "Invalid action, this unit type had related data."

    Scenario: can delete a unit type not associated with units
      Given create a unit type "RANDOM"
      And delete the created unit type
      Then  Check toast mesage contains text "Task Type is deleted successfully"
  ##end masterdata unit Type Customization ##

## Setup Unit Type Customization ##
  Rule: property unit type customization
    Background: going to the customization page
      And go to unit type customization page

    Scenario: Check paginations
      Then check unit Type pagination

    Scenario:can't add new unit type with ore than 10 photos
      When click on new type button
      And select type "Single Room" and enter description "the new unit type"
      And upload photos "src/main/resources/Images" 11 of the unit
      And click on the submit button
      Then Check toast mesage contains text "Maximum photos number is 10"

    Scenario: add new unit type
      When click on new type button
      And select type "Single Room" and enter description "the new unit type"
      And upload photos "src/main/resources/Images" 1 of the unit
      And click on the submit button
      Then check the room type "Single Room" is added
      And Check toast mesage contains text "Saved Successed"

    Scenario: edit unit type and description
      When click on edit Button for the unit Type "Single Room"
      And select type "Two Rooms" and enter description "the edited unit type"
      And upload photos "src/main/resources/Images" 1 of the unit
      And click on the submit button
      Then check the room type "Two Rooms" is added
      And Check toast mesage contains text "Saved Successed"

    Scenario: can't add duplicated unit types
      When click on new type button
      And select type "duplicate" and enter description "duplicate type"
      And click on the submit button
      Then Check toast mesage contains text "This unit type already exists"

    Scenario: can't edit type to an existing type
      When click on edit Button for the unit Type "Two Rooms"
      And select type "duplicate" and enter description "the duplicate edited room type"
      And click on the submit button
      Then Check toast mesage contains text "This unit type already exists"

    Scenario: delete a unit type
      When click on more menu for unit type "Two Rooms" and click delete button
      Then  check del unit type dialog contains selected unit type and description
      When click on del unit type dilaog's confirm button
      Then Check toast mesage contains text "Unit Type Customization Deleted Successfully"
      And Check the room type "Single Room" isn't visible in the grid
## end Setup Unit Type Customization ##
  Rule: property unit creation and merge settings
    Background: going to the Unit setup page
      Given go to unit Setup page
## units setup settings ##
    Scenario: add a unit for newly added unit type
      Given open the new unit page
      And  enter unit required data with room number "RANDOM" mergable "true" class "Random"
      When  click on the add unit button
#    Then Check toast mesage contains text "Saved Successfully"
      Then check unit card in the card grid with number "RANDOM"


    Scenario: view and edit a unit
      When open the view mode for a unit "RANDOM"
      Then check the url contains "view" click on the edit button to enter edit mode and check the url contains "edit"
      And  enter unit required data with room number "RANDOM" mergable "false" class "Random"
      And save the edited unit
      And Check toast mesage contains text "Saved Successfully"
      And check unit card in the card grid with number "RANDOM"


    Scenario: add group of units
      Given open the add group of units popup
      And enter the required data with number of units 50 and type "RANDOM" and block "RANDOM"
      Then  submit adding group of units
      And Check toast mesage contains text "Units Added Successfully"
      Then check the newly added units


    Scenario: edit Group of Units
      Given  open the edit group of units popup
      When  Select units to be edited criteria of type "RANDOM" type_exclusivly "NO"
      And select all units
      Then  edit all the features related to the selected units and save
      Then Check toast mesage contains text "Updated Successfully"

#FIXME add create a unit to be deleted
    Scenario: delete a unit
      When clicking delete button for unit "RANDOM"
      Then Check the deleted unit number matches the selected unit number
      When clicking the confirm delete button and Check toast mesage contains text "Saved Successfully"

    Scenario: filter units by Activity State
      Given clicking onthe filter button to open filter menue
      And Selecting Status "RANDOM" and Filtring
      Then Check all visible units card have the status "RANDOM"

    Scenario: filter units with unit number
      Given clicking onthe filter button to open filter menue
      And enter the unit number "1" and filter
      #FIXME assertion error
      Then check all units visible contains  number "1"

    Scenario: filter units with unit type
      Given clicking onthe filter button to open filter menue
      And Select Type "RANDOM" and filter
      Then check all visible units have type "RANDOM"

    Scenario: filter units by blocks and floors
      Given clicking onthe filter button to open filter menue
      And select block "RANDOM" and floor "RANDOM" and filter
      Then  check the data presnet are related to the selected block
 ## end units setup settings ##
 ## Merge Settings ##
    Scenario: create new merge setting bet two units
      Given successfully create 2 unit with room number "RANDOM" mergable "true" class "unit"
      And create new merge rule between the two created units with class "unit" unitA "generated" unitB "generated"
      Then Check toast mesage contains text "Saved Successfully"
      And the merge setting bet the two numbers is visible on the grid with class "unit"

    Scenario Outline:
      Given create new merge rule between the two created units with class "" unitA "<unitA>" unitB "<unitB>"
      Then Check toast mesage contains text "<msg>"
      Examples:
        | unitA  | unitB  | msg                                   |
        | Random |        | Select Second Unit                    |
        |        | Random | Select First Unit                     |
        |        |        | Select First Unit\nSelect Second Unit |

    Scenario Outline: Filter the merge records whith unit number
      Given  Filter the merge unit page with unit "<uNum>"
      Then  Check only one record is visible with the unit "<uNum>"
      Examples:
        | uNum   |
        | Random |


    Scenario: Delete Created merge setting
      Given delete any merge setting and note the related units
      Then Check toast mesage contains text "Unit Merge Deleted Successfully"
      And check the units no more merged
## end Merge Settings ##
    ## Rates Customization ##

    @Rates
    Rule: Base Rate Customization
    #noinspection GherkinMisplacedBackground
    Background:going to the Base Rate page
      Given go to Base Rate Page
      And open baseRate edit mode

    Scenario: can't Save Without filling all rates Fields
      And Clear fields
      When clicking on save
      Then Check toast mesage contains text "Please fill empty rates fields"


    Scenario: successfully updating all base rates with valid inputs
      When fill rates "all" with low "200" high "300" min "120" mon "1000" monmin "800"
      And clicking on save
      Then Check toast mesage contains text "Updated Successfully"

    Scenario: high weekdays must be containuos
      When selecting week day "mon"
      Then check weekdays after next  or before previous "mon" are disabled

    Scenario Outline:cant set the base rates with min greater than high or low
      When fill rates "all" with low "200" high "300" min "<min>" mon "1000" monmin "800"
      And clicking on save
      Then Check toast mesage contains text "<msg>"

      Examples:
        | min | msg                                                                                                               |
        | 201 | Min Rate' value must be lower than or equal to 'High Weekdays Rate' and 'Low Weekdays Rate' values for Daily Rate |
        | 200 | Updated Successfull                                                                                               |

  Rule:seasonal Rate Customization
    Background:go to Seasonal Rate Page
      Given open "Seasonal" Rate Page

    Scenario Outline: seasonal Rates
      Given open new rate page
      And fill "seasonal" rate name "<rateName>" startDate "<sDate>" endDate "<eDate>" type "all" with low "200" high "300" min "<min>"
      When clicking on save
      Then Check toast mesage contains text "<msg>"
      Examples:
        | rateName                            | sDate    | eDate    | min | msg                                                                                                               |
        | the new rate                        | 18022024 | 19022024 | 120 | Added Successfully                                                                                                |
        | rate with name                      | 18022024 | 17022024 | 120 | Start date must be less than end date                                                                             |
        | the new rate                        | 16022024 | 20022024 | 120 | Duplicated rate name                                                                                              |
        | rate with min less than high or low | 16022024 | 20022024 | 201 | Min Rate' value must be lower than or equal to 'High Weekdays Rate' and 'Low Weekdays Rate' values for Daily Rate |

  Rule:Special Rate Customization
    Background:go to Special Rate Page
      Given open "Special" Rate Page

    Scenario Outline: Special Rates
      Given open new rate page
      And fill "special" rate name "<rateName>" startDate "<sDate>" endDate "<eDate>" type "all" with low "200" high "300" min "<min>"
      When clicking on save
      Then Check toast mesage contains text "<msg>"
      Examples:
        | rateName                            | sDate    | eDate    | min | msg                                            |
        | the new rate                        | 18022024 | 19022024 | 120 | Added Successfully                             |
        | rate with name                      | 18022024 | 17022024 | 120 | Start date must be less than end date          |
        | the new rate                        | 16022024 | 20022024 | 120 | Duplicated rate name                           |
        | rate with min less than high or low | 16022024 | 20022024 | 201 | Min Rate' value must be lower than or equal to |


  Rule: Rates application priority
    Scenario Outline: check the priority of rates to be applied
      Given open "<rateType>" Rate Page
      And open new rate page
      And fill "<rateType>" rate name "<rateName>" startDate "<sDate>" endDate "<eDate>" type "all" with low "<low>" high "300" min "<min>"
      When clicking on save
      Then Check toast mesage contains text "<msg>"
      And open reservations Page
      When Click on Add new Reservation
      And elect start date "<sDate>" and end Date "<eDate>"
      And open unit selection Popup
      And select a unit "RANDOM"
      Then Check the rent to equal "<low>"
      Examples:
        | rateType | rateName      | sDate    | eDate    | low | min | msg                |
        | seasonal | seasonal rate | 20022024 | 21022024 | 200 | 120 | Added Successfully |
        | special  | special rate  | 20022024 | 21022024 | 300 | 100 | Added Successfully |
## end Rates Customization ##

  Rule:unit amenities settings
    Background:navigating to amenities settings
      Given go to amenities Page

    Scenario: adding amenities
      When adding new amenity
      Then Check toast mesage contains text "Amenity added successfully"
      And Check the newly added amenity is added

    Scenario: edit unit amenity
      Given edit unit amenity description "the newly added description" and state "inactive"
      Then  Check toast mesage contains text "Amenity updated successfully"
      And Check the edited amenity ddescriptioon "the newly added description" and state "inactive"

    Scenario: applying aminities for all units
      Given apply "Random" amenity to all units
      Then  Check the amenity is applied for any unit

    Scenario Outline: delte amenities
      Given delete amenity "<amenity>"
      Then Check toast mesage contains text "<msg>" and the amenity is removed
      Examples:
        | amenity    | msg                                                      |
        | nonRelated | Amenity deleted successfully                             |
        | related    | Can not delete this amenity because it has related units |