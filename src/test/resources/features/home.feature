Feature: Home Page Scenarios
  Background:
    Given I enter "Username"
    And I enter "Password"
    And I click on "Login" button

  @IN-1
  Scenario: Verify title of the page is "interview App"
    Then Verify that "Interview App" is the title of the page
