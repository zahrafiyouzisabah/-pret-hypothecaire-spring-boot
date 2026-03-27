package ca.uqam.mgl7460.tp3.ressources.types.processus.instances;

import java.util.Collection;
import java.util.Iterator;

import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;

public class ExceptionDefinitionProcessus extends Exception {

    private InstanceProcessus instanceProcessus;

    private Collection<DefinitionTache> prochainesTachesCandidates;

    public ExceptionDefinitionProcessus(InstanceProcessus instanceProcessus, Collection<DefinitionTache> candidates, String message) {
        super(message);
        this.instanceProcessus = instanceProcessus;
        this.prochainesTachesCandidates = candidates;
    }

    public InstanceProcessus getInstanceProcessus() {
        return instanceProcessus;
    }

    public Iterator<DefinitionTache> getProchainesTachesCandidates() {
        return prochainesTachesCandidates.iterator();
    }

}
