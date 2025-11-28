import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Dictionary.
 */
public class Dictionary {
    private static Set<String> dictionary = new HashSet<>();

    /**
     * Load dictionary.
     *
     * @param filePath the file path
     */
    public static void loadDictionary(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Is word in dictionary boolean.
     *
     * @param word the word
     * @return the boolean
     */
    public static boolean isWordInDictionary(String word) {
        return dictionary.contains(word);
    }
}