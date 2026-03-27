package ca.uqam.mgl7460.tp3.ressources.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import ca.uqam.mgl7460.tp3.ressources.drools.UtilitaireRegles;
import ca.uqam.mgl7460.tp3.ressources.implementations.utils.CreationDefinitionsProcessusAvecReglesEtIds;
import ca.uqam.mgl7460.tp3.ressources.implementations.utils.CreationDemandesPretAvecBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ModeExecution;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;
import ca.uqam.mgl7460.tp3.ressources.types.utils.InstanceProcessusLauncher;

public class TestCreationInstanceProcessusAvecReglesEtIds {

    private static String idProcessusComplexeAvecRegles = null;

    private static String idProcessusLineaireAvecRegles = null;

    private static Fabrique fabrique = null;

    private static InstanceProcessusLauncher launcher = null;

    @BeforeAll
    public static void initialiser() {

        fabrique = Fabrique.getSingletonFabrique();

        launcher = fabrique.getInstanceProcessusLauncher();

        idProcessusLineaireAvecRegles = CreationDefinitionsProcessusAvecReglesEtIds.getProcessusLineaireAvecRegles();

        idProcessusComplexeAvecRegles = CreationDefinitionsProcessusAvecReglesEtIds.getProcessusComplexeAvecRegles();
;
    }


    private boolean aRecuLesDiagnostics(DemandePret demandePret, String...diagnostics){

        ArrayList<String> messagesErreur = new ArrayList<>();
        demandePret.getResultatTraitement().getMessages().forEachRemaining(message -> messagesErreur.add(message));

        if (messagesErreur.size() != diagnostics.length) return false;

        boolean itMatchesAll = true;
        for (String diagnostic: diagnostics) {
            boolean itMatchesDiagnostic = false;
            for (String message: messagesErreur) {
                itMatchesDiagnostic = itMatchesDiagnostic || message.equals(diagnostic);
            }
            itMatchesAll = itMatchesAll && itMatchesDiagnostic;
        }
        return itMatchesAll;
    }


    @Test
    public void testCreationInstanceProcessus() {
        // 1. DemandePret demandePret = CreationDemandesPret.getDemandePretOuiOuiOui();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandePretOuiOuiOui();

        // 2. L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);        
        
        // 3. InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(processusLineaireAvecRegles, demandePret);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        assertEquals(fabrique.getDefinitionProcessusByID(idProcessusLineaireAvecRegles),fabrique.getInstanceProcessusByID(idInstanceProcessus).getDefinitionProcessus(),"Le nouveau processus n'a pas la bonne definition");
        assertEquals(Resultat.ACCEPTEE, fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande de prêt est supposée être acceptée");
    }

    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeRevenuTropFaible() {

        // 1. Obtenir une demande de pret oui-oui-oui
        // DemandePret demandePret = CreationDemandesPret.getDemandeRevenuTropFaible();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeRevenuTropFaible();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec le message d'erreur UtilitaireRegles.SALAIRE_TROP_FAIBLE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.SALAIRE_TROP_FAIBLE));
    }

    
    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeScoreCreditFaible() {

        // 1. Obtenir une demande de pret oui-oui-oui
        // DemandePret demandePret = CreationDemandesPret.getDemandeScoreCreditTropFaible();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeScoreCreditTropFaible();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        // 3. verifier que c'est refuse
        // assertEquals(Resultat.REFUSEE,demandePret.getResultatTraitement().getResultat(), "Demande " + demandePret + " était supposée être refusée");
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");


        // 4. avec le message d'erreur UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE));
    }

    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeTauxEndettementEleve() {

        // 1. Obtenir une demande de pret oui-oui-oui
        // DemandePret demandePret = CreationDemandesPret.getDemandeTauxEndettementTropEleve();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeTauxEndettementTropEleve();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec le message d'erreur UtilitaireRegles.TAUX_ENDETTEMENT_TROP_ELEVE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.TAUX_ENDETTEMENT_TROP_ELEVE));
    }

    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeProprieteHorsQuebec() {

        // 1. Obtenir une demande de pret oui-oui-oui
        // DemandePret demandePret = CreationDemandesPret.getDemandeProprieteHorsQuebec();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeProprieteHorsQuebec();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec le message d'erreur UtilitaireRegles.PROPRIETE_HORS_QUEBEC
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.PROPRIETE_HORS_QUEBEC));
    }

    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeTauxMiseDeFondsTropFaible() {

        // 1. Obtenir une demande de pret MiseDeFondsTropFaible
        // DemandePret demandePret = CreationDemandesPret.getDemandeMiseDeFondsTropFaible();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeMiseDeFondsTropFaible();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec le message d'erreur UtilitaireRegles.TAUX_MISE_FONDS_TROP_FAIBLE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.TAUX_MISE_FONDS_TROP_FAIBLE));

    }

    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeRatioEmpruntValeurMarcheTropEleve() {

        // 1. Obtenir une demande de pret RatioEmpruntValeurMarcheTropEleve
        // DemandePret demandePret = CreationDemandesPret.getDemandeRatioEmpruntValeurMarcheTropEleve();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeRatioEmpruntValeurMarcheTropEleve();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);
        
        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);

        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec le message d'erreur UtilitaireRegles.RATIO_EMPRUNT_VALEUR_TROP_ELEVE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.RATIO_EMPRUNT_VALEUR_TROP_ELEVE));
    }
    
    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeRevenuCoteCreditFaibles() {

        // 1. Obtenir une demande de pret RevenuCoteCreditFaibles
        // DemandePret demandePret = CreationDemandesPret.getDemandeRevenuCoteCreditFaibles();
        // String idDemandePret = demandePret.getID();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeRevenuCoteCreditFaibles();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);        
        
        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE,fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec les messages d'erreur UtilitaireRegles.SALAIRE_TROP_FAIBLE et UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.SALAIRE_TROP_FAIBLE,UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE));
    }

    @Test
    public void testExecutionProcessusSimpleAvecReglesSurDemandeCoteCreditMiseDefondsFaibles() {

        // 1. Obtenir une demande de pret RevenuCoteCreditFaibles
        // DemandePret demandePret = CreationDemandesPret.getDemandeCoteCreditMiseDefondsFaibles();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeCoteCreditMiseDefondsFaibles();

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusLineaireAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);        
        
        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE, fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec les messages d'erreur UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE et UtilitaireRegles.TAUX_MISE_FONDS_TROP_FAIBLE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE, UtilitaireRegles.TAUX_MISE_FONDS_TROP_FAIBLE));
    }

    @Test
    public void testExecutionProcessusComplexeAvecReglesSurDemandeCoteCreditMiseDefondsFaibles() {

        // 1. Obtenir une demande de pret RevenuCoteCreditFaibles
        // DemandePret demandePret = CreationDemandesPret.getDemandeCoteCreditMiseDefondsFaibles();
        String idDemandePret = CreationDemandesPretAvecBuilder.getDemandeCoteCreditMiseDefondsFaibles();        

        // 2.   L'initialiser à ACCEPTEE. Si problème, ce sera changé par les règles
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        String idInstanceProcessus = launcher.creerInstanceProcessus(idProcessusComplexeAvecRegles, idDemandePret);
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        launcher.lancerInstanceProcessusAvecId(idInstanceProcessus);        
        
        // 3. verifier que c'est refuse
        assertEquals(Resultat.REFUSEE, fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().getResultat(), "Demande " + fabrique.getDemandePretByID(idDemandePret) + " était supposée être refusée");

        // 4. avec le SEUL message d'erreur UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE
        assertTrue(aRecuLesDiagnostics(fabrique.getDemandePretByID(idDemandePret),UtilitaireRegles.SCORE_CREDIT_TROP_FAIBLE));
    }
}
