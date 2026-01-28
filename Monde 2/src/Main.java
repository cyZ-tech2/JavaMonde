import include.Player;
import include.Level;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Player[] player = Player.createPlayer(scanner);
        Player.displayPlayers(player);
        if (Player.getNbPlayers()>1) {
            player[0].addPoint(4);
            player[1].removePoint(5);
            Player.displayPlayers(player);
            boolean egalite = player[0].equals(player[1]);
            System.out.println("Les players 1 et 2 sont-ils égaux ? : " + egalite);
        }

        // Partie 2

        System.out.println("\n=== Création du niveau ===\n");

        /*
        Niveau petitNiveau = new Niveau(10, 10);
        petitNiveau.addWallOutSide();
        petitNiveau.addWall(2, 2);
        petitNiveau.addWall(3, 2);
        petitNiveau.addWall(4, 2);
        petitNiveau.displayMap();
        */
        Level level = new Level(10, 20);
        level.addWallOutSide();
        level.addWall(5, 5);
        level.addWall(5, 6);
        level.addWall(5, 7);
        level.displayMap();
        if(Player.getNbPlayers() > 0) {
            System.out.println("\n=== Tentative de placement du player ===\n");

            level.placePlayer(player[0], 2, 2);
            level.displayMap();
        }
        System.out.println("Donner direction");
        String x = scanner.next();

        switch (x){
            case "z":
                level.playerY += 1;
                break;
            case "s":
                level.playerY -= 1;
                break;
            case "d":
                level.playerX += 1;
                break;
            case "q":
                level.playerX -= 1;
                break;

        }
        level.placePlayer(player[0]);
        level.displayMap();
        scanner.close();
    }
}
