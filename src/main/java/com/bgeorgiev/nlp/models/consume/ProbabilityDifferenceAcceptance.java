package com.bgeorgiev.nlp.models.consume;

public enum ProbabilityDifferenceAcceptance {
  NONE(0),
  VERY_STRICT(0.05),
  STRICT(0.1),
  LOOSE(0.15),
  VERY_LOOSE(0.20);
  public final double value;

  private ProbabilityDifferenceAcceptance(double acceptance) {
    this.value = acceptance;
  }
}
