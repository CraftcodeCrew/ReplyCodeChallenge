import java.util.List;

public class World {

    public List<Customer> customerOffices;
    public int[][] map;
    public int maximumReplyOfficesToPlace;

    public World(List<Customer> customerOffices, int[][] map, int maximumReplyOfficesToPlace) {
        this.customerOffices = customerOffices;
        this.map = map;
        this.maximumReplyOfficesToPlace = maximumReplyOfficesToPlace;
    }

    public List<Customer> getCustomerOffices() {
        return customerOffices;
    }

    public void setCustomerOffices(List<Customer> customerOffices) {
        this.customerOffices = customerOffices;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getMaximumReplyOfficesToPlace() {
        return maximumReplyOfficesToPlace;
    }

    public void setMaximumReplyOfficesToPlace(int maximumReplyOfficesToPlace) {
        this.maximumReplyOfficesToPlace = maximumReplyOfficesToPlace;
    }
}
