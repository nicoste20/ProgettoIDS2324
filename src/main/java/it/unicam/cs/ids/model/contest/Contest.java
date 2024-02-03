package it.unicam.cs.ids.model.contest;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.IUserPlatform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Represents a base implementation of the {@link IContest} interface.
 * This class provides methods that concerns about Contests.
 */
public class Contest implements IContest{
    private final int id;
    private String contestName;
    private String contestDescription;
    private List <IUserPlatform>  allowedUsers;
    private List<Multimedia> multimediaList;
    private final boolean isPrivate;
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
    public List<IUserPlatform> getAllowedUsers() {
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
    public void addAllowedUsers(IUserPlatform user){
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
