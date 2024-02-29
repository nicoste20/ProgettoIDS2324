package it.unicam.cs.ids.controller.Repository;
import it.unicam.cs.ids.model.content.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point,Integer> {
    @Query ("SELECT COUNT (z) FROM Point2D z WHERE z.x = :x AND z.y= :y")
    int isAlreadyIn(@Param("x") Float x, @Param("y") Float y);

    @Query ("SELECT COUNT (z) FROM Point z WHERE z.name = :name")
    int isAlreadyInByTitle(@Param("name") String name);

    @Query("SELECT m.id FROM Monument m WHERE m.name = :title " +
            "UNION " +
            "SELECT g.id FROM GreenZone g WHERE g.name = :title " +
            "UNION " +
            "SELECT r.id FROM Restaurant r WHERE r.name = :title " +
            "UNION " +
            "SELECT s.id FROM Square s WHERE s.name = :title")
    Integer findAllByTitle(@Param("title") String title);
}
