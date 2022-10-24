package org.moqucu.games.nightstalker.view;

import javafx.scene.Parent;
import org.moqucu.games.nightstalker.utility.LoadListenerAdapter;

import java.io.IOException;
import java.util.ResourceBundle;

public class FXMLLoader {

    Parent load(StageManager stageManager, String fxmlPath) throws IOException {

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLoadListener(new LoadListenerAdapter(stageManager));

        return loader.load();
    }
}
