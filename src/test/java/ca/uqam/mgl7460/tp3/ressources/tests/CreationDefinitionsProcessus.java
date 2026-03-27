package ca.uqam.mgl7460.tp3.ressources.tests;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class CreationDefinitionsProcessus {
    
    private static DefinitionProcessus processusLineaire;

    private static DefinitionProcessus processusComplexe;

    private static DefinitionProcessus processusErrone;

    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private CreationDefinitionsProcessus() {}

    public static DefinitionProcessus getProcessusLineaire() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusLineaire != null) return processusLineaire;

        // 1.   Création de coquille
        processusLineaire = fabrique.creerDefinitionProcessus("Processus éligibilité simple", "Processus d'éeligibilité qui évalue les trois critères l'un à la suite de l'autre même si le premier échoue");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        DefinitionTache eligibiliteEmprunteur = CreationDefinitionsTaches.getTacheEligibiliteEmprunteur();
        processusLineaire.ajoutePremiereTache(eligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        // 3.a.     Ajouter la tache
        DefinitionTache eligibilitePropriete = CreationDefinitionsTaches.getTacheEligibilitePropriete();
        processusLineaire.ajouteTache(eligibilitePropriete);
        
        // 3.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePropriete
            // la transition est automatique, sans condition
        ConditionTransition conditionTransition = (InstanceTache tacheSource, DemandePret demandePret) -> true;
        processusLineaire.ajouteTransition(eligibiliteEmprunteur, eligibilitePropriete, conditionTransition);

        // 4.   Ajouter vérification de prêt
        // 4.a.     Ajouter la tâche
        DefinitionTache eligibilitePret = CreationDefinitionsTaches.getTacheEligibilitePret();
        processusLineaire.ajouteTache(eligibilitePret);

        // 4.b.     Ajouter la transition entre eligibilitePropriete et eligibilitePret
            // on va utiliser la même condition, qui n'en est pas une
        processusLineaire.ajouteTransition(eligibilitePropriete, eligibilitePret, conditionTransition);

        return processusLineaire;
    }

    public static DefinitionProcessus getProcessusErrone() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusErrone != null) return processusErrone;

        // 1.   Création de coquille
        processusErrone = fabrique.creerDefinitionProcessus("Processus erroné", "Processus d'éeligibilité contenant deux transitions sortantes sans condition de la même tâche");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        DefinitionTache eligibiliteEmprunteur = CreationDefinitionsTaches.getTacheEligibiliteEmprunteur();
        processusErrone.ajoutePremiereTache(eligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        // 3.a.     Ajouter la tache
        DefinitionTache eligibilitePropriete = CreationDefinitionsTaches.getTacheEligibilitePropriete();
        processusErrone.ajouteTache(eligibilitePropriete);
        
        // 3.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePropriete
            // la transition est automatique, sans condition
        ConditionTransition conditionTransition = (InstanceTache tacheSource, DemandePret demandePret) -> true;
        processusErrone.ajouteTransition(eligibiliteEmprunteur, eligibilitePropriete, conditionTransition);

        // 4.   Ajouter vérification de prêt
        // 4.a.     Ajouter la tâche
        DefinitionTache eligibilitePret = CreationDefinitionsTaches.getTacheEligibilitePret();
        processusErrone.ajouteTache(eligibilitePret);

        // 4.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePret
            // on va utiliser la même condition, qui n'en est pas une
        processusErrone.ajouteTransition(eligibiliteEmprunteur, eligibilitePret, conditionTransition);

        return processusErrone;
    }



    public static DefinitionProcessus getProcessusComplexe() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusComplexe != null) return processusComplexe;

        // 1.   Création de coquille
        processusComplexe = fabrique.creerDefinitionProcessus("Processus éligibilité complexe", "Processus d'éeligibilité qui sort dès que la demande échoue un critère");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        DefinitionTache eligibiliteEmprunteur = CreationDefinitionsTaches.getTacheEligibiliteEmprunteur();
        processusComplexe.ajoutePremiereTache(eligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        DefinitionTache eligibilitePropriete = CreationDefinitionsTaches.getTacheEligibilitePropriete();
        processusComplexe.ajouteTache(eligibilitePropriete);
        
        // 4.   Ajouter vérification de prêt
        DefinitionTache eligibilitePret = CreationDefinitionsTaches.getTacheEligibilitePret();
        processusComplexe.ajouteTache(eligibilitePret);

        // 5.   Ajouter affichage de messages d'erreur
        DefinitionTache affichageErreurs = CreationDefinitionsTaches.getTacheAffichageErreur();
        processusComplexe.ajouteTache(affichageErreurs);

        // 6.   Ajouter tache acceptation
        DefinitionTache tacheAcceptation = CreationDefinitionsTaches.getTacheAcceptation();
        processusComplexe.ajouteTache(tacheAcceptation);


        // 7.   Ajouter les transitions

        // 7.a  Les transitions du happy path
        ConditionTransition happyCondition = (InstanceTache tacheSource, DemandePret demandePret) -> demandePret.getResultatTraitement().getResultat() == Resultat.ACCEPTEE;
        
        // 7.a.1    Transition eligibiliteEmprunteur -> eligibilitePropriete
        processusComplexe.ajouteTransition(eligibiliteEmprunteur, eligibilitePropriete, happyCondition);

        // 7.a.2    Transition eligibilitePropriete -> eligibilitePret
        processusComplexe.ajouteTransition(eligibilitePropriete, eligibilitePret, happyCondition);

        // 7.a.3    Transition eligibilitePret -> tacheAcceptation
        processusComplexe.ajouteTransition(eligibilitePret,tacheAcceptation, happyCondition);

        // 7.b  Les transitions du unhappy path
        ConditionTransition unhappyCondition = (InstanceTache tacheSource, DemandePret demandePret) -> demandePret.getResultatTraitement().getResultat() != Resultat.ACCEPTEE;

        // 7.b.1    Transition eligibiliteEmprunteur -> affichageErreurs
        processusComplexe.ajouteTransition(eligibiliteEmprunteur,affichageErreurs,unhappyCondition);

        // 7.b.2    Transition eligibilitePropriété -> affichageErreurs
        processusComplexe.ajouteTransition(eligibilitePropriete,  affichageErreurs,unhappyCondition);

        // 7.b.3    Transition eligibilitePret -> affichageErreurs
        processusComplexe.ajouteTransition(eligibilitePret,  affichageErreurs,unhappyCondition);

        return processusComplexe;
    }
}
