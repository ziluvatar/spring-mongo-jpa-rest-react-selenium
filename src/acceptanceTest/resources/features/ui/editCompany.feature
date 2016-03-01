Feature: Edit a company

  Background:
    Given No company exists
    When User opens the home page
    And user clicks on New Company button
    And user fills and submits the company form with this information:
      | name     | My Company 1           |
      | address  | Company St, 1          |
      | city     | New York               |
      | country  | USA                    |
      | email    | myemail@mail.com       |
      | phone    | 55509876               |
      | owners   | Mr Owner 1             |

  @view-edit @view-show-edit-form
  Scenario: Form to edit company is shown with information
    When user clicks on Edit Button in row 1 button
    Then the company form is displayed
    And company form is already pre-filled with this information:
      | name     | My Company 1           |
      | address  | Company St, 1          |
      | city     | New York               |
      | country  | USA                    |
      | email    | myemail@mail.com       |
      | phone    | 55509876               |
      | owners   | Mr Owner 1             |

  @view-edit @view-edit-company
  Scenario: Edit a company with all fields
    When user clicks on Edit Button in row 1 button
    And user modifies and submits the company form with this information:
      | name     | My Company 2           |
      | owners   | Mr Owner 1, Mr Owner 2 |
    Then the company list shows this information
      | name         | address       | city     | country | email             | phone    | owners                 |
      | My Company 2 | Company St, 1 | New York | USA     | myemail@mail.com  | 55509876 | Mr Owner 1, Mr Owner 2 |
    And the company form is not displayed

  @view-edit @view-edit-company-invalid-fields
  Scenario: Try to edit a company some invalid fields
    When user clicks on Edit Button in row 1 button
    And user modifies and submits the company form with this information:
      | name     ||
      | owners   ||
    Then company form shows these errors for these fields:
      | name     | This field can not be empty |
      | owners   | This field can not be empty |
    And the company form is displayed


