package it.unicam.cs.ids.controller.Repository;
import it.unicam.cs.ids.model.content.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends CrudRepository<Point,Integer> {
    @Query ("SELECT COUNT (z) FROM Point z WHERE z.coordinates.x= :x AND z.coordinates.y= :y")
    int isAlreadyIn(@Param("x") double x, @Param("y") double y);

    @Query ("SELECT x FROM Point x WHERE x.title= :title")
    Point findAllByTitle(@Param("title") String title);
}
