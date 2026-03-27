package ca.uqam.mgl7460.tp3.ressources.types.processus.instances;

import java.time.Instant;
import java.util.logging.Logger;

import ca.uqam.mgl7460.tp3.ressources.types.ObjetIdentifiable;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;

/**
 * Ce type représente une exécution particulière d'un modèle de processus (
 * <code>DefinitionProcessus</code>).
 * 
 * Cette exécution est caractérisée par:
 * 1) la definitionProcessus (modèle de processus) correspondant-e
 * 2) la demande de pret faisant l'pobjet de l'exécution
 * 3) le temps de démarrage
 * 4) le temps d'arrêt
 * et à tout moment:
 * 5) l'état actuel d'exécution du processus
 * 6) la tâche courante
 * 
 * Concernant l'exécution, c'est (évidemment) l'instance de processus qui "mène".
 * 
 * La méthode <code>void demarrer() throws ExceptionDefinitionProcessus</code> lance
 * l'exécution en allant chercher la première <code>DefinitionTache</code> de la
 * <code>definitionProcessus</code>, l'instancie (créer une <code>InstanceTache</code>)
 * et lance l'exécution de code>InstanceTache</code>.
 * 
 * Quand l'instance termine son exécution, elle "enregistre ses résultats" sur la demande de
 * prêt (demandePret.setResultatTraitement(...)) et 'crie "j'ai fini" ' auquel case l'instance 
 * de processus consulte le modèle de processus (getDefinitionProcessus()) pour voir, selon
 * le modèle, qu'elle est la tâche suivante.
 * 
 * Le 'cri' "j'ai fini", c'est en fait l'appel, par les instances de tâches quand elles ont fini,
 * de la méthode <code>void signalerFinTache(InstanceTache instanceTache) throws ExceptionDefinitionProcessus</code>
 * 
 * Donc, c'est la fonction <code>void signalerFinTache(InstanceTache instanceTache) throws ExceptionDefinitionProcessus</code>
 * qui va identifier la (définition de) tache suivante à instancier et à démarrer.
 * 
 * Si elle n'en trouve pas, c'est la fin du processus
 * 
 * Si elle en trouve plusieurs (prochaines taches candidates), c'est que le processus est mal formé, et c'est dans ce cas
 * que la fonction  <code>void signalerFinTache(...) throws ExceptionDefinitionProcessus</code> lance
 * l'exception <code>ExceptionDefinitionProcessus</code>
 * 
 * @TP3: ajouté l'interface ObjetIdentifiable pour que les objets concernés puissent être identifiés par ID
 * 
 */
public interface InstanceProcessus extends ObjetIdentifiable {

    /**
     * retourne la demande de pret faisant l'objet de l'exécution
     * @return
     */
    public DemandePret getDemandePret();

    /**
     * retourne la définition (modèle) de processus, dont cette exécution
     * est une instance
     * @return
     */
    public DefinitionProcessus getDefinitionProcessus();

    /**
     * retourne l'état actuel de l'exécution du processus. Ça consiste
     * en deux choses:
     * 1)   La tâche courante (on peut y penser comme un pointeur/curseur
     *      sur la tâche qui est sur le point de démarrer, en train d'exécuter,
     *      ou qui vient de se terminer)
     * 2)   l'état d'exécution (<code>EtatTraitement</code>) qui peut etre l'un de
     *      PRET, ENCOUR ou TERMINE
     * @return
     */
    public EtatProcessus getEtatProcessus();

    /**
     * modifie l'état du processus
     * @param etatProcessus
     */
    public void setEtatProcessus(EtatProcessus etatProcessus);

    /**
     * retourne la tache courante
     * @return
     */
    public InstanceTache getTacheCourante();

    /**
     * modifie la tâche courante
     * @param tacheCourante
     */
    public void setTacheCourante(InstanceTache tacheCourante);

    /**
     * cette méthode va instancier la première tâche de la <code>definitionProcessus</code>
     * et la lancer (l'exécuter). Quand la première instance de tache aura fini, elle appelera 
     * la méthode <code>void signalerFinTache(InstanceTache instanceTache) throws ExceptionDefinitionProcessus</code>
     * sur le processus. Voir suite de l'explication dans la documentation de l'entête de l'interface
     * @throws ExceptionDefinitionProcessus
     */
    public void demarrer() throws ExceptionDefinitionProcessus;

    /**
     * retourne le temps de démarrage
     * @return
     */
    public Instant getTempsDemarrage();

    /**
     * initialise (modifie) le temps de démarrage
     */
    public void setTempsDemarrage(Instant temps);

    /**
     * retourne le temps d'arret
     * @return
     */
    public Instant getTempsArret();

    /**
     * initialise (modifie) le temps d'arret
     * @param temps
     */
    public void setTempsArret(Instant temps);

    /**
     * une méthode appelée par les <code>InstanceTache</code> quand elles font
     * fini d'exécuter. Le récepteur (instance de processus) va alors consulter la 
     * définition (le modèle) de processus qu'il est entrain d'exécuter pour identifier
     * la prochaine tâche à exécuter. Si la méthode n'en trouve pas, cela marquera la
     * fin de l'exécution du processus.
     * 
     * Si on en trouve plusieurs, problème! le processus est mal formé, ce qui va enclencher
     * l'exception <code>ExceptionDefinitionProcessus</code>
     * @param instanceTache
     * @throws ExceptionDefinitionProcessus
     */
    public void signalerFinTache(InstanceTache instanceTache) throws ExceptionDefinitionProcessus;

    /**
     * retourne le <code>java.util.logging.Logger</code> utiliser par le processus pour le "tracing",
     * au besoin.
     * @return
     */
    public Logger getLogger();

    /**
     * modifie le logger
     * @param logger
     */
    public void setLogger(Logger logger);
    
    /**
     * retourne le mode d'exécution du processus (EXPRESSION_LAMBDA
     * ou REGLES_DROOLS)
     * @return
     */
    public ModeExecution getModeExecution();

    /**
     * modifie le mode d'exécution du processus, par EXPRESSION_LAMBDA
     * ou par REGLES_DROOLS. Selon le mode, la méthode activer() de la tâche
     * va soit appeler executer(), soit appeler executerRegles()
     * @param mode
     */
    public void setModeExecution(ModeExecution mode);
}
