import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WorldReaderImpl implements WorldReader {
    private int numberOfCustomerOffices;
    private int maximumReplyOfficesToPlace;
    private int xMax;
    private int yMax;
    private int[][] map;
    private List<Customer> customerOffices;

    @Override
    public World readMap(Path pathToFile) {
        InputStream resourceAsStream = WorldReaderImpl.class.getResourceAsStream(pathToFile.toString());
        World world = null;
        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
            String currentLine = reader.readLine();
            String[] metaData = currentLine.split(" ");

            this.getMetadata(metaData);
            customerOffices = new ArrayList<>(numberOfCustomerOffices);
            map = new int[yMax][xMax];
            this.readCustomerOffices(reader);
            this.readWorldMap(reader);

            world = new World(customerOffices, map, maximumReplyOfficesToPlace);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return world;
    }

    private void readWorldMap(BufferedReader reader) throws Exception {
        for (int i = 0; i < yMax; i++){
            String worldLine = reader.readLine();
            for (int k = 0; k < xMax; k++) {
                char environment = worldLine.charAt(k);
                int cost = getCost(environment);
                map[i][k] = cost;
            }
        }
    }

    private int getCost(char environment) {
        switch (environment) {
            case '#': return -1;
            case '~': return 800;
            case '*': return 200;
            case '+': return 150;
            case 'X': return 120;
            case '_': return 100;
            case 'H': return 70;
            case 'T': return 50;
            default: return -1;
        }
    }

    private void readCustomerOffices(BufferedReader reader) throws Exception {
        for (int i = 0; i < numberOfCustomerOffices; i++){
            String customerString = reader.readLine();
            String[] split = customerString.split(" ");
            Customer customerOffice = new Customer(new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1])),
                    Integer.parseInt(split[2]));
            customerOffices.add(customerOffice);
        }
    }

    private void getMetadata(String[] metaData) {
        xMax = Integer.parseInt(metaData[0]);
        yMax = Integer.parseInt(metaData[1]);
        numberOfCustomerOffices = Integer.parseInt(metaData[2]);
        maximumReplyOfficesToPlace = Integer.parseInt(metaData[3]);
    }
}
