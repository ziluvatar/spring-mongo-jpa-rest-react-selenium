Feature: Get a detailed company

  Scenario: Get a company
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
    And the resource Location header is saved in '${location}'
    When I make a GET request to '${location}'
    Then response status is 200
    And content type is 'application/json'
    And the response body contains the expected company
      | name     | My Company 1       |
      | address  | Company St, 1      |
      | city     | New York           |
      | country  | USA                |
      | email    | myemail@mail.com   |
      | phone    | 55509876           |
    And the company has these owners
      | Mr Owner 1 |


  Scenario: Company not found
    Given No company exists
    When I make a GET request to '/companies/aId'
    Then response status is 404