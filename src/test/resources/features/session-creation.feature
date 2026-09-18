Feature: Session creation

  Scenario: Create a session with valid data
    Given a trainee and a coach prepared in the database
    And a valid session creation request
    When I send a POST request to "/v1-0-0/sessions" with the session creation request
    Then the response status should be 201