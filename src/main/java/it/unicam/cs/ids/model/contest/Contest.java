package it.unicam.cs.ids.model.contest;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a base implementation of the {@link IContest} interface.
 * This class provides methods that concerns about Contests.
 */
@Entity
public class Contest implements IContest{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contestName;
    private String contestDescription;
    @ManyToMany
    private List <BaseUser>  allowedUsers;
    @OneToMany
    private List<Multimedia> multimediaList;
    private boolean isPrivate;
    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param id       The unique identifier of a contest.
     * @param contestName The name of a contest.
     * @param contestDescription The description of a contest.
     */
    public Contest(int id, String contestName, String contestDescription, boolean isPrivate) {
        this.id = id;
        this.contestName = contestName;
        this.contestDescription = contestDescription;
        this.isPrivate = isPrivate;
        this.multimediaList=new ArrayList<Multimedia>();
    }

    public Contest() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getID() {
        return this.id;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getContestName() {
        return this.contestName;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getContestDescription() {
        return this.contestDescription;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<BaseUser> getAllowedUsers() {
        return this.allowedUsers;
    }
    /**
     *
     *{@inheritDoc}
     */
    @Override
    public boolean isPrivate(){ return this.isPrivate;}

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllowedUsers(BaseUser user){
        this.allowedUsers.add(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Multimedia> getMultimediaList() {
        return multimediaList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMultimedia(Multimedia multimedia) {
        this.multimediaList.add(multimedia);
    }
}
