package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Festival;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface FestivalRepository extends CrudRepository<Festival,Integer> {
    @Query("SELECT COUNT(f) FROM Festival f WHERE f.description = :text")
    int countFestivalsWithDescription(@Param("text") String text);

    @Query("SELECT f.id FROM Festival f WHERE f.description = :text")
    Integer findFestivalIdByDescription(@Param("text") String text);
}
