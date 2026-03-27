package ca.uqam.mgl7460.tp3.ressources.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ExceptionDefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class TestCreationInstanceProcessus {

    private static DefinitionProcessus processusLineaire = null;

    private static DefinitionProcessus processusComplexe = null;

    private static DefinitionProcessus processusErrone = null;

    private static Fabrique fabrique = null;

    @BeforeAll
    public static void initialiser() {

        fabrique = Fabrique.getSingletonFabrique();

        processusLineaire = CreationDefinitionsProcessus.getProcessusLineaire();

        processusComplexe = CreationDefinitionsProcessus.getProcessusComplexe();

        processusErrone = CreationDefinitionsProcessus.getProcessusErrone();
    }

    @Test
    public void testCreationInstanceProcessus() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiOui();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaire, demandePret);

        assertEquals(processusLineaire,instanceProcessus.getDefinitionProcessus(),"Le nouveau processus n'a pas la bonne definition");
    }

    @Test
    public void testExecutionProcessusSimpleSurDemandeOuiOuiOui() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiOui();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaire, demandePret);
        try {
            instanceProcessus.demarrer();
            assertEquals(Resultat.ACCEPTEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être acceptée");
        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }
    }

    @Test void testExecutionProcessusSimpleSurDemandeOuiOuiNon() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiNon();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaire, demandePret);
        try {
            instanceProcessus.demarrer();

            // la demande est supposée être refusée
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");
            // et avoir échoué la troisième tâche
            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibilitePret()));

        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }
    }

    private boolean aEchoueLesTaches(DemandePret demandePret, DefinitionTache...definitionTaches){

        ArrayList<String> messagesErreur = new ArrayList<>();
        demandePret.getResultatTraitement().getMessages().forEachRemaining(message -> messagesErreur.add(message));

        if (messagesErreur.size() != definitionTaches.length) return false;

        boolean itMatchesAll = true;
        for (DefinitionTache tache: definitionTaches) {
            String nomTache = tache.getNom();
            boolean itMatchesTask = false;
            for (String message: messagesErreur) {
                itMatchesTask = itMatchesTask || message.contains(nomTache);
            }
            itMatchesAll = itMatchesAll && itMatchesTask;
        }
        return itMatchesAll;
    }

    @Test 
    void testExecutionProcessusSimpleSurDemandeOuiNonNon() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiNonNon();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaire, demandePret);
        try {
            instanceProcessus.demarrer();
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");

            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibilitePropriete() ,CreationDefinitionsTaches.getTacheEligibilitePret()));
        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }

    }
    @Test 
    void testExecutionProcessusSimpleSurDemandeNonNonNon() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretNonNonNon();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaire, demandePret);
        try {
            instanceProcessus.demarrer();
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");

            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibiliteEmprunteur() ,CreationDefinitionsTaches.getTacheEligibilitePropriete() ,CreationDefinitionsTaches.getTacheEligibilitePret()));
        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }

    }

    
    @Test 
    void testExecutionProcessusSimpleSurDemandeOuiNonOui() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiNonOui();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaire, demandePret);
        try {
            instanceProcessus.demarrer();
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");
            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibilitePropriete()));
        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }

    }

    @Test
    void testExecutionProcessusMalForme(){
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiOui();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusErrone, demandePret);

        assertThrows(ExceptionDefinitionProcessus.class, () -> {instanceProcessus.demarrer();});
    }

    @Test
    void testExecutionProcessusComplexeSurDemandeOuiOuiOui() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiOui();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusComplexe, demandePret);
        try {
            instanceProcessus.demarrer();
            assertEquals(Resultat.ACCEPTEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être acceptée");

        } catch (ExceptionDefinitionProcessus edp) {
            edp.printStackTrace();
        }

    }

     @Test void testExecutionProcessusComplexeSurDemandeOuiOuiNon() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiNon();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusComplexe, demandePret);
        try {
            instanceProcessus.demarrer();

            // la demande est supposée être refusée
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");
            // et avoir échoué la troisième tâche
            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibilitePret()));

        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }
    }

         @Test void testExecutionProcessusComplexeSurDemandeOuiNonNon() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretOuiNonNon();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusComplexe, demandePret);
        try {
            instanceProcessus.demarrer();

            // la demande est supposée être refusée
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");
            // et avoir échoué la deuxième tâche, auquel cas le processus se termine, de toutes les
            // façons
            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibilitePropriete()));

        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }
    }

        @Test 
        void testExecutionProcessusComplexeSurDemandeNonNonNon() {
        DemandePret demandePret = CreationDemandesPret.getDemandePretNonNonNon();
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusComplexe, demandePret);
        try {
            instanceProcessus.demarrer();

            // la demande est supposée être refusée
            assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée au niveau du pret");
            // et avoir échoué la première tâche, auquel cas le processus se termine, de toutes les
            // façons
            assertTrue(aEchoueLesTaches(demandePret,CreationDefinitionsTaches.getTacheEligibiliteEmprunteur()));

        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        }
    }


}
