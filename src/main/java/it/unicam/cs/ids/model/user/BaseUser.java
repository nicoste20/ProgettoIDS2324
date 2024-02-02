package it.unicam.cs.ids.model.user;

/**
 * Represents a base implementation of the {@link IUserPlatform} interface.
 * This class provides basic functionality for user-related operations.
 */
public class BaseUser implements IUserPlatform {

    private final int id;
    private final UserRole userRole;
    private String name;
    private String surname;
    private final String username;
    private String password;
    private String email;

    /**
     * Constructs a new BaseUser object with the specified parameters.
     *
     * @param id       The unique identifier of the user.
     * @param userRole The role of the user represented by the {@link UserRole} enum.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     */
    public BaseUser(int id, UserRole userRole, String name, String surname, String username, String email, String password) {
        this.id = id;
        this.userRole = userRole;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRole getUserType() {
        return this.userRole;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSurname() {
        return this.surname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmail() {
        return this.email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return this.password;
    }
}
