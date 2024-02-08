package it.unicam.cs.ids.controllerTest;

import it.unicam.cs.ids.controller.ContestController;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.contest.Contest;
import it.unicam.cs.ids.model.contest.IContest;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContestControllerTest {

    private ContestController contestController;
    private List<IContest> contests;

    @BeforeEach
    void setUp() {
        contests = new ArrayList<>();
        contestController = new ContestController();
    }

    @Test
    void addContest() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);
        contestController.addContest(contest);
        assertTrue(contests.contains(contest));
    }

    @Test
    void removeContest() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);
        contestController.addContest(contest);
        contestController.removeContest(contest);
        assertFalse(contests.contains(contest));
    }

    @Test
    void inviteToPrivateContest() {
        IContest privateContest = new Contest(0,"ContestName","Contest Description" , true);
        IUserPlatform user = new BaseUser(1, UserRole.TouristAuthorized, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");
        contestController.addContest(privateContest);
        contestController.invite(privateContest, user);
        assertTrue(privateContest.getAllowedUsers().contains(user));
    }

    @Test
    void inviteToPublicContest() {
        IContest publicContest = new Contest(0,"ContestName","Contest Description" , true);
        IUserPlatform user = new BaseUser(1, UserRole.TouristAuthorized, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");
        contestController.addContest(publicContest);
        contestController.invite(publicContest, user);
        assertFalse(publicContest.getAllowedUsers().contains(user));
    }

    @Test
    void addMultimediaToExistingContest() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);
        IUserPlatform user = new BaseUser(1, UserRole.Curator, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");
        Multimedia multimedia = new Multimedia(user, "This is a multimedia text", "path/to/photo.jpg", 2);
        contestController.addContest(contest);
        contestController.addMultimedia(multimedia, contest);
        assertTrue(contest.getMultimediaList().contains(multimedia));
    }

    @Test
    void addMultimediaToNonExistingContest() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);
        IUserPlatform user = new BaseUser(1, UserRole.Curator, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");
        Multimedia multimedia = new Multimedia(user, "This is a multimedia text", "path/to/photo.jpg", 2);
        contestController.addMultimedia(multimedia, contest);
        assertFalse(contests.contains(contest));
        assertFalse(multimedia.isValidate());
    }

    @Test
    void validateMultimediaByCurator() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);
        IUserPlatform user = new BaseUser(1, UserRole.Curator, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");
        Multimedia multimedia = new Multimedia(user, "This is a multimedia text", "path/to/photo.jpg", 2);
        contestController.addContest(contest);
        contestController.addMultimedia(multimedia, contest);
        contestController.validateMultimedia(multimedia, contest, user, true);
        assertTrue(multimedia.isValidate());
    }

    @Test
    void validateMultimediaByNonCurator() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);

        IUserPlatform user = new BaseUser(1, UserRole.TouristAuthorized, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");

        Multimedia multimedia = new Multimedia(user, "This is a multimedia text", "path/to/photo.jpg", 2);
        contestController.addContest(contest);
        contestController.addMultimedia(multimedia, contest);
        contestController.validateMultimedia(multimedia, contest, user, true);
        assertFalse(multimedia.isValidate());
    }

    @Test
    void addWithPendingValidation() {
        IContest contest = new Contest(0,"ContestName","Contest Description" , false);
        IUserPlatform user = new BaseUser(1, UserRole.TouristAuthorized, "John", "Doe", "john_doe",
                "john.doe@email.com", "password123");
        Multimedia multimedia = new Multimedia(user, "This is a multimedia text", "path/to/photo.jpg", 2);
        contestController.addContest(contest);
        contestController.addWithPending(multimedia, contest);
        assertFalse(multimedia.isValidate());
    }
}

