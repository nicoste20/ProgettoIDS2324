package it.unicam.cs.ids.controllerTest;

import it.unicam.cs.ids.controller.ItineraryController;
import it.unicam.cs.ids.model.content.Itinerary;
import it.unicam.cs.ids.model.point.Monument;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import it.unicam.cs.ids.util.Point2D;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test class for the {@link ItineraryController} class.
 */
public class ItineraryControllerTest {

    private final ItineraryController itineraryController = new ItineraryController();

    /**
     * Test for adding an Itinerary with no pending
     */
    @Test
    public void testAddItineraryNoPending(){
        BaseUser contributorAuthorized = new BaseUser(1, UserRole.ContributorAuthorized, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Itinerary itinerary1= new Itinerary(contributorAuthorized, "Itinerary1" , 345);
        Monument colosseum= new Monument(new Point2D(23.0, 67.0),contributorAuthorized,"Monument",  "testPoint", 1 , "colosseum", 12/11/2002, "antique monument");
        itinerary1.addPoint(colosseum);

        itineraryController.addItinerary(itinerary1, contributorAuthorized);
        assertEquals(1,itineraryController.getItinerariesSize());
        assertEquals(0,itineraryController.getPendingItinerariesSize());
    }

    /**
     * Test for adding an Itinerary with pending
     */
    @Test
    public void testAddItineraryPending(){
        BaseUser contributor = new BaseUser(1, UserRole.Contributor, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Itinerary itinerary1= new Itinerary(contributor, "Itinerary1" , 543);
        BaseUser contributorAuthorized = new BaseUser(2, UserRole.ContributorAuthorized, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Monument colosseum= new Monument(new Point2D(23.0, 67.0),contributorAuthorized,"Monument",  "testPoint", 1 , "colosseum", 12/11/2002, "antique monument");        itinerary1.addPoint(colosseum);

        itineraryController.addItinerary(itinerary1, contributor);
        assertEquals(0,itineraryController.getItinerariesSize());
        assertEquals(1,itineraryController.getPendingItinerariesSize());
    }
    /**
     * Test for adding an Itinerary from an invalid user
     */
    @Test
    public void testAddItineraryInvalidUserType(){
        BaseUser touristUser = new BaseUser(1, UserRole.Tourist, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Itinerary itinerary1= new Itinerary(touristUser, "Itinerary1" , 567);
        BaseUser contributorAuthorized = new BaseUser(2, UserRole.ContributorAuthorized, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Monument colosseum= new Monument(new Point2D(23.0, 67.0),contributorAuthorized,"Monument",  "testPoint", 1 , "colosseum", 12/11/2002, "antique monument");        itinerary1.addPoint(colosseum);

        itineraryController.addItinerary(itinerary1, touristUser);
        assertEquals(0,itineraryController.getItinerariesSize());
        assertEquals(0,itineraryController.getPendingItinerariesSize());
    }
    /**
     * Test for approving an itinerary to be added
     */
    @Test
    public void testValidItineraryApproved(){
        BaseUser contributor = new BaseUser(1, UserRole.Contributor, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Itinerary itinerary1= new Itinerary(contributor, "Itinerary1" , 543);
        BaseUser contributorAuthorized = new BaseUser(2, UserRole.ContributorAuthorized, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Monument colosseum= new Monument(new Point2D(23.0, 67.0),contributorAuthorized,"Monument",  "testPoint", 1 , "colosseum", 12/11/2002, "antique monument");
        itinerary1.addPoint(colosseum);

        itineraryController.addItinerary(itinerary1, contributor);
        assertEquals(0,itineraryController.getItinerariesSize());
        assertEquals(1,itineraryController.getPendingItinerariesSize());
        BaseUser curator = new BaseUser(1, UserRole.Curator, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        itineraryController.validateItinerary(curator, true, itinerary1);
        assertEquals(1,itineraryController.getItinerariesSize());
        assertEquals(0,itineraryController.getPendingItinerariesSize());
    }
    /**
     * Test for not approving an itinerary to be added
     */
    @Test
    public void testValidItineraryRejected(){
        BaseUser contributor = new BaseUser(1, UserRole.Contributor, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Itinerary itinerary1= new Itinerary(contributor, "Itinerary1" , 543);
        BaseUser contributorAuthorized = new BaseUser(2, UserRole.ContributorAuthorized, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        Monument colosseum= new Monument(new Point2D(23.0, 67.0),contributorAuthorized,"Monument",  "testPoint", 1 , "colosseum", 12/11/2002, "antique monument");
        itinerary1.addPoint(colosseum);

        itineraryController.addItinerary(itinerary1, contributor);
        assertEquals(0,itineraryController.getItinerariesSize());
        assertEquals(1,itineraryController.getPendingItinerariesSize());
        BaseUser curator = new BaseUser(1, UserRole.Curator, "Alessio", "Marinelli", "marinx", "ale@example.com", "password");
        itineraryController.validateItinerary(curator, false, itinerary1);
        assertEquals(0,itineraryController.getItinerariesSize());
        assertEquals(0,itineraryController.getPendingItinerariesSize());
    }

}
