package ca.uqam.mgl7460.tp3.ressources.types.utils;

public interface DemandePretBuilder {

    /**
     * Cette méthode crée une propriété ayant comme adresse les champs passés en argument.
     * 
     * Elle retourne l'ID de la propriété crée
     * @param numeroPorte
     * @param numeroRue
     * @param nomRue
     * @param ville
     * @param codeProvince
     * @param codePostal
     * @return
     */
    public String creerProprieteAvecAdresse(String numeroPorte, String numeroRue, String nomRue, String ville, String codeProvince, String codePostal);

    /**
     * Cette méthode "set" la valeur de marché de la propriété ayant comme id <code>idPropriete</code>
     * @param idPropriete
     * @param valeurMarche
     * @return
     */
    public float setValeurMarcheProprieteWithId(String idPropriete, float valeurMarche);

    /**
     * Cette méthode crée une personne demandeuse de prêt avec le nom, prénom, et NAS donnés en arguments.
     * Elle retourne l'ID de la personne nouvellement créée
     * @param nom
     * @param prenom
     * @param NAS
     * @return
     */
    public String creerDemandeurPret(String nom, String prenom, String NAS);

    /**
     * Cette méthode crée une personne demandeuse de prêt avec toutes les données passées en paramètre.
     * Elle retourne l'identificateur de la personne nouvellement créée.
     * @param nom
     * @param prenom
     * @param NAS
     * @param revenuAnnuel
     * @param coteCredit
     * @param obligationsAnnuelles
     * @return
     */
    public String creerDemandeurPret(String nom, String prenom, String NAS, float revenuAnnuel, int coteCredit, float obligationsAnnuelles);

    /**
     * Cette méthode modifie la cote de crédit de la personne demandeuse de prêt dont on passe l'ID comme 
     * argument.
     * 
     * Elle retourne la cote de crédit. L'idée ici est de retourner une valeur improbable (par exemple 0 ou -100) si
     * l'opération a échoué pour une raison ou une autre
     * @param idDemamdeur
     * @param coteCredit
     * @return
     */
    public int setCoteCreditDemandeurWithId(String idDemamdeur, int coteCredit);

    /**
     * Cette méthode modifie le revenu annuel de la personne demandeuse de prêt
     * ayant comme id <code>idDemandeur</code>. Elle retourne le revenuAnnuel en
     * question, nous permettant de nous assurer que l'opération s'est bien déroulée, ou
     * -1, le cas cobntraire
     * @param idDemandeur
     * @param revenuAnnuel
     * @return
     */
    public float setRevenuAnnuelDemandeurWithId(String idDemandeur, float revenuAnnuel);

    /**
     * Cette méthode modifie les obligations annuelles de la personne demandeuse de prêt
     * ayant comme id <code>idDemandeur</code>. Elle retourne le obligationsAnnuelle en
     * question, nous permettant de nous assurer que l'opération s'est bien déroulée, ou
     * -1, le cas cobntraire
     * @param idDemandeur
     * @param obligationsAnnuelle
     * @return
     */
    public float setObligationsAnnuellesWithId(String idDemandeur, float obligationsAnnuelle);

    /**
     * Cette méthode crée une demande de prêt correspondant à la personne demandeuse de prêt
     * représentée par l'ID <code>idDemandeurPret</code>, à la propriété ayant comme identifiant
     * <code>idPropriete</code>, et avec les paramètres financiers <code>miseDeFonds</code> et
     * <code>prixAchat</code>.
     * 
     * Elle retourne l'idemntififant de la demande de prêt nouvellement créée.
     * @param idDemandeurPret
     * @param idPropriete
     * @param miseDeFonds
     * @param prixAchat
     * @return
     */
    public String creerDemandePret(String idDemandeurPret, String idPropriete, float miseDeFonds, float prixAchat);

    /**
     * Cette méthode modifie le montant de mise de fonds de la demande de prêt  ayant pour identifiant <code>idDemandePret</code>
     * Elle retourne <code>miseDeFonds</code>, si l'opération s'est bien déroulée, et une valeur négative sinon
     * @param idDemandePret
     * @param miseDeFonds
     * @return
     */
    public float setMiseDeFondsDemandePretWithId(String idDemandePret, float miseDeFonds);
    
    /**
     * Cette méthode modifie le montant de prix d'achat de la demande de prêt  ayant pour identifiant <code>idDemandePret</code>
     * Elle retourne <code>prixAchat</code>, si l'opération s'est bien déroulée, et une valeur négative sinon
     * @param idDemandePret
     * @param prixAchat
     * @return
     */
    public float setPrixAchatDemandePretWithId(String idDemandePret, float prixAchat);
    
}
