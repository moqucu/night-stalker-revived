package org.moqucu.games.nightstalker.utility;

import javafx.fxml.LoadListener;
import org.moqucu.games.nightstalker.controller.GameController;
import org.moqucu.games.nightstalker.view.Sprite;

public class LoadListenerAdapter implements LoadListener {

    private final GameController gameController;

    public LoadListenerAdapter(GameController gameController) {

        this.gameController = gameController;
    }

    @Override
    public void readImportProcessingInstruction(String target) {
    }

    @Override
    public void readLanguageProcessingInstruction(String language) {
    }

    @Override
    public void readComment(String comment) {
    }

    @Override
    public void beginInstanceDeclarationElement(Class<?> type) {
    }

    @Override
    public void beginUnknownTypeElement(String name) {
    }

    @Override
    public void beginIncludeElement() {
    }

    @Override
    public void beginReferenceElement() {
    }

    @Override
    public void beginCopyElement() {
    }

    @Override
    public void beginRootElement() {
    }

    @Override
    public void beginPropertyElement(String name, Class<?> sourceType) {
    }

    @Override
    public void beginUnknownStaticPropertyElement(String name) {
    }

    @Override
    public void beginScriptElement() {
    }

    @Override
    public void beginDefineElement() {
    }

    @Override
    public void readInternalAttribute(String name, String value) {
    }

    @Override
    public void readPropertyAttribute(String name, Class<?> sourceType, String value) {
    }

    @Override
    public void readUnknownStaticPropertyAttribute(String name, String value) {
    }

    @Override
    public void readEventHandlerAttribute(String name, String value) {
    }

    @Override
    public void endElement(Object value) {

        if (value instanceof Sprite)
            gameController.addSprite((Sprite) value);
    }
}
