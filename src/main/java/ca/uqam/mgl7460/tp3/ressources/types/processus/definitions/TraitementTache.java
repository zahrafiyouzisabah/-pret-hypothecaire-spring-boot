package ca.uqam.mgl7460.tp3.ressources.types.processus.definitions;

import java.util.logging.Logger;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;

/**
 * interface fonctionnelle représentant le traitement fait par une tâche. Elle
 * prend comme arguments:
 * 1) la demande de prêt, en tant que telle
 * 2) un <code>java.util.loggin.Logger</code>, au cas où la fonction voudrait
 * produire une trace du traitement (autre que le résultat de traitement de la 
 * demande de prêt), par exemple pour du debugging ou autre
 */
@FunctionalInterface
public interface TraitementTache {
    public boolean traiteDemandePret(DemandePret demande, Logger logger);

}
