package it.unicam.cs.ids.model.user;

/**
 * Builder class for creating instances of the {@link BaseUser} class with specific user roles.
 * It provides methods to construct users with different roles.
 */
public class UserBuilder {

    /**
     * Creates a new BaseUser with the Contributor role.
     *
     * @param id       The unique identifier of the user.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A new BaseUser with the Contributor role.
     */
    public BaseUser Contributor(int id, String name, String surname, String username, String email, String password) {
        return new BaseUser(id, UserRole.Contributor, name, surname, username, email, password);
    }

    /**
     * Creates a new BaseUser with the ContributorAuthorized role.
     *
     * @param id       The unique identifier of the user.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A new BaseUser with the ContributorAuthorized role.
     */
    public BaseUser ContributorAuthorized(int id, String name, String surname, String username, String email, String password) {
        return new BaseUser(id, UserRole.ContributorAuthorized, name, surname, username, email, password);
    }

    /**
     * Creates a new BaseUser with the TouristAuthorized role.
     *
     * @param id       The unique identifier of the user.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A new BaseUser with the TouristAuthorized role.
     */
    public BaseUser TouristAuthorized(int id, String name, String surname, String username, String email, String password) {
        return new BaseUser(id, UserRole.TouristAuthorized, name, surname, username, email, password);
    }

    /**
     * Creates a new BaseUser with the Curator role.
     *
     * @param id       The unique identifier of the user.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A new BaseUser with the Curator role.
     */
    public BaseUser Curator(int id, String name, String surname, String username, String email, String password) {
        return new BaseUser(id, UserRole.Curator, name, surname, username, email, password);
    }

    /**
     * Creates a new BaseUser with the Animator role.
     *
     * @param id       The unique identifier of the user.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @return A new BaseUser with the Animator role.
     */
    public BaseUser Animator(int id, String name, String surname, String username, String email, String password) {
        return new BaseUser(id, UserRole.Animator, name, surname, username, email, password);
    }
}

