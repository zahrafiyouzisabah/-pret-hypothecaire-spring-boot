package ca.uqam.mgl7460.tp3.ressources.types.utils;

public interface InstanceProcessusLauncher {

    /**
     * Cette méthode permet de créer une instance de processus pour la définition de proessus 
     * ayant <code>idDefinitionProcessus</code> comme identification, et la demande de prêt
     * avec id <code>idDemandePret</code>. Elle retourne l'identificateur de la nouvelle instance
     * de processus démarré
     * @param idDefinitionProcessus
     * @param idDemandePret
     * @return
     */
    public String creerInstanceProcessus(String idDefinitionProcessus, String idDemandePret);

    /**
     * Cette methode lance l'instance de processus dont l'identificateur est <code>idInstanceProcessus</code>
     * Elle retourne la valeur 'string' du resultat de l'exécution.
     * 
     * Cette string est une JSON string ayant le format suivant:
     * { "etatProcessus" : "TERMINE",
     *   "resultatTraitement" : {
     *      "resultat" : "REFUSEE",
     *      "messages" : ["message 1", "message 2",...]
     *                          }
     * }
     * @param idInstanceProcessus
     * @return
     */
    public String lancerInstanceProcessusAvecId(String idInstanceProcessus);
    
}
