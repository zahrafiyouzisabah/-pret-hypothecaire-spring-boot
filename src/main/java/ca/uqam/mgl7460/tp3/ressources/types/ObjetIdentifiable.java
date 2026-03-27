package ca.uqam.mgl7460.tp3.ressources.types;

/**
 * @TP3
 * 
 * Ajouté interface commune à toutes les ressources permettant
 * de désigner un objet juste par son ID, pour ne pas avoir
 * à balader des objets complexes entre client et serveurs
 */
public interface ObjetIdentifiable {

    /**
     * retourne un identifiant unique
     * @return
     */
    public String getID();
}
