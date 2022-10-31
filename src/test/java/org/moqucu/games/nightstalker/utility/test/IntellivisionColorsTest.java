package org.moqucu.games.nightstalker.utility.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.utility.IntellivisionColors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IntellivisionColorsTest {

    @Test
    public void colorBlack() {

        assertThat(IntellivisionColors.COLOR_BLACK, is(0x131313));
    }

    @Test
    public void colorBlue() {

        assertThat(IntellivisionColors.COLOR_BLUE, is(0x0562F1));
    }

    @Test
    public void colorRed() {

        assertThat(IntellivisionColors.COLOR_RED, is(0xFF362F));
    }

    @Test
    public void colorLightBrown() {

        assertThat(IntellivisionColors.COLOR_TAN, is(0xDAA524));
    }

    @Test
    public void colorDarkGreen() {

        assertThat(IntellivisionColors.COLOR_DARK_GREEN, is(0x14A108));
    }

    @Test
    public void colorGreen() {

        assertThat(IntellivisionColors.COLOR_GREEN, is(0x25D226));
    }

    @Test
    public void colorYellow() {

        assertThat(IntellivisionColors.COLOR_YELLOW, is(0xFFE102));
    }

    @Test
    public void colorWhite() {

        assertThat(IntellivisionColors.COLOR_WHITE, is(0xFFFFFF));
    }

    @Test
    public void colorGrey() {

        assertThat(IntellivisionColors.COLOR_GRAY, is(0x727272));
    }

    @Test
    public void colorCyan() {

        assertThat(IntellivisionColors.COLOR_CYAN, is(0x2DD69A));
    }

    @Test
    public void colorOrange() {

        assertThat(IntellivisionColors.COLOR_ORANGE, is(0xFF7F16));
    }

    @Test
    public void colorBrown() {

        assertThat(IntellivisionColors.COLOR_BROWN, is(0x534E03));
    }

    @Test
    public void colorMagenta() {

        assertThat(IntellivisionColors.COLOR_MAGENTA, is(0xFF23FF));
    }

    @Test
    public void colorLightBlue() {

        assertThat(IntellivisionColors.COLOR_LIGHT_BLUE, is(0x6B75FF));
    }

    @Test
    public void colorYellowGreen() {

        assertThat(IntellivisionColors.COLOR_YELLOW_GREEN, is(0x61E315));
    }

    @Test
    public void colorPurple() {

        assertThat(IntellivisionColors.COLOR_PURPLE, is(0xB925FF));
    }
}
