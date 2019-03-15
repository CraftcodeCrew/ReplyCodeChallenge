import java.awt.*;
import java.util.List;

public interface FitnessFunction{

    int calculateFitness(World world, List<Point> replyOffices);

}
