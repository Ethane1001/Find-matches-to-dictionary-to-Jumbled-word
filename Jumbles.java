
//java Lab4.java jumbles.txt
//java Jumbles tinyDictionary.txt tinyJumbles.txt
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;


public class Jumbles {

    public static void main(String[] args) throws IOException {

        String fileName1 = args[0];
        String fileName2 = args[1];

        // Step 0
        ArrayList<String> dictionary = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName1))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(dictionary);

        // Step 1
        ArrayList<String> pairs = new ArrayList<String>();

        // Step 2
        for (String dWord : dictionary) {
            String pair = toCanonical(dWord) + " " + dWord;
            pairs.add(pair);
        }
        Collections.sort(pairs);

        // Step 3
        ArrayList<String> dCanons = new ArrayList<String>();
        ArrayList<String> dWords = new ArrayList<String>();

        // Step 4
        for (String line : pairs) {
            String[] pair = line.split("\\s+");
            dCanons.add(pair[0]);
            dWords.add(pair[1]);
        }

        // Step 5
        ArrayList<String> jWords = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName2))) {
            String line;
            while ((line = br.readLine()) != null) {
                jWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(jWords);

        // Step 6
        ArrayList<ArrayList<String>> jumbledWords = new ArrayList<ArrayList<String>>();
        for (String jWord : jWords) {
            jumbledWords.add(new ArrayList<String>());
            String jCanon = toCanonical(jWord);
            int index = Collections.binarySearch(dCanons, jCanon);
            if (index >= 0) {
                int start = index;
                while (start >= 0 && dCanons.get(start).equals(jCanon)) {
                    start--;
                }
                start++;
                index = start;
                while (index < dCanons.size() && dCanons.get(index).equals(jCanon)) {
                    jumbledWords.get(jumbledWords.size() - 1).add(dWords.get(index));
                    index++;
                }
            }
        }


        for (int i = 0; i < jWords.size(); i++) {
            System.out.print(jWords.get(i) + " ");
            Collections.sort(jumbledWords.get(i));
            for (String word : jumbledWords.get(i)) {
                System.out.print(word + " ");
            }
            System.out.println();
        }
    }

    public static String toCanonical(String word) {
        char[] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
