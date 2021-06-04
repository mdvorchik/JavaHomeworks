package ru.sbt.mipt.homework.hw4.report;

import java.lang.reflect.Field;
import java.util.*;

public class SimpleReportGenerator<T> implements ReportGenerator<T> {
    private final Class<T> clazz;

    public SimpleReportGenerator(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Report generate(List<T> entities) {
        SortedSet<Map<String, String>> rowReport = new TreeSet<>();
        List<Field> fields = extractFieldsFromClass();
        entities.forEach((entity) -> {
            Map<String, String> fieldNameToValueMap = new LinkedHashMap<>();
            fields.forEach((field) -> {
                try {
                    fieldNameToValueMap.put(field.getName(), (String) field.get(entity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            rowReport.add(fieldNameToValueMap);
        });
        return new ConsoleReport(clazz.getSimpleName(), rowReport);
    }

    private List<Field> extractFieldsFromClass() {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        fields.forEach(field -> field.setAccessible(true));
        return fields;
    }
}
