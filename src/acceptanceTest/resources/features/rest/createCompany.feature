Feature: Create a company

  @create
  Scenario: Create a new company with all fields
    Given No company exists
    When I make a POST request to '/companies' with content-type 'application/json' and payload:
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
    Then response status is 201
    And response contains a location header with the uri to the created resource

  @create-required
  Scenario: Create a new company with only required fields
    Given No company exists
    When I make a POST request to '/companies' with content-type 'application/json' and payload:
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
    Then response status is 201
    And response contains a location header with the uri to the created resource

  @create-missing
  Scenario: Error creating a new company with required fields missing
    Given No company exists
    When I make a POST request to '/companies' with content-type 'application/json' and payload:
      """
      {
        "email": "myemail@mail.com",
        "phone": "55509876",
        "owners": []
      }
      """
    Then response status is 400
    And content type is 'application/json'
    And response contains these errors
      | field    | code      |
      | name     | NotEmpty  |
      | address  | NotEmpty  |
      | city     | NotEmpty  |
      | country  | NotEmpty  |
      | owners   | NotEmpty  |

  @create-email
  Scenario: Error creating a new company with invalid email
    Given No company exists
    When I make a POST request to '/companies' with content-type 'application/json' and payload:
      """
      {
        "name": "My Company 1",
        "address": "Company St, 1",
        "city": "New York",
        "country": "USA",
        "email": "myemail-mail.com",
        "phone": "55509876",
        "owners": [
          "Mr Owner 1"
        ]
      }
      """
    Then response status is 400
    And content type is 'application/json'
    And response contains these errors
      | field    | code    |
      | email    | Email   |