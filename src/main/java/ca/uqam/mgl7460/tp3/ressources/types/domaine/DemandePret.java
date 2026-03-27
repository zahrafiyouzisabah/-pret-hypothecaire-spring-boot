package ca.uqam.mgl7460.tp3.ressources.types.domaine;
import java.time.Instant;

import ca.uqam.mgl7460.tp3.ressources.types.ObjetIdentifiable;

/**
 * La classe représentant une demande de prêt hypothécaire. Vous référer au modèle objet
 * inclus dans l'énoncé, et fourni séparément (format PNG) pour complément d'informations
 * sur la structure de la classe
 * @TP3: ajouté l'interface ObjetIdentifiable pour que les objets concernés puissent être identifiés par ID
 * 
 */
public interface DemandePret extends ObjetIdentifiable {
    
    /**
     * C'est un numéro unique qui est généré automatiquement au moment de la création
     * de l'objet, et qui est donc accessible juste en lecture.
     * @return
     */
    public String getNumeroDemande();

    /**
     * La date est initialisée au moment où on crée la demande de prêt, i.e. dans 
     * le constructeur ==> accessible juste en lecteur
     * @return
     */
    public Instant getDateDemande();
    
    /**
     * retourne le/la demandeur-se de prêt
     * @return
     */
    public DemandeurPret getDemandeurPret();

    /**
     * retourne la propriété donnée en garantie (hypothéquée) par la demande de prêt
     * @return
     */
    public Propriete getPropriete();

    /**
     * retourne le montant du prêt demandé.
     * @return
     */
    public float getMontantPret();

    /**
     * retourne le montant de mise de fonds initiale de l'empreunteur-se pour acquérir
     * la propriété. 
     * @return
     */
    public float getMontantMiseDeFonds();

    /**
     * cette méthode modifie le montant de mise de fonds
     * @param montant
     */
    public void setMontantMiseDeFonds(float montant);

    /**
     * Cette méthode retourne le résultat du traitement de la demande de prêt. Je vous réfère
     * à la définition du type <code>ResultatTraitement</code>, mais en gros, ça consiste
     * en deux choses:
     * 1) un résultat: l'une de trois valeurs: [NONDETERMINE,ACCEPTEE,REFUSEE]
     * 2) un ensemble de messages indiquant les "erreurs" trouvées dans la demande
     * de prêt
     * @return
     */
    public ResultatTraitement getResultatTraitement();

    /**
     * cette méthode modifie le résultat de traitement de la demande
     * @param etat
     */
    public void setResultatTraitement(ResultatTraitement etat);

    /**
     * cette méthode retourne le ratio entre le montant du prêt, et la valeur de
     * marché (et NON le prix d'achat) de la propriété
     * @return
     */
    public float getRatioEmpruntValeur();

    /**
     * cette méthode retourne le prix d'achat de la propriété
     * @return
     */
    public float getPrixAchat();

    /**
     * cette méthode modifie le prixAchat de la propriété
     * @param prixAchat
     */
    public void setPrixAchat(float prixAchat);

    /**
     * cette méthode retourne les termes du prêt, si jamais il est
     * accordé. Vous pouvez consulter le "record" TermesPret, mais en
     * gros ça consiste en deux valeurs: 1) taux d'intérêt, et 2) durée 
     * d'amortissement du prêt, c-a-d, le nombre de mois du prêt.
     * 
     * Normalement, on calcule les termes du prêt une fois qu'on a jugé qu'une
     * demande est éligible.
     * 
     * Cela pourrait être pertinent pour le TP3, mais les termes du pret ne sont
     * pas utilisés dans le TP1
     * @return
     */
    public TermesPret getTermesPret();

    /**
     * cette methode modifie les termes du prêt.
     * @param terms
     */
    public void setTermesPret(TermesPret terms);
}