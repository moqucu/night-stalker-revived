module org.moqucu.games.nightstalker {
    requires javafx.controls;
    requires javafx.media;
    requires static lombok;
    requires org.apache.logging.log4j;
    exports org.moqucu.games.nightstalker;
    opens org.moqucu.games.nightstalker to javafx.graphics;
}