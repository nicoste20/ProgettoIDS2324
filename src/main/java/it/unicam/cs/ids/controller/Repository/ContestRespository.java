package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContestRespository extends JpaRepository<Contest,Integer> {
    @Query ("SELECT x FROM Contest x WHERE x.name= :title")
    Contest findAllByTitle(@Param("title") String title);
}
