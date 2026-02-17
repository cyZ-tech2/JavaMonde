package games;
import java.util.Random;
import games.character.Player;
import games.character.PlayerExtension;
import games.environment.Level;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n======= CY MAZE =======");
            System.out.println("1.   JOUER");
            System.out.println("2.   Quitter le programme");
            System.out.print("Entrez le numéro : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    Level level1 = new Level();
                    Player[] p2 = PlayerExtension.createPlayers(scanner);
                    level1.createLevel(scanner, p2);
                    level1.placeCredit();
                    level1.placeTrap();
                    level1.displayMap();
                    boolean running = true;
                    while (running) {

                        level1.displayMap();

                        System.out.println("Déplacement (Z: Haut, S: Bas, Q: Gauche, D: Droite) ou 'exit' pour quitter :");
                        String input = scanner.nextLine();

                        if (input.equalsIgnoreCase("exit")) {
                            running = false;
                            System.out.println("Fermeture du niveau...");
                        } else {
                            level1.movePlayer(input);
                        }
                    }
                    PlayerExtension.removeAllPlayers(p2);
                    break;
                case 2:
                    System.out.println("Fermeture du jeu...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix incorrect.");
            }
        }
    }
}