
Feature: Trainee update

  Scenario: Update trainee with full data
    Given an authenticated trainee and a trainee update request with full data
    When I send a PUT request to "/v1-0-0/trainees"
    Then the update response status should be 200
    And the trainee should be updated in the database

  Scenario: Update non-existing trainee
    Given an authenticated non-existing trainee and a trainee update request with full data
    When I send a PUT request to "/v1-0-0/trainees"
    Then the update should throw user not found exception

