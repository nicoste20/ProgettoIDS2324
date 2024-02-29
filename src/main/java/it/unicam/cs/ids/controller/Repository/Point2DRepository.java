package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.util.Point2D;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Point2DRepository extends JpaRepository<Point2D,Integer> {

    @Query("SELECT z.id FROM Point2D z WHERE z.x = :x AND z.y = :y")
    void deleteByCoordinate(@Param("x") Float x, @Param("y") Float y);
}
