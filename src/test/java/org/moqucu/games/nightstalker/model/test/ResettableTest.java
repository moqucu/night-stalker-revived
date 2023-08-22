package org.moqucu.games.nightstalker.model.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.model.Resettable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ResettableTest {

    private final Resettable resettable = mock(Resettable.class);

    @Test
    public void makeSureThatResetMethodExists() {

        doThrow(new RuntimeException("Nothing to reset!")).when(resettable).reset();

        Throwable throwable = assertThrows(
                RuntimeException.class,
                resettable::reset
        );
        assertThat(throwable.getMessage(), is("Nothing to reset!"));
    }
}
