package org.moqucu.games.nightstalker.utility.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.utility.SystemWrapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isA;

public class SystemWrapperTest {

    private final SystemWrapper systemWrapper = new SystemWrapper();

    @Test
    public void isOfTypeSystemWrapper() {

        assertThat(systemWrapper, isA(SystemWrapper.class));
    }
}
