package ca.uqam.mgl7460.tp3.ressources.types.domaine;

import java.util.Iterator;

/**
 * object qui contient le resultat du traitement.
 * 
 * En plus du résultat en tant que tel, le traitement
 * d'une demande peut "collecter" plusieurs messages indiquant
 * ce va ou ne va pas avec une demande de prêt
 */
public interface ResultatTraitement {

    /**
     * retourne le resultat (une valeur parmi NONDETERMINE, ACCEPTEE, REFUSEE)
     * @return
     */
    public Resultat getResultat();

    /**
     * modifie le résultat
     * @param resultat
     */
    public void setResultat(Resultat resultat);

    /**
     * Retourne la liste de messages 'collectés' durant le traitement
     * @return
     */
    public Iterator<String> getMessages();

    /**
     * ajoute un message à la liste de message
     * @param message
     */
    public void ajouteMessage(String message);

}
