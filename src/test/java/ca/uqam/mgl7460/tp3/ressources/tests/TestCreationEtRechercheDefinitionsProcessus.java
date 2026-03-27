package ca.uqam.mgl7460.tp3.ressources.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DefinitionProcessusBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;


import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;

import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class TestCreationEtRechercheDefinitionsProcessus {

    private static DefinitionTache tacheVerificationEmprunteur;
    private static final String nomTacheVerificationEmprunteur = "Vérification de l'emprunteur-se";
    private static final String descriptionTacheVerificationEmprunteur = "Cette tâche vérifie l'éligibilité de la personne qui emprunte";

    private static DefinitionTache tacheVerificationPropriete;
    private static final String nomTacheVerificationPropriete = "Vérification de la propriété";
    private static final String descriptionTacheVerificationPropriete = "Cette tâche vérifie l'éligibilité de la propriété à acheter";


    private static DefinitionTache tacheVerificationPret;
    private static final String nomTacheVerificationPret = "Vérification du prêt";
    private static final String descriptionTacheVerificationPret = "Cette tâche vérifie l'éligibilité des termes financiers du prêt";

    private static DefinitionTache tacheImpressionErreurs;
    private static final String nomTacheImpressionErreurs = "Impression erreurs";
    private static final String descriptionTacheImpressionErreurs = "Cette tâche imprime les messages d'erreurs produits par les t^ches de vérification d'éligibilité";

    private static DefinitionTache tacheImpressionSucces;
    private static final String nomTacheImpressionSucces = "Impression message succès";
    private static final String descriptionTacheImpressionSucces = "Cette tâche imprime le message de félicitations pour accord du prêt";

    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private static final String nomFichierReglesEmprunteur = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_emprunteur.drl";

    private static final String nomFichierReglesPropriete = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_propriete.drl";

    private static final String nomFichierReglesPret = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_pret.drl";

    private static final String nomFichierReglesTransitionSiAcceptee = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_si_acceptee.drl";

    private static final String nomFichierReglesTransitionSiRefusee = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_si_refusee.drl";

    private static final String nomFichierReglesTransitionTrue = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_true.drl";

    private static final String nomFichierReglesAffichageErreur = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_affichage_erreurs.drl";

    private static final String nomFichierReglesAffichageAcceptation = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_affichage_acceptation.drl";


    @BeforeAll
    public static void initialiser() {

        fabrique = Fabrique.getSingletonFabrique();
        tacheVerificationEmprunteur = fabrique.creerDefinitionTache(nomTacheVerificationEmprunteur,descriptionTacheVerificationEmprunteur);
        tacheVerificationEmprunteur.setNomFichierRegles(nomFichierReglesEmprunteur);

        tacheVerificationPropriete = fabrique.creerDefinitionTache(nomTacheVerificationPropriete,descriptionTacheVerificationPropriete);
        tacheVerificationPropriete.setNomFichierRegles(nomFichierReglesPropriete);

        tacheVerificationPret = fabrique.creerDefinitionTache(nomTacheVerificationPret,descriptionTacheVerificationPret);
        tacheVerificationPret.setNomFichierRegles(nomFichierReglesPret);

        tacheImpressionErreurs = fabrique.creerDefinitionTache(nomTacheImpressionErreurs, descriptionTacheImpressionErreurs);
        tacheImpressionErreurs.setNomFichierRegles(nomFichierReglesAffichageErreur);

        tacheImpressionSucces = fabrique.creerDefinitionTache(nomTacheImpressionSucces, descriptionTacheImpressionSucces);
        tacheImpressionSucces.setNomFichierRegles(nomFichierReglesAffichageAcceptation);
    }

    @Test
    public void testCreationEtRechercheDefinitionsProcessusVide() {
        DefinitionProcessus definitionProcessus = fabrique.creerDefinitionProcessus("Mortgage Underwriting Process","Processus de souscription hypothécaire");
        String id = definitionProcessus.getID();
        Assertions.assertEquals(definitionProcessus,fabrique.getDefinitionProcessusByID(id),"Je suis supposé retrouver le processus que je viens de créer");
    }

    @Test
    public void testCreationDefinitionsProcessusLineaireAvecID() {
        DefinitionProcessusBuilder builder = fabrique.getDefinitionProcessusBuilder();

        DefinitionProcessus processusLineaire = fabrique.creerDefinitionProcessus("Mortgage Underwriting Process","Processus de souscription hypothécaire");
        String idProcessus = processusLineaire.getID();
        String idTacheEmprunteur= tacheVerificationEmprunteur.getID();
        String idTachePropriete = tacheVerificationPropriete.getID();
        String idTachePret = tacheVerificationPret.getID();

        // 1. Vérifier la première tâche
        builder.ajoutePremiereTache(idProcessus,idTacheEmprunteur);
        Assertions.assertEquals(tacheVerificationEmprunteur,processusLineaire.getPremiereTache(),"Builder est supposé avoir ajouté la tache nommée "+tacheVerificationEmprunteur.getNom());

        // 2. Ajouter les deux autres tâches
        builder.ajouteTache(idProcessus,idTachePropriete);
        builder.ajouteTache(idProcessus,idTachePret);

        // 3. Vérifier que toutes les tâches ont été ajoutées
        DefinitionProcessus definitionProcessus = fabrique.getDefinitionProcessusByID(idProcessus);
        Iterator<DefinitionTache> tachesDuProcessus = definitionProcessus.getTaches();
        HashSet<String> ids = new HashSet<>(Arrays.asList(idTachePret,idTacheEmprunteur,idTachePropriete));
        HashSet<String> idsOfProcessTasks = new HashSet<>();
        while (tachesDuProcessus.hasNext()) idsOfProcessTasks.add(tachesDuProcessus.next().getID());
        Assertions.assertEquals(ids,idsOfProcessTasks);

        // 4.   Ajouter transition
        //  4.a Ajouter transition avec des IDs
        //  4.a.1   Créer la transition
        String idTransition = builder.ajouteTransition(idProcessus, idTacheEmprunteur,idTachePropriete,nomFichierReglesTransitionTrue);

        //  4.a.2   La chercher byID
        DefinitionTransition definitionTransition = fabrique.getDefinitionTransitionByID(idTransition);

        //  4.a.3   Vérifier qu'elle a les bons tache début et tache fin
        Assertions.assertEquals(tacheVerificationEmprunteur,definitionTransition.getTachesource(), "Tache source de transition avec ID: " + idTransition + " est supposée être " + tacheVerificationEmprunteur.getNom());
        Assertions.assertEquals(tacheVerificationPropriete,definitionTransition.getTacheDestination(), "Tache destination de transition avec ID: " + idTransition + " est supposée être " + tacheVerificationPropriete.getNom());
    }

     @Test
    public void testCreationDefinitionsProcessusComplexeAvecID() {
        DefinitionProcessusBuilder builder = fabrique.getDefinitionProcessusBuilder();

        DefinitionProcessus processusComplexe = fabrique.creerDefinitionProcessus("Mortgage Underwriting Process","Processus de souscription hypothécaire");
        String idProcessusComplexe = processusComplexe.getID();
        String idTacheEmprunteur= tacheVerificationEmprunteur.getID();
        String idTachePropriete = tacheVerificationPropriete.getID();
        String idTachePret = tacheVerificationPret.getID();
        String idTacheAffichageErreur = tacheImpressionErreurs.getID();
        String idTacheAffichageSucces = tacheImpressionSucces.getID();

        // 1. Vérifier la première tâche
        builder.ajoutePremiereTache(idProcessusComplexe,idTacheEmprunteur);
        Assertions.assertEquals(tacheVerificationEmprunteur,processusComplexe.getPremiereTache(),"Builder est supposé avoir ajouté la tache nommée "+tacheVerificationEmprunteur.getNom());

        // 2. Ajouter les quatre autres tâches
        builder.ajouteTache(idProcessusComplexe,idTachePropriete);
        builder.ajouteTache(idProcessusComplexe,idTachePret);
        builder.ajouteTache(idProcessusComplexe, idTacheAffichageSucces);
        builder.ajouteTache(idProcessusComplexe, idTacheAffichageErreur);

        // 3. Vérifier que toutes les tâches ont été ajoutées
        DefinitionProcessus definitionProcessusComplexe = fabrique.getDefinitionProcessusByID(idProcessusComplexe);
        Iterator<DefinitionTache> tachesDuProcessus = definitionProcessusComplexe.getTaches();
        HashSet<String> ids = new HashSet<>(Arrays.asList(idTachePret,idTacheEmprunteur,idTachePropriete,idTacheAffichageErreur, idTacheAffichageSucces));
        HashSet<String> idsOfProcessTasks = new HashSet<>();
        while (tachesDuProcessus.hasNext()) idsOfProcessTasks.add(tachesDuProcessus.next().getID());
        Assertions.assertEquals(ids,idsOfProcessTasks);

        // 4.   Ajouter transitions
        
        //  4.a Ajouter transition <Eligibilité emprunter --> Eligibilité propriété> avec des IDs
        String idTransitionEmprunteurProprieteString = builder.ajouteTransition(idProcessusComplexe, idTacheEmprunteur,idTachePropriete,nomFichierReglesTransitionSiAcceptee);
        
        //  4.b Ajouter transition <Eligibilité propriété --> Eligibilité pret>
        String idTransitionProprietePret = builder.ajouteTransition(idProcessusComplexe, idTachePropriete, idTachePret, nomFichierReglesTransitionSiAcceptee);

        //  4.c Ajouter transition <Eligibilité prêt --> Tavhe acceptation>
        String idTransitionPretAcceptation = builder.ajouteTransition(idProcessusComplexe, idTachePret, idTacheAffichageSucces, nomFichierReglesTransitionSiAcceptee);

        //  4.d Ajouter transition <Eligibilité emprunteur --> Tache erreur>
        String idTransitionEmprunteurErreur = builder.ajouteTransition(idProcessusComplexe, idTacheEmprunteur, idTacheAffichageErreur, nomFichierReglesTransitionSiRefusee);

        //  4.e Ajouter transition <Eligibilité propriété --> tache erreur>
        String idTransitionProprieteErreur = builder.ajouteTransition(idProcessusComplexe, idTachePropriete, idTacheAffichageErreur, nomFichierReglesTransitionSiRefusee);
        
        //  4.f Ajouter transition <Eligibilité pret --> Tache erreur
        String idTransitionPretErreur = builder.ajouteTransition(idProcessusComplexe, idTachePret, idTacheAffichageErreur, nomFichierReglesAffichageErreur);
    }
}
