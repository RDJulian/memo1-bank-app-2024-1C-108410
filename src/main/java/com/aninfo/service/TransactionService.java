package com.aninfo.service;

import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.model.Transaction;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {

  @Autowired private TransactionRepository transactionRepository;

  @Autowired private AccountService accountService;

  private static final String DEPOSIT = "deposit";

  private static final String WITHDRAW = "withdraw";

  @Transactional
  public Transaction createTransaction(Transaction transaction) {
    if (transaction.getType().equals(DEPOSIT)) {
      accountService.deposit(transaction.getCbu(), transaction.getSum());
    } else if (transaction.getType().equals(WITHDRAW)) {
      accountService.withdraw(transaction.getCbu(), transaction.getSum());
    } else {
      throw new InvalidTransactionTypeException("Invalid transaction type" + transaction.getType());
    }

    transactionRepository.save(transaction);

    return transaction;
  }

  public Collection<Transaction> getTransactionsByCbu(Long cbu) {
    return transactionRepository.findTransactionsByCbu(cbu);
  }

  public Optional<Transaction> getTransactionById(Long id) {
    return transactionRepository.findById(id);
  }

  public Collection<Transaction> getTransactions() {
    return transactionRepository.findAll();
  }

  @Transactional
  public void deleteTransaction(Long id) {
    Optional<Transaction> transaction = transactionRepository.findById(id);

    if (transaction.isPresent()) {
      try {
        rollback(transaction.get());
      } catch (InsufficientFundsException ife) {
        throw new InsufficientFundsException("Cannot rollback transaction: insufficient funds");
      }

      transactionRepository.delete(transaction.get());
    }
  }

  private void rollback(Transaction transaction) {
    if (transaction.getType().equals(DEPOSIT)) {
      accountService.rollbackDeposit(transaction.getCbu(), transaction.getSum());
    } else if (transaction.getType().equals(WITHDRAW)) {
      accountService.rollbackWithdraw(transaction.getCbu(), transaction.getSum());
    }
  }
}
