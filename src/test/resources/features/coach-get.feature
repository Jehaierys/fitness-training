Feature: Get coach by username

  Scenario: Get existing coach by username
    Given an existing coach
    When I fetch the coach by sending a GET request to "/v1-0-0/coaches" with its username
    Then the get coach response status should be 200
    And the response should contain the coach's data

  Scenario: Get non-existing coach by username
    Given a username that does not belong to any coach
    When I fetch the coach by sending a GET request to "/v1-0-0/coaches" with its username
    Then the get coach should indicate coach not found
