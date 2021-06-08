package ru.sbt.mipt.homework.hw5;

import java.time.LocalDate;
import java.util.Collection;

public class BonusAccount implements Account {
    private final DebitCard debitCard;
    private final double bonusPercent;
    private double bonusScore = 0;

    public BonusAccount(DebitCard debitCard, double bonusPercent) {
        this.debitCard = debitCard;
        this.bonusPercent = bonusPercent;
    }

    @Override
    public boolean withdraw(double amount, Account beneficiary) {
        boolean result = debitCard.withdraw(amount, beneficiary);
        if (result) bonusScore += amount * bonusPercent * 0.01;
        return result;
    }

    @Override
    public boolean withdrawCash(double amount) {
        return debitCard.withdrawCash(amount);
    }

    @Override
    public boolean addCash(double amount) {
        return debitCard.addCash(amount);
    }

    @Override
    public void addEntry(Entry entry) {
        debitCard.addEntry(entry);
    }

    @Override
    public boolean add(double amount) {
        return debitCard.add(amount);
    }

    @Override
    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return debitCard.history(from, to);
    }

    @Override
    public double balanceOn(LocalDate date) {
        return debitCard.balanceOn(date);
    }

    @Override
    public void rollbackLastTransaction() {
        debitCard.rollbackLastTransaction();
    }

    public double getBonusScore() {
        return bonusScore;
    }
}
