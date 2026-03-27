package ca.uqam.mgl7460.tp3.ressources.types.utils;


/**
 * Cette interface est utilisée pour manipuler des définition de processus juste avec des identificateurs
 * plutôt que des pointeurs d'objet. Dans un client serveur, le client doit être capable de parler au
 * serveur d'objets qui vivent du coté du serveur mais sont référencés du coté client.
 *
 * Le client n'a pas besoin de manipuler des copies des objets qui vivent coté serveur: juste pouvoir les
 * désigner et provoquer des opérations dessus
 */
public interface DefinitionProcessusBuilder {

    /**
     * Créer une définition de tache, en fournissant le nom et la description de la tache.
     * Elle retourne l'identificateur de la tache
     * @param nom
     * @param description
     * @param nomFichierRegles
     * @return
     */
    public String creerDefinitionTache(String nom, String description, String nomFichierRegles);

    /**
     * Créer une définition (spécification) de transition entre deux (définitions de) tâche, en
     * fournissant: 1) l'ID de la tacheSource, 2) l'ID de la tacheDestination, et 3) le nom de fichier de règles
     * @param idTacheSource
     * @param idTacheDestination
     * @param nomFichierRegles
     * @return the ID of the newly created transition
     */
    public String creerDefinitionTransition(String idTacheSource, String idTacheDestination,
                                                          String nomFichierRegles);
    /**
     * Crèer une coquille de définition de processus, en fournissant le nom et la description du processus.
     * Elle retourne l'identificateur de la definition du processus
     * @param nomProcessus
     * @param descriptionProcessus
     * @return the ID of the newly created process definition
     */
    public String creerDefinitionProcessus(String nomProcessus, String descriptionProcessus);

    /**
     * Ajoute  a la définition du processus ayant popur id idProcessus la definition de tache ayant pour
     * ID idDefinitionTache
     * @param idProcessus
     * @param idDefinitionTache
     */
    public void ajouteTache(String idProcessus, String idDefinitionTache);

    /**
     * ajoute une définition de transition ayant pour id idDefinitionTransition à la définition
     * de processus ayant pour ID idProcessus des définition de tâche.
     * @param idProcessus
     * @param idDefinitionTransition
     */
    public void ajouteTransition(String idProcessus, String idDefinitionTransition);

    /**
     * Méthode de "commodité" pour rajouter une définition detransition à une définition de processus. Plutôt que construire
     * une <code>DefinitionTransition</code> et passer son identificateur comme argument à
     * <code>void ajouteTransition(String idProcessus, String idDefinitionTransition)</code>,
     * on passe      * les ids nécessaires pour construire une <code>DefinitionTransition</code>, et on laisse
     * cette méthode créer la code>DefinitionTransition</code> et l'ajouter.
     * @param idProcessus
     * @param idTacheSource
     * @param idTacheDestination
     * @param nomsFichiersRegles
     * @return
     */
    public String ajouteTransition(String idProcessus, String idTacheSource, String idTacheDestination, String nomsFichiersRegles);

    /**
     * retourne l'ID de la première tâche de la définition du processus ayant pour id idProcessus
     * @param idProcessus
     * @return
     */
    public String getIdPremiereTache(String idProcessus);

    /**
     * set la tache ayant pour id idTache comme la première tâche du
     * processus ayant pour id idProcessus
     * @param idProcessus
     * @param idTache
     */
    public void setPremiereTache(String idProcessus, String idTache);

    /**
     * cette méthode combine les effets <code>ajouteTache(String idProcessus, String idTache)</code> (qui suppose
     * que la tache avec id <code>idTache</code> ne faisait pas encore partie des noeuds) et
     * <code>setPremiereTache(String idProcessus, String idTache)</code>, qui marque la tache nouvelle ajoutée comme
     * première tâche
     * @param idTache
     */
    public void ajoutePremiereTache(String idProcessus, String idTache);

    /**
     * cette méthode détermine, pour une définition de tâche donnée par son ID, si la tâche à des
     * "tâches suivantes" ou non dans le processus ayant pour ID idProcessus
     * @param idProcessus
     * @param idTache
     * @return
     */
    public boolean isTacheFinale(String idProcessus, String idTache);



}
