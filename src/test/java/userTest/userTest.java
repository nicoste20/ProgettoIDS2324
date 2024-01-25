package userTest;

import it.unicam.cs.ids.model.user.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for validating the creation of different user roles using the {@link UserBuilder}.
 */
public class userTest {

    /**
     * Test method for creating a Contributor user.
     */
    @Test
    public void testCreateContributor() {
        BaseUser contributor = new UserBuilder().Contributor(1, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        assertEquals(UserRole.Contributor, contributor.getUserType());
        assertEquals("Nicolo", contributor.getName());
        assertEquals("Stefani", contributor.getSurname());
        assertEquals("nico_ste", contributor.getUsername());
        assertEquals("nico@example.com", contributor.getEmail());
        assertEquals("password", contributor.getPassword());
    }

    /**
     * Test method for creating a ContributorAuthorized user.
     */
    @Test
    public void testCreateContributorAuthorized() {
        BaseUser contributor = new UserBuilder().ContributorAuthorized(1, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        assertEquals(UserRole.ContributorAuthorized, contributor.getUserType());
        assertEquals("Nicolo", contributor.getName());
        assertEquals("Stefani", contributor.getSurname());
        assertEquals("nico_ste", contributor.getUsername());
        assertEquals("nico@example.com", contributor.getEmail());
        assertEquals("password", contributor.getPassword());
    }

    /**
     * Test method for creating a Curator user.
     */
    @Test
    public void testCreateCurator() {
        BaseUser curator = new UserBuilder().Curator(1, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        assertEquals(UserRole.Curator, curator.getUserType());
        assertEquals("Nicolo", curator.getName());
        assertEquals("Stefani", curator.getSurname());
        assertEquals("nico_ste", curator.getUsername());
        assertEquals("nico@example.com", curator.getEmail());
        assertEquals("password", curator.getPassword());
    }

    /**
     * Test method for creating an Animator user.
     */
    @Test
    public void testCreateAnimator() {
        BaseUser animator = new UserBuilder().Animator(1, "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        assertEquals(UserRole.Animator, animator.getUserType());
        assertEquals("Nicolo", animator.getName());
        assertEquals("Stefani", animator.getSurname());
        assertEquals("nico_ste", animator.getUsername());
        assertEquals("nico@example.com", animator.getEmail());
        assertEquals("password", animator.getPassword());
    }
}
