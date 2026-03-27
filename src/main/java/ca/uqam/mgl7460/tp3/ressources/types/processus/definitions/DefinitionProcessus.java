package ca.uqam.mgl7460.tp3.ressources.types.processus.definitions;

import java.util.Iterator;

import ca.uqam.mgl7460.tp3.ressources.types.ObjetIdentifiable;

/**
 * Cette interface représnte les MODÈLES de processus, tels que les trois visualiés dans
 * l'énoncé. Un processus a un nom, une description, et consiste en un graphe où les noeuds sont
 * des (définitions de) tâches, et les liens sont des (définitions de) transitions, pouvant être
 * assujetties de conditions. 
 * 
 * On va désigner  * l'une des tâches comme point de départ du processus. Évidemment, c'est une
 * version (trrès) simplifiée de la 'Business Process Modeling Notation' (BPMN) qui fera l'affaire
 * pour les besoins de ce TP. 
 * 
 * @TP3: ajouté l'interface ObjetIdentifiable pour que les objets concernés puissent être identifiés par ID
 * 
 */
public interface DefinitionProcessus extends ObjetIdentifiable{

    /**
     * retourne le nom du processus
     * @return
     */
    public String getNom();

    /**
     * retourne la description du processus (un cours texte qui explique
     * ce qu'il fait)
     * @return
     */
    public String getDescription();

    /**
     * modifie la description du processus
     * @param description
     */
    public void setDescription(String description);
    
    /**
     * retourne la liste des (définitions de) tâches de (la définition du) processus
     * @return
     */
    public Iterator<DefinitionTache> getTaches ();

    /**
     * Ajoute une (définition de) tâche a (la définition du) processus
     * @param definitionTache
     */
    public void ajouteTache(DefinitionTache definitionTache);

    /**
     * ajoute une (définition de) transition à la définition du processus.
     * Rappelez vous qu'un modèle de (définition de) processus est un graphe
     * dont les noeuds sont des définitions de tâches, et dont les liens sont
     * des définition de tâche.
     * @param definitionTransition
     */
    public void ajouteTransition(DefinitionTransition definitionTransition);

    /**
     * Méthode de "commodité" pour rajouter une définition detransition à un modèle/définition
     * de processus. Plutôt que construire une <code>DefinitionTransition</code> et la passer
     * comme argument à <code>void ajouteTransition(DefinitionTransition dt)</code>, on passe
     * les éléments nécessaire pour construire une <code>DefinitionTransition</code>, et on laisse
     * cette méthode créer la code>DefinitionTransition</code> et l'ajouter.
     * 
     * Notez que la condition de transition est optionnelle. Si elle n'est pas fournie, on utilisera comme
     * ConditionTransition une fonction qui retourne toujours vrai.
     * @param tacheSource
     * @param tacheDestination
     * @param conditions
     * @return
     */
    public DefinitionTransition ajouteTransition(DefinitionTache tacheSource, DefinitionTache tacheDestination, ConditionTransition... conditions);

    /**
     * retourne la liste des transitions sortantes d'une (déinition de) tache donnée (paramètre <code>tache</code>)
     * @param tache
     * @return
     */
    public Iterator<DefinitionTransition> getTransitionsSortantesDe(DefinitionTache tache);

    /**
     * retourne la première tâche de la définition du processus
     * @return
     */
    public DefinitionTache getPremiereTache();

    /**
     * modifie la première tâche du processus
     * @param tache
     */
    public void setPremiereTache(DefinitionTache tache);

    /**
     * cette méthode combine les effets <code>ajouteTache(DefinitionTache tache)</code> (qui suppose
     * que <code>tache</code> ne faisait pas encore partie des noeuds) et 
     * <code>setPremiereTache(DefinitionTache tache)</code>, qui marque la tache nouvelle ajoutée comme
     * première tâche 
     * @param tache
     */
    public void ajoutePremiereTache(DefinitionTache tache);

    /**
     * cette méthode détermine, pour une définition de tâche donnée, si la tâche à des "tâches suivantes" ou non,
     * c-a-d, si elle a des transitions sortantes
     * @param tache
     * @return
     */
    public boolean isTacheFinale(DefinitionTache tache);
}
