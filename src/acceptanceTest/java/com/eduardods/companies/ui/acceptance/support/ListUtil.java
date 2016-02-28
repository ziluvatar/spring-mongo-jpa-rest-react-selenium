package com.eduardods.companies.ui.acceptance.support;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtil {

  public static <T, R> List<R> map(List<T> list, Function<T, R> mapFunction) {
    return list.stream().map(mapFunction).collect(Collectors.toList());

  }
}
