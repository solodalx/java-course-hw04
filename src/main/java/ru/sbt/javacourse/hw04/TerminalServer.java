package ru.sbt.javacourse.hw04;


class NotEnoughMoneyException extends RuntimeException {
    @Override
    public String toString() {
        return "Недостаточно средств на счете";
    }
}

class ServerDisabledException extends RuntimeException {
    @Override
    public String toString() {
        return "Сервер недоступен";
    }
}

public interface TerminalServer {
    public int balance();
    public int put(int amount);
    public int withdraw(int amount);
}
