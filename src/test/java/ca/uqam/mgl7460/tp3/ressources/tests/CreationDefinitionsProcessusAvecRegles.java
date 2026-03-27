package ca.uqam.mgl7460.tp3.ressources.tests;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;


public class CreationDefinitionsProcessusAvecRegles {
        
    private static DefinitionProcessus processusComplexe;

    private static DefinitionProcessus processusErrone;

    private static DefinitionProcessus processusLineaire;

    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private static String nomFichierReglesTransitionSiAcceptee = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_si_acceptee.drl";

    private static String nomFichierReglesTransitionSiRefusee = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_si_refusee.drl";

    private static String nomFichierReglesTransitionTrue = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_true.drl";


    private CreationDefinitionsProcessusAvecRegles() {}

    public static DefinitionProcessus getProcessusErrone() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusErrone != null) return processusErrone;

        // 1.   Création de coquille
        processusErrone = fabrique.creerDefinitionProcessus("Processus erroné avec règles", "Processus d'éeligibilité contenant deux transitions sortantes sans condition de la même tâche");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        DefinitionTache eligibiliteEmprunteur = CreationDefinitionsTachesAvecRegles.getTacheEligibiliteEmprunteur();
        processusErrone.ajoutePremiereTache(eligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        // 3.a.     Ajouter la tache
        DefinitionTache eligibilitePropriete = CreationDefinitionsTachesAvecRegles.getTacheEligibilitePropriete();
        processusErrone.ajouteTache(eligibilitePropriete);
        
        // 3.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePropriete
            // la transition est automatique, sans condition
        ConditionTransition conditionTransition = (InstanceTache tacheSource, DemandePret demandePret) -> true;
        DefinitionTransition transition=processusErrone.ajouteTransition(eligibiliteEmprunteur, eligibilitePropriete, conditionTransition);
        transition.setNomFichierRegles(nomFichierReglesTransitionTrue);

        // 4.   Ajouter vérification de prêt
        // 4.a.     Ajouter la tâche
        DefinitionTache eligibilitePret = CreationDefinitionsTachesAvecRegles.getTacheEligibilitePret();
        processusErrone.ajouteTache(eligibilitePret);

        // 4.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePret
            // on va utiliser la même condition, qui n'en est pas une
        DefinitionTransition transition2 = processusErrone.ajouteTransition(eligibiliteEmprunteur, eligibilitePret, conditionTransition);
        transition2.setNomFichierRegles(nomFichierReglesTransitionTrue);

        return processusErrone;
    }



    public static DefinitionProcessus getProcessusComplexeAvecRegles() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusComplexe != null) return processusComplexe;

        // 1.   Création de coquille
        processusComplexe = fabrique.creerDefinitionProcessus("Processus éligibilité complexe avec règles", "Processus d'éeligibilité qui sort dès que la demande échoue un critère");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        DefinitionTache eligibiliteEmprunteur = CreationDefinitionsTachesAvecRegles.getTacheEligibiliteEmprunteur();
        processusComplexe.ajoutePremiereTache(eligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        DefinitionTache eligibilitePropriete = CreationDefinitionsTachesAvecRegles.getTacheEligibilitePropriete();
        processusComplexe.ajouteTache(eligibilitePropriete);
        
        // 4.   Ajouter vérification de prêt
        DefinitionTache eligibilitePret = CreationDefinitionsTachesAvecRegles.getTacheEligibilitePret();
        processusComplexe.ajouteTache(eligibilitePret);

        // 5.   Ajouter affichage de messages d'erreur
        DefinitionTache affichageErreurs = CreationDefinitionsTachesAvecRegles.getTacheAffichageErreur();
        processusComplexe.ajouteTache(affichageErreurs);

        // 6.   Ajouter tache acceptation
        DefinitionTache tacheAcceptation = CreationDefinitionsTachesAvecRegles.getTacheAcceptation();
        processusComplexe.ajouteTache(tacheAcceptation);


        // 7.   Ajouter les transitions

        // 7.a  Les transitions du happy path
        ConditionTransition happyCondition = (InstanceTache tacheSource, DemandePret demandePret) -> demandePret.getResultatTraitement().getResultat() == Resultat.ACCEPTEE;
        
        // 7.a.1    Transition eligibiliteEmprunteur -> eligibilitePropriete
        DefinitionTransition happy_1 = processusComplexe.ajouteTransition(eligibiliteEmprunteur, eligibilitePropriete, happyCondition);
        happy_1.setNomFichierRegles(nomFichierReglesTransitionSiAcceptee);

        // 7.a.2    Transition eligibilitePropriete -> eligibilitePret
        DefinitionTransition happy_2 = processusComplexe.ajouteTransition(eligibilitePropriete, eligibilitePret, happyCondition);
        happy_2.setNomFichierRegles(nomFichierReglesTransitionSiAcceptee);

        // 7.a.3    Transition eligibilitePret -> tacheAcceptation
        DefinitionTransition happy_3 = processusComplexe.ajouteTransition(eligibilitePret,tacheAcceptation, happyCondition);
        happy_3.setNomFichierRegles(nomFichierReglesTransitionSiAcceptee);

        // 7.b  Les transitions du unhappy path
        ConditionTransition unhappyCondition = (InstanceTache tacheSource, DemandePret demandePret) -> demandePret.getResultatTraitement().getResultat() != Resultat.ACCEPTEE;

        // 7.b.1    Transition eligibiliteEmprunteur -> affichageErreurs
        DefinitionTransition unhappy_1 = processusComplexe.ajouteTransition(eligibiliteEmprunteur,affichageErreurs,unhappyCondition);
        unhappy_1.setNomFichierRegles(nomFichierReglesTransitionSiRefusee);

        // 7.b.2    Transition eligibilitePropriété -> affichageErreurs
        DefinitionTransition unhappy_2 = processusComplexe.ajouteTransition(eligibilitePropriete,  affichageErreurs,unhappyCondition);
        unhappy_2.setNomFichierRegles(nomFichierReglesTransitionSiRefusee);

        // 7.b.3    Transition eligibilitePret -> affichageErreurs
        DefinitionTransition unhappy_3 = processusComplexe.ajouteTransition(eligibilitePret,  affichageErreurs,unhappyCondition);
        unhappy_3.setNomFichierRegles(nomFichierReglesTransitionSiRefusee);

        return processusComplexe;
    }

    public static DefinitionProcessus getProcessusLineaireAvecRegles() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusLineaire != null) return processusLineaire;

        // 1.   Création de coquille
        processusLineaire = fabrique.creerDefinitionProcessus("Processus éligibilité simple avec règles", "Processus d'éeligibilité qui évalue les trois critères avec des règles, l'un à la suite de l'autre même si le premier échoue");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        DefinitionTache eligibiliteEmprunteur = CreationDefinitionsTachesAvecRegles.getTacheEligibiliteEmprunteur();
        processusLineaire.ajoutePremiereTache(eligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        // 3.a.     Ajouter la tache
        DefinitionTache eligibilitePropriete = CreationDefinitionsTachesAvecRegles.getTacheEligibilitePropriete();
        processusLineaire.ajouteTache(eligibilitePropriete);
        
        // 3.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePropriete
            // la transition est automatique, sans condition
        ConditionTransition conditionTransition = (InstanceTache tacheSource, DemandePret demandePret) -> true;
        DefinitionTransition goThrough= processusLineaire.ajouteTransition(eligibiliteEmprunteur, eligibilitePropriete, conditionTransition);
        goThrough.setNomFichierRegles(nomFichierReglesTransitionTrue);

        // 4.   Ajouter vérification de prêt
        // 4.a.     Ajouter la tâche
        DefinitionTache eligibilitePret = CreationDefinitionsTachesAvecRegles.getTacheEligibilitePret();
        processusLineaire.ajouteTache(eligibilitePret);

        // 4.b.     Ajouter la transition entre eligibilitePropriete et eligibilitePret
            // on va utiliser la même condition, qui n'en est pas une
        DefinitionTransition anotherGoThrough= processusLineaire.ajouteTransition(eligibilitePropriete, eligibilitePret, conditionTransition);
        anotherGoThrough.setNomFichierRegles(nomFichierReglesTransitionTrue);

        return processusLineaire;
    }


}
