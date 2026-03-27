package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import java.util.HashMap;

import ca.uqam.mgl7460.tp3.ressources.implementations.domaine.DemandePretImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.domaine.DemandeurPretImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.domaine.ProprieteImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.domaine.ResultatTraitementImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.processus.definitions.DefinitionProcessusImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.processus.definitions.DefinitionTacheImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.processus.definitions.DefinitionTransitionImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.processus.instances.InstanceProcessusImpl;
import ca.uqam.mgl7460.tp3.ressources.implementations.processus.instances.InstanceTacheImpl;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Adresse;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.ResultatTraitement;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.TraitementTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DefinitionProcessusBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DemandePretBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;
import ca.uqam.mgl7460.tp3.ressources.types.utils.InstanceProcessusLauncher;

public class FabriqueImpl implements Fabrique {

    private static Fabrique singleton = null;

    private static DefinitionProcessusBuilder singletonDefinitionProcessusBuilder = new DefinitionProcessusBuilderImpl();

    private static DemandePretBuilder singletonDemandePretBuilder = DemandePretBuilderImpl.getSingleton();

    private static InstanceProcessusLauncher singletonInstanceProcessusLauncher = InstanceProcessusLauncherImpl.getSingleton();


    private static HashMap<String,Propriete> tableProprietes=new HashMap<>();

    private static HashMap<String,DemandeurPret> tableDemandeursPret=new HashMap<>();

    private static HashMap<String,DemandePret> tableDemandesPret=new HashMap<>();

    private static HashMap<String,DefinitionProcessus> tableDefinitionsProcessus=new HashMap<>();

    private static HashMap<String,DefinitionTache> tableDefinitionsTaches=new HashMap<>();

    private static HashMap<String,DefinitionTransition> tableDefinitionsTransitions=new HashMap<>();

    private static HashMap<String,InstanceProcessus> tableInstancesProcessus=new HashMap<>();


    @Override
    public Propriete creerPropriete(Adresse adresse) {
        Propriete prop = new ProprieteImpl(adresse);
        tableProprietes.put(prop.getID(), prop);
        return prop;
    }

    @Override
    public Propriete creerPropriete(Adresse adresse, float valeurMarche) {
        Propriete prop = new ProprieteImpl(adresse, valeurMarche);
        tableProprietes.put(prop.getID(), prop);
        return prop;
    }

    @Override
    public DemandeurPret creerDemandeurPret(String prenom, String nom, String nas) {
        DemandeurPret demandeur = new DemandeurPretImpl(prenom, nom, nas);
        tableDemandeursPret.put(demandeur.getID(),demandeur);
        return demandeur;
    }

    @Override
    public DemandeurPret creerDemandeurPret(String prenom, String nom, String nas, float revenuAnnuel,
            float obligationsAnnuelles, int scoreCredit) {
        DemandeurPret demandeur =  new DemandeurPretImpl(prenom, nom, nas);
        demandeur.setRevenuAnnuel(revenuAnnuel);
        demandeur.setObligationsAnnuelles(obligationsAnnuelles);
        demandeur.setScoreCredit(scoreCredit);
        tableDemandeursPret.put(demandeur.getID(),demandeur);
        return demandeur;
    }

    @Override
    public DemandePret creerDemandePret(Propriete propriete, DemandeurPret demandeurPret) {
        DemandePret demande= new DemandePretImpl(demandeurPret, propriete);
        tableDemandesPret.put(demande.getID(),demande);
        return demande;
    }

    @Override
    public DemandePret creerDemandePret(Propriete propriete, DemandeurPret demandeurPret, float prixAchat,
            float miseDeFonds) {
        DemandePret demande = new DemandePretImpl(demandeurPret, propriete,prixAchat, miseDeFonds);
        tableDemandesPret.put(demande.getID(),demande);
        return demande;
    }

    @Override
    public ResultatTraitement creerResultatTraitement(Resultat resultat) {
        return new ResultatTraitementImpl(resultat);
    }

    @Override
    public DefinitionTransition creerDefinitionTransition(DefinitionTache tacheSource, DefinitionTache tacheDestination,
            ConditionTransition conditionTransition) {
        DefinitionTransition definitionT= new DefinitionTransitionImpl(tacheSource,tacheDestination,conditionTransition);
        tableDefinitionsTransitions.put(definitionT.getID(),definitionT);
        return definitionT;
    }

    @Override
    public DefinitionTransition creerDefinitionTransition(DefinitionTache tacheSource,
            DefinitionTache tacheDestination) {
        ConditionTransition conditionTransition = (InstanceTache tache, DemandePret demande)-> true;
        return creerDefinitionTransition(tacheSource, tacheDestination,conditionTransition);
    }    

    @Override
    public DefinitionTache creerDefinitionTache(String nom, String description) {
        DefinitionTache  definitionTache = new DefinitionTacheImpl(nom,description);
        tableDefinitionsTaches.put(definitionTache.getID(),definitionTache);
        return definitionTache;
    }

    @Override
    public DefinitionTache creerDefinitionTache(String nom, String description, TraitementTache traitementTache) {
        DefinitionTache  definitionTache = new DefinitionTacheImpl(nom, description, traitementTache);
        tableDefinitionsTaches.put(definitionTache.getID(),definitionTache);
        return definitionTache;
    }

    @Override
    public DefinitionProcessus creerDefinitionProcessus(String nomProcessus, String descriptionProcessus) {
        DefinitionProcessus definitionProcessus= new DefinitionProcessusImpl(nomProcessus, descriptionProcessus);
        tableDefinitionsProcessus.put(definitionProcessus.getID(),definitionProcessus);
        return definitionProcessus;
    }

    @Override
    public InstanceProcessus creerInstanceProcessus(DefinitionProcessus definitionProcessus, DemandePret demandePret) {
        InstanceProcessus instanceProcessus= new InstanceProcessusImpl(definitionProcessus, demandePret);
        tableInstancesProcessus.put(instanceProcessus.getID(),instanceProcessus);
        return instanceProcessus;
    }

    @Override
    public InstanceTache creerInstanceTache(InstanceProcessus instanceProcessus, DefinitionTache definitionTache) {
        return new InstanceTacheImpl(definitionTache,instanceProcessus);
    }

    /**
     * retourne un DefinitionProcessusBuilder (un singleton)
     *
     * @return
     */
    @Override
    public DefinitionProcessusBuilder getDefinitionProcessusBuilder() {
        return singletonDefinitionProcessusBuilder;
    }

    @Override
    public Propriete getProprieteByID(String id) {
        return tableProprietes.get(id);
    }

    @Override
    public DemandeurPret getDemandeurPretByID(String id) {
        return tableDemandeursPret.get(id);
    }

    @Override
    public DemandePret getDemandePretByID(String id) {
        return tableDemandesPret.get(id);
    }

    @Override
    public DefinitionTache getDefinitionTacheByID(String id) {
        return tableDefinitionsTaches.get(id);
    }

    @Override
    public DefinitionTransition getDefinitionTransitionByID(String id) {
        return tableDefinitionsTransitions.get(id);
    }

    @Override
    public DefinitionProcessus getDefinitionProcessusByID(String id) {
        return tableDefinitionsProcessus.get(id);
    }

    @Override
    public InstanceProcessus getInstanceProcessusByID(String id) {
        return tableInstancesProcessus.get(id);
    }

    @Override
    public DemandePretBuilder getDemandePretBuilder() {
        return singletonDemandePretBuilder;
    }

    @Override
    public InstanceProcessusLauncher getInstanceProcessusLauncher() {
        return singletonInstanceProcessusLauncher;
    }

    public static Fabrique getSingleton() {
        if (singleton == null) singleton = new FabriqueImpl();
        return singleton;
    }

}