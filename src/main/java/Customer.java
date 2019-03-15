import java.awt.*;

public class Customer {

    Point location;
    int score;

    public Customer(Point location, int score) {
        this.location = location;
        this.score = score;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
