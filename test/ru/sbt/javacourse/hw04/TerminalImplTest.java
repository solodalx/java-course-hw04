package ru.sbt.javacourse.hw04;

import org.junit.Test;

import static org.junit.Assert.*;

public class TerminalImplTest {
    @Test
    public void test01_enterPin() throws Exception {
        Terminal terminal = new TerminalImpl(null, new PinValidator("qwerty"));
        assertTrue(terminal.enterPin("qwerty"));
        assertFalse(terminal.enterPin("123456"));
    }

    @Test //(expected = AccountIsLockedException.class)
    public void test02_lockUnlockAccout() throws Exception {
        Terminal terminal = new TerminalImpl(null, new PinValidator("qwerty"));
        assertTrue(terminal.enterPin("qwerty"));
        assertFalse(terminal.enterPin("123456"));
        assertTrue(terminal.enterPin("qwerty"));
        assertFalse(terminal.enterPin("123456"));
        assertFalse(terminal.enterPin("QWERTY"));
        try {
            assertFalse(terminal.enterPin("Qwerty"));
            assertTrue(false);
        } catch (AccountIsLockedException e) {
            assertTrue(e.toString().startsWith("Счет заблокирован"));
            assertTrue(true);
//            throw e;
        }
        Thread.currentThread().sleep(1000);
        try {
            assertFalse(terminal.enterPin("qwerty"));
            assertTrue(false);
        } catch (AccountIsLockedException e) {
            assertTrue(e.toString().startsWith("Счет заблокирован"));
            assertTrue(true);
        }
        Thread.currentThread().sleep(4000);
        assertFalse(terminal.enterPin("Qwerty"));
        assertTrue(terminal.enterPin("qwerty"));
    }

    @Test
    public void test03_withoutPin() throws Exception {
        Terminal terminal = new TerminalImpl(null, new PinValidator("qwerty"));
        // Без пин кода
        try {
            terminal.checkAccount();
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            assertTrue(true);
        }
        try {
            terminal.changeAccount(100.);
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            assertTrue(true);
        }
        // С пин кодом
        terminal.enterPin("qwerty");
        try {
            terminal.checkAccount();
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            assertTrue(false);
        } catch (NullPointerException e) {
            assertTrue(true);
        }
        // С неправильным пин кодом
        terminal.enterPin("Qwerty");
        try {
            terminal.checkAccount();
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            assertTrue(true);
        }
        try {
            terminal.changeAccount(100.);
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            assertTrue(true);
        }
    }

    @Test
    public void changeAccount() throws Exception {
    }

}