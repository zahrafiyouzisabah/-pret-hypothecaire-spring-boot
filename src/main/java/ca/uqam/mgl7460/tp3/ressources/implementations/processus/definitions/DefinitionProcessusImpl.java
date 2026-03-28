package ca.uqam.mgl7460.tp3.ressources.implementations.processus.definitions;

import java.util.*;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class DefinitionProcessusImpl implements DefinitionProcessus {

    private String ID;
    private static int compteur = 0;

    private Collection<DefinitionTache> taches;

    private DefinitionTache premiereTache;

    private HashMap<DefinitionTache,Collection<DefinitionTransition>> transitions;

    private String nom;

    private String description;

    public DefinitionProcessusImpl(String nom, String description) {
        this.ID = prochaineID();
        this.nom = nom;
        this.description = description;
        this.taches = new ArrayList<>();
        this.transitions = new HashMap<>();
    }

    private String prochaineID(){
        return "DEFPROC" + (++compteur);
    }
    

    @Override
    public Iterator<DefinitionTache> getTaches() {
        return taches.iterator();
    }

    @Override
    public void ajouteTache(DefinitionTache definitionTache) {
        taches.add(definitionTache);
    }

    @Override
    public void ajouteTransition(DefinitionTransition definitionTransition) {
        // 1. D'abord, vérifier si on a déjà des transitions sortantes de la 
        //    meme source que l'argument. 
        
        Collection<DefinitionTransition> transitionsPourSource = transitions.get(definitionTransition.getTachesource());
        if (transitionsPourSource == null) {
            // Sinon, créer une collection vide
            transitionsPourSource = new ArrayList<>();

            // et l'insérer
            transitions.put(definitionTransition.getTachesource(),transitionsPourSource);
        }

        // 2. Ajouter la transition a la liste des transitions sortante
        transitionsPourSource.add(definitionTransition);
    }

    @Override
    public DefinitionTransition ajouteTransition(DefinitionTache tacheSource, DefinitionTache tacheDestination,
            ConditionTransition... conditions) {
        DefinitionTransition transition = null;

        // 1.   D'abord, si on ne spécifie pas de condition par défaut, on en définit une
        //      qui retourne "true" tout le temps
        ConditionTransition conditionTransition = null;
        if (conditions != null) {
            conditionTransition = conditions[0];
        } else {
            conditionTransition = (InstanceTache source, DemandePret demande) ->  true;
        }

        // 2.   Créer la transition et l'ajouter
        transition = Fabrique.getSingletonFabrique().creerDefinitionTransition(tacheSource,tacheDestination,conditionTransition);
        this.ajouteTransition(transition);
        
        // 3.   La retourner
        return transition;
    }

    @Override
    public Iterator<DefinitionTransition> getTransitionsSortantesDe(DefinitionTache tache) {
        Collection<DefinitionTransition> transitionsSortantes = transitions.get(tache);
        if (transitionsSortantes != null) return transitionsSortantes.iterator();
        return new ArrayList<DefinitionTransition>().iterator();
    }

    @Override
    public DefinitionTache getPremiereTache() {
        return premiereTache;
    }

    @Override
    public void setPremiereTache(DefinitionTache tache) {
        if (!taches.contains(tache)) ajoutePremiereTache(tache);
        else this.premiereTache = tache;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean isTacheFinale(DefinitionTache tache) {
        // je vérifie tout simplement que je n'ai pas de transition
        // qui sort de tache. Ça va règler le cas où tache ne fait pas
        // partie du processus.
        return ((transitions.get(tache) == null) || (transitions.get(tache).isEmpty()));
    }


    @Override
    public void ajoutePremiereTache(DefinitionTache tache) {
        if (!taches.contains(tache)) taches.add(tache);
        setPremiereTache(tache);
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
