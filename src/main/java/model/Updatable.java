package model;

import javafx.scene.input.KeyCode;

import java.util.Set;

public interface Updatable {

    void update(Set<KeyCode> input);
}