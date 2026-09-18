Feature: Coach registration

  Scenario: Register coach with full data
    Given a coach registration request with full data
    When I register a coach by sending a POST request to "/v1-0-0/coaches"
    Then the coach registration response status should be 201
    And the coach should be saved in the database

  Scenario: Register coach with a blank username
    Given a coach registration request with a blank username
    When I register a coach by sending a POST request to "/v1-0-0/coaches"
    Then the coach registration response status should be 400
