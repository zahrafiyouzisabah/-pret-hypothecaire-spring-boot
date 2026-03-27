package ca.uqam.mgl7460.tp3.ressources.implementations.processus.instances;


import org.kie.api.runtime.KieSession;


import ca.uqam.mgl7460.tp3.ressources.drools.UtilitaireRegles;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.TraitementTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.EtatTraitement;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ExceptionDefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ModeExecution;

public class InstanceTacheImpl implements InstanceTache {

    private DefinitionTache definitionTache;
    private InstanceProcessus processusEnglobant;
    private DemandePret demandePret;

    private EtatTraitement etatTraitementInstanceTache;

    public InstanceTacheImpl(DefinitionTache definitionTache, InstanceProcessus processusEnglobant) {
        this.definitionTache = definitionTache;
        this.processusEnglobant = processusEnglobant;
        this.demandePret = processusEnglobant.getDemandePret();
        etatTraitementInstanceTache = EtatTraitement.PRET;
    }

    @Override
    public DefinitionTache getDefinitionTache() {
        return definitionTache;
    }

    @Override
    public EtatTraitement getEtatInstanceTache() {
        return etatTraitementInstanceTache;
    }

    @Override
    public void setEtatInstanceTache(EtatTraitement etat) {
        this.etatTraitementInstanceTache = etat;
    }

    @Override
    public InstanceProcessus getProcessusEnglobant() {
        return processusEnglobant;
    }

    @Override
    public DemandePret getDemandePret() {
        return demandePret;
    }

    @Override
    public void setDemandePret(DemandePret demande) {
        this.demandePret = demande;
    }

    @Override
    public void executer() throws ExceptionDefinitionProcessus {
        // 1.   Vérifier le mode d'exécution du processus englobant
        ModeExecution mode = this.processusEnglobant.getModeExecution();

        // 2.   Exécuter la tâche selon le mode d'exécution
        if (mode == ModeExecution.EXPRESSIONS_LAMBDA) {
                this.executerExpressionsLambda();
        } else if (mode == ModeExecution.REGLES_DROOLS) {
                this.executerRegles();
        }
        // 3.   Signaler la fin de cette tache
        this.processusEnglobant.signalerFinTache(this);
    }

    private void executerExpressionsLambda() {

        // 1.   Get the body of the task
        TraitementTache traitementTache = definitionTache.getTraitementTache();

        // 2.   Execute it
        boolean succes = traitementTache.traiteDemandePret(demandePret,processusEnglobant.getLogger());
        this.setEtatInstanceTache(EtatTraitement.TERMINE);

        // 3.   If success, say that the application is successful
        Resultat ancienResultat = demandePret.getResultatTraitement().getResultat();

        if (succes) {
            // 3.1  Si le sort de la demande n'est pas encore scellé, la marquer comme acceptée
            if ((ancienResultat == Resultat.ACCEPTEE) || (ancienResultat == Resultat.NONDETERMINE))
                 demandePret.getResultatTraitement().setResultat(Resultat.ACCEPTEE);
        } else {
            // 3.2  Elle est refusée (aussi) au titre de la tache courant
            demandePret.getResultatTraitement().setResultat(Resultat.REFUSEE);
            //      ... mettre un message à cet effet
            demandePret.getResultatTraitement().ajouteMessage("Échec de la tâche: " + this.definitionTache.getNom());
        }
    }

    private void executerRegles() {

        // 0.   Get the status of the application prior to rule invocation
        Resultat ancienResultat = demandePret.getResultatTraitement().getResultat();

        // 1.   Get the name of the rules package
        String nomFichierRegles = definitionTache.getNomFichierRegles();

        // 2.   Execute it
        KieSession kieSession = UtilitaireRegles.getKieSessionPourFichier(nomFichierRegles);
        kieSession.insert(this.getDemandePret());
        System.out.println("Trying to execute rules in "+ nomFichierRegles + " with kiesession: " + kieSession + " demande pret result "+ this.getDemandePret().getResultatTraitement().getResultat());
        try {
            kieSession.fireAllRules();
            System.out.println("Finished executing rules in "+ nomFichierRegles + " with kiesession: " + kieSession);
        } finally {
            kieSession.dispose();
        }
    
        this.setEtatInstanceTache(EtatTraitement.TERMINE);
   }  
}
