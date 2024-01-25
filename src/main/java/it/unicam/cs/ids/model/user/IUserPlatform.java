package it.unicam.cs.ids.model.user;

/**
 * Interface representing a user platform in a system.
 * It defines methods to retrieve basic information about a user.
 */
public interface IUserPlatform {

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user identifier.
     */
    int getId();

    /**
     * Gets the role of the user in the system.
     *
     * @return The user's role represented by the {@link UserRole} enum.
     */
    UserRole getUserType();

    /**
     * Gets the first name of the user.
     *
     * @return The user's first name.
     */
    String getName();

    /**
     * Gets the last name of the user.
     *
     * @return The user's last name.
     */
    String getSurname();

    /**
     * Gets the username of the user.
     *
     * @return The user's username.
     */
    String getUsername();

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    String getEmail();

    /**
     * Gets the password of the user.
     *
     * @return The user's password.
     */
    String getPassword();
}
