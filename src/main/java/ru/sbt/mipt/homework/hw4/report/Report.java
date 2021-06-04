package ru.sbt.mipt.homework.hw4.report;

import java.io.OutputStream;

public interface Report {
    byte[] asBytes();

    void writeTo(OutputStream os);
}
