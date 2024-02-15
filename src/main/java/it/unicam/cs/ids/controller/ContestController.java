package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.ContestNotExistException;
import it.unicam.cs.ids.Exception.MultimediaNotFoundException;
import it.unicam.cs.ids.Exception.UserNotCorrectException;
import it.unicam.cs.ids.controller.Repository.ContestRespository;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.contest.Contest;
import it.unicam.cs.ids.model.contest.IContest;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The ContestController class manages operations related to contests and multimedia within a platform.
 * This controller provides endpoints for adding, removing, inviting users, and managing multimedia in contests.
 */
@RestController
public class ContestController {

    private final ContestRespository contestList;
    private final UserRepository users;
    private final MultimediaRepository multimediaRepository;

    /**
     * Constructs a new ContestController with the specified repositories.
     *
     * @param contestList           The repository for contests.
     * @param users                 The repository for users.
     * @param multimediaRepository  The repository for multimedia.
     */
    @Autowired
    public ContestController(ContestRespository contestList, UserRepository users, MultimediaRepository multimediaRepository){
        this.contestList = contestList;
        this.users = users;
        this.multimediaRepository = multimediaRepository;
    }

    /**
     * Adds a contest to the list of contests.
     *
     * @param contest The contest to be added.
     */
    @PostMapping("/add/contest/{userId}")
    public void addContest(@RequestBody Contest contest,@PathParam(("userId"))int userId) {
        if(users.existsById(userId) && users.findById(userId).get().getUserType().equals(UserRole.Animator)) {
            this.contestList.save(contest);
        } throw new UserNotCorrectException();
    }

    /**
     * Removes a contest from the list of contests.
     *
     * @param contestId The ID of the contest to be removed.
     */
    @DeleteMapping("/delete/contest/{userId}")
    public void removeContest(@RequestBody int contestId, @PathParam(("userId")) int userId) {
        if(users.existsById(userId) && users.findById(userId).get().getUserType().equals(UserRole.Animator)) {
            this.contestList.deleteById(contestId);
        } throw new UserNotCorrectException();
    }

    /**
     * Invites a user to a private contest, adding them to the allowed users if the contest is private and exists.
     *
     * @param contestId The ID of the contest to invite the user to.
     * @param userId    The ID of the user to be invited.
     * @throws ContestNotExistException If the contest does not exist.
     * @throws UserNotCorrectException If the user is not correct.
     */
    @RequestMapping(value="/invite/contest/{contestId}/{userId}", method = RequestMethod.PUT)
    public void invite(@PathParam(("contestId"))int contestId,@PathParam(("userId")) int userId) {
        if (contestList.existsById(contestId) && contestList.findById(contestId).get().isPrivate() ) {
            if(users.existsById(userId)) {
                contestList.findById(contestId).get().addAllowedUsers(users.findById(userId).get());
            }throw new UserNotCorrectException();
        }throw new ContestNotExistException();
    }

    /**
     * Adds multimedia to a contest if the contest exists.
     *
     * @param multimediaId The ID of the multimedia to be added.
     * @param contestId    The ID of the contest to which multimedia is added.
     * @throws MultimediaNotFoundException If the multimedia is not found.
     * @throws ContestNotExistException If the contest does not exist.
     */
    @RequestMapping(value="/add/contest/{contestId}/{multimediaId}", method = RequestMethod.POST)
    public void addMultimedia(@PathParam(("multimediaId")) int multimediaId,@PathParam(("contestId")) int contestId) {
        if (contestList.existsById(contestId)) {
            if(multimediaRepository.existsById(multimediaId)) {
                contestList.findById(contestId).get().addMultimedia(multimediaRepository.findById(multimediaId).get());
            }throw new MultimediaNotFoundException();
        }throw new ContestNotExistException();
    }

    /**
     * Validates multimedia in a contest by a curator user, based on a choice.
     *
     * @param multimediaId The ID of the multimedia to be validated.
     * @param contestId    The ID of the contest containing the multimedia.
     * @param animatorId   The ID of the user performing the validation.
     * @param choice       The choice for validation.
     */
    @RequestMapping(value="/validate/contest/{contestId}/{multimediaId}/{animatorId}/{choice}", method = RequestMethod.POST)
    public void validateMultimedia(@PathParam(("multimediaId")) int multimediaId, @PathParam(("contestId")) int contestId,@PathParam(("animatorId")) int animatorId,@PathParam(("choice")) boolean choice) {
        if (users.findById(animatorId).get().getUserType().equals(UserRole.Animator) && this.contestList.existsById(contestId)) {
            if (this.contestList.findById(contestId).get().getMultimediaList().contains(multimediaId)) {
                if (choice) {
                    contestList.findById(contestId).get().getMultimediaList().get(multimediaId).setValidation(true);
                }
            }
        }
    }

    /**
     * Adds multimedia to a contest with pending validation.
     *
     * @param multimediaId The ID of the multimedia to be added.
     * @param contestId    The ID of the contest to which multimedia is added.
     * @throws MultimediaNotFoundException If the multimedia is not found.
     * @throws ContestNotExistException If the contest does not exist.
     */
    @RequestMapping(value="/add/pending/contest/{contestId}/{multimediaId}", method = RequestMethod.POST)
    public void addWithPending(@PathParam(("multimediaId")) int multimediaId,@PathParam(("contestId")) int contestId) {
        if (contestList.existsById(contestId)) {
            if(multimediaRepository.existsById(multimediaId)) {
                multimediaRepository.findById(multimediaId).get().setValidation(false);
                contestList.findById(contestId).get().addMultimedia(multimediaRepository.findById(multimediaId).get());
            }throw new MultimediaNotFoundException();
        }throw new ContestNotExistException();
    }

    /**
     * Searches for a contest by its name.
     *
     * @param contestName The name of the contest to search for.
     * @return An Optional containing the found contest, if any.
     */
    @RequestMapping(value="/search/contest/{contestName}", method = RequestMethod.POST)
    public Optional<IContest> searchContest(@PathParam(("contestName")) String contestName) {
        return Optional.of(contestList.findAllByTitle(contestName));
    }
}
