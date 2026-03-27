package ca.uqam.mgl7460.tp3.ressources.types.processus.instances;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;

/**
 * le type InstanceTache représente l'exécution d'une <code>DefinitionTache</code> faisant partie
 * d'une modèle de processus (<code>DefinitionProcessus</code>), sur une <code>DemandePret</code>,
 * dans le cadre d'un processus englobant (<code>InstanceProcessus</code>)
 */
public interface InstanceTache {

    /**
     * la définition de tâche dont cette instance est une exécution particulière.
     * 
     * C'est la définition de tâche qui me permet de "aller chercher la fonction
     * à exécuter" (voir méthode <code>executer</code> plus bas)
     * @return
     */
    public DefinitionTache getDefinitionTache();

    /**
     * retourne l'état d'exécution de la tache. C'est l'un de PRET, ENCOURS et TERMINE
     * @return
     */
    public EtatTraitement getEtatInstanceTache();

    /**
     * Modifie l'état de traitement de la tâche
     * @param etat
     */
    public void setEtatInstanceTache(EtatTraitement etat);

    /**
     * retourne le processus englobant, i.e. la <code>InstanceProcessus</code>
     * dont je fais partie (c-a-d dont je suis l'une des étapes)
     * @return
     */
    public InstanceProcessus getProcessusEnglobant();

    /**
     * retourne la demande de prêt sur laquelle je vais exécuter le traitement
     * @return
     */
    public DemandePret getDemandePret();

    /**
     * modifie la demande de pret sur laquelle je (vais) opérer
     * @param demande
     */
    public void setDemandePret(DemandePret demande);

    /**
     * Cette méthode exécute l'instance de tâche.
     * 
     * Dépendant du mode d'exécution du processus englobant, elle
     * va soit faire appel aux expressions lambda, soit faire appel aux
     * règles.
     * 
     * Dans un cas comme dans l'autre, l'exécution de la tâche va modifier:
     * 1)   le <code>ResultatTraitement</code> de la demande de prêt
     * 2)   l'état de traitement de l'instance de tache elle même
     * 
     * Et elle va signaler au processus englobant qu'elle a fini, en appelant la méthode 
     * <code>void signalerFinTache(InstanceTache instanceTache) throws ExceptionDefinitionProcessus</code>
     * 
     * @throws ExceptionDefinitionProcessus
     */
    public void executer() throws ExceptionDefinitionProcessus;

}
