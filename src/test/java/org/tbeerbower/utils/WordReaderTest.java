package org.tbeerbower.utils;

import org.junit.Before;
import org.junit.Test;
import org.tbeerbower.view.View;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WordReaderTest {

    private static final String WORD_1 = "train";
    private static final String WORD_2 = "brick";
    private static final String WORD_3 = "testy";
    public static final String MOCK_PATH = "resources/words.txt";

    private View view;
    private ScannerProvider scannerProvider;
    private WordReader wordReader;

    @Before
    public void setUp() throws Exception {
        view = mock(View.class);
        scannerProvider = mock(ScannerProvider.class);
        wordReader = new WordReader(view, scannerProvider);
    }

    @Test
    public void getWords() throws FileNotFoundException {
        // arrange
        Scanner scanner = new Scanner(String.format("%s%n%s%n%s", WORD_1, WORD_2, WORD_3));
        when(scannerProvider.getScanner(MOCK_PATH)).thenReturn(scanner);
        List<String> expectedWords = Arrays.asList(WORD_1, WORD_2, WORD_3);

        // act
        List<String> actualWords = wordReader.getWords(MOCK_PATH);

        // assert
        assertEquals(expectedWords, actualWords);
        verify(view, never()).displayLine(anyString());
    }

    @Test
    public void getWords_fileNotFound() throws FileNotFoundException {
        // arrange
        when(scannerProvider.getScanner(MOCK_PATH)).thenThrow(new FileNotFoundException("File not found!"));

        // act
        List<String> actualWords = wordReader.getWords(MOCK_PATH);

        // assert
        assertEquals(0, actualWords.size());
        verify(view).displayLine("Can't read words from " + MOCK_PATH + ": " + "File not found!");
    }
}