@Group3
Feature: subscriptions

  Background:logging in and selecting property
    Given Logging in with superuser
    And Select Property "P01405"

  Rule: master data subscriptions prices
    Background: oppening subscriptions PRices Page
      And go to subscriptions prices page

    Scenario Outline: Creating new Price
      When  adding "<type>" price for "<service>" with subscription Period "<period>" and price "<price>"
      Then Check toast mesage contains text "<toast>"
      And  the "<type>" price for "<service>" with subscription Period "<period>" and price "<price>" and status "Active" is visible in grid
      Examples:
        | type  | service | period | price | toast                               |
        | renew | nazeel  | 18     | 800   | Saved Successfully                  |
        | new   | Shomoos | 18     | 500   | Saved Successfully                  |
        | renew | Shomoos | 18     | 300   | Saved Successfully                  |
        | renew | nazeel  | 18     | 200   | Error ,Duplicate subscription Price |
        | new   | Shomoos | 18     | 21    | Error ,Duplicate subscription Price |

    Scenario Outline: edit a created price
      Given   adding "<type>" price for "<service>" with subscription Period "<period>" and price "<price>"
      When edit "<type>" price for "<service>" with subscription Period "<period>" to new status "<newStatus>" new period "<newPeriod>" and new Price "<newPrice>"
      Then Check toast mesage contains text "<msg>"
      And  the "<type>" price for "<service>" with subscription Period "<newPeriod>" and price "<newPrice>" and status "<newStatus>" is visible in grid
      Examples:
        | type | service | period | price | newStatus | newPeriod | newPrice | msg                                 |
        | new  | NTMP    | 90     | 200   | inActive  | 89        | 9000     | Saved Successfully                  |
        | new  | NTMP    | 90     | 200   | inActive  | 90        | 200      | Error ,Duplicate subscription Price |

    Scenario Outline:Search Filter
      Given Filter Records With Type "<type>" and Service "<service>" and Period "<period>"  and status "<status>"
      Then all Records Visible in grid are Type "<type>" and Service "<service>" and Period "<period>"  and status "<status>"
      Examples:
        | type  | service | period | status   |
        | Renew |         |        |          |
        |       | Shomoos |        |          |
        |       |         | 12     |          |
        |       |         |        | Inactive |
        | Renew | Nazeel  |        |          |

    Scenario Outline: delete a created price
      When delete "<type>" price for "<service>" with subscription Period "<period>"
      Then Check toast mesage contains text "Saved Successfully"
      Examples:
        | type  | service | period |
        | renew | nazeel  | 18     |
        | new   | Shomoos | 18     |
        | renew | Shomoos | 18     |

  # Todo with new subscriptions for admin check on the relation
  Rule:Subscriptions bu admin user
    Background:go to subscriptions Page
      Given go to subscriptions Setup Page

    Scenario: Check the Subscriptions got their prices from master data
      Given open new Subscription Page
      Then  Check the subscriptions periods and prices and expiry dates for the below table exist
        | service       | period             | price                |
        | Fatoora/ZATCA | 12 Months          | 2000                 |
        | Nazeel        | 12 Months          | 3000.00              |
        | Nazeel        | Custom (Per Day) 2 | enabled Free to edit |


  #  todo :: containue the subscription from end user