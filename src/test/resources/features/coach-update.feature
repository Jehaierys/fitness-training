Feature: Coach update

  Scenario: Update coach with full data
    Given an authenticated coach and a coach update request with full data
    When I update a coach by sending a PUT request to "/v1-0-0/coaches"
    Then the coach update response status should be 200
    And the coach should be updated in the database

  Scenario: Update non-existing coach
    Given an authenticated non-existing coach and a coach update request with full data
    When I update a coach by sending a PUT request to "/v1-0-0/coaches"
    Then the coach update should indicate coach not found
