Feature: SyntaxHRM Employee API Automation


  Background:

    Given I authenticate to SyntaxHRM


  Scenario: Create a new employee

    When I create the assessment employee

    Then response status code should be 201

    And response time should be less than 2000 ms

    And response header "Content-Type" should contain "application/json"

    And created employee data should be correct

    And employee number should be returned



  Scenario: Get the created employee

    Given an assessment employee already exists

    When I get the employee

    Then response status code should be 200

    And response time should be less than 2000 ms

    And response header "Content-Type" should contain "application/json"

    And employee data should match the assessment employee

    And employee job title should be "SDET"



  Scenario: Update the employee

    Given an assessment employee already exists

    When I update the employee first name to "AfafUpdated"

    Then response status code should be 200

    And response time should be less than 2000 ms

    And response header "Content-Type" should contain "application/json"

    And updated employee first name should be "AfafUpdated"

    When I get the employee

    Then response status code should be 200

    And employee first name should be "AfafUpdated"

    And employee last name should be "Alraddadi"

    And employee middle name should be "Abdullah"

    And employee job title should be "SDET"