package com.aninfo.repository;

import com.aninfo.model.Transaction;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  Transaction findTransactionById(Long id);

  @Override
  List<Transaction> findAll();

  @Query("SELECT t FROM Transaction t WHERE t.cbu = :cbu")
  Collection<Transaction> findTransactionsByCbu(@Param("cbu") Long cbu);
}
