package com.sample.app;

import java.lang.String;

public final class Employee<T> {
  String name;

  T id;

  public String getDetails(T id, final String name) {
    return "Hi";
  }
}
