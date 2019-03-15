import org.junit.Test;

import java.nio.file.Paths;

public class WorldReaderImplTest {

    private WorldReader worldReader = new WorldReaderImpl();

    @Test
    public void readMap() {
        World world = worldReader.readMap(Paths.get("/3_budapest.txt"));
    }
}