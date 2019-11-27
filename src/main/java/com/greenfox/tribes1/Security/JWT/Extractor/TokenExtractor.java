package com.greenfox.tribes1.Security.JWT.Extractor;

public interface TokenExtractor {
  String extract(String payload);
}
