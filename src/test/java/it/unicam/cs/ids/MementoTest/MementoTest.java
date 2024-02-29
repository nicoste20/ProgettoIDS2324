package it.unicam.cs.ids.MementoTest;

import it.unicam.cs.ids.model.content.Festival;
import it.unicam.cs.ids.model.memento.FestivalMemento;

import java.util.Arrays;
import java.util.Date;

import java.util.Arrays;

public class MementoTest {
    public static void main(String[] args) {
        // Create a Festival object
        Festival festival = new Festival("My Festival",
                Arrays.asList(1, 2, 3),
                new Date(),
                new Date(System.currentTimeMillis() + 86400000), // Adding one day in milliseconds
                "A great festival");

        // Display the initial state of the Festival
        System.out.println("Initial Festival State:");
        displayFestivalDetails(festival);

        // Create a Memento and store the state
        FestivalMemento memento = festival.createMemento();

        // Modify the Festival object
        festival.setName("Updated Festival");
        festival.setEndDate(new Date(System.currentTimeMillis() + 172800000)); // Adding two days in milliseconds
        festival.setDescription("An even better festival");

        // Display the modified state of the Festival
        System.out.println("\nModified Festival State:");
        displayFestivalDetails(festival);

        // Restore the Festival state from the Memento
        festival.restoreFromMemento(memento);

        // Display the restored state of the Festival
        System.out.println("\nRestored Festival State:");
        displayFestivalDetails(festival);
    }

    private static void displayFestivalDetails(Festival festival) {
        System.out.println("Name: " + festival.getName());
        System.out.println("Points: " + festival.getPoints());
        System.out.println("Start Date: " + festival.getStartDate());
        System.out.println("End Date: " + festival.getEndDate());
        System.out.println("Description: " + festival.getDescription());
        System.out.println("Author: " + festival.getAuthor());
        System.out.println("Is Validated: " + festival.isValidate());
        System.out.println("------------");
    }
}
