Feature: UI - Company list

  @view-empty-list
  Scenario: List empty
    Given No company exists
    When User opens the home page
    Then the list header with 8 columns is displayed
    And the company list is empty

  @view-list-some
  Scenario: List some companies
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
    And user clicks on New Company button
    And user fills and submits the company form with this information:
      | name     | My Company 2           |
      | address  | Company St, 2          |
      | city     | London                 |
      | country  | UK                     |
      | email    | myemail2@mail.com      |
      | phone    | 44409876               |
      | owners   | Mr Owner 2, Mr Owner 3 |
    Then the list header with 8 columns is displayed
    And the company list shows this information
      | name         | address       | city     | country | email             | phone    | owners                 |
      | My Company 1 | Company St, 1 | New York | USA     | myemail@mail.com  | 55509876 | Mr Owner 1             |
      | My Company 2 | Company St, 2 | London   | UK      | myemail2@mail.com | 44409876 | Mr Owner 2, Mr Owner 3 |
