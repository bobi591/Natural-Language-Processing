package com.bgeorgiev.nlp.models.train.impl;

import com.bgeorgiev.nlp.data.impl.CsvFileDescriptor;
import com.bgeorgiev.nlp.models.train.NLPModelTrainer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;

public class CsvCategorisationModelTrainer
    implements NLPModelTrainer<DoccatModel, CsvFileDescriptor> {
  @Override
  public DoccatModel trainModel(CsvFileDescriptor inputFileDescriptor) throws IOException {
    ArrayList<DocumentSample> documentSamples =
        createDocumentSamples(inputFileDescriptor.getFile(), inputFileDescriptor.getSeparator());
    ObjectStream<DocumentSample> documentSampleObjectStream =
        new ObjectStream<>() {
          int index = 0;

          @Override
          public DocumentSample read() {
            if (index < documentSamples.size()) {
              DocumentSample toReturn = documentSamples.get(index);
              index = index + 1;
              return toReturn;
            }
            // Stream is exhausted in this case...
            return null;
          }
        };
    System.out.printf(
        "Data was loaded with the following amount of samples %s ...%n", documentSamples.size());
    TrainingParameters trainingParameters = TrainingParameters.defaultParams();
    trainingParameters.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT"); // algorithm to use,
    // set by default but changeable here...
    DoccatModel model =
        DocumentCategorizerME.train(
            inputFileDescriptor.getLanguage(),
            documentSampleObjectStream,
            trainingParameters,
            new DoccatFactory());
    String modelFileName =
        inputFileDescriptor.getLanguage() + "_" + Instant.now().getEpochSecond() + ".model";
    model.serialize(new File(modelFileName));
    System.out.printf("Your model has been saved as %s file.%n", modelFileName);
    return model;
  }

  private ArrayList<DocumentSample> createDocumentSamples(File csvFile, String csvSeparator)
      throws FileNotFoundException {
    ArrayList<DocumentSample> posSamples = new ArrayList<>();
    Scanner csvFileScanner = new Scanner(csvFile);
    while (csvFileScanner.hasNextLine()) {
      String[] commandAndSentence = csvFileScanner.nextLine().split(csvSeparator);
      String sentence = commandAndSentence[0];
      String command = commandAndSentence[1];
      // TODO: Better sentence data normalization
      DocumentSample posSample = new DocumentSample(command, sentence.split(" "));
      posSamples.add(posSample);
    }
    return posSamples;
  }
}
