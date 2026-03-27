package ca.uqam.mgl7460.tp3.ressources.implementations.processus.definitions;


import ca.uqam.mgl7460.tp3.ressources.drools.UtilitaireRegles;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;
import org.kie.api.runtime.KieSession;

import java.util.UUID;

public class DefinitionTransitionImpl implements DefinitionTransition{

    private String ID;

    private DefinitionTache tacheSource;

    private DefinitionTache tacheDestination;

    private ConditionTransition conditionTransition;

    private String nomFichierRegles;

    private boolean isPass = false;

    public DefinitionTransitionImpl(DefinitionTache source, DefinitionTache destination, ConditionTransition conditionTransition){
        this.ID = UUID.randomUUID().toString();
        this.tacheSource = source;
        this.tacheDestination = destination;
        this.conditionTransition = conditionTransition;
    }

    public DefinitionTransitionImpl(DefinitionTache source, DefinitionTache destination, String nomFichierRegles){
        this.ID = UUID.randomUUID().toString();
        this.tacheSource = source;
        this.tacheDestination = destination;
        this.nomFichierRegles = nomFichierRegles;
    }
    
    @Override
    public DefinitionTache getTachesource() {
        return tacheSource;
    }

    @Override
    public DefinitionTache getTacheDestination() {
        return tacheDestination;
    }

    @Override
    public ConditionTransition getConditionTransition() {
        return conditionTransition;
    }

    @Override
    public String getNomFichierRegles() {
        return nomFichierRegles;
    }

    @Override
    public void setIsPass(boolean isPass){
        this.isPass = isPass;
    }

    @Override
    public boolean caPasse(InstanceProcessus instanceProcessus) {
        KieSession kieSession = UtilitaireRegles.getKieSessionPourFichier(nomFichierRegles);

        kieSession.insert(instanceProcessus);
        kieSession.insert(this.tacheSource);
        kieSession.insert(this);

        System.out.println("&&&& in caPass. instance process id:"+instanceProcessus.getID()+
                "\n tache source id: "+this.tacheSource.getID() +
                "\n and the id of transition itself: "+ this.getID());

        try {
            kieSession.fireAllRules();
            System.out.println("TRANSITION: Finished executing rules in "+ nomFichierRegles + " with kiesession: " + kieSession);
        } finally {
            kieSession.dispose();
        }

        return this.isPass;
    }

    @Override
    public void setNomFichierRegles(String nomFichierDrools) {
        this.nomFichierRegles = nomFichierDrools;
    }


    @Override
    public String getID() {
        return this.ID;
    }
}
