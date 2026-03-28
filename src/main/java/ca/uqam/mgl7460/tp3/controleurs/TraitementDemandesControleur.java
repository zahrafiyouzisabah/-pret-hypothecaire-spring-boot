package ca.uqam.mgl7460.tp3.controleurs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ModeExecution;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;
import ca.uqam.mgl7460.tp3.ressources.types.utils.InstanceProcessusLauncher;
import ca.uqam.mgl7460.tp3.ressources.implementations.utils.CreationDefinitionsProcessusAvecReglesEtIds;
import ca.uqam.mgl7460.tp3.ressources.implementations.utils.CreationDemandesPretAvecBuilder;


@RestController
public class TraitementDemandesControleur {

    private final Fabrique fabrique= Fabrique.getSingletonFabrique();

    private final InstanceProcessusLauncher lanceurProcessus = fabrique.getInstanceProcessusLauncher();

    /**
     * cette méthode créer une instance de processus, en prenant comme argument l'identificateur de la
     * DefinitionProcessus (iddefinitionprocessus) et l'identificateur de la demande de prêt
     * (iddemandepret). Elle retourne l'identificateur de la nouvelle instance de processus,
     * permettant à un appel appel REST de lancer l'exécution de l'instance de processus identifiée par
     * son identificateur
     * @param iddefinitionprocessus
     * @param iddemandepret
     * @return
     */
	@GetMapping("/instanceprocessus/creer")
	public String creerInstanceProcessus(@RequestParam() String iddefinitionprocessus, @RequestParam() String iddemandepret ) {
        return lanceurProcessus.creerInstanceProcessus(iddefinitionprocessus, iddemandepret);
	}

    /**
     * Cette méthode lance l'exécution de l'instance de processus identifiée par le paramètre id.
     * 
     * Elle retourne le résultat de l'exécution qui est une chaine de caractère regroupement l'état d'exécution de l'instance de
     * processus (normalement, TERMINEE), et le resultat de traitement de la demande de prêt. Pour avoir le format exact,
     * consulter la documentation de la méthode InstanceProcessusLauncher.lancerInstanceProcessusAvecId(String idInstanceProcessus)
     * @param id
     * @return
     */
	@GetMapping("/instanceprocessus/lancer")
	public String lancerInstanceProcessus(@RequestParam() String id) {
        return lanceurProcessus.lancerInstanceProcessusAvecId(id);
	}

    /**
     * Cette "route"/méthode est une méthode de commodité nous permettant de tester des définitions de processus
     * existances (linéaire et complexe) sur certaines données que nous avons déjà utilisées pour les tests.
     * 
     * Elle nous permet de tester la "RESTification" de l'application, sans avoir à exécuter de longs scripts pour 
     * la création de processus et de demandes de prêt "à la mitaine"
     * @param typeprocessus
     * @param typedemandepret
     * @return
     */
    @GetMapping("/instanceprocessus/tester")
	public String testerInstanceProcessus(@RequestParam() String typeprocessus, @RequestParam() String typedemandepret ) {
        String idProcessus = null;
        String idDemandePret = null;
        switch (typeprocessus) {
            case "lineaire":
                idProcessus = CreationDefinitionsProcessusAvecReglesEtIds.getProcessusLineaireAvecRegles();               
                break;
        
            case "complexe":
                idProcessus = CreationDefinitionsProcessusAvecReglesEtIds.getProcessusComplexeAvecRegles();
                break;

            case "errone":
                idProcessus = CreationDefinitionsProcessusAvecReglesEtIds.getProcessusErrone();
                break;

            default:
                System.out.println(String.format("Il n'y a pas de processus de type %s",typeprocessus));
                return "Type processus non-existant";
        };


        switch (typedemandepret) {
            case "ouiouioui":
                idDemandePret = CreationDemandesPretAvecBuilder.getDemandePretOuiOuiOui();
                break;
            case "ouiouinon":
                idDemandePret = CreationDemandesPretAvecBuilder.getDemandePretOuiOuiNon();
                break;
            case "ouinonnon":
                idDemandePret = CreationDemandesPretAvecBuilder.getDemandePretOuiNonNon();
                break;
            case "nonnonnon":
                idDemandePret = CreationDemandesPretAvecBuilder.getDemandePretNonNonNon();
                break;
            case "ouinonoui":
                idDemandePret = CreationDemandesPretAvecBuilder.getDemandePretOuiNonOui();
                break;
            default:
                System.out.println(String.format("Il n'y a pas de demande de pret de type %s",typedemandepret));
                return "Type demande de pret non-existant";

        }
        String idInstanceProcessus = lanceurProcessus.creerInstanceProcessus(idProcessus, idDemandePret);
        // choisis le mode d'exécution par règles
        fabrique.getInstanceProcessusByID(idInstanceProcessus).setModeExecution(ModeExecution.REGLES_DROOLS);
        // initialise l'état de la demande à ACCEPTÉE, vu la façon dont les règles ont été codées
        fabrique.getDemandePretByID(idDemandePret).getResultatTraitement().setResultat(Resultat.ACCEPTEE);

        return lanceurProcessus.lancerInstanceProcessusAvecId(idInstanceProcessus);
	}
}
    

