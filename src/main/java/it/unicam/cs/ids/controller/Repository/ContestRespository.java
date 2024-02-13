package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.contest.Contest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContestRespository extends CrudRepository<Contest,Integer> {
    @Query ("SELECT x FROM Contest x WHERE x.contestName= :title")
    Contest findAllByTitle(@Param("title") String title);
}
