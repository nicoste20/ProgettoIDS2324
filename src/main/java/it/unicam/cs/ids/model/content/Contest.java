package it.unicam.cs.ids.model.content;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a base implementation of the {@link Content} class.
 * This class provides methods that concerns about Contests.
 */
@Entity
public class Contest extends Content {
    @ElementCollection
    @CollectionTable(name = "contest_users", joinColumns = @JoinColumn(name = "contenst_id"))
    @Column(name = "users_id")
    private List<Integer> allowedUsers;
    @ElementCollection
    @CollectionTable(name = "contest_users", joinColumns = @JoinColumn(name = "contenst_id"))
    @Column(name = "multimedia_id")
    private List<Integer> multimediaList;

    private boolean privacy;
    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param id       The unique identifier of a contest.
     * @param name The name of a contest.
     * @param description The description of a contest.
     * @param privacy
     */

    public Contest(int id, String name, String description, boolean privacy) {
        super(name,description);
        super.setValidation(true);
        this.privacy = privacy;
        this.multimediaList=new ArrayList<Integer>();
    }

    public Contest() {

    }

    /**
     * {@inheritDoc}
     */
    public List<Integer> getAllowedUsers() {
        return this.allowedUsers;
    }
    /**
     *
     *{@inheritDoc}
     */
    public boolean isPrivacy(){ return this.privacy;}

    /**
     * {@inheritDoc}
     */
    public void addAllowedUsers(int user){
        this.allowedUsers.add(user);
    }

    /**
     * {@inheritDoc}
     */
    public List<Integer> getMultimediaList() {
        return multimediaList;
    }

    /**
     * {@inheritDoc}
     */
    public void addMultimedia(int multimedia){
        this.multimediaList.add(multimedia);
    }
}
