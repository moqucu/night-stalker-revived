package org.moqucu.games.nightstalker.view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadListener;
import javafx.scene.Parent;
import org.moqucu.games.nightstalker.utility.LoadListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component("SpringFXMLLoader")
public class SpringFXMLLoader {

    private final ResourceBundle resourceBundle;
    private final ApplicationContext context;

    private BlockingQueue<LoadListener> loadListeners = new LinkedBlockingQueue<>();

    @Autowired
    public SpringFXMLLoader(ApplicationContext context) {

        this.resourceBundle = ResourceBundle.getBundle("org.moqucu.games.nightstalker.Bundle");
        this.context = context;
    }

    @SuppressWarnings("unused")
    public boolean addLoadListener(LoadListener loadListener) {

        return loadListeners.add(loadListener);
    }

    @SuppressWarnings("unused")
    public boolean removeLoadListener(LoadListener loadListener) {

        return loadListeners.remove(loadListener);
    }

    Parent load(String fxmlPath) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLoadListener(new LoadListenerAdapter() {

            @Override
            public void endElement(Object value) {
                loadListeners.forEach(loadListener -> endElement(value));
            }
        });

        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setResources(resourceBundle);
        Resource resource = new ClassPathResource(fxmlPath);
        loader.setLocation(resource.getURL());

        return loader.load();
    }
}
