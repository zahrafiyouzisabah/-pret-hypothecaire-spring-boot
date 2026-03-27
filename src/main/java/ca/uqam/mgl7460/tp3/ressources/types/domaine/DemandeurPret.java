package ca.uqam.mgl7460.tp3.ressources.types.domaine;

import ca.uqam.mgl7460.tp3.ressources.types.ObjetIdentifiable;

/**
 * Cette classe représente les demandeur-se-s de prêt
 * 
 *  * @TP3: ajouté l'interface ObjetIdentifiable pour que les objets concernés puissent être identifiés par ID
 */
public interface DemandeurPret extends ObjetIdentifiable{

    /**
     * retourne le nom.
     * @return
     */
    public String getNom();

    /**
     * retourne le prenom
     * @return
     */
    public String getPrenom();

    /**
     * retourne le numéro d'assurance sociale
     * @return
     */
    public String getNas();

    /**
     * retourne le revenu annuel brut
     */
    public float getRevenuAnnuel();

    /**
     * modifie le revenu annuel brut
     * @param revenu
     */
    public void setRevenuAnnuel(float revenu);

    /**
     * retourne les obligations annuelles de l'emprunteur-se, 
     * c'est à dire, les choses qu'il/elle doit payer, qui sont 
     * incompressibles, telles les frais de logement, transport pour
     * se rendre au travail, etc. En d'autre termes, les dépenses
     * incompressibles.
     * @return
     */
    public float getObligationsAnnuelles();

    /**
     * cette méthode modifie les obligations annuelles de l'emprunteur-se
     * @param obligations
     */
    public void setObligationsAnnuelles(float obligations);

    /**
     * cette méthode retourne le taux d'endettement de l'emprunteur-se
     * ("debt over income ratio"), qui consiste, simplement, en le
     * ratio obligationsAnnuelles/revenuAnnuel
     * @return
     */
    public float getTauxEndettement();

    /**
     * retourne la cote de crédit de l'emprunteur-se
     * @return
     */
    public int getScoreCredit();

    /**
     * modifie la cote de crédit de l'emprunteur-se
     * @param score
     */
    public void setScoreCredit(int score);
}
