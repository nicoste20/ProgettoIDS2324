package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FestivalRepository extends JpaRepository<Festival,Integer> {
    @Query("SELECT COUNT(f) FROM Festival f WHERE f.name = :name")
    int countFestivalsWithName(@Param("name") String text);

    @Query("SELECT f.id FROM Festival f WHERE f.name = :name")
    int findFestivalIdByDescription(@Param("name") String text);
}
