Feature: UI - Add owners to a company

  Background:
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

  @view-add-owners @view-show-add-owners-form
  Scenario: Form to add owners company is shown with information
    When user clicks on Add Owners Button in row 1 button
    Then the owners form is displayed
    And add owners form is empty

  @view-add-owners @view-edit-owners
  Scenario: Add owners to a company
    When user clicks on Add Owners Button in row 1 button
    And user enter these owners: 'Mr Owner 3, Mr Owner 4'
    Then the company list shows this information
      | name         | address       | city     | country | email             | phone    | owners                             |
      | My Company 1 | Company St, 1 | New York | USA     | myemail@mail.com  | 55509876 | Mr Owner 1, Mr Owner 3, Mr Owner 4 |
    And the owners form is not displayed

  @view-add-owners @view-edit-owners-invalid
  Scenario: Try to edit the owners company some invalid fields
    When user clicks on Add Owners Button in row 1 button
    And user enter these owners: ''
    Then owners form shows this error: 'This field can not be empty'
    And the owners form is displayed


