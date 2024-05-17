package org.tbeerbower.utils;

import org.tbeerbower.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class to read in a list of words.
 */
public class WordReader {
    private final View view;

    public WordReader(View view) {
        this.view = view;
    }

    /**
     * Read the words from the file at the given path and return a list of all the words
     * @param path path to the file containing words to add
     * @return the list of words
     */
    public List<String> getWords(String path) {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                words.add(word);
            }

        } catch (FileNotFoundException e) {
            view.displayLine("Can't read words from " + path + ": " + e.getMessage());
        }
        return words;
    }
}
