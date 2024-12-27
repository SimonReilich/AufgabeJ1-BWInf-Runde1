import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        //args = new String[]{"test.txt"};

        if (args.length == 0) {
            throw new IllegalArgumentException("Missing argument: Textfile to read from");
        }

        ArrayList<Integer> data = readFile(args[0]);
        int numberOfBags = data.get(0);
        int diferentGames = data.get(1);
        int[] numOfGames = new int[diferentGames];
        for (int i = 2; i < 2 + diferentGames; i++) {
            numOfGames[i - 2] = data.get(i);
        }

        SurpriseBag[] bags = new SurpriseBag[numberOfBags];
        for (int i = 0; i < numberOfBags; i++) {
            bags[i] = new SurpriseBag(diferentGames);
        }

        int currentBag = -1;
        for (int i = 0; i < diferentGames; i++) {
            for (int j = 0; j < numOfGames[i]; j++) {
                currentBag++;
                currentBag %= numberOfBags;
                bags[currentBag].addGame(i);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bags.length; i++) {
            sb.append("Surprisebag ").append(i + 1).append(": \n");
            sb.append(bags[i]);
            sb.append("\n");
        }
        output(sb.toString());
    }

    public static ArrayList<Integer> readFile(String filename) throws IOException {
        ArrayList<Integer> read = new ArrayList<>();
        Pattern pattern = Pattern.compile(".+\\.txt");
        Matcher matcher = pattern.matcher(filename);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Wrong Argument: .txt file needed to read from");
        }

        // Einlesen der übergebenen Datei
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            line = br.readLine();

            while (line != null) {
                try {
                    read.add(Integer.parseInt(line));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Wrong format: Only numbers are allowed");
                }
                line = br.readLine();
            }
        }
        return read;
    }

    private static void output(String string) {
        String fileName = "output.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(string);

            System.out.println("Text file created successfully: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}