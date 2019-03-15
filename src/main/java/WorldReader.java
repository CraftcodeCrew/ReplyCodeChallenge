import java.nio.file.Path;

public interface WorldReader {

    World readMap(Path pathToFile);

}
