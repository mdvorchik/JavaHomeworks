package ru.sbt.mipt.homework.hw4.report;

import java.io.OutputStream;
import java.util.Map;
import java.util.SortedSet;

public class ConsoleReport implements Report {
    private final String className;
    private final SortedSet<Map<String, String>> rowReport;

    public ConsoleReport(String className, SortedSet<Map<String, String>> rowReport) {
        this.className = className;
        this.rowReport = rowReport;
    }

    @Override
    public byte[] asBytes() {
        return new byte[0];
    }

    @Override
    public void writeTo(OutputStream os) {

    }
}
