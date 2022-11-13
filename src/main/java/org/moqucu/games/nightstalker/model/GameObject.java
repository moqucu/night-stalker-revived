package org.moqucu.games.nightstalker.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;

public class GameObject {

    private final UUID objectId = UUID.randomUUID();

    protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public String getObjectId() {

        return objectId.toString();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
