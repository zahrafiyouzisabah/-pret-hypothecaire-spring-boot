package ca.uqam.mgl7460.tp3.ressources.types.domaine;

import ca.uqam.mgl7460.tp3.ressources.types.ObjetIdentifiable;

/**
 * ce type représente la propriété mise en garantie dans la demande
 * de prêt. C'est une version (très) simple de la réalité.
 *  
 * @TP3: ajouté l'interface ObjetIdentifiable pour que les objets concernés puissent être identifiés par ID
 */
public interface Propriete extends ObjetIdentifiable {

    /**
     * l'adresse de la propriété
     * @return
     */
    public Adresse getAdresse();

    /**
     * la valeur de marché de la propriété, qui peut être
     * (plus ou moins) différent du prix d'achat. La valeur de marché
     * est déterminée par la banque qui traite la demande de prêt, en se
     * basant sur plein de choses (elle a ses propres expert-e-s dans le 
     * domaine pour ce faire)
     * @return
     */
    public float getValeurDeMarche();

    /**
     * cette méthode modifie la valeur marchande de la propriété
     * @param valeur
     */
    public void setValeurDeMarche(float valeur);

}
