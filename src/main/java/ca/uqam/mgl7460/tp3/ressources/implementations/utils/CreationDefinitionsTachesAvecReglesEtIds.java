package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.ResultatTraitement;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.TraitementTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class CreationDefinitionsTachesAvecReglesEtIds {
    
    private static DefinitionTache tacheEligibiliteEmprunteur;

    private static DefinitionTache tacheEligibilitePropriete;

    private static DefinitionTache tacheEligibilitePret;

    private static DefinitionTache tacheAffichageErreurs;

    private static DefinitionTache tacheAcceptation;

    private static String nomFichierReglesEmprunteur = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_emprunteur.drl";

    private static String nomFichierReglesPropriete = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_propriete.drl";

    private static String nomFichierReglesPret = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_pret.drl";

    private static String nomFichierReglesAffichageErreur = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_affichage_erreurs.drl";

    private static String nomFichierReglesAffichageAcceptation = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_affichage_acceptation.drl";


    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private CreationDefinitionsTachesAvecReglesEtIds() {}


    public static String getTacheAffichageErreur() {
        if (tacheAffichageErreurs != null) return tacheAffichageErreurs.getID();

        tacheAffichageErreurs = fabrique.creerDefinitionTache("Affichage messages d'erreur", "Cette tâche affiche les messages d'erreur des différents traitements");
        TraitementTache traitementTache = (DemandePret demandePret, Logger log) -> {
            ResultatTraitement resultatTraitement = demandePret.getResultatTraitement();
            log.log(Level.INFO,"Nous regrettons de vous informer que votre demande a été refusée pour les raisons suivantes");
            resultatTraitement.getMessages().forEachRemaining(message-> log.log(Level.INFO,message));
            return true;
        };
        tacheAffichageErreurs.setTraitementTache(traitementTache);
        tacheAffichageErreurs.setNomFichierRegles(nomFichierReglesAffichageErreur);
        return tacheAffichageErreurs.getID();
    }

    public static String getTacheAcceptation() {
        if (tacheAcceptation != null) return tacheAcceptation.getID();

        tacheAcceptation = fabrique.creerDefinitionTache("Acceptation", "Cette tâche affiche le m,essage d'acceptation");
        TraitementTache traitementTache = (DemandePret demandePret, Logger log) -> {
            if (demandePret.getResultatTraitement().getResultat() == Resultat.ACCEPTEE) {
                log.log(Level.INFO,"Félicitations! votre demande de prêt a été approuvée. Veuillez prendre contact avec votre agent pour finaliser les termes du prêt");
                return true;
            } else {
                log.log(Level.INFO,"Je comprends pas! votre demande a été refusée. Je devrais pas être là!");
                return false;
            }
        };
        tacheAcceptation.setTraitementTache(traitementTache);
        tacheAcceptation.setNomFichierRegles(nomFichierReglesAffichageAcceptation);
        return tacheAcceptation.getID();
    }

    public static String getTacheEligibiliteEmprunteur() {
        if (tacheEligibiliteEmprunteur != null) return tacheEligibiliteEmprunteur.getID();

        tacheEligibiliteEmprunteur = fabrique.creerDefinitionTache("Vérifier éligibilité emprunteur", "Cette tâche utilise des règles pour vérifier si la personne qui emprunte est éligible pour un prêt");
        tacheEligibiliteEmprunteur.setNomFichierRegles(nomFichierReglesEmprunteur);
        
        return tacheEligibiliteEmprunteur.getID();
    }

    public static String getTacheEligibilitePropriete() {

        if (tacheEligibilitePropriete!= null) return tacheEligibilitePropriete.getID();
        
        tacheEligibilitePropriete = fabrique.creerDefinitionTache("Vérifier éligibilité propriété", "Cette tâche Cette tâche utilise des règles pour vérifier l'éligibilité de la propriété");
        tacheEligibilitePropriete.setNomFichierRegles(nomFichierReglesPropriete);

        return tacheEligibilitePropriete.getID();

    }

    public static String getTacheEligibilitePret() {
        if (tacheEligibilitePret != null) return tacheEligibilitePret.getID();
        
        tacheEligibilitePret = fabrique.creerDefinitionTache("Éligibilité prêt", "Cette tâche Cette tâche utilise des règles pour vérifier l'éligibilité du prêt en terms de mise de fonds et LTV");
        tacheEligibilitePret.setNomFichierRegles(nomFichierReglesPret);

        return tacheEligibilitePret.getID();
    }
}
