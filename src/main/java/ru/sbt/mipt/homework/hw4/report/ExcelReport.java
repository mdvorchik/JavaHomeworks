package ru.sbt.mipt.homework.hw4.report;

import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelReport implements Report {
    private final String className;
    private final List<Map<String, String>> rowReport;
    private final File file;

    public ExcelReport(String className, List<Map<String, String>> rowReport, File file) {
        this.className = className;
        this.rowReport = rowReport;
        this.file = file;
    }

    @Override
    public byte[] asBytes() {
        return (className + rowReport.toString()).getBytes();
    }

    @Override
    public void writeTo(OutputStream os) throws IOException, WriteException {
        PrintStream printStream = new PrintStream(os);
        printStream.println(className);
        printStream.println(rowReport.toString());
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("Sheet 1", 0);
        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setWrap(true);
        for (int i = 0; i < rowReport.size() * 3; i += 3) {
            sheet.addCell(new Label(0, i, className, cellFormat));
            Map<String, String> map = rowReport.get(i / 3);
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            for (int j = 0; iterator.hasNext(); ++j) {
                Map.Entry<String, String> entry = iterator.next();
                sheet.addCell(new Label(j, i + 1, entry.getKey(), cellFormat));
                sheet.addCell(new Label(j, i + 2, entry.getValue(), cellFormat));
            }
        }
        workbook.write();
        workbook.close();
    }
}
