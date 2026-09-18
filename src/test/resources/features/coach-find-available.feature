Feature: Find available coaches

  Scenario: Find available coaches for authenticated trainee
    Given an authenticated trainee and an available coach
    When I search for available coaches by sending a GET request to "/v1-0-0/coaches" with available set to "true"
    Then the find available coaches response status should be 200
    And the response should contain the available coach