package it.unicam.cs.ids.controller.Repository;
import it.unicam.cs.ids.model.content.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point,Integer> {
    @Query ("SELECT COUNT (z) FROM Point2D z WHERE z.x = :x AND z.y= :y")
    int isAlreadyIn(@Param("x") Float x, @Param("y") Float y);

    @Query ("SELECT x FROM Monument x WHERE x.name= :title")
    Point findAllByTitle(@Param("title") String title);
}
