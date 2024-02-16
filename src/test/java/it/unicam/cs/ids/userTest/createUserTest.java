package it.unicam.cs.ids.userTest;

import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import org.h2.engine.UserBuilder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test class for validating the creation of different user roles using the {@link UserBuilder}.
 */
public class createUserTest {

    /**
     * Test method for creating a Contributor user.
     */
    @Test
    public void testCreateContributor() {
        BaseUser contributor = new BaseUser(1,"Contributor", "Nicolo", "Stefani", "nico_ste", "nico@example.com", "password");

        assertEquals(UserRole.Contributor, contributor.getUserType());
        assertEquals("Contributor", contributor.getUserTypeString());
        assertEquals("Nicolo", contributor.getName());
        assertEquals("Stefani", contributor.getSurname());
        assertEquals("nico_ste", contributor.getUsername());
        assertEquals("nico@example.com", contributor.getEmail());
        assertEquals("password", contributor.getPassword());
    }


}
