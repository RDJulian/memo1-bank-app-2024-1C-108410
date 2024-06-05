package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

  /* Transaction no lleva registro de las promos.
   * Esto funciona para una sola promo que depende de la
   * operación deposit.
   * De haber más promos, será necesario guardar qué promo
   * se aplicó en una transacción para luego hacer rollback
   * correctamente. */

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

  public Long getCbu() {
    return cbu;
  }

  public void setCbu(Long cbu) {
    this.cbu = cbu;
  }

  public Double getSum() {
    return sum;
  }

  public String getType() {
    return type;
  }
}
