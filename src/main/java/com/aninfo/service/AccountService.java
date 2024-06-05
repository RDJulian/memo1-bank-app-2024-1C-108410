package com.aninfo.service;

import com.aninfo.exceptions.AccountNotFoundException;
import com.aninfo.exceptions.NegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

  @Autowired private AccountRepository accountRepository;

  public Account createAccount(Account account) {
    return accountRepository.save(account);
  }

  public Collection<Account> getAccounts() {
    return accountRepository.findAll();
  }

  public Optional<Account> findById(Long cbu) {
    return accountRepository.findById(cbu);
  }

  public void save(Account account) {
    accountRepository.save(account);
  }

  public void deleteById(Long cbu) {
    accountRepository.deleteById(cbu);
  }

  /* Supuestos: no se puede retirar un monto negativo.
   * No se puede retirar o depositar a una cuenta no existente. */

  public Account withdraw(Long cbu, Double sum) {
    if (sum <= 0) {
      throw new NegativeSumException("Cannot withdraw negative sums");
    }

    Account account = accountRepository.findAccountByCbu(cbu);

    if (account == null) {
      throw new AccountNotFoundException("Cannot withdraw from non-existent account");
    }

    if (account.getBalance() < sum) {
      throw new InsufficientFundsException("Insufficient funds");
    }

    account.setBalance(account.getBalance() - sum);
    accountRepository.save(account);

    return account;
  }

  public Account deposit(Long cbu, Double sum) {
    if (sum <= 0) {
      throw new NegativeSumException("Cannot deposit negative sums");
    }

    Account account = accountRepository.findAccountByCbu(cbu);

    if (account == null) {
      throw new AccountNotFoundException("Cannot deposit to non-existent account");
    }

    account.setBalance(account.getBalance() + sum);
    accountRepository.save(account);

    return account;
  }
}
