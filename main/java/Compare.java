import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Stream;


public class Compare {

    public static void main(String[] args) {

//        String firstFile = "/Users/xxx/Downloads/TS";
//        String secondFile = "/Users/xxx/Downloads/PG";

        System.out.println("Pls enter the full path for the 1st file. Example on Linux: /Pathto/file; Win D:\\file");

        Scanner input = new Scanner(System.in);
        String firstFile = input.nextLine();

        System.out.println("Please enter the full path for the 2nd file");
        String secondFile = input.nextLine();

        String fileNameA;
        String fileNameB;
        String path;

        HashSet<String> firstFile_Content = loadTxtFile(firstFile);
        HashSet<String> secondFile_Content = loadTxtFile(secondFile);

        if (firstFile_Content.isEmpty() || secondFile_Content.isEmpty()) {
            System.out.println("Failed to load file content or the file is empty. Please check your path.");

        } else {
            HashSet<String> inFirstButNotSec = new HashSet<String>(firstFile_Content);
            HashSet<String> inSecButNotFirst = new HashSet<String>(secondFile_Content);

            inFirstButNotSec.removeAll(secondFile_Content);
            inSecButNotFirst.removeAll(firstFile_Content);

            if (firstFile.lastIndexOf('/') == -1) {
                // For Windows machine
                int index = firstFile.lastIndexOf('\\');
                fileNameA = firstFile.substring(index + 1);
                fileNameB = secondFile.substring(index + 1);
                path = firstFile.substring(0, index + 1);

            } else {
                // Linux
                int index = firstFile.lastIndexOf('/');
                fileNameA = firstFile.substring(index + 1);
                fileNameB = secondFile.substring(index + 1);
                path = firstFile.substring(0, index + 1);
            }
            String resultFileNameA = "Items_in_" + fileNameA + "_but_NOT_in_" + fileNameB;
            String resultFileNameB = "Items_in_" + fileNameB + "_but_NOT_in_" + fileNameA;

            StringBuilder inFirstButNotSec_Result = new StringBuilder();
            StringBuilder inSecButNotFirst_Result = new StringBuilder();

            inFirstButNotSec.forEach(s -> inFirstButNotSec_Result.append(s).append("\n"));
            inSecButNotFirst.forEach(s -> inSecButNotFirst_Result.append(s).append("\n"));

            try {
                writeToFile(path + resultFileNameA, inFirstButNotSec_Result.toString());
                writeToFile(path + resultFileNameB, inSecButNotFirst_Result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Result saved to " + resultFileNameA + " and " + resultFileNameB);
            System.out.println(resultFileNameA + " has " + inFirstButNotSec.size() + " items");
            System.out.println(resultFileNameB + " has " + inSecButNotFirst.size() + " items");
        }
    }


    private static HashSet<String> loadTxtFile(String filePath) {

        HashSet<String> list = new HashSet<>();

        try {
            Stream<String> lines = Files.lines(Paths.get(filePath));
            lines.forEach(line -> list.add(line.trim().toLowerCase()));
            lines.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);

    }
}



