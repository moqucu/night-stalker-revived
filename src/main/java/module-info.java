module org.moqucu.games.nightstalker {

    requires org.apache.logging.log4j;

    requires java.sql;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    requires static lombok;

    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.statemachine.core;
    requires spring.messaging;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports org.moqucu.games.nightstalker;
    exports org.moqucu.games.nightstalker.configuration;
    exports org.moqucu.games.nightstalker.controller;
    exports org.moqucu.games.nightstalker.view;
    exports org.moqucu.games.nightstalker.model;
    exports org.moqucu.games.nightstalker.sprite;
    exports org.moqucu.games.nightstalker.sprite.object;
    exports org.moqucu.games.nightstalker.sprite.enemy;
    exports org.moqucu.games.nightstalker.sprite.hero;

    opens org.moqucu.games.nightstalker to javafx.graphics, spring.core;
    opens org.moqucu.games.nightstalker.configuration to spring.core, spring.beans, spring.context;
    opens org.moqucu.games.nightstalker.controller to spring.beans, javafx.fxml;
    opens org.moqucu.games.nightstalker.view to spring.core;
    opens org.moqucu.games.nightstalker.sprite to javafx.fxml;
}