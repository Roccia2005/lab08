package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {

    private static final String PAOLO = "Paolo";
    private static final String GIORGIO = "Giorgio";
    private static final String CAUSE_OF_DEATH = "Heart Attack";
    private static final String SECOND_CAUSE_OF_DEATH = "Karting Incident";
    private static final int NUMER_OF_RULES = 13;

    private DeathNoteImpl myDeathNote;

    @BeforeEach
    void setUp() {
        myDeathNote = new DeathNoteImpl();
    }

    private void checkRuleDoesntExist(final int... numbers) {
        for (final int number: numbers) {
            assertThrowsExactly(
                IllegalArgumentException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        myDeathNote.getRule(number);
                    }          
                }
            );
        }
    }

    @Test
    void testRuleNumber() {
        checkRuleDoesntExist(-1, 0, 1);
        /* 
            Metodo un po più bruttino
         *  try {
            myDeathNote.getRule(-1);
            Assertions.fail("The rule -1 should give an exception, but it does not");
            } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            }

        */

    }

    @Test
    void testRuleText() {
        for (int i = 1; i < NUMER_OF_RULES; i++) {
            final String rule = myDeathNote.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    void testWriteName() {
        assertFalse(myDeathNote.isNameWritten(PAOLO));
        myDeathNote.writeName(PAOLO); 
        assertTrue(myDeathNote.isNameWritten(PAOLO));
        assertFalse(myDeathNote.isNameWritten(GIORGIO)); 
        assertFalse(myDeathNote.isNameWritten(""));
    }

    @Test
    void testDeathCause() throws InterruptedException {
        assertThrowsExactly(
            IllegalStateException.class,
            new Executable() {
                @Override
                public void execute() throws Throwable {
                    myDeathNote.writeDeathCause(CAUSE_OF_DEATH);
                }  
            }
        );
        myDeathNote.writeName(PAOLO);
        assertEquals(myDeathNote.getDeathCause(PAOLO), CAUSE_OF_DEATH);
        myDeathNote.writeName(GIORGIO);
        assertTrue(myDeathNote.writeDeathCause(SECOND_CAUSE_OF_DEATH));
        assertEquals(myDeathNote.getDeathCause(GIORGIO), SECOND_CAUSE_OF_DEATH);
        Thread.sleep(100);
        assertFalse(myDeathNote.writeDeathCause(CAUSE_OF_DEATH));
        assertEquals(myDeathNote.getDeathCause(GIORGIO), SECOND_CAUSE_OF_DEATH);
    }
}
