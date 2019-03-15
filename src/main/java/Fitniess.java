import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Fitniess implements FitnessFunction {

    private World currentWorld;


    @Override
    public int calculateFitness(World world, List<Point> replyOffices) {
        this.currentWorld = world;
        return replyOffices.stream()
                .parallel()
                .map(office -> getAllPossibleSteps(currentWorld, office))
                .map(steps -> steps.map(p -> calcFitniessRec(this.currentWorld, p, 0)))
                .map(value -> value.mapToInt(Integer::intValue).sum())
                .mapToInt(Integer::intValue)
                .sum();
    }


    private int calcFitniessRec(World myWorld, Point myPosition, int pathWeight) {
        if(isCustomers(myPosition)) {
            int totalWeight = pathWeight - wightOfTerran(myPosition);
            return this.customerValue(myPosition) + totalWeight;
        } else if (this.getAllPossibleSteps(myWorld, myPosition).count() == 0) {
            return Integer.MIN_VALUE;
        } else {
            final int newWeight = pathWeight - this.wightOfTerran(myPosition);
            World newWorld = killMe(myWorld, myPosition);
            return this.getAllPossibleSteps(newWorld, myPosition)
                    .parallel()
                    .map(p -> this.calcFitniessRec(newWorld, p, newWeight))
                    .filter(i -> ! i.equals(Integer.MIN_VALUE))
                    .mapToInt(Integer::intValue)
                    .sum();
        }
    }

    private World killMe(World oldWorld, Point myPosition) {
        World newWorld = new World(oldWorld.customerOffices, oldWorld.map, oldWorld.maximumReplyOfficesToPlace);
        newWorld.map[myPosition.y][myPosition.x] = 0;
        return newWorld;
    }

    private Stream<Point> getAllPossibleSteps(World myWorld, Point myPosition) {
        List<Point> possibleSteps = new ArrayList<>();
        possibleSteps.add(new Point(myPosition.x, myPosition.y + 1));
        possibleSteps.add(new Point(myPosition.x, myPosition.y - 1));
        possibleSteps.add(new Point(myPosition.x + 1, myPosition.y ));
        possibleSteps.add(new Point(myPosition.x -1 , myPosition.y ));

        return possibleSteps.stream()
                .filter(this::inBorder)
                .filter(this::isMounten)
                .filter(p -> this.alreadyVisted(myWorld, p));
    }

    private boolean alreadyVisted(World myWorld, Point point) {
        return myWorld.map[point.y][point.x] == 0;
    }

    private int customerValue(Point customer) {
        return this.currentWorld.customerOffices.stream()
                .filter(c -> c.location.equals(customer))
                .findAny()
                .map(c -> c.score)
                .orElse(0);
    }

    private int wightOfTerran(Point terran){
        return this.currentWorld.map[terran.y][terran.x];
    }

    private boolean isMounten(Point terran){
        return this.wightOfTerran(terran) == -1;
    }

    private boolean isCustomers(Point terran){
        return this.currentWorld.customerOffices
                .stream()
                .map(customer -> customer.location)
                .anyMatch(p -> p.equals(terran));
    }

    private boolean inBorder(Point destionationPoint) {
        int destinationX = destionationPoint.x;
        int destinationY = destionationPoint.y;

        return destinationX < currentWorld.map[0].length && destinationY < currentWorld.map.length;
    }
}
