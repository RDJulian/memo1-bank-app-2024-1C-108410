package com.aninfo.service;

import org.springframework.stereotype.Service;

@Service
public class PromoService {
  public Double applyPromo(Double sum) {
    double extra = sum >= 2000 ? Math.min(sum * 0.1, 500.0) : 0.0;
    return sum + extra;
  }
}
