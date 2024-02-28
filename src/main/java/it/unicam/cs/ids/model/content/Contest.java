package it.unicam.cs.ids.model.content;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a base implementation of the {@link Content} class.
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
     * @param id          The unique identifier of the contest.
     * @param name        The name of the contest.
     * @param description The description of the contest.
     * @param privacy     The privacy status of the contest.
     */
    public Contest(int id, String name, String description, boolean privacy) {
        super(name,description);
        this.privacy = privacy;
        this.multimediaList=new ArrayList<Integer>();
    }

    /**
     * Default constructor.
     */
    public Contest() {}

    /**
     * Retrieves the list of users allowed to participate in the contest.
     * @return The list of user IDs allowed to participate.
     */
    public List<Integer> getAllowedUsers() {
        return this.allowedUsers;
    }

    /**
     * Checks the privacy status of the contest.
     * @return True if the contest is private, false otherwise.
     */
    public boolean getPrivacy(){ return this.privacy;}

    /**
     * Deletes a multimedia item from the contest.
     *
     * @param id The ID of the multimedia item to be deleted.
     */
    public void deleteMultimedia(int id){
        for(int currentId : multimediaList){
            if(currentId == id){
                multimediaList.remove(id);
            }
        }
    }

    /**
     * Adds a user to the list of allowed participants in the contest.
     * @param user The ID of the user to be added.
     */
    public void addAllowedUsers(int user){
        this.allowedUsers.add(user);
    }

    /**
     * Retrieves the list of multimedia items associated with the contest.
     * @return The list of multimedia item IDs.
     */
    public List<Integer> getMultimediaList() {
        return multimediaList;
    }

    /**
     * Adds a multimedia item to the contest.
     * @param multimedia The ID of the multimedia item to be added.
     */
    public void addMultimedia(int multimedia){
        this.multimediaList.add(multimedia);
    }
}
