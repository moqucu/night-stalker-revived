package org.moqucu.games.nightstalker.view;

import java.util.ResourceBundle;

public enum FxmlView {

    SPLASH_SCREEN {
        @Override
        String getTitle() {
            return getStringFromResourceBundle("splash.screen.title");
        }

        @Override
        String getFxmlFile() {
            return "/SplashScreen.fxml";
        }
    };

    abstract String getTitle();
    abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("org.moqucu.games.nightstalker.Bundle").getString(key);
    }

}
