package ca.uqam.mgl7460.tp3.ressources.implementations.processus.instances;

import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.EtatProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.EtatTraitement;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ExceptionDefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ModeExecution;

import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class InstanceProcessusImpl implements InstanceProcessus{

    private String ID;

    private Instant tempsDemarrage;

    private Instant tempsArret;

    private DemandePret demandePret;

    private DefinitionProcessus definitionProcessus;

    private EtatProcessus etatProcessus;

    private InstanceTache tacheCourante;

    private Logger logger;

    private HashMap<DefinitionTache,InstanceTache> taches;

    /**
     * mode d'exécution du processus. Par défaut, c'est EXPRESSIONS_LAMBDA
     */
    private ModeExecution modeExecution = ModeExecution.EXPRESSIONS_LAMBDA;

    public InstanceProcessusImpl(DefinitionProcessus definitionProcessus, DemandePret demandePret){
        this(definitionProcessus,demandePret,Logger.getLogger(definitionProcessus.getNom()));
    }

    public InstanceProcessusImpl(DefinitionProcessus definitionProcessus, DemandePret demandePret, Logger logger){
        this.ID = UUID.randomUUID().toString();
        this.definitionProcessus = definitionProcessus;
        this.demandePret = demandePret;
        this.logger = logger;
        this.taches = new HashMap<>();
        this.etatProcessus = new EtatProcessus(null, EtatTraitement.PRET);
    }

    public InstanceProcessusImpl(DefinitionProcessus definitionProcessus, DemandePret demandePret, Logger logger, ModeExecution mode){
        this(definitionProcessus, demandePret,logger);
        this.modeExecution = mode;
    }

    @Override
    public DemandePret getDemandePret() {
        return demandePret;
    }

    @Override
    public DefinitionProcessus getDefinitionProcessus() {
        return definitionProcessus;
    }

    @Override
    public EtatProcessus getEtatProcessus() {
        return etatProcessus;
    }

    @Override
    public void setEtatProcessus(EtatProcessus etatProcessus) {
        this.etatProcessus = etatProcessus;
    }

    @Override
    public InstanceTache getTacheCourante() {
        return tacheCourante;
    }

    @Override
    public void setTacheCourante(InstanceTache tacheCourante) {
        this.tacheCourante = tacheCourante;
    }

    @Override
    public void demarrer() throws ExceptionDefinitionProcessus {
        this.setTempsDemarrage(Instant.now());
        this.activerTache(definitionProcessus.getPremiereTache());
    }

    private void activerTache(DefinitionTache definitionTache) throws ExceptionDefinitionProcessus {
        InstanceTache instanceTache = Fabrique.getSingletonFabrique().creerInstanceTache(this, definitionTache);
        taches.put(definitionTache, instanceTache);
        setTacheCourante(instanceTache);
        this.etatProcessus = new EtatProcessus(tacheCourante, EtatTraitement.ENCOURS);
        tacheCourante.executer();
    }

    @Override
    public Instant getTempsDemarrage() {
        return tempsDemarrage;
    }

    @Override
    public void setTempsDemarrage(Instant temps) {
        this.tempsDemarrage = temps;
    }

    @Override
    public Instant getTempsArret() {
        return tempsArret;
    }

    @Override
    public void setTempsArret(Instant temps) {
        this.tempsArret = temps;
    }

    @Override
    public void signalerFinTache(InstanceTache instanceTache) throws ExceptionDefinitionProcessus {
        // 1.   Vérifier si la tache quivient de se terminer a fini
        //      proprement.
        EtatTraitement etatInstance = instanceTache.getEtatInstanceTache();

        // 2    Si la tâche s'est terminée correctement, il faut exécuter
        //      la tâche suivante.
        if (etatInstance == EtatTraitement.TERMINE) {
            Collection<DefinitionTache> definitionTachesCandidates = new ArrayList<>();
            // 2.a. D'abord, aller chercher les transitions sortantes
            //      
            Iterator<DefinitionTransition> transitionsSortantes = definitionProcessus.getTransitionsSortantesDe(instanceTache.getDefinitionTache());

            // 2.b. Maintenant, vérifier, parmi les transitions candidates, celles dont les
            //      conditions sont satisfaites, et ajoute la tache destination comme
            //      tâche candidate
            while (transitionsSortantes.hasNext()){
                DefinitionTransition prochaineTransition = transitionsSortantes.next();
                boolean satisfaite = false;
                if (this.getModeExecution() == ModeExecution.EXPRESSIONS_LAMBDA){
                    ConditionTransition condition = prochaineTransition.getConditionTransition();
                    satisfaite = condition.isTransitionOK(instanceTache, this.getDemandePret());

                }else if (this.getModeExecution() == ModeExecution.REGLES_DROOLS){
                    satisfaite = prochaineTransition.caPasse(this);
                }
                if (satisfaite) {
                    definitionTachesCandidates.add(prochaineTransition.getTacheDestination());
                }
            }

            // 2.c  Maintenant, vérifie si <code>definitionTachesCandidates</code> à zéro,
            //      une, ou plusieurs tâches candidates
            switch (definitionTachesCandidates.size()) {
                //  2.c.1   Ceci veut dire que c'est la fin du processus
                case 0: {
                    this.setEtatProcessus(new EtatProcessus(instanceTache, EtatTraitement.TERMINE));
                    break;
                }

                //  2.c.2   Il y a une seule "next step". Exeécute là
                case 1: {
                    DefinitionTache prochaineTache = definitionTachesCandidates.iterator().next();
                    this.activerTache(prochaineTache);
                    break;
                }
                
                //  2.c.3   Problème: j'ai plusieurs prochaines tâches
                //          possibles ==> processus non déterministe
                default : {
                    throw new ExceptionDefinitionProcessus(this,definitionTachesCandidates,"Processus non déterministe");
                }

            }
        } else {
            // 3.   Dans ce cas, conclure l'exécution avec échec
            this.setEtatProcessus(new EtatProcessus(tacheCourante, EtatTraitement.TERMINE));
            // on marque aussi la demande de prêt comme refusée
            this.demandePret.getResultatTraitement().setResultat(Resultat.REFUSEE);
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public ModeExecution getModeExecution() {
        return modeExecution;
    }

    @Override
    public void setModeExecution(ModeExecution mode) {
       this.modeExecution = mode;
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
