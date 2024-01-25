package it.unicam.cs.ids.model.contest;

import java.util.Date;

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
     * It gets the starting date of a contest.
     *
     * @return the starting date of a contest.
     */
    Date startDate();

    /**
     * It gets the ending date of a contest.
     *
     * @return the ending date of a contest.
     */
    Date endDate();

    /**
     * It gets a list of allowed users if the contest is private.
     *
     * @return the entire list of allowed users.
     */
    String [] allowedUsers();

}
