package com.sample.app;

import java.lang.String;
import java.lang.System;

public final class Test {
  {
    System.out.println("In initializer block");
  }

  public static void main(String[] args) {
    Test obj = new Test();
  }
}
