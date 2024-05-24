package org.tbeerbower.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility class for getting a scanner.
 */
public class ScannerProvider {
    /**
     * @return a new Scanner from the given file path
     */
    public Scanner getScanner(String path) throws FileNotFoundException {
        return new Scanner(new File(path));
    }
}
