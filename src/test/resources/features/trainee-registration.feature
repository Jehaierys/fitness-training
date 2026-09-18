Feature: Trainee registration

  Scenario: Register trainee with full data
    Given a trainee registration request with full data
    When I send a POST request to "/v1-0-0/trainees"
    Then the registration response status should be 201
    And the trainee should be saved in the database