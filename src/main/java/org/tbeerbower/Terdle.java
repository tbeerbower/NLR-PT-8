package org.tbeerbower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tbeerbower.services.GameService;
import org.tbeerbower.services.PlayerService;
import org.tbeerbower.view.MainMenu;
import org.tbeerbower.view.View;

import java.util.Scanner;

@SpringBootApplication
public class Terdle  implements CommandLineRunner {

    private final PlayerService playerService;
    private final GameService gameService;

    @Autowired
    public Terdle(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Terdle.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        View view = new View(new Scanner(System.in));

        view.displayLine("Welcome to TErdle!");
        GameService.initWords(view);
        MainMenu mainMenu = new MainMenu(view, playerService, gameService);
        mainMenu.run();
    }
}