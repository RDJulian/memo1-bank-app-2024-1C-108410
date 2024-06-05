package com.aninfo.integration.cucumber;

import com.aninfo.exceptions.AccountNotFoundException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.NegativeSumException;
import com.aninfo.model.Account;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountOperationsTest extends AccountIntegrationServiceTest {

  private Account account;
  private InsufficientFundsException ife;
  private NegativeSumException nse;
  private AccountNotFoundException anfe;

  @Before
  public void setup() {
    System.out.println("Before any test execution");
  }

  @Given("^Account with a balance of (\\d+)$")
  public void account_with_a_balance_of(Double balance) {
    account = createAccount(balance);
  }

  @When("^Trying to withdraw (.*)$")
  public void trying_to_withdraw(Double sum) {
    try {
      account = withdraw(account, sum);
    } catch (InsufficientFundsException ife) {
      this.ife = ife;
    } catch (NegativeSumException nse) {
      this.nse = nse;
    } catch (AccountNotFoundException anfe) {
      this.anfe = anfe;
    }
  }

  @When("^Trying to deposit (.*)$")
  public void trying_to_deposit(Double sum) {
    try {
      account = deposit(account, sum);
    } catch (NegativeSumException nse) {
      this.nse = nse;
    } catch (AccountNotFoundException anfe) {
      this.anfe = anfe;
    }
  }

  @Then("^Account balance should be (\\d+)$")
  public void account_balance_should_be(Double balance) {
    assertEquals(balance, account.getBalance());
  }

  @Then("^Operation should be denied due to insufficient funds$")
  public void operation_should_be_denied_due_to_insufficient_funds() {
    assertNotNull(ife);
  }

  @Then("^Operation should be denied due to negative sum$")
  public void operation_should_be_denied_due_to_negative_sum() {
    assertNotNull(nse);
  }

  @And("^Account balance should remain (\\d+)$")
  public void account_balance_should_remain(Double balance) {
    assertEquals(balance, account.getBalance());
  }

  @Given("^No account was created$")
  public void noAccountWasCreated() {
    account = new Account(0.0);
  }

  @Then("^Operation should be denied due to non-existent account$")
  public void operationShouldBeDeniedDueToNonExistentAccount() {
    assertNotNull(anfe);
  }

  @After
  public void tearDown() {
    System.out.println("After all test execution");
  }
}
