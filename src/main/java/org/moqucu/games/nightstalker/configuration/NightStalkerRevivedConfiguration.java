package org.moqucu.games.nightstalker.configuration;

import javafx.stage.Stage;
import org.moqucu.games.nightstalker.view.SpringFXMLLoader;
import org.moqucu.games.nightstalker.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.model.DefaultStateMachineComponentResolver;
import org.springframework.statemachine.config.model.StateMachineComponentResolver;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;

@Configuration
@EnableStateMachine
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

    @Bean
    public StateMachineModelFactory<String, String> modelFactory() {

        UmlStateMachineModelFactory factory = new UmlStateMachineModelFactory(
                "classpath:org/moqucu/games/nightstalker/statemachine/nightstalker.uml");
        factory.setStateMachineComponentResolver(stateMachineComponentResolver());
        System.out.println("I was run!");
        return factory;
    }

    @Bean
    public StateMachineComponentResolver<String, String> stateMachineComponentResolver() {

        return new DefaultStateMachineComponentResolver<>();
    }
}
