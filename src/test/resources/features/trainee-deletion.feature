Feature: Trainee deletion

  Scenario: Delete existing trainee
    Given an authenticated trainee with full data
    When I send a DELETE request to "/v1-0-0/trainees"
    Then the delete response status should be 200
    And the trainee should be deleted from the database