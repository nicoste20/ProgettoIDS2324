package it.unicam.cs.ids.controllerTest;

import it.unicam.cs.ids.controller.MultimediaController;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the {@link MultimediaController} class.
 */
public class MultimediaTest {

    private final MultimediaController multimediaController = new MultimediaController();
    /**
     * Test for adding content with no pending approval.
     */
    @Test
    public void testAddContentNoPending() {
        BaseUser contributorAuthorized = new BaseUser(1, UserRole.ContributorAuthorized, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");
        String description = "This is my first post!";
        String photo = "/path";


        Multimedia multimedia = new Multimedia(contributorAuthorized, description, photo, 0);

        multimediaController.addContent(multimedia);

        assertEquals(1, multimediaController.getContentListSize());
    }

    /**
     * Test for adding content with pending approval.
     */
    @Test
    public void testAddContentPending() {
        BaseUser contributor = new BaseUser(1, UserRole.Contributor, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");
        String description = "This is my first post!";
        String photo = "/path";

        Multimedia multimedia = new Multimedia(contributor, description, photo, 0);

        multimediaController.addContent(multimedia);

        assertEquals(1, multimediaController.getContentListSize());
    }

    /**
     * Test for adding content with an invalid user type.
     */
    @Test
    public void testAddContentInvalidUserType() {
        BaseUser tourist = new BaseUser(1, UserRole.Tourist, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");
        String description = "This is my first post!";
        String photo = "/path";

        Multimedia multimedia = new Multimedia(tourist, description, photo, 0);

        multimediaController.addContent(multimedia);

        assertEquals(0, multimediaController.getContentListSize());
    }

    /**
     * Test for validating content with approval.
     */
    @Test
    public void testValidateContentApprove() {
        BaseUser contributor = new BaseUser(1, UserRole.Contributor, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");
        String description = "This is my first post!";
        String photo = "/path";

        Multimedia multimedia = new Multimedia(contributor, description, photo, 0);

        multimediaController.addContent(multimedia);

        assertEquals(1, multimediaController.getContentListSize());

        BaseUser curator = new BaseUser(2, UserRole.Curator, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        multimediaController.validateContent(curator, true, multimedia);

        assertEquals(1, multimediaController.getContentListSize());
    }

    /**
     * Test for validating content with rejection.
     */
    @Test
    public void testValidateContentReject() {
        BaseUser contributor = new BaseUser(1, UserRole.Contributor, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");
        String description = "This is my first post!";
        String photo = "/path";

        Multimedia multimedia = new Multimedia(contributor, description, photo, 0);

        multimediaController.addContent(multimedia);

        assertEquals(1, multimediaController.getContentListSize());

        BaseUser curator = new BaseUser(2, UserRole.Curator, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        multimediaController.validateContent(curator, false, multimedia);

        assertEquals(0, multimediaController.getContentListSize());
    }
}
