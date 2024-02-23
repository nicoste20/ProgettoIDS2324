package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.ContestRespository;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Contest;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * @param contestList          The repository for contests.
     * @param users                The repository for users.
     * @param multimediaRepository The repository for multimedia.
     */
    @Autowired
    public ContestController(ContestRespository contestList, UserRepository users, MultimediaRepository multimediaRepository) {
        this.contestList = contestList;
        this.users = users;
        this.multimediaRepository = multimediaRepository;
    }

    /**
     * Adds a contest to the list of contests.
     *
     * @param contest The contest to be added.
     */
    @PostMapping("/add/contest{userId}")
    public ResponseEntity<Object> addContest(@RequestBody Contest contest, @PathParam(("userId")) int userId) {
        contest.setAuthor(userId);
        contest.setValidation(true);
        if (!(contestList.existsById(contest.getId()))) {
            if (users.existsById(userId) && users.findById(userId).get().getUserType().equals(UserRole.Animator)) {
                this.contestList.save(contest);
                return new ResponseEntity<>("Contest created", HttpStatus.OK);
            }else throw new UserBadTypeException();
        }else throw new ContestAlreadyInException();
    }

    /**
     * Removes a contest from the list of contests.
     *
     * @param contestId The ID of the contest to be removed.
     * @return
     */
    @DeleteMapping("/delete/contest{userId}")
    public ResponseEntity<Object> removeContest(@RequestBody int contestId, @PathParam(("userId")) int userId) {
        if ((contestList.existsById(contestId))) {
            if (users.existsById(userId) && users.findById(userId).get().getUserType().equals(UserRole.Animator)) {
                this.contestList.deleteById(contestId);
                return new ResponseEntity<>("Contest deleted", HttpStatus.OK);
            }else throw new UserBadTypeException();
        }else throw new ContestNotExistException();
    }

    /**
     * Invites a user to a private contest, adding them to the allowed users if the contest is private and exists.
     *
     * @param contestId The ID of the contest to invite the user to.
     * @param userId    The ID of the user to be invited.
     * @return
     * @throws ContestNotExistException If the contest does not exist.
     * @throws UserBadTypeException     If the user is not correct.
     */
    @RequestMapping(value = "/invite/contest{contestId}{userId}{animatorId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> invite(@PathParam(("contestId")) int contestId, @PathParam(("userId")) int userId, @PathParam(("animatorId")) int animatorId) {
        if (contestList.existsById(contestId)) {
            if (users.existsById(userId)) {
                if (contestList.findById(contestId).get().isPrivacy()) {
                    Contest x = contestList.findById(contestId).get();
                    x.addAllowedUsers(userId);
                    contestList.save(x);
                    return new ResponseEntity<>("User invited", HttpStatus.OK);
                }else throw new UninvitableContestException();
            }else throw new UserBadTypeException();
        }else throw new ContestNotExistException();
    }


    /**
     * Validates multimedia in a contest by a curator user, based on a choice.
     *
     * @param multimediaId The ID of the multimedia to be validated.
     * @param contestId    The ID of the contest containing the multimedia.
     * @param animatorId   The ID of the user performing the validation.
     * @param choice       The choice for validation.
     * @return
     */
    @RequestMapping(value = "/validateMultimedia/contest{contestId}{multimediaId}{animatorId}{choice}", method = RequestMethod.POST)
    public ResponseEntity<Object> validateMultimedia(@PathParam(("multimediaId")) int multimediaId, @PathParam(("contestId")) int contestId,
        @PathParam(("animatorId")) int animatorId, @PathParam(("choice")) boolean choice)
    {
        if (users.findById(animatorId).get().getUserType().equals(UserRole.Animator) && this.contestList.existsById(contestId)) {
            if (this.contestList.findById(contestId).get().getMultimediaList().contains(multimediaId)) {
                if (choice) {
                    Contest contest = contestList.findById(contestId).get();
                    Multimedia multimedia = multimediaRepository.findById(multimediaId).get();
                    multimedia.setValidation(true);
                    multimediaRepository.save(multimedia);
                    contestList.save(contest);
                    return new ResponseEntity<>("multimedia validated", HttpStatus.OK);
                }
            }
        }else throw new UserBadTypeException();
        return new ResponseEntity<>("Multimedia not validated", HttpStatus.OK);
    }

    /**
     * Searches for a contest by its name.
     *
     * @param contestName The name of the contest to search for.
     * @return An Optional containing the found contest, if any.
     */
    @RequestMapping(value = "/search/contest{contestName}", method = RequestMethod.POST)
    public Optional<Contest> searchContest(@PathParam(("contestName")) String contestName) {
        return Optional.of(contestList.findAllByTitle(contestName));
    }

    @GetMapping(value ="/get/contests")
    public ResponseEntity<Object> getContest(){
        return new ResponseEntity<>(contestList.findAll(), HttpStatus.OK);
    }

    @GetMapping(value ="/get/multimedias/contest{contestId}")
    public ResponseEntity<Object> getMultimedia(@PathParam("contestId") int contestId){
        Contest contest = contestList.findById(contestId).get();
        List<Multimedia> multimedias = new ArrayList<Multimedia>();
        for (Integer id: contest.getMultimediaList()) {
            multimedias.add(multimediaRepository.findById(id).get());
        }
        return new ResponseEntity<>(multimedias, HttpStatus.OK);
    }
}
