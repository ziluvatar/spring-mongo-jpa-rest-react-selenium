Feature: Create a company

  Background:
    Given No company exists
    And User opens the home page

  @view-show-create-form
  Scenario: Form to create company is shown
    When user clicks on New Company button
    Then the company form is displayed

  @view-create-company
  Scenario: Create a new company with all fields
    When user clicks on New Company button
    And user fills and submits the company form with this information:
      | name     | My Company 1           |
      | address  | Company St, 1          |
      | city     | New York               |
      | country  | USA                    |
      | email    | myemail@mail.com       |
      | phone    | 55509876               |
      | owners   | Mr Owner 1, Mr Owner 2 |
    Then the company list shows this information
      | name         | address       | city     | country | email             | phone    | owners                 |
      | My Company 1 | Company St, 1 | New York | USA     | myemail@mail.com  | 55509876 | Mr Owner 1, Mr Owner 2 |
    And the company form is not displayed

  @view-create-company-invalid-fields
  Scenario: Try to create a new company with invalid fields
    When user clicks on New Company button
    And user fills and submits the company form with this information:
      | name     ||
      | address  ||
      | city     ||
      | country  ||
      | email    | aaa |
      | phone    ||
      | owners   ||
    Then company form shows these errors for these fields:
      | name     | This field can not be empty |
      | address  | This field can not be empty |
      | city     | This field can not be empty |
      | country  | This field can not be empty |
      | email    | The email format is invalid |
      | owners   | This field can not be empty |
    And the company form is displayed


