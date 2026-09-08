package com.sample.app.keys;

import com.sample.app.enums.RequestCategory;
import dev.langchain4j.agentic.declarative.TypedKey;

public class Category implements TypedKey<RequestCategory> {

  @Override
  public RequestCategory defaultValue() {
    return RequestCategory.UNKNOWN;
  }
}
