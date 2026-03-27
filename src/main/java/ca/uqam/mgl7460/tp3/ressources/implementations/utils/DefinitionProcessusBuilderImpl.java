package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DefinitionProcessusBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class DefinitionProcessusBuilderImpl implements DefinitionProcessusBuilder {

    private final Fabrique fabrique = Fabrique.getSingletonFabrique();

    @Override
    public String creerDefinitionTache(String nom, String description, String nomFichierRegles) {
        DefinitionTache definitionTache = fabrique.creerDefinitionTache(nom, description);
        definitionTache.setNomFichierRegles(nomFichierRegles);

        return definitionTache.getID();
    }

    @Override
    public String creerDefinitionTransition(String idTacheSource, String idTacheDestination, String nomFichierRegles) {
        DefinitionTache tacheSource = fabrique.getDefinitionTacheByID(idTacheSource);
        DefinitionTache tacheDestination = fabrique.getDefinitionTacheByID(idTacheDestination);

        DefinitionTransition definitionTransition = fabrique.creerDefinitionTransition(tacheSource, tacheDestination);

        definitionTransition.setNomFichierRegles(nomFichierRegles);

        return definitionTransition.getID();
    }

    @Override
    public String creerDefinitionProcessus(String nomProcessus, String descriptionProcessus) {
        DefinitionProcessus definitionProcessus = fabrique.creerDefinitionProcessus(nomProcessus, descriptionProcessus);
        return definitionProcessus.getID();
    }

    @Override
    public void ajouteTache(String idProcessus, String idDefinitionTache) {
        DefinitionProcessus processus = fabrique.getDefinitionProcessusByID(idProcessus);
        DefinitionTache tache = fabrique.getDefinitionTacheByID(idDefinitionTache);
        processus.ajouteTache(tache);
    }

    @Override
    public void ajouteTransition(String idProcessus, String idDefinitionTransition) {
        DefinitionProcessus processus = fabrique.getDefinitionProcessusByID(idProcessus);
        DefinitionTransition transition = fabrique.getDefinitionTransitionByID(idDefinitionTransition);
        processus.ajouteTransition(transition);
    }

    @Override
    public String ajouteTransition(String idProcessus, String idTacheSource, String idTacheDestination, String nomsFichiersRegles) {

        // create transition
        String idTransition = creerDefinitionTransition(idTacheSource, idTacheDestination, nomsFichiersRegles);

        // add it to process
        this.ajouteTransition(idProcessus, idTransition);

        return idTransition;
    }

    @Override
    public String getIdPremiereTache(String idProcessus) {
        DefinitionProcessus processus = fabrique.getDefinitionProcessusByID(idProcessus);
        DefinitionTache tache = processus.getPremiereTache();
        return (tache != null) ? tache.getID() : null;
    }

    @Override
    public void setPremiereTache(String idProcessus, String idTache) {
        DefinitionProcessus processus = fabrique.getDefinitionProcessusByID(idProcessus);
        DefinitionTache tache = fabrique.getDefinitionTacheByID(idTache);

        processus.setPremiereTache(tache);
    }

    @Override
    public void ajoutePremiereTache(String idProcessus, String idTache) {
        DefinitionProcessus processus = fabrique.getDefinitionProcessusByID(idProcessus);
        DefinitionTache tache = fabrique.getDefinitionTacheByID(idTache);

        processus.ajoutePremiereTache(tache);
    }

    @Override
    public boolean isTacheFinale(String idProcessus, String idTache) {
        DefinitionProcessus processus = fabrique.getDefinitionProcessusByID(idProcessus);
        DefinitionTache tache = fabrique.getDefinitionTacheByID(idTache);
        return processus.isTacheFinale(tache);
    }
}
