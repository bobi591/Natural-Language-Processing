package com.bgeorgiev.nlp.models.consume;

import java.util.List;
import opennlp.tools.util.model.BaseModel;

public abstract class NLPModelConsumer<T extends BaseModel, R, I> {
  protected final T model;

  protected NLPModelConsumer(T model) {
    this.model = model;
  }

  public abstract R predict(I input);

  public abstract List<R> predictMultiple(I input, ProbabilityDifferenceAcceptance acceptance);
}
