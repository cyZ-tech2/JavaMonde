package include;
import java.util.Scanner;

/**
 * Représente un joueur avec un nom et un score.
 * Cette classe permet de gérer les points et l'identité du joueur.
 */
public class Player {
    private String name;
    private int score;
    private static int nbPlayers = 0;

    /**
     * Constructeur par défaut.
     */
    public Player() {
    }

    /**
     * Constructeur principal pour créer un joueur.
     * Le nom par défaut sera "Joueur N" si aucun nom n'est fourni.
     *
     * @param a Le nom du joueur.
     * @param b Le score initial du joueur.
     */
    public Player(String a, int b) {
        nbPlayers++;
        this.name = a;
        // Niveau 6 : Gestion du nom par défaut "Joueur N"
        if (name == null || name.trim().isEmpty()) {
            this.name = "Joueur " + nbPlayers;
        }
        this.score = b;
    }

    /**
     * Retourne une représentation textuelle du joueur.
     * Format : "nom : score pts" (avec gestion du pluriel).
     *
     * @return La chaîne décrivant le joueur.
     */
    @Override
    public String toString() {
        if(this.score < 2){
            return  name + " a un score de : " + score + " pt";
        }
        return  name + " a un score de : " + score + " pt(s)";
    }

    /**
     * Récupère le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getName(){
        return name;
    }

    /**
     * Récupère le score du joueur.
     *
     * @return Le score actuel.
     */
    public int getScore(){
        return score;
    }

    /**
     * Récupère le nombre total de joueurs créés.
     *
     * @return Le nombre de joueurs.
     */
    public static int getNbPlayers() {
        return nbPlayers;
    }

    /**
     * Ajoute des points au score du joueur.
     *
     * @param point Le nombre de points à ajouter.
     */
    public void addPoint(int point){
        this.score += point;
        // Niveau 2 : Le score ne peut pas être négatif (bien que l'addition ne pose généralement pas ce souci, c'est une sécurité)
        if(this.score < 0){
            this.score = 0;
        }
    }

    /**
     * Retire des points au score du joueur.
     * Si le score devient négatif, il est ramené à 0.
     *
     * @param point Le nombre de points à retirer.
     */
    public void removePoint(int point){
        this.score -= point;
        // Niveau 2 : Si le joueur perd plus de points qu'il n'en a, son score devient nul
        if(this.score < 0){
            this.score = 0;
        }
    }

    /**
     * Vérifie l'égalité entre ce joueur et un autre objet.
     * L'égalité repose uniquement sur le nom (insensible à la casse).
     *
     * @param obj L'objet qu'on va comparer.
     * @return true si les noms sont identiques (sans casse), false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Player)){
            return false;
        }
        Player other = (Player) obj;

        return this.name.equalsIgnoreCase(other.name);
    }

    /**
     * Méthode utilitaire pour créer plusieurs joueurs via la console.
     *
     * @param scanner Le scanner pour lire les entrées.
     * @return Un tableau de joueurs créés.
     */
    public static Player[] createPlayer(Scanner scanner) {
        System.out.print("Combien de joueurs voulez-vous créer ? ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Player[] player = new Player[n];

        for (int i = 0; i < n; i++) {
            System.out.println("=== Joueur " + (i + 1) + " ===");
            System.out.print("Entrez le nom : ");
            String nom = scanner.nextLine();
            player[i] = new Player(nom, 0);
        }

        return player;
    }

    /**
     * Affiche la liste des joueurs passés en paramètre.
     *
     * @param player Le tableau des joueurs à afficher.
     */
    public static void displayPlayers(Player[] player) {
        System.out.println("\n=== Liste des joueurs ===");
        for (int i = 0; i < player.length; i++) {
            Player j = player[i];
            System.out.println(j);
        }
        if (getNbPlayers() < 2) {
            System.out.println("Il y a " + getNbPlayers() + " include");
        } else{
            System.out.println("Il y a " + getNbPlayers() + " joueur(s)");
        }
    }
}