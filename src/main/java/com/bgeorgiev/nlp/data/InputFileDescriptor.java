package com.bgeorgiev.nlp.data;

import java.io.File;

public abstract class InputFileDescriptor {
  protected final File file;
  protected final String lang;

  protected InputFileDescriptor(File file, String lang) {
    this.file = file;
    this.lang = lang;
  }

  public File getFile() {
    return file;
  }

  public String getLanguage() {
    return lang;
  }
}
