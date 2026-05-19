package manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static final String DATA_DIR = "data";
    public static final String USERS_FILE = DATA_DIR + "/users.csv";
    public static final String STATISTICS_FILE = DATA_DIR + "/statistics.csv";
    public static final String SAVED_GAMES_FILE = DATA_DIR + "/saved_games.csv";
    public static final String QUESTIONS_FILE = DATA_DIR + "/pasapalabra_questions.csv";

    public static void initializeDataFiles() {
        createFile(USERS_FILE);
        createFile(STATISTICS_FILE);
        createFile(SAVED_GAMES_FILE);
        createFile(QUESTIONS_FILE);
        if (readFile(QUESTIONS_FILE).isEmpty()) {
            List<String> questions = new ArrayList<String>();
            questions.add("A|Fruit that can be red or green|apple");
            questions.add("B|Place where you keep money|bank");
            questions.add("C|Animal often kept as a pet and says meow|cat");
            questions.add("D|Animal often kept as a pet and says bark|dog");
            questions.add("E|Large animal with a trunk|elephant");
            questions.add("F|Animal that lives in water and has fins|fish");
            questions.add("G|Color made by mixing black and white|gray");
            questions.add("H|Building where doctors work|hospital");
            questions.add("I|Frozen water|ice");
            questions.add("J|Sweet fruit often made into jam|jam");
            questions.add("K|Object used to open a lock|key");
            questions.add("L|Object used to read books at night|lamp");
            questions.add("M|Natural satellite of Earth|moon");
            questions.add("N|Opposite of day|night");
            questions.add("O|Fruit used to make juice|orange");
            questions.add("P|Writing tool with ink|pen");
            questions.add("Q|Person who rules as a female monarch|queen");
            questions.add("R|Color of an apple or rose|red");
            questions.add("S|Large star in our solar system|sun");
            questions.add("T|Tall plant with a trunk and branches|tree");
            questions.add("U|Object used when it rains|umbrella");
            questions.add("V|Large vehicle used to transport goods|van");
            questions.add("W|Liquid we drink|water");
            questions.add("X|Musical instrument with bars struck by mallets|xylophone");
            questions.add("Y|Color of a banana|yellow");
            questions.add("Z|Animal with black and white stripes|zebra");
            writeFile(QUESTIONS_FILE, questions);
        }
    }

    private static void createFile(String path) {
        try {
            File file = new File(path);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not create file " + path, ex);
        }
    }

    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<String>();
        try {
            File file = new File(path);
            if (!file.exists()) {
                createFile(path);
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    lines.add(line);
                }
            }
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException("Could not read file " + path, ex);
        }
        return lines;
    }

    public static void writeFile(String path, List<String> lines) {
        try {
            createFile(path);
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, false));
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException("Could not write file " + path, ex);
        }
    }

    public static void appendLine(String path, String line) {
        try {
            createFile(path);
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException("Could not append file " + path, ex);
        }
    }
}
