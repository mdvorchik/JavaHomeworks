package ru.sbt.mipt.homework.hw4.report;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class SimpleReportGenerator<T> implements ReportGenerator<T> {
    private final Class<T> clazz;
    private final Map<String, String> classFieldNameToUserFieldName;
    private File file;

    public SimpleReportGenerator(Class<T> clazz, Map<String, String> classFieldNameToUserFieldName) {
        this.clazz = clazz;
        this.classFieldNameToUserFieldName = classFieldNameToUserFieldName;
    }

    @Override
    public Report generate(List<T> entities) {
        List<Map<String, String>> rowReport = new ArrayList<>();
        List<Field> fields = extractFieldsFromClass();
        entities.forEach((entity) -> {
            Map<String, String> fieldNameToValueMap = new LinkedHashMap<>();
            fields.forEach((field) -> {
                try {
                    fieldNameToValueMap.put(classFieldNameToUserFieldName.getOrDefault(field.getName(), field.getName()),
                            String.valueOf(field.get(entity)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            rowReport.add(fieldNameToValueMap);
        });
        return new ExcelReport(clazz.getSimpleName(), rowReport, file);
    }

    public void setFile(File file) {
        this.file = file;
    }

    private List<Field> extractFieldsFromClass() {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        fields.forEach(field -> field.setAccessible(true));
        return fields;
    }
}
