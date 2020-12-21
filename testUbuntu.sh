#!/bin/bash
javac -cp bin:./junit-4.10.jar ./src/store/test/*.java -d bin
java -cp bin:./junit-4.10.jar org.junit.runner.JUnitCore store.test.BookTest store.test.ClientTest store.test.DVDTest store.test.GameTest store.test.PriceComparatorTest store.test.StoreTest store.test.TransactionTest