/**
 * The Observer interface represents an object that can observe changes in a subject.
 * Objects implementing this interface will be notified when the subject undergoes changes.
 */
package it.unicam.cs.ids.model.observer;

/**
 * The Observer interface.
 */
public interface Observer {

    /**
     * This method is called by the subject to notify the observer about a change.
     * @param message The message containing information about the change.
     */
    void update(String message);

}
