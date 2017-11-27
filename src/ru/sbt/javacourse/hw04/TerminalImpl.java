package ru.sbt.javacourse.hw04;

public class TerminalImpl implements Terminal{
    private final TerminalServer server;
    private final PinValidator pinValidator;

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }

    @Override
    public boolean enterPin(String pin) {
        return pinValidator.enterPin(pin);
    }

    @Override
    public double checkAccount() {
        pinValidator.validate();
        return server.balance();
    }

    @Override
    public double changeAccount(double amount) {
        pinValidator.validate();
        if (amount > 0)
            return server.put(amount);
        else
            return server.withdraw(amount);
    }
}
