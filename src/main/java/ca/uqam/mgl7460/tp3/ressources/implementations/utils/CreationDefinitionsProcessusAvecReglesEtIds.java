package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionTache;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DefinitionProcessusBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;


public class CreationDefinitionsProcessusAvecReglesEtIds {
        
    private static DefinitionProcessus processusComplexe;

    private static DefinitionProcessus processusErrone;

    private static DefinitionProcessus processusLineaire;

    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private static DefinitionProcessusBuilder builder = fabrique.getDefinitionProcessusBuilder();

    private static String nomFichierReglesTransitionSiAcceptee = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_si_acceptee.drl";

    private static String nomFichierReglesTransitionSiRefusee = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_si_refusee.drl";

    private static String nomFichierReglesTransitionTrue = "src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/regles_trans_true.drl";


    private CreationDefinitionsProcessusAvecReglesEtIds() {}

    public static String getProcessusErrone() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusErrone != null) return processusErrone.getID();

        // 1.   Création de coquille
        String idProcessusErrone = builder.creerDefinitionProcessus("Processus erroné avec règles", "Processus d'éeligibilité contenant deux transitions sortantes sans condition de la même tâche");

        // 2.   Ajoute de première tache qui est la vérification de l'éligibilité de l'emprunteur
        String idTacheEligibiliteEmprunteur = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibiliteEmprunteur();
        builder.ajoutePremiereTache(idProcessusErrone, idTacheEligibiliteEmprunteur);

        // 3.   Ajouter vérification de propriété
        // 3.a.     Ajouter la tache
        String idTacheEligibilitePropriete = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibilitePropriete();
        builder.ajouteTache(idProcessusErrone,idTacheEligibilitePropriete);
        
        // 3.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePropriete
            // la transition est automatique, sans condition
        builder.ajouteTransition(idProcessusErrone, idTacheEligibiliteEmprunteur, idTacheEligibilitePropriete, nomFichierReglesTransitionTrue);

        // 4.   Ajouter vérification de prêt
        // 4.a.     Ajouter la tâche
        String idTacheEligibilitePret = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibilitePret();
        builder.ajouteTache(idProcessusErrone, idTacheEligibilitePret);

        // 4.b.     Ajouter la transition entre eligibiliteEmprunteur et eligibilitePret
            // on va utiliser la même condition, qui n'en est pas une
        builder.ajouteTransition(idProcessusErrone, idTacheEligibiliteEmprunteur, idTacheEligibilitePret,nomFichierReglesTransitionTrue);

        return idProcessusErrone;
    }



    public static String getProcessusComplexeAvecRegles() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusComplexe != null) return processusComplexe.getID();

        String idProcessusComplexe = builder.creerDefinitionProcessus("Mortgage Underwriting Process","Processus de souscription hypothécaire");
        String idTacheEmprunteur= CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibiliteEmprunteur();
        String idTachePropriete = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibilitePropriete();
        String idTachePret = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibilitePret();
        String idTacheAffichageErreur = CreationDefinitionsTachesAvecReglesEtIds.getTacheAffichageErreur();
        String idTacheAffichageSucces = CreationDefinitionsTachesAvecReglesEtIds.getTacheAcceptation();

        // 1. Vérifier la première tâche
        builder.ajoutePremiereTache(idProcessusComplexe,idTacheEmprunteur);
    

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
        assert ids.equals(idsOfProcessTasks);

        // 4.   Ajouter transitions
        
        //  4.a Ajouter transition <Eligibilité emprunter --> Eligibilité propriété> avec des IDs
        builder.ajouteTransition(idProcessusComplexe, idTacheEmprunteur,idTachePropriete,nomFichierReglesTransitionSiAcceptee);
        
        //  4.b Ajouter transition <Eligibilité propriété --> Eligibilité pret>
        builder.ajouteTransition(idProcessusComplexe, idTachePropriete, idTachePret, nomFichierReglesTransitionSiAcceptee);

        //  4.c Ajouter transition <Eligibilité prêt --> Tavhe acceptation>
        builder.ajouteTransition(idProcessusComplexe, idTachePret, idTacheAffichageSucces, nomFichierReglesTransitionSiAcceptee);

        //  4.d Ajouter transition <Eligibilité emprunteur --> Tache erreur>
        builder.ajouteTransition(idProcessusComplexe, idTacheEmprunteur, idTacheAffichageErreur, nomFichierReglesTransitionSiRefusee);

        //  4.e Ajouter transition <Eligibilité propriété --> tache erreur>
        builder.ajouteTransition(idProcessusComplexe, idTachePropriete, idTacheAffichageErreur, nomFichierReglesTransitionSiRefusee);
        
        //  4.f Ajouter transition <Eligibilité pret --> Tache erreur
        builder.ajouteTransition(idProcessusComplexe, idTachePret, idTacheAffichageErreur, nomFichierReglesTransitionSiRefusee);

        return idProcessusComplexe;
    }

    public static String getProcessusLineaireAvecRegles() {
        // 0.   Il a déjà été. initialisé, retourne le
        if (processusLineaire != null) return processusLineaire.getID();

        String idProcessusLineaire = builder.creerDefinitionProcessus("Mortgage Underwriting Process","Processus de souscription hypothécaire");
        processusLineaire = fabrique.getDefinitionProcessusByID(idProcessusLineaire);

        String idTacheEmprunteur= CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibiliteEmprunteur();
        String idTachePropriete = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibilitePropriete();
        String idTachePret = CreationDefinitionsTachesAvecReglesEtIds.getTacheEligibilitePret();


        // 1. Vérifier la première tâche
        builder.ajoutePremiereTache(idProcessusLineaire,idTacheEmprunteur);
        assert idTacheEmprunteur.equals(processusLineaire.getPremiereTache().getID());

        // 2. Ajouter les deux autres tâches
        builder.ajouteTache(idProcessusLineaire,idTachePropriete);
        builder.ajouteTache(idProcessusLineaire,idTachePret);

        // 3. Vérifier que toutes les tâches ont été ajoutées
        Iterator<DefinitionTache> tachesDuProcessus = processusLineaire.getTaches();
        HashSet<String> ids = new HashSet<>(Arrays.asList(idTachePret,idTacheEmprunteur,idTachePropriete));
        HashSet<String> idsOfProcessTasks = new HashSet<>();
        while (tachesDuProcessus.hasNext()) idsOfProcessTasks.add(tachesDuProcessus.next().getID());
        assert ids.equals(idsOfProcessTasks);

        // 4.   Ajouter transitions

        //  4.a Ajouter transition <Eligibilite emprunteur --> Eligibilite propriete>
        builder.ajouteTransition(idProcessusLineaire, idTacheEmprunteur,idTachePropriete,nomFichierReglesTransitionTrue);

        //  4.b Ajouter transition <Eligiilite propriété --> Eligibilite pret>
        builder.ajouteTransition(idProcessusLineaire,idTachePropriete,idTachePret,nomFichierReglesTransitionTrue);

        return processusLineaire.getID();
    }


}
