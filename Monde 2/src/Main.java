import include.Player;
import include.Level;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Player[] players = Player.createPlayer(scanner);
        Player.displayPlayers(players);

        if (Player.getNbPlayers() > 1) {
            players[0].addPoint(4);
            players[1].removePoint(5);
            Player.displayPlayers(players);
            boolean equal = players[0].equals(players[1]);
            System.out.println("Les joueurs 1 et 2 sont-ils égaux ? : " + equal);
        }

        System.out.println("\n=== Création du niveau ===\n");
        Level level = new Level(10, 20);
        level.addWallOutSide();
        level.addWall(5, 5);
        level.addWall(5, 6);
        level.addWall(5, 7);

        level.displayMap();

        if(Player.getNbPlayers() > 0) {
            System.out.println("\n=== Tentative de placement du player ===\n");
            try {
                level.placePlayer(players[0], 2, 2);
                level.displayMap();

                boolean playing = true;
                while (playing) {
                    System.out.print("Déplacement (z/q/s/d) ou 'exit' pour quitter : ");
                    String input = scanner.next();

                    if (input.equalsIgnoreCase("exit")) {
                        playing = false;
                        System.out.println("Fin de la partie.");
                    } else {
                        level.movePlayer(input);
                        level.displayMap();
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERREUR CRITIQUE : " + e.getMessage());
            }
        } else {
            System.out.println("Pas de joueur créé, impossible de jouer.");
        }

        scanner.close();
    }
}