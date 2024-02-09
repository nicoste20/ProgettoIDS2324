package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Itinerary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItineraryRepository extends CrudRepository<Itinerary,Integer> {

    @Query("SELECT i FROM Itinerary i WHERE i.description = :title")
    Optional<Itinerary> findByDescription(@Param("title") String title);

}
