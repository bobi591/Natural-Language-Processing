package com.bgeorgiev.nlp.data.impl;

import com.bgeorgiev.nlp.data.InputFileDescriptor;
import java.io.File;

public class CsvFileDescriptor extends InputFileDescriptor {
  private final String separator;

  public CsvFileDescriptor(File file, String separator, String lang) {
    super(file, lang);
    this.separator = separator;
  }

  public String getSeparator() {
    return separator;
  }
}
