Feature: Bank account promo, get 10% extra in your $2000+ deposits, up to $500

  Scenario: Successfully promo applied, cap not reached.
    Given Account with a balance of 0
    When Trying to deposit 2000
    Then Account balance should be 2200

  Scenario: Successfully promo applied, cap reached.
    Given Account with a balance of 0
    When Trying to deposit 6000
    Then Account balance should be 6500

  Scenario: Promo not applied
    Given Account with a balance of 0
    When Trying to deposit 1500
    Then Account balance should be 1500

  Scenario Outline: Successfully create <type> transaction, promo applied
    Given An account with a balance of 3000
    When Creating <type> transaction with sum 2000
    Then A transaction is succesfully created
    And Account balance is <total>
    Examples:
      | type     | total |
      | deposit  | 5200  |
      | withdraw | 1000  |

  Scenario Outline: Successfully rollback <type> transaction, promo applied
    Given An account with a balance of 3000
    When Creating <type> transaction with sum 2000
    And Deleting the transaction
    Then Account balance is 3000
    And The transaction is deleted
    Examples:
      | type     |
      | deposit  |
      | withdraw |