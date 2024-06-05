package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long cbu;

  private Double sum;

  private String type;

  public Transaction() {}

  public Transaction(Long cbu, Double sum, String type) {
    this.cbu = cbu;
    this.sum = sum;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long transactionID) {
    this.id = transactionID;
  }

  public Long getCbu() {
    return cbu;
  }

  public void setCbu(Long cbu) {
    this.cbu = cbu;
  }

  public Double getSum() {
    return sum;
  }

  public void setSum(Double amount) {
    this.sum = amount;
  }

  public String getType() {
    return type;
  }

  public void setType(String transactionType) {
    this.type = transactionType;
  }
}
