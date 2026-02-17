package games.character;

/**
 * Représente un joueur avec un nom et un score.
 * Cette classe permet de gérer les points et l'identité du joueur.
 */
public class Player {
    private final String name;
    private int score = 0;
    public static int nbPlayers = 0;
    private int hp = 5;

    /**
     * Constructeur par défaut.
     */
    public Player() {
        this(null);
    }

    /**
     * Constructeur principal pour créer un joueur.
     *
     * @param a Le nom du joueur.
     */
    public Player(String a) {
        nbPlayers++;
        if (a == null || a.trim().isEmpty()) {
            this.name = "Joueur" + nbPlayers;
        } else {
            this.name = a;
        }
    }

    /**
     * Retourne une représentation textuelle du joueur.
     * Format : "nom : score pts" (avec gestion du pluriel).
     *
     * @return La chaîne décrivant le joueur.
     */
    @Override
    public String toString() {
        if (this.score < 2) {
            return this.name + " (" + this.hp + " PV" + ")" + " a un score de : " + this.score + " pt";
        }
        return this.name + " (" + this.hp + ")" + " a un score de : " + this.score + " pt(s)";
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int a){
        if(a<0){
            this.hp = 0;
        }else {
            this.hp = a;
        }
    }


    public void addHp(int a) {
        if (a > 0) {
            this.hp += a;
        }
    }
    public void removeHp(int a) {
        if(a > 0) {
            this.hp -= a;
        }
        if(this.hp < 0) {
            this.hp = 0;
        }
    }

    /**
     * Récupère le nom du joueur.
     *
     * @return Le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * Récupère le score du joueur.
     *
     * @return Le score actuel.
     */
    public int getScore() {
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

    /*
    public void setName(String name){ // Inutile car name est final
        this.name = name;
    }
    */

    /**
     * Modifie le score du joueur.
     *
     * @param score Le nouveau score du jeu.
     */
    public void setScore(int score) {
        if (score >= 0) {
            this.score = score;
        }

    }


    /**
     * Ajoute des points au score du joueur.
     *
     * @param point Le nombre de points à ajouter.
     */
    public void addPoint(int point) {
        if(point > 0) {
            this.score += point;
        }
    }

    /**
     * Retire des points au score du joueur.
     *
     * @param point Le nombre de points à retirer.
     */
    public void removePoint(int point) {
        if(point > 0) {
            this.score -= point;
        }
        if(this.score < 0) {
            this.score = 0;
        }
    }


    /**
     * Supprime le joueur courant (méthode d'instance).
     * Décrémente le compteur global de joueurs.
     *
     * @return null
     */
    public Player remove() {
        if (nbPlayers > 0) {
            nbPlayers--;
        }
        return null;
    }


    /**
     * Vérifie l'égalité entre ce joueur et un autre objet.
     * L'égalité est basée sur le nom (insensible à la casse).
     *
     * @param obj L'objet à comparer.
     * @return true si les noms sont identiques, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player other = (Player) obj;

        return this.name.equalsIgnoreCase(other.name);
    }

}