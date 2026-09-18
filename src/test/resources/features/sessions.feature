Feature: Session search

  @disabled
  Scenario: Get sessions for authenticated coach
    Given an authenticated coach with sessions in the database
    And an empty session search criteria
    When I send a GET request to "/v1-0-0/sessions" with the session search criteria
    Then the searching response status should be 200