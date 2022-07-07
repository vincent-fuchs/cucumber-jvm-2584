@Dwh
Feature: User would like to get dwhs
  Background:
    Given the following dwhs exists in the library
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all dwhs
    When user requests for all dwhs
    Then the user gets the following dwhs
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get dwhs by code
    When user requests for dwhs by code "1"
    Then the user gets the following dwhs
      | code | description                 |
      | 1    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get dwhs by an invalid code
    When user requests for dwhs by id "10000" that does not exists
    Then the user gets an exception "Dwh with code 10000 does not exist"