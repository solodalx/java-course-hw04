package ru.sbt.javacourse.hw04;

import org.junit.Test;
import org.mockito.Mockito;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
            System.out.println(e);
            assertTrue(true);
//            throw e;
        }
        Thread.currentThread().sleep(1000);
        try {
            assertFalse(terminal.enterPin("qwerty"));
            assertTrue(false);
        } catch (AccountIsLockedException e) {
            assertTrue(e.toString().startsWith("Счет заблокирован"));
            System.out.println(e);
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
            terminal.check();
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            System.out.println(e);
            assertTrue(true);
        }
        try {
            terminal.cash(150);
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            System.out.println(e);
            assertTrue(true);
        }
        // С пин кодом
        terminal.enterPin("qwerty");
        try {
            terminal.check();
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            System.out.println(e);
            assertTrue(false);
        } catch (NullPointerException e) {
            System.out.println("Подключение к серверу отсутствует. Обратитесь к администратору.");
            assertTrue(true);
        }
        // С неправильным пин кодом
        terminal.enterPin("Qwerty");
        try {
            terminal.check();
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            System.out.println(e);
            assertTrue(true);
        }
        try {
            terminal.cash(-150);
            assertTrue(false);
        } catch (PinIsNotEnteredException e) {
            System.out.println(e);
            assertTrue(true);
        }
    }

    @Test
    public void test04_amountMod100() throws Exception {
        TerminalServer server = Mockito.mock(TerminalServer.class);
        when(server.balance()).thenReturn(300);

        Terminal terminal = new TerminalImpl(server, new PinValidator("qwerty"));
        terminal.enterPin("qwerty");

        try {
            terminal.cash(10);
            assertTrue(false);
        } catch (AmountMod100Exception e) {
            System.out.println(e);
            assertTrue(true);
        }
        try {
            terminal.cash(-110);
            assertTrue(false);
        } catch (AmountMod100Exception e) {
            System.out.println(e);
            assertTrue(true);
        }
        try {
            terminal.cash(0);
            assertTrue(false);
        } catch (AmountMod100Exception e) {
            System.out.println(e);
            assertTrue(true);
        }
    }

    @Test
    public void test05_checkBalance() throws Exception {
        TerminalServer server = Mockito.mock(TerminalServer.class);
        when(server.balance()).thenReturn(300);

        Terminal terminal = new TerminalImpl(server, new PinValidator("qwerty"));
        terminal.enterPin("qwerty");
        assertEquals(300, terminal.check());
    }

    @Test
    public void test06_cashOperations() throws Exception {
        TerminalServer server = Mockito.mock(TerminalServer.class);
        when(server.balance()).thenReturn(300);
        when(server.put(100)).thenReturn(400);
        when(server.withdraw(300)).thenReturn(100);
        when(server.withdraw(200)).thenThrow(new NotEnoughMoneyException());

        Terminal terminal = new TerminalImpl(server, new PinValidator("qwerty"));
        terminal.enterPin("qwerty");
        assertEquals(300, terminal.check());
        assertEquals(400, terminal.cash(100));
        assertEquals(100, terminal.cash(-300));
        try {
            terminal.cash(-200);
            assertTrue(false);
        } catch (NotEnoughMoneyException e) {
            System.out.println(e);
            assertTrue(true);
        }
    }


}
