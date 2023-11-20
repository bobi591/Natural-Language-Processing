package com.bgeorgiev.nlp.models.consume.impl;

import com.bgeorgiev.nlp.models.consume.NLPModelConsumer;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;

public class CategorisationModelConsumer extends NLPModelConsumer<DoccatModel, String, String> {
  public CategorisationModelConsumer(DoccatModel model) {
    super(model);
  }

  @Override
  public String predict(String inputSentence) {
    DocumentCategorizer documentCategorizer = new DocumentCategorizerME(this.model);
    double[] outcomes = documentCategorizer.categorize(inputSentence.split(" "));
    return documentCategorizer.getBestCategory(outcomes);
  }
}
