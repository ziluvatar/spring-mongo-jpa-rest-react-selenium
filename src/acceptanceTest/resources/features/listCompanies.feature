Feature: List companies

  @list-empty
  Scenario: List empty
    Given No company exists
    When I make a GET request to '/companies'
    Then the response list contains 0 companies
    And response status is 200
    And content type is 'application/json'

  @list-some
  Scenario: List some companies
    Given This company exists
      """
      {
        "name": "My Company 1",
        "address": "Company St, 1",
        "city": "New York",
        "country": "USA",
        "email": "myemail@mail.com",
        "phone": "55509876",
        "owners": [
          "Mr Owner 1"
        ]
      }
      """
    And This company exists
      """
      {
        "name": "My Company 2",
        "address": "Company St, 2",
        "city": "London",
        "country": "UK",
        "email": "myemail2@mail.com",
        "phone": "44409876",
        "owners": [
          "Mr Owner 2",
          "Mr Owner 3"
        ]
      }
      """
    When I make a GET request to '/companies'
    Then response status is 200
    And content type is 'application/json'
    And the response list contains 2 companies
    And the response list contains this expected company
      | name     | My Company 2       |
      | address  | Company St, 2      |
      | city     | London             |
      | country  | UK                 |
      | email    | myemail2@mail.com  |
      | phone    | 44409876           |
    And the company 'My Company 2' in the list has these owners
      | Mr Owner 2 |
      | Mr Owner 3 |