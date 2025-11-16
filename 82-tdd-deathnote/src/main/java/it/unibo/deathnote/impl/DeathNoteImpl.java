package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

/**
 * This class implements the DeathNote interface.
 */
public class DeathNoteImpl implements DeathNote {

    public static final int ADD_CAUSE_TIME = 40;
    public static final int ADD_DETAILS_TIME = 6000 + ADD_CAUSE_TIME;

    private final Map<String, Death> nameWritten = new HashMap<>();
    private String lastNameAdded;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("The rule number is not valid!");
        }
        return RULES.get(ruleNumber - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        nameWritten.put(name, new Death());
        this.lastNameAdded = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        throwException(cause);
        final boolean operationPermitted = accetableTime(lastNameAdded, ADD_CAUSE_TIME);
        if (operationPermitted) {
            nameWritten.get(lastNameAdded).setCause(cause);
        }
        return operationPermitted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(final String details) {
        throwException(details);
        final boolean operationPermitted = accetableTime(lastNameAdded, ADD_DETAILS_TIME);
        if (operationPermitted) {
            nameWritten.get(lastNameAdded).setDetails(details);
        }
        return operationPermitted;
    }

    private boolean accetableTime(final String lastName, final int maxTime) {
        return System.currentTimeMillis() - nameWritten.get(lastName).timeNameWritten <= maxTime;
    }

    private void throwException(final String notnull) {
        if (nameWritten.isEmpty() || notnull == null) {
            throw new IllegalStateException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(final String name) {
        return nameWritten.get(name).cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(final String name) {
        return nameWritten.get(name).details;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(final String name) {
        return nameWritten.containsKey(name);
    }

    private static final class Death {
        private String cause;
        private String details;
        private final long timeNameWritten;

        Death() {
            this.cause = "Heart Attack";
            this.details = "";
            this.timeNameWritten = System.currentTimeMillis();
        }

        private void setCause(final String cause) {
            this.cause = cause;
        }

        private void setDetails(final String details) {
            this.details = details;
        }

    }

}
