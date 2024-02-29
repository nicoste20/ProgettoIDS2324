/**
 * The MultimediaListener class represents a subject in the Observer design pattern,
 * responsible for managing a list of observers and notifying them when a new multimedia post is published.
 */
package it.unicam.cs.ids.model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * The MultimediaListener class.
 */
public class MultimediaListener {

    private List<Observer> observers;

    /**
     * Constructs a MultimediaListener object with an empty list of observers.
     */
    public MultimediaListener(){
        this.observers = new ArrayList<>();
    }

    /**
     * Publishes a multimedia post and notifies all registered observers.
     * @param post The multimedia post to be published.
     */
    public void publishPost(String post) {
        notifyObservers(post);
    }

    /**
     * Registers a new observer to receive notifications.
     * @param observer The observer to be registered.
     */
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of registered observers.
     * @param observer The observer to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers with a given message.
     * @param message The message to be sent to observers.
     */
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
