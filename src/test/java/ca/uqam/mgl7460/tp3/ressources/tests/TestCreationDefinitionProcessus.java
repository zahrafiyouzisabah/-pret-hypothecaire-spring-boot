package ca.uqam.mgl7460.tp3.ressources.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.Adresse;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.ProvinceOuTerritoire;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.ConditionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTransition;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.TraitementTache;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class TestCreationDefinitionProcessus {
    
    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private String nomProcessus = "Vérification Éligibilité";

    private String descriptionProcessus = "Ce processus vérifie l'éligibilité du demandeur, de la propriété, et des termes du prêt";

    private String nomTache_1 = "Éligibilité demandeur-se";

    private String descriptionTache_1 = "Cette tâche vérifie l'éligibilité de la personne demandeuse";

    private String nomTache_2 = "Éligibilité propriété";

    private String descriptionTache_2 = "Cette tâche vérifie l'éligibilité de la propriété";

    private String nomTache_3 = "Éligibilité prêt";

    private String descriptionTache_3 = "Cette tâche vérifie l'éligibilité de l'emprunt";

    private DefinitionProcessus coquille;

    @BeforeEach
    void creationCoquille() {
        coquille = fabrique.creerDefinitionProcessus(nomProcessus, descriptionProcessus);
        assertNotNull(coquille);
    }

    @Test
    void testerAttributsDefinitionProcessus() {
        assertEquals(nomProcessus,coquille.getNom(),"Nom du processus mal initialisé");
        assertEquals(descriptionProcessus,coquille.getDescription(),"Description du processus mal initialisée");
    }

    @Test
    void testerCreationDefinitionTache() {
        DefinitionTache definitionTache = fabrique.creerDefinitionTache(nomTache_1, descriptionTache_1);
        assertNotNull(definitionTache);
        assertEquals(nomTache_1, definitionTache.getNom(), "Nom définition tâche mal initialisé");
        assertEquals(descriptionTache_1, definitionTache.getDescription(),"Description tache mal initialisée");
    } 

    @Test
    void testerAjoutPremiereDefinitionTache() {
        DefinitionTache definitionTache = fabrique.creerDefinitionTache(nomTache_1, descriptionTache_1);
        coquille.ajouteTache(definitionTache);
        // vérifier qu'elle a été ajoutée
        assertEquals(definitionTache,coquille.getTaches().next(), "La tache n'a pas été ajoutée");

        // vérifier qu'elle est terminale
        assertTrue(coquille.isTacheFinale(definitionTache),"Tache est supposée être finale");
        
    }

    @Test
    void testerAjoutTransition() {
        // 1.   Ajouter première tâche
        DefinitionTache definitionTache_1 = fabrique.creerDefinitionTache(nomTache_1, descriptionTache_1);
        coquille.ajouteTache(definitionTache_1);
 
        // 2.   Ajouter deuxième tâche
        DefinitionTache definitionTache_2 = fabrique.creerDefinitionTache(nomTache_2, descriptionTache_2);
        coquille.ajouteTache(definitionTache_2);

        // 3.   Ajouter troisième tâche
        DefinitionTache definitionTache_3 = fabrique.creerDefinitionTache(nomTache_3, descriptionTache_3);
        coquille.ajouteTache(definitionTache_3);

        // 4.a. Créer et ajouter une transition sans condition (toujours active)
        DefinitionTransition definitionTransition = fabrique.creerDefinitionTransition(definitionTache_1, definitionTache_2);
        coquille.ajouteTransition(definitionTransition);

        // 4.b. Créer et ajouter une transition avec condition
        ConditionTransition conditionTransition = (InstanceTache tache, DemandePret demande) ->  (demande.getRatioEmpruntValeur() > 0.95f);
        coquille.ajouteTransition(definitionTache_1, definitionTache_3, conditionTransition);

        // 5.   vérifier que definitionTache_1 a deux taches subséquentes
        int numberTransitionsSortantes = 0;

        Iterator<DefinitionTransition> transitionsSortantes = coquille.getTransitionsSortantesDe(definitionTache_1);
        while (transitionsSortantes.hasNext()) {
            transitionsSortantes.next();
            numberTransitionsSortantes++;
        }
        assertEquals(2,numberTransitionsSortantes, "Je m'attendais à 2 transitions sortantes de"+definitionTache_1.getNom());

        // 6.   Vérifier que definitionTache_2 et definitionTache_3 sont terminales
        assertTrue(coquille.isTacheFinale(definitionTache_2), "oops! tache "+ definitionTache_2.getNom() + " est supposée être finale.");
        assertTrue(coquille.isTacheFinale(definitionTache_3), "oops! tache "+ definitionTache_3.getNom() + " est supposée être finale.");

        // 7.   Vérifier que definitionTache_1 n'est pas terminale
        assertFalse(coquille.isTacheFinale(definitionTache_1), "oops! tache "+ definitionTache_1.getNom() + " n'est PAS supposée être finale.");
    }

    DemandePret creerDemandePret() {
        DemandeurPret demandeur = fabrique.creerDemandeurPret("Jeanne","Bergeron","123-456-789",120000f,40000f,765);
        Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),400000f);
        return fabrique.creerDemandePret(propriete, demandeur,405000f,30000f);
    }

    @Test
    void testExecuterDefinitionTache() {

        // 1.   Créer définition de tâche
        DefinitionTache definitionTache = fabrique.creerDefinitionTache(nomTache_1, descriptionTache_1, (DemandePret demandePret,Logger log)-> (demandePret.getDemandeurPret().getTauxEndettement() <= 0.37f));

        // 2.   Aller lire le traitement de la définition de la tache
        TraitementTache traitementTache = definitionTache.getTraitementTache();

        // 3.   Aller chercher une demande de prêt, pour le fun
        DemandePret demandePret = this.creerDemandePret();

        // 4.   Exécuter la tâche sur la demande de prêt
        Logger logger = Logger.getLogger("Logger pour tache "+nomTache_1);

        boolean resultat = traitementTache.traiteDemandePret(demandePret,logger);

        // 5.   Tester le résultat
        assertTrue(resultat, "Coudonc! l'emprunteur-se " + demandePret.getDemandeurPret() + " est supposé-e être éligible");
    }

    @Test
    void testExecuterDefinitionTransition() {
                // 1.   Ajouter première tâche
        DefinitionTache definitionTache_1 = fabrique.creerDefinitionTache(nomTache_1, descriptionTache_1);
        coquille.ajouteTache(definitionTache_1);
 
        // 2.   Ajouter deuxième tâche
        DefinitionTache definitionTache_2 = fabrique.creerDefinitionTache(nomTache_2, descriptionTache_2);
        coquille.ajouteTache(definitionTache_2);

        // 3.   Créer et ajouter une transition conditionnelle à ce que definitionTache_1 ait réussi
        ConditionTransition conditionTransition = (InstanceTache instance, DemandePret demandePret) -> demandePret.getResultatTraitement().getResultat() == Resultat.ACCEPTEE;
        DefinitionTransition definitionTransition = fabrique.creerDefinitionTransition(definitionTache_1, definitionTache_2, conditionTransition);

        assertEquals(conditionTransition, definitionTransition.getConditionTransition());

        // 4.   Exécuter la transition sur une demande de prêt fictive
        
        // 4.a  Créer demande de prêt
        DemandePret demandePret = creerDemandePret();
        
        // 4.b  Set son etat de traitement à réussi
        demandePret.setResultatTraitement(fabrique.creerResultatTraitement(Resultat.ACCEPTEE));
        
        // 4.c  va chercher la condition de transition, et l'exécuter
        conditionTransition = definitionTransition.getConditionTransition();
        boolean resultat = conditionTransition.isTransitionOK(null, demandePret);

        // 4.d  Vérifie que c'est OK
        assertTrue(resultat,"Transition de " + definitionTache_1.getNom() + " à " + definitionTache_2 + " est supposée être activée");
    }
}
