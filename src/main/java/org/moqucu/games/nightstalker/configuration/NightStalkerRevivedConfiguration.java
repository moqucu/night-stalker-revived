package org.moqucu.games.nightstalker.configuration;

import javafx.stage.Stage;
import org.moqucu.games.nightstalker.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class NightStalkerRevivedConfiguration {

    private final SpringFXMLLoader springFXMLLoader;

    @Autowired
    public NightStalkerRevivedConfiguration(SpringFXMLLoader springFXMLLoader) {

        this.springFXMLLoader = springFXMLLoader;
    }

    @Bean
    @Lazy
    public StageManager stageManager(Stage stage) {

        return new StageManager(springFXMLLoader, stage);
    }
}
