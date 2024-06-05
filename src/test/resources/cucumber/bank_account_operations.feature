Feature: Bank account operations
  Checking bank account operations

  Scenario: Successfully withdraw money when balance is enough
    Given Account with a balance of 1000
    When Trying to withdraw 500
    Then Account balance should be 500

  Scenario: Cannot withdraw more money than the account balance
    Given Account with a balance of 1000
    When Trying to withdraw 1001
    Then Operation should be denied due to insufficient funds
    And Account balance should remain 1000

  Scenario: Successfully deposit money when sum is not negative
    Given Account with a balance of 1000
    When Trying to deposit 500
    Then Account balance should be 1500

  Scenario Outline: Cannot <operation> money when sum is negative
    Given Account with a balance of 200
    When Trying to <operation> -100
    Then Operation should be denied due to negative sum
    And Account balance should remain 200
    Examples:
      | operation |
      | deposit   |
      | withdraw  |

  Scenario Outline: Cannot <operation> money, non-existent account
    Given No account was created
    When Trying to <operation> 100
    Then Operation should be denied due to non-existent account
    Examples:
      | operation |
      | deposit   |
      | withdraw  |