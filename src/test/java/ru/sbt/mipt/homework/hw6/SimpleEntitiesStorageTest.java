package ru.sbt.mipt.homework.hw6;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleEntitiesStorageTest {
    private final KeyExtractor<Account> accountKeyExtractor = (account) -> "Account_" + account.hashCode();
    private final KeyExtractor<Entry> entryKeyExtractor = (entry) -> "Entry_" + entry.hashCode();

    @Test
    public void saveAccount() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        //when
        storage.save(accounts.get(0));
        //verify
        assertEquals(accounts.get(0), storage.findByKey(accountKeyExtractor.extract(accounts.get(0))));
    }

    @Test
    public void saveBonusAccounts() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        BonusAccount bonusAccount1 = new BonusAccount((DebitCard) accounts.get(0), 10);
        BonusAccount bonusAccount2 = new BonusAccount((DebitCard) accounts.get(1), 10);
        //when
        storage.save(bonusAccount1);
        storage.save(bonusAccount2);
        //verify
        assertEquals(bonusAccount1, storage.findByKey(accountKeyExtractor.extract(bonusAccount1)));
        assertEquals(bonusAccount2, storage.findByKey(accountKeyExtractor.extract(bonusAccount2)));
    }

    @Test
    public void saveEntry() {
        //given
        List<Entry> entryList = setUpEntryList();
        SimpleEntitiesStorage<Entry> storage = new SimpleEntitiesStorage<>(entryKeyExtractor);
        //when
        storage.save(entryList.get(0));
        //verify
        assertEquals(entryList.get(0), storage.findByKey(entryKeyExtractor.extract(entryList.get(0))));
    }

    @Test
    public void saveAllAccounts() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        //when
        storage.saveAll(accounts);
        //verify
        assertTrue(storage.findAll().containsAll(accounts));
    }

    @Test
    public void findByKey() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        storage.saveAll(accounts);
        //verify
        assertEquals(accounts.get(0), storage.findByKey(accountKeyExtractor.extract(accounts.get(0))));
    }

    @Test
    public void findAll() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        storage.saveAll(accounts);
        //when
        List<Account> foundList = storage.findAll();
        //verify
        assertTrue(foundList.containsAll(accounts));
    }

    @Test
    public void deleteByKey() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        storage.save(accounts.get(0));
        //when
        storage.deleteByKey(accountKeyExtractor.extract(accounts.get(0)));
        //verify
        assertNull(storage.findByKey(accountKeyExtractor.extract(accounts.get(0))));
    }

    @Test
    public void deleteAll() {
        //given
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> accounts = prepareAccountList(transactionManager);
        SimpleEntitiesStorage<Account> storage = new SimpleEntitiesStorage<>(accountKeyExtractor);
        storage.saveAll(accounts);
        //when
        storage.deleteAll(accounts);
        //verify
        assertEquals(Collections.emptyList(), storage.findAll());
    }

    private TransactionManager prepareTransactionManager() {
        return new TransactionManager(new HashMap<>(), new ArrayList<>());
    }

    private List<Account> prepareAccountList(TransactionManager transactionManager) {
        List<Account> debitCards = new ArrayList<>();
        debitCards.add(new DebitCard(1, transactionManager));
        debitCards.add(new DebitCard(2, transactionManager));
        return debitCards;
    }

    private List<Transaction> prepareTransactionList(TransactionManager transactionManager, List<Account> debitCards) {
        List<Transaction> transactions = new ArrayList<>();
        if (debitCards.size() > 1) {
            transactions.add(transactionManager.createTransaction(333, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(-777, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(-222, debitCards.get(0), debitCards.get(1)));
            transactions.add(transactionManager.createTransaction(888, debitCards.get(1), debitCards.get(0)));
        }
        return transactions;
    }

    private List<Entry> prepareEntryList(List<Transaction> transactionList, List<Account> debitCardList) {
        List<Entry> entries = new ArrayList<>();
        if (transactionList.size() > 3 && !debitCardList.isEmpty()) {
            entries.add(new Entry(debitCardList.get(0), transactionList.get(0), transactionList.get(0).getAmount(), LocalDateTime.now()));
            entries.add(new Entry(debitCardList.get(0), transactionList.get(1), transactionList.get(1).getAmount(), LocalDateTime.now().plusMinutes(1)));
            entries.add(new Entry(debitCardList.get(0), transactionList.get(2), transactionList.get(2).getAmount(), LocalDateTime.now().plusDays(5)));
            entries.add(new Entry(debitCardList.get(0), transactionList.get(3), transactionList.get(3).getAmount(), LocalDateTime.now().plusDays(15)));
        }
        return entries;
    }

    private List<Entry> setUpEntryList() {
        TransactionManager transactionManager = prepareTransactionManager();
        List<Account> debitCardList = prepareAccountList(transactionManager);
        List<Transaction> transactionList = prepareTransactionList(transactionManager, debitCardList);
        return prepareEntryList(transactionList, debitCardList);
    }
}