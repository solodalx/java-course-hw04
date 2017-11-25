package ru.sbt.javacourse.hw04;


import java.util.Date;

class AccountIsLockedException extends RuntimeException {
    private final int created;
    private final int lockTimeout;

    public AccountIsLockedException (int lockTimeout) {
//        created = DateTime
        this.lockTimeout = lockTimeout;
    }

    public String toString() {
        super.toString();
    }
}

public class PinValidator {
    private final String pin;
    private int attempts = 0;
    private final int maxAttempts = 3;
    private final int lockTimeout = 5000;

    public PinValidator(String pin) {
        this.pin = pin;
    }

    public boolean validate(String pin) {
        if (pin != this.pin) {
            if (++attempts >= maxAttempts)
                throw new AccountIsLockedException(lockTimeout);
            return false;
        }

        attempts = 0;
        return true;
    }
}
