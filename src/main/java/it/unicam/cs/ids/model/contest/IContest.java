package it.unicam.cs.ids.model.contest;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;

import java.util.Date;
import java.util.List;

/**
 * Interface representing a contest platform in our system
 * It defines methods to retrieve basic attributes about a published contest.
 */
public interface IContest {
    /**
     * It gets the unique identifier of a contest.
     *
     * @return The contest ID
     */
    int getID();

    /**
     * It gets the name of a contest.
     *
     * @return the name of a specific contest.
     */
    String getContestName();

    /**
     *It gets the description of a contest.
     *
     * @return the description of a contest.
     */
    String ContestDescription();

    /**
     * It gets a list of allowed users if the contest is private.
     *
     * @return the entire list of allowed users.
     */
    List<IUserPlatform> getAllowedUsers();

    /**
     * Permit to add a new allowed user for this contest
     */
    void addAllowedUsers(IUserPlatform user);

    /**
     *Return all contest's multimedia.
     *
     * @return the entire list of multimedia.
     */
    public List<Multimedia> getMultimediaList();

    /**
     *Permit to add a new multimedia.
     */
    public void addMultimedia(Multimedia multimedia);

    /**
     *It gets true if the contest is private, otherwise it returns false.
     *
     * @return true if the contest is private.
     */
    boolean isPrivate();

}
