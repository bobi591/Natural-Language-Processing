package com.bgeorgiev.nlp.models.consume;

import opennlp.tools.util.model.BaseModel;

public abstract class NLPModelConsumer<T extends BaseModel, R, I> {
  protected final T model;

  protected NLPModelConsumer(T model) {
    this.model = model;
  }

  public abstract R predict(I input);
}
