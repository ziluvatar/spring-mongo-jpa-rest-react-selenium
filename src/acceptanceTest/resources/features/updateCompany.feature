Feature: Update Company

  Background:
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

  @update
  Scenario: Update some fields successfully
    When I make a PUT request to '${location}' with content-type 'application/json' and payload:
      """
      {
        "name": "My Company 1",
        "address": "Another address",
        "city": "New York",
        "country": "USA",
        "email": "myemail@mail.com",
        "phone": "55509876",
        "owners": [
          "Mr Another Owner"
        ]
      }
      """
    And response status is 200
    And I make a GET request to '${location}'
    Then the response body contains the expected company
      | name     | My Company 1       |
      | address  | Another address    |
      | city     | New York           |
      | country  | USA                |
      | email    | myemail@mail.com   |
      | phone    | 55509876           |
    And the company has this owner
      | Mr Another Owner |


  @update-not-found
  Scenario: Company not found
    Given No company exists
    When I make a PUT request to '/companies/aId' with content-type 'application/json' and payload:
      """
      {
        "name": "My Company 1",
        "address": "Company St, 1",
        "city": "New York",
        "country": "USA",
        "owners": [
          "Mr Owner 1"
        ]
      }
      """
    Then response status is 404

  @update-missing
  Scenario: Update with some required fields missing
    When I make a PUT request to '${location}' with content-type 'application/json' and payload:
      """
      {
        "name": "My Company 1",
        "address": "Another address",
        "country": "USA",
        "email": "myemail@mail.com",
        "phone": "55509876",
        "owners": [
          "Mr Another Owner"
        ]
      }
      """
    Then response status is 400
    And response contains this error
      | field   | code      |
      | city    | NotEmpty  |


  @update-email
  Scenario: Update with invalid email
    When I make a PUT request to '${location}' with content-type 'application/json' and payload:
      """
      {
        "name": "My Company 1",
        "address": "Another address",
        "country": "USA",
        "city": "New York",
        "email": "no-valid-email",
        "phone": "55509876",
        "owners": [
          "Mr Another Owner"
        ]
      }
      """
    Then response status is 400
    And response contains this error
      | field    | code     |
      | email    | Email    |


  @update-deleting
  Scenario: Update deleting some no required fields
    When I make a PUT request to '${location}' with content-type 'application/json' and payload:
      """
      {
        "name": "My Company 1",
        "address": "Another address",
        "city": "New York",
        "country": "USA",
        "owners": [
          "Mr Another Owner"
        ]
      }
      """
    And response status is 200
    And I make a GET request to '${location}'
    Then the response body contains the expected company
      | name     | My Company 1       |
      | address  | Another address    |
      | city     | New York           |
      | country  | USA                |
    And the response body does not contain these fields
      | email |
      | phone |
    And the company has this owner
      | Mr Another Owner |


  @update-id
  Scenario: Update even sending the id field
    When I make a PUT request to '${location}' with content-type 'application/json' and payload:
      """
      {
        "id": "optional-id-to-be-ignored",
        "name": "My Company 1",
        "address": "Another address",
        "city": "New York",
        "country": "USA",
        "owners": [
          "Mr Another Owner"
        ]
      }
      """
    And response status is 200
    And I make a GET request to '${location}'
    Then the response body contains the expected company
      | name     | My Company 1       |
      | address  | Another address    |
      | city     | New York           |
      | country  | USA                |
    And the company has this owner
      | Mr Another Owner |
