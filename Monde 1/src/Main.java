import include.Player;
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
            System.out.println("Les joueurs 1 et 2 sont-ils égaux ? : " + egalite);
        }
        scanner.close();

    }
}
