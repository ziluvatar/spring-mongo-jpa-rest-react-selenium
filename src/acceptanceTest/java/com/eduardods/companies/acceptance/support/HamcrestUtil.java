package com.eduardods.companies.acceptance.support;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class HamcrestUtil {

  public static Matcher<Map<? extends String, ? extends String>> hasEntries(Map<String, String> map) {
    return allOf(entriesFrom(map));
  }

  private static List<Matcher<? super Map<? extends String, ? extends String>>> entriesFrom(Map<String, String> map) {
    return map.entrySet().stream()
      .map((e) -> hasEntry(e.getKey(), e.getValue()))
      .collect(Collectors.toList());
  }

  public static Matcher<Map<? extends String, ?>> notAnyKey(List<String> keys) {
    return not(anyOf(entriesFrom(keys)));
  }

  private static List<Matcher<? super Map<? extends String, ?>>> entriesFrom(List<String> keys) {
    return keys.stream()
      .map(Matchers::hasKey)
      .collect(Collectors.toList());
  }
}
