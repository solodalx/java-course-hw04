package ru.sbt.javacourse.hw04;


class AmountMod100Exception extends RuntimeException {
    @Override
    public String toString() {
        return "Сумма должна быть кратна 100";
    }
}

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
    public int check() {
        pinValidator.validate();
        return server.balance();
    }

    @Override
    public int cash(int amount) {
        pinValidator.validate();
        if (amount % 100 != 0)
            throw new AmountMod100Exception();

        if (amount > 0) {
            return server.put(amount);
        }
        else if (amount < 0) {
            return server.withdraw(-amount);
        }
        else {
            throw new AmountMod100Exception();
        }
    }
}
