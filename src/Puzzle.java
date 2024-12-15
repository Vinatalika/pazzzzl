import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * File: Puzzle.java
 * ---------------------------
 * This program assembles a sequence
 * of fragments into the longest possible single-row puzzle
 */

public class Puzzle {
    /**
     * The name of the file containing the data.
     */
    static final String FILE_NAME = System.getProperty("user.dir") + "/src/source.txt";

    /**
     * This method reads a file and returns a list of string fragments.
     * Each line from the file is treated as a separate fragment.
     * The file path is specified by the `FILE_NAME` constant.
     *
     * @return A list of strings representing the fragments read from the file.
     *         Each string corresponds to a line in the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static List<String> readFromFile() throws IOException {
        List<String> fragments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                fragments.add(line.trim());
            }
        }
        return fragments;
    }
    /**
     * This method searches for the longest sequence of fragments in the provided list.
     * A fragment sequence is formed when the last two characters of one fragment match
     * the first two characters of the next fragment in the sequence. The method builds
     * a sequence starting from each fragment in the list and tries to extend it by
     * connecting fragments based on the matching characters.
     * The longest sequence found is returned as a string.
     *
     * @param fragments A list of string fragments that will be checked for forming the longest sequence.
     *                  Each fragment is considered as a possible starting point for a sequence.
     *
     * @return A string representing the longest sequence of fragments, where each subsequent fragment
     *         starts with the last two characters of the previous one.
     */
    public static String findLongestSequenceAsString(List<String> fragments) {
        String longestSequence = "";

            // We go through all fragments as possible starting elements
        for (int startIndex = 0; startIndex < fragments.size(); startIndex++) {
            List<String> remainingFragments = new ArrayList<>(fragments); // a copy of the list
            StringBuilder currentSequence = new StringBuilder();

            // We add the starting element to the current line
            String currentFragment = remainingFragments.get(startIndex);
            currentSequence.append(currentFragment);
            remainingFragments.remove(startIndex);

            // We find the following elements of the sequence
            String lastTwo = currentFragment.substring(currentFragment.length() - 2);
            boolean foundMatch;
            // Attempt to find the next fragment that matches
            do {
                foundMatch = false;
                Iterator<String> iterator = remainingFragments.iterator();
                // Iterate through the remaining fragments to find a match
                while (iterator.hasNext()) {
                    String nextFragment = iterator.next();
                    String firstTwo = nextFragment.substring(0, 2);

                    if (lastTwo.equals(firstTwo)) {
                        currentSequence.append(nextFragment.substring(2));
                        lastTwo = nextFragment.substring(nextFragment.length() - 2);
                        iterator.remove();
                        foundMatch = true;
                        break;
                    }
                }
            } while (foundMatch);

            // If the current sequence is longer than the longest, we update the longest
            if (currentSequence.length() > longestSequence.length()) {
                longestSequence = currentSequence.toString();
            }
        }
        return longestSequence;
    }

    /**
     * The main method serves as the entry point of the program.
     * It reads a list of string fragments from a file, then finds the longest sequence of fragments
     * where the last two characters of one fragment match the first two characters of the next.
     * Finally, it prints the longest sequence as a single string.
     * The method handles possible `IOException` when reading the file, printing an error message if the file cannot be read.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        try {
            List<String> fragments = readFromFile();
            String longestSequence = findLongestSequenceAsString(fragments);
            System.out.println("Longest sequence as a string:");
            System.out.println(longestSequence);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
