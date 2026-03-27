package ca.uqam.mgl7460.tp3.ressources.types.processus.definitions;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;

/**
 * interface 'fonctionnelle' correspondant àa la fonction booleenne à
 * exécuter pour vérifier si une transition sortante d'une tâche donnée
 * (<code>tacheSource</code>) est active/"traversable" ou non.
 * 
 * Cette condition va (très) souvent dépendre de l'état de la demande de prêt
 * à ce moment là (la fin de <code>tacheSource</code>), raison pour laquelle
 * on doit fournir la demandePret comme paramètre.
 */
@FunctionalInterface
public interface ConditionTransition {

    public boolean isTransitionOK(InstanceTache tacheSource, DemandePret demandePret);
    
}
