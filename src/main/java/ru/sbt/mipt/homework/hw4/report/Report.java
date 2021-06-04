package ru.sbt.mipt.homework.hw4.report;

import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;

public interface Report {
    byte[] asBytes();

    void writeTo(OutputStream os) throws IOException, WriteException;
}
