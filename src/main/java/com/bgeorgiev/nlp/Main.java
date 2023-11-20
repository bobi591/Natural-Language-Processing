package com.bgeorgiev.nlp;

import com.bgeorgiev.nlp.data.impl.CsvFileDescriptor;
import com.bgeorgiev.nlp.models.consume.ProbabilityDifferenceAcceptance;
import com.bgeorgiev.nlp.models.consume.impl.CategorisationModelConsumer;
import com.bgeorgiev.nlp.models.train.NLPModelTrainer;
import com.bgeorgiev.nlp.models.train.impl.CsvCategorisationModelTrainer;
import java.io.*;
import java.util.Scanner;
import opennlp.tools.doccat.DoccatModel;

public class Main {
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(System.in);
    System.out.println("Enter the path of the CSV file. The CSV should be semicolon separated: ");
    File file = new File(in.nextLine());
    CsvFileDescriptor fileDescriptor = new CsvFileDescriptor(file, ";", "en");
    NLPModelTrainer<DoccatModel, CsvFileDescriptor> modelTrainer =
        new CsvCategorisationModelTrainer();
    DoccatModel model = modelTrainer.trainModel(fileDescriptor);
    CategorisationModelConsumer categorisationModelConsumer =
        new CategorisationModelConsumer(model);
    while (true) {
      System.out.println("Ask your question about Linux commands: ");
      System.out.println(
          "Predicted command: "
              + categorisationModelConsumer.predictMultiple(
                  in.nextLine().toLowerCase(), ProbabilityDifferenceAcceptance.STRICT));
    }
  }
}
