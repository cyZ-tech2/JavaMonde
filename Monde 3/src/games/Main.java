package games;

import games.character.Player;
import games.character.PlayerExtension;
import games.environment.Level;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mapPath = null;

        if (args.length > 0) {
            mapPath = args[0];
        }

        while (true) {
            System.out.println("\n======= CY MAZE =======");
            if (mapPath != null) {
                System.out.println("Fichier chargé : " + mapPath);
            }
            System.out.println("1.   JOUER");
            System.out.println("2.   Changer le fichier niveau");
            System.out.println("3.   Quitter");
            System.out.print("Entrez le numéro : ");

            int choix = 0;
            try {
                choix = scanner.nextInt();
            } catch (Exception e) {
                scanner.next();
            }
            scanner.nextLine();

            switch (choix) {
                case 1:
                    if (mapPath == null || mapPath.isEmpty()) {
                        System.out.print("Veuillez entrer le chemin du fichier niveau (ex: map.txt) : ");
                        mapPath = scanner.nextLine();
                    }

                    File f = new File(mapPath);
                    if(f.exists() && !f.isDirectory()) {
                        Level level = new Level();
                        Player[] players = PlayerExtension.createPlayers(scanner);
                        level.start(scanner, players, mapPath);
                        PlayerExtension.removeAllPlayers(players);
                    } else {
                        System.out.println("Erreur : Le fichier '" + mapPath + "' n'existe pas.");
                        mapPath = null;
                    }
                    break;
                case 2:
                    System.out.print("Entrez le nouveau chemin du fichier niveau : ");
                    mapPath = scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Fermeture du jeu...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix incorrect.");
            }
        }
    }
}