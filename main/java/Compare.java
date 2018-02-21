import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Stream;


public class Compare {


    public static void main(String[] args) throws IOException {
        // create a new file input stream with the input file specified
        // at the command line
        //Need to modify the path for different username here
        String activeListing = "/Users/jianan/Downloads/active.txt";
        String onSkuGrid = "/Users/jianan/Downloads/skuids.txt";
        // Win
        //  String onSkuGrid = "D:\\skuids.txt";

        HashSet<String> idsActiveListing = loadTxtFile(activeListing);
        HashSet<String> idsOnSku = loadTxtFile(onSkuGrid);

        HashSet<String> idsActiveListingClone = (HashSet<String>) idsActiveListing.clone();

        idsActiveListingClone.removeAll(idsOnSku);

        HashSet<String> idsOnSkuClone = (HashSet<String>) idsOnSku.clone();
        idsOnSkuClone.removeAll(idsActiveListing);

        // In porject output directory
        writeToFile("activebutnotinSkugrid.txt", idsActiveListingClone.toString());
        writeToFile("notactive.txt",idsOnSkuClone.toString());


        System.out.println("Good");

    }


    public static HashSet<String> loadTxtFile(String filePath) {

        String ids = "";
        HashSet<String> list = new HashSet<>();

        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            StringBuilder data = new StringBuilder();
            //    lines.forEach(line -> data.append(line).append("\n"));
            lines.forEach(line -> list.add(line));
            lines.close();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        if (ids != "") {
            System.out.println("Txt File Loaded");
        }
        return list;
    }

    public static void writeToFile(String fileName, String content) throws IOException {

        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);

    }
}



