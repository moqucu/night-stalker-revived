package org.moqucu.games.nightstalker.utility.test;

import org.junit.jupiter.api.Test;
import org.moqucu.games.nightstalker.utility.IntellivisionColors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IntellivisionColorsTest {

    @Test
    public void colorBlack() {

        assertThat(IntellivisionColors.getCOLOR_BLACK(), is(0x131313));
    }

    @Test
    public void colorBlue() {

        assertThat(IntellivisionColors.getCOLOR_BLUE(), is(0x0562F1));
    }

    @Test
    public void colorRed() {

        assertThat(IntellivisionColors.getCOLOR_RED(), is(0xFF362F));
    }

    @Test
    public void colorLightBrown() {

        assertThat(IntellivisionColors.getCOLOR_TAN(), is(0xDAA524));
    }

    @Test
    public void colorDarkGreen() {

        assertThat(IntellivisionColors.getCOLOR_DARK_GREEN(), is(0x14A108));
    }

    @Test
    public void colorGreen() {

        assertThat(IntellivisionColors.getCOLOR_GREEN(), is(0x25D226));
    }

    @Test
    public void colorYellow() {

        assertThat(IntellivisionColors.getCOLOR_YELLOW(), is(0xFFE102));
    }

    @Test
    public void colorWhite() {

        assertThat(IntellivisionColors.getCOLOR_WHITE(), is(0xFFFFFF));
    }

    @Test
    public void colorGrey() {

        assertThat(IntellivisionColors.getCOLOR_GRAY(), is(0x727272));
    }

    @Test
    public void colorCyan() {

        assertThat(IntellivisionColors.getCOLOR_CYAN(), is(0x2DD69A));
    }

    @Test
    public void colorOrange() {

        assertThat(IntellivisionColors.getCOLOR_ORANGE(), is(0xFF7F16));
    }

    @Test
    public void colorBrown() {

        assertThat(IntellivisionColors.getCOLOR_BROWN(), is(0x534E03));
    }

    @Test
    public void colorMagenta() {

        assertThat(IntellivisionColors.getCOLOR_MAGENTA(), is(0xFF23FF));
    }

    @Test
    public void colorLightBlue() {

        assertThat(IntellivisionColors.getCOLOR_LIGHT_BLUE(), is(0x6B75FF));
    }

    @Test
    public void colorYellowGreen() {

        assertThat(IntellivisionColors.getCOLOR_YELLOW_GREEN(), is(0x61E315));
    }

    @Test
    public void colorPurple() {

        assertThat(IntellivisionColors.getCOLOR_PURPLE(), is(0xB925FF));
    }
}
