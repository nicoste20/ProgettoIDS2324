package it.unicam.cs.ids.model.user;

import it.unicam.cs.ids.model.observer.Observer;
import jakarta.persistence.*;

/**
 * Represents a base implementation of the {@link IUserPlatform} interface and
 * implements the {@link Observer} interface for observing multimedia content publication.
 * This class provides basic functionality for user-related operations and multimedia notifications.
 */
@Entity
public class BaseUser implements IUserPlatform , Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String name;
    private String surname;
    private  String username;
    private String password;
    @Column(unique = true)
    private String email;
    private int postCount;

    /**
     * Constructs a new BaseUser object with the specified parameters.
     * @param id       The unique identifier of the user.
     * @param role     The role of the user represented by the {@link UserRole} enum.
     * @param name     The first name of the user.
     * @param surname  The last name of the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     */
    public BaseUser(int id, String role, String name, String surname, String username, String email, String password) {
        this.id = id;
        this.userRole = UserRole.valueOf(UserRole.class,role);
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.postCount=0;
    }

    /**
     * Default constructor for BaseUser class.
     */
    public BaseUser() {

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
    public String getPassword() {return this.password;}
    /**
     * {@inheritDoc}
     */
    @Override
    public void setRole(UserRole role) {this.userRole= role;}
    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementPostCount() {this.postCount+=1;}
    /**
     * {@inheritDoc}
     */
    public int getPostCount(){return this.postCount;}

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(String message) {
        System.out.println(username + ": Notifica - Nuovo contenuto multimediale pubblicato: " + message);
    }
}
