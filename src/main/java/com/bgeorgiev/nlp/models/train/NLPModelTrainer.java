package com.bgeorgiev.nlp.models.train;

import com.bgeorgiev.nlp.data.InputFileDescriptor;
import opennlp.tools.util.model.BaseModel;

public interface NLPModelTrainer<M extends BaseModel, F extends InputFileDescriptor> {
  M trainModel(F inputFileDescriptor) throws Exception;
}
