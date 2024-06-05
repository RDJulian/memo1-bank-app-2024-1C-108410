package com.aninfo.integration.cucumber;

import com.aninfo.exceptions.AccountNotFoundException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.exceptions.NegativeSumException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest extends TransactionIntegrationServiceTest {

  private Transaction transaction;
  private Queue<Transaction> transactions;
  private Account account;
  private AccountNotFoundException anfe;
  private InsufficientFundsException ife;
  private NegativeSumException nse;
  private InvalidTransactionTypeException itte;

  @Before
  public void setUp() {
    transactions = new LinkedList<>();
  }

  @BeforeEach
  public void beforeEach() {
    transactions = new LinkedList<>();
  }

  @Given("^An account with a balance of (\\d+)$")
  public void anAccountWithABalanceOf(Double balance) {
    account = createAccount(balance);
  }

  @When("^Creating (.*) transaction with sum (.*)$")
  public void creatingTypeTransactionWithSum(String type, Double sum) {
    try {
      transaction = createTransaction(account.getCbu(), sum, type);
      transactions.add(transaction);
    } catch (AccountNotFoundException anfe) {
      this.anfe = anfe;
    } catch (InsufficientFundsException ife) {
      this.ife = ife;
    } catch (NegativeSumException nse) {
      this.nse = nse;
    } catch (InvalidTransactionTypeException itte) {
      this.itte = itte;
    }
  }

  @Then("^A transaction is succesfully created$")
  public void aTransactionIsSuccesfullyCreated() {
    assertNotNull(transaction);
  }

  @And("^Account balance is (\\d+)$")
  public void accountBalanceIsTotal(Double balance) {
    Optional<Account> targetAccount = getAccount(account.getCbu());
    assertTrue(targetAccount.isPresent());
    assertEquals(balance, targetAccount.get().getBalance());
  }

  @Given("^No account is created$")
  public void noAccountIsCreated() {
    account = new Account(0.0);
  }

  @Then("^The transaction is denied due to non existent account$")
  public void theTransactionIsDeniedDueToNonExistentAccount() {
    assertNotNull(anfe);
  }

  @And("^No transaction is created$")
  public void noTransactionIsCreated() {
    assertNull(transaction);
  }

  @Then("^The transaction is denied due to insufficient funds$")
  public void theTransactionIsDeniedDueToInsufficientFunds() {
    assertNotNull(ife);
  }

  @Then("^The transaction is denied due to negative sum$")
  public void theTransactionIsDeniedDueToNegativeSum() {
    assertNotNull(nse);
  }

  @Then("^The transaction is denied due to invalid transaction type$")
  public void theTransactionIsDeniedDueToInvalidTransactionType() {
    assertNotNull(itte);
  }

  @And("^Deleting the transaction$")
  public void deletingTheTransaction() {
    try {
      deleteTransaction(transaction.getId());
    } catch (InsufficientFundsException ife) {
      this.ife = ife;
    } catch (AccountNotFoundException anfe) {
      this.anfe = anfe;
    }
  }

  @And("^The transaction is deleted$")
  public void theTransactionIsDeleted() {
    assertTrue(getTransaction(transaction.getId()).isEmpty());
  }

  @And("^Deleting the account$")
  public void deletingTheAccount() {
    deleteAccount(account.getCbu());
  }

  @And("^Deleting the first transaction$")
  public void deletingTheFirstTransaction() {
    if (!transactions.isEmpty()) {
      try {
        deleteTransaction(transactions.peek().getId());
      } catch (InsufficientFundsException ife) {
        this.ife = ife;
      }
    }
  }

  @And("^The first transaction is not deleted$")
  public void theFirstTransactionIsNotDeleted() {
    assertFalse(transactions.isEmpty());
    assertTrue(getTransaction(transactions.peek().getId()).isPresent());
  }

  @Then("^The rollback is denied due to insufficient funds$")
  public void theRollbackIsDeniedDueToInsufficientFunds() {
    assertNotNull(ife);
  }
}
