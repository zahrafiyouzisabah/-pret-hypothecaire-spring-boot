package ca.uqam.mgl7460.tp3.ressources.types.processus.definitions;

import ca.uqam.mgl7460.tp3.ressources.types.ObjetIdentifiable;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;

/**
 * cette interface représente les définitions de transitions.
 * 
 * Une transition est définie par:
 * 1) une <code>tacheSource</code>
 * 2) une <code>tacheDestination</code>, et
 * 3) une <code>conditionTransition</code>, qui est une 
 * fonction booleenne que l'on exécute pour savoir si
 * on passer de <code>tacheSource</code> à 
 * <code>tacheDestination</code> ou non.
 *
 * @TP3: ajouté l'interface ObjetIdentifiable pour que les objets concernés puissent être identifiés par ID
 * 
 */
public interface DefinitionTransition extends ObjetIdentifiable {

    /**
     * retourne la tache source
     * @return
     */
    public DefinitionTache getTachesource();

    /**
     * retourne la tache destination
     * @return
     */
    public DefinitionTache getTacheDestination();

    /**
     * retourne la condition de transition
     * @return
     */
    public ConditionTransition getConditionTransition();

    /**
     * On suppose que la verification d'une transition peut aussi être 
     * exécutée avec des règles, auquel cas, on fait la vérification
     * en évaluant des règles appropriées
     * @param nomFichierDrools
     */
    public void setNomFichierRegles(String nomFichierDrools);


    /**
     * retourne le nom du fichier règles associé
     * @return
     */
    public String getNomFichierRegles();

    public void setIsPass(boolean isPass);

    /**
     * cette méthode incarne le comportement de la transition. Elle prend l'instance
     * de processus englobant, à partir de laquelle elle pourra accéder à l'état
     * courant, et à la demande de prêt
     * @param instanceProcessus
     * @return
     */
    public boolean caPasse(InstanceProcessus instanceProcessus);

}
