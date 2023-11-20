package com.bgeorgiev.nlp.models.consume.impl;

import com.bgeorgiev.nlp.models.consume.NLPModelConsumer;
import com.bgeorgiev.nlp.models.consume.ProbabilityDifferenceAcceptance;
import java.util.*;
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

  @Override
  public List<String> predictMultiple(
      String inputSentence, ProbabilityDifferenceAcceptance acceptance) {
    DocumentCategorizer documentCategorizer = new DocumentCategorizerME(this.model);
    double[] outcomes = documentCategorizer.categorize(inputSentence.split(" "));
    if (acceptance != ProbabilityDifferenceAcceptance.NONE) {
      double[] sortedOutcomes = Arrays.copyOf(outcomes, outcomes.length);
      Arrays.sort(sortedOutcomes);
      List<String> closestCategories = new LinkedList<>();
      double bestOutcome = sortedOutcomes[sortedOutcomes.length - 1];
      for (int i = sortedOutcomes.length - 2; i >= 0; i--) {
        double neighbour = sortedOutcomes[i];
        if (bestOutcome - neighbour <= acceptance.value) {
          for (int closeNeighbourIndex = 0;
              closeNeighbourIndex < outcomes.length;
              closeNeighbourIndex++) {
            if (outcomes[closeNeighbourIndex] == neighbour) {
              closestCategories.add(documentCategorizer.getCategory(closeNeighbourIndex));
            }
          }
        }
      }
      // Do not forget to add the best outcome in the results!
      closestCategories.add(documentCategorizer.getBestCategory(outcomes));
      return closestCategories;
    }
    return Collections.singletonList(documentCategorizer.getBestCategory(outcomes));
  }
}
