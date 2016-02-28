Feature: UI - Company list

  @view-empty-list
  Scenario: List empty
    Given No company exists
    When User opens the home page
    Then you can see the list header with 8 columns
    And you can not see any company in the list