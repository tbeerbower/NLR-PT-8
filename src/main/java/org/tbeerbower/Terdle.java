package org.tbeerbower;

import org.tbeerbower.services.GameService;
import org.tbeerbower.view.View;

import java.util.Scanner;

public class Terdle {

    public static void main(String[] args) {
        Terdle terdleGame = new Terdle();
        terdleGame.run();
    }

    public void run() {
        View view = new View(new Scanner(System.in));

        view.displayLine("Welcome to TErdle!");
        GameService.initWords(view);
        MainMenu mainMenu = new MainMenu(view);
        mainMenu.run();
    }
}