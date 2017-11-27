package ru.sbt.javacourse.hw04;


class NotEnoughMoneyException extends RuntimeException {
    @Override
    public String toString() {
        return "Не достаточно средств на счете";
    }
}

class ServerDisabledException extends RuntimeException {
    @Override
    public String toString() {
        return "Сервер недоступен";
    }
}

public interface TerminalServer {
    public double balance();
    public double put(double amount);
    public double withdraw(double amount);
}
