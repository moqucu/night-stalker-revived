package org.moqucu.games.nightstalker.view;

import java.util.ResourceBundle;

public enum FxmlView {

    SPLASH_SCREEN {

        @Override
        public String getTitle() {

            return "Night Stalker Revived";
        }

        @Override
        public String getFxmlFile() {

            return "/SplashScreen.fxml";
        }
    },
    GAME_SCREEN {

        @Override
        public String getTitle() {

            return "Now playing Night Stalker Revived";
        }

        @Override
        public String getFxmlFile() {

            return "/GameScreen.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){

        return ResourceBundle.getBundle("org.moqucu.games.nightstalker.Bundle").getString(key);
    }
}
