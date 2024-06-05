Feature: Bank transaction

  Scenario Outline: Successfully create <type> transaction
    Given An account with a balance of 1000
    When Creating <type> transaction with sum 500
    Then A transaction is succesfully created
    And Account balance is <total>
    Examples:
      | type     | total |
      | deposit  | 1500  |
      | withdraw | 500   |

  Scenario Outline: Cannot create transaction, non existent account
    Given No account is created
    When Creating <type> transaction with sum 500
    Then The transaction is denied due to non existent account
    And No transaction is created
    Examples:
      | type     |
      | deposit  |
      | withdraw |

  Scenario: Cannot create transaction, withdraw more money than the account balance
    Given An account with a balance of 1000
    When Creating withdraw transaction with sum 1500
    Then The transaction is denied due to insufficient funds
    And Account balance is 1000
    And No transaction is created

  Scenario Outline: Cannot create transaction, <type> sum is negative
    Given An account with a balance of 1000
    When Creating <type> transaction with sum -500
    Then The transaction is denied due to negative sum
    And Account balance is 1000
    And No transaction is created
    Examples:
      | type     |
      | deposit  |
      | withdraw |

  Scenario: Cannot create transaction, invalid transaction type
    Given An account with a balance of 1000
    When Creating transfer transaction with sum 1500
    Then The transaction is denied due to invalid transaction type
    And Account balance is 1000
    And No transaction is created

  Scenario Outline: Successfully rollback <type> transaction
    Given An account with a balance of 1000
    When Creating <type> transaction with sum 500
    And Deleting the transaction
    Then Account balance is 1000
    And The transaction is deleted
    Examples:
      | type     |
      | deposit  |
      | withdraw |

  Scenario Outline: Successfully delete <type> transaction by deleted account
    Given An account with a balance of 1000
    When Creating <type> transaction with sum 500
    And Deleting the account
    And Deleting the transaction
    Then The transaction is deleted
    Examples:
      | type     |
      | deposit  |
      | withdraw |

  Scenario: Cannot rollback transaction, insufficient funds
    Given An account with a balance of 1000
    When Creating deposit transaction with sum 1500
    And Creating withdraw transaction with sum 2500
    And Deleting the first transaction
    Then The rollback is denied due to insufficient funds
    And Account balance is 0
    And The first transaction is not deleted