package org.moqucu.games.nightstalker.view;

public enum FxmlView {

    SPLASH_SCREEN {

        @Override
        public String getTitle() {

            return "Night Stalker Revived";
        }

        @Override
        public String getFxmlFile() {

            return "/fxml/SplashScreen.fxml";
        }
    },
    GAME_SCREEN {

        @Override
        public String getTitle() {

            return "Now playing Night Stalker Revived";
        }

        @Override
        public String getFxmlFile() {

            return "/fxml/GameScreen.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();

}
