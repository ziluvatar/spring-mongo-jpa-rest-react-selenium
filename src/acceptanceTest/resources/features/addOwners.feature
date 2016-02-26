Feature: Add owners to a company

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

  @owners-add
  Scenario: Add some owners to company
    When I make a POST request to '${location}/owners' with content-type 'application/json' and payload:
      """
      {
        "owners": [
          "Mr Owner 2"
        ]
      }
      """
    And response status is 200
    And I make a GET request to '${location}'
    Then the company has these owners
      | Mr Owner 1 |
      | Mr Owner 2 |

  @owners-duplicates
  Scenario: Owners list does not have duplicates
    When I make a POST request to '${location}/owners' with content-type 'application/json' and payload:
      """
      {
        "owners": [
          "Mr Owner 1"
        ]
      }
      """
    And response status is 200
    And I make a GET request to '${location}'
    Then the company has this owner
      | Mr Owner 1 |

  @owners-not-found
  Scenario: Company not found
    When I make a POST request to '/companies/aCompanyId/owners' with content-type 'application/json' and payload:
      """
      {
        "owners": [
          "Mr Owner 1"
        ]
      }
      """
    And response status is 404
