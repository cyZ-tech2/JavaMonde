package games.character;

import java.util.Scanner;

public class PlayerExtension {

    /**
     * Méthode utilitaire pour créer plusieurs joueurs via la console.
     *
     * @param scanner Le scanner pour lire les entrées utilisateur.
     * @return Un tableau contenant les joueurs créés.
     */
    public static Player[] createPlayers(Scanner scanner) {
        System.out.println("\n--- CREATION DES JOUEURS ---");
        System.out.print("Combien de joueurs voulez-vous créer ? ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Player[] players = new Player[n];

        for (int i = 0; i < n; i++) {
            System.out.println("=== Joueur " + (i + 1) + " ===");
            System.out.print("Entrez le nom : ");
            String nom = scanner.nextLine();
            players[i] = new Player(nom);
        }

        return players;
    }

    /**
     * Affiche la liste des joueurs passés en paramètre.
     *
     * @param players Le tableau de joueurs à afficher.
     */
    public static void displayPlayers(Player[] players) {
        System.out.println("\n=== Liste des joueurs ===");
        for (int i = 0; i < players.length; i++) {
            Player j = players[i];
            if (j != null) {
                System.out.println(j);
            }
        }
        if (Player.getNbPlayers() < 2) {
            System.out.println("Il y a " + Player.getNbPlayers() + " joueur");
        } else {
            System.out.println("Il y a " + Player.getNbPlayers() + " joueur(s)");
        }
    }

    /**
     * Supprime un joueur d'un tableau à un index donné.
     * Met à jour le compteur global si le joueur existe.
     *
     * @param p Le tableau de joueurs.
     * @param n L'index du joueur à supprimer.
     */
    public static void removePlayer(Player[] p, int n) {
        if(n >= 0 && n < p.length && p[n] != null) {
            p[n] = null;
            Player.nbPlayers--;
        }
    }

    /**
     * Supprime tous les joueurs présents dans le tableau.
     * Parcourt le tableau et met à jour le compteur global pour chaque joueur supprimé.
     *
     * @param players Le tableau de joueurs à vider.
     */
    public static void removeAllPlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                players[i] = null;
                Player.nbPlayers--;
            }
        }
    }
}
