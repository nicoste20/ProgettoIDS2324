package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.contest.Contest;
import it.unicam.cs.ids.model.contest.IContest;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import java.util.List;
import java.util.Optional;

/**
 * The ContestController class manages operations related to contests and multimedia within a platform.
 */
public class ContestController {

    private List<IContest> contests;

    public ContestController(List<IContest> contests){
        this.contests = contests;
    }

    public ContestController(){
    }

    /**
     * Adds a contest to the list of contests.
     *
     * @param contest The contest to be added.
     */
    public void addContest(IContest contest) {
        this.contests.add(contest);
    }

    /**
     * Removes a contest from the list of contests.
     *
     * @param contest The contest to be removed.
     */
    public void removeContest(IContest contest) {
        this.contests.remove(contest);
    }

    /**
     * Invites a user to a private contest, adding them to the allowed users if the contest is private and exists.
     *
     * @param contest The contest to invite the user to.
     * @param user    The user to be invited.
     */
    public void invite(IContest contest, IUserPlatform user) {
        if (contest.isPrivate() && this.contests.contains(contest)) {
            int index = this.contests.indexOf(contest);
            this.contests.get(index).addAllowedUsers(user);
        }
    }

    /**
     * Adds multimedia to a contest if the contest exists.
     *
     * @param multimedia The multimedia to be added.
     * @param contest    The contest to which multimedia is added.
     */
    public void addMultimedia(Multimedia multimedia, IContest contest) {
        if (this.contests.contains(contest)) {
            int index = this.contests.indexOf(contest);
            this.contests.get(index).addMultimedia(multimedia);
        }
    }

    /**
     * Validates multimedia in a contest by a curator user, based on a choice.
     *
     * @param multimedia The multimedia to be validated.
     * @param contest    The contest containing the multimedia.
     * @param animator   The user performing the validation.
     * @param choice     The choice for validation.
     */
    public void validateMultimedia(Multimedia multimedia, IContest contest, IUserPlatform animator, boolean choice) {
        if (animator.getUserType().equals(UserRole.Curator) && this.contests.contains(contest)) {
            int index = this.contests.indexOf(contest);
            if (this.contests.get(index).getMultimediaList().contains(multimedia)) {
                int indexOfMultimedia = this.contests.get(index).getMultimediaList().indexOf(multimedia);
                if (choice) {
                    this.contests.get(index).getMultimediaList().get(indexOfMultimedia).setValidation(true);
                }
            }
        }
    }

    /**
     * Adds multimedia to a contest with pending validation.
     *
     * @param multimedia The multimedia to be added.
     * @param contest    The contest to which multimedia is added.
     */
    public void addWithPending(Multimedia multimedia, IContest contest) {
        if (this.contests.contains(contest)) {
            int index = this.contests.indexOf(contest);
            multimedia.setValidation(false);
            this.contests.get(index).addMultimedia(multimedia);
        }
    }
    /**
     *It does the research of a contest by his name
     * @param title The title of a contest
     * @return the Contest researched
     */
    public Optional<IContest> searchPoint(String title) {
        for (IContest contest : contests) {
            if (contest.getContestName().equals(title))
                return Optional.of(contest);
        }
        return Optional.empty();
    }
}
