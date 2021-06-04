package ru.sbt.mipt.homework.hw4.report;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class ConsoleReport implements Report {
    private final String className;
    private final List<Map<String, String>> rowReport;

    public ConsoleReport(String className, List<Map<String, String>> rowReport) {
        this.className = className;
        this.rowReport = rowReport;
    }

    @Override
    public byte[] asBytes() {
        return (className + rowReport.toString()).getBytes();
    }

    @Override
    public void writeTo(OutputStream os) {
        PrintStream printStream = new PrintStream(os);
        printStream.println(className);
        printStream.println(rowReport.toString());
    }
}
