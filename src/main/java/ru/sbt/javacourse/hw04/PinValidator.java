package ru.sbt.javacourse.hw04;


import java.util.Date;

class AccountIsLockedException extends RuntimeException {
    private final Date locked, unlockAt;
    private final long lockTimeout;

    public AccountIsLockedException (Date locked, long lockTimeout) {
        this.locked = locked;
        this.lockTimeout = lockTimeout;
        this.unlockAt = new Date(locked.getTime() + lockTimeout);
    }

    @Override
    public String toString() {
        return "Счет заблокирован до " + unlockAt.toString();
    }
}

class PinIsNotEnteredException extends RuntimeException {
    @Override
    public String toString() {
        return "Корректный пин код не был введен";
    }
}

public class PinValidator {
    private final String pin;
    private int attempts = 0;
    private final int maxAttempts = 3;
    private final int lockTimeout = 5000;
    private Date locked;
    private boolean isPinEntered = false;

    public PinValidator(String pin) {
        this.pin = pin;
    }

    public boolean enterPin(String pin) {
        isPinEntered = false;
        Date now = new Date();

        if (locked != null && now.getTime() - locked.getTime() < lockTimeout) {
            throw new AccountIsLockedException(locked, lockTimeout);
        }
        locked = null;

        if (pin != this.pin) {
            if (++attempts >= maxAttempts) {
                attempts = 0;
                locked = now;
                throw new AccountIsLockedException(locked, lockTimeout);
            }
            return false;
        }

        attempts = 0;
        isPinEntered = true;
        return true;
    }

    public void validate() {
        if (!isPinEntered)
            throw new PinIsNotEnteredException();
    }
}
