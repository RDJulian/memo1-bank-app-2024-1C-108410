package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class TransactionIntegrationServiceTest {

  @Autowired AccountService accountService;
  @Autowired TransactionService transactionService;

  Account createAccount(Double balance) {
    return accountService.createAccount(new Account(balance));
  }

  Optional<Account> getAccount(Long cbu) {
    return accountService.findById(cbu);
  }

  void deleteAccount(Long cbu) {
    accountService.deleteById(cbu);
  }

  Transaction createTransaction(Long cbu, Double sum, String type) {
    return transactionService.createTransaction(new Transaction(cbu, sum, type));
  }

  Optional<Transaction> getTransaction(Long id) {
    return transactionService.getTransactionById(id);
  }

  void deleteTransaction(Long id) {
    transactionService.deleteTransaction(id);
  }
}
