package it.unicam.cs.ids.PrototypeDesignPatternTest;

import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.point.GreenZone;
import it.unicam.cs.ids.model.point.Square;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PrototypeDesignPatternTest {
    @Test
    public void testCopy(){
        List<Point> points = new ArrayList<>();
        List<Point> pointsCopy = new ArrayList<>();

        GreenZone zonaVerde = new GreenZone((float) 0.0,(float) 0.0,"GreenZone","ZonaVerde1","Altalena");
        points.add(zonaVerde);

        Square piazza = new Square((float) 0.0,(float) 0.0,"Square","PiazzaCentrale","La vecchia piazza della citta");
        points.add(piazza);

        cloneAndCompare(points,pointsCopy);
    }


    private static void cloneAndCompare(List<Point> points, List<Point> pointsCopy){
        for(Point point:points){
            pointsCopy.add(point.clone());
        }

        for(int i=0;i<points.size();i++){
            System.out.println(points.get(i)+"-----"+pointsCopy.get(i));
            if (points.get(i) != pointsCopy.get(i)) {
                System.out.println(i + ": Points are different objects");
                if (points.get(i).equals(pointsCopy.get(i))) {
                    System.out.println(i + ": And they are identical");
                } else {
                    System.out.println(i + ": But they are not identical ");
                }
            } else {
                System.out.println(i + ": Points objects are the same");
            }
        }
    }
}
