package ru.sbt.mipt.homework.hw4.report;

import java.util.List;

public interface ReportGenerator<T> {
    Report generate(List<T> entities);
}
