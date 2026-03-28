package ca.uqam.mgl7460.tp3.controleurs;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7460.tp3.ressources.types.utils.DefinitionProcessusBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;

@RestController
public class DefinitionProcessusControleur {

	private static final String template = "Ceci est un processus de %s!";
    private static final String PATH="src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/";

    private final Fabrique fabrique= Fabrique.getSingletonFabrique();

    private final DefinitionProcessusBuilder builder = fabrique.getDefinitionProcessusBuilder();

    /**
     * cette méthode permet de créér une coquille de définition de processus, prenant comme argument
     * son name et sa description. Elle retourne l'identificateur de la DefinitionProcessus nouvellement
     * créée.
     * @param name
     * @param description
     * @return
     */
	@GetMapping("/definitionprocessus/creer")
	public String creerDefinitionProcessus(@RequestParam() String name, @RequestParam(defaultValue="") String description) {
        return builder.creerDefinitionProcessus(name, description);
	}

    @GetMapping("/definitionprocessus")
    public DefinitionProcessus getDefinitionProcessusById(@RequestParam() String id) {
        return fabrique.getDefinitionProcessusByID(id);
    }

    @GetMapping("/definitiontache/creer")
    public String creerDefinitionTache(@RequestParam() String name, @RequestParam(defaultValue="") String description, @RequestParam() String nomfichierregles) {
        System.out.println("Je suis dans la méthode creerDefinitionTache("+name+","+description+","+nomfichierregles+")");
        String cheminFichierComplet = PATH + nomfichierregles+".drl";
        String idTache = builder.creerDefinitionTache(name, description, cheminFichierComplet);
        return idTache;
    }

    /**
     * ajoute la tache ayant l'identificateur idtache au processus ayant l'identificateur idprocessus
     * et retourne l'objet DefinitionProcessus après modification
     * @param idprocessus
     * @param idtache
     * @return
     */
    @GetMapping("/definitionprocessus/ajoutertache")
    public DefinitionProcessus ajouteDefinitionTache(@RequestParam() String idprocessus, @RequestParam() String idtache) {
        builder.ajouteTache(idprocessus, idtache);
        return fabrique.getDefinitionProcessusByID(idprocessus);
    }

    /**
     * cette méthode ajoute la tâche ayant comme identificateur idtache  au processus ayant l'identificateur idprocessus
     * et la marque comme étant la première tâche du processus en question. Elle retourne l'objet DefinitionProcessus
     * après modification
     * @param idprocessus
     * @param idtache
     * @return
     */
    @GetMapping("/definitionprocessus/ajoutepremieretache")
    public DefinitionProcessus ajoutePremiereTache(@RequestParam() String idprocessus, @RequestParam() String idtache) {
       builder.ajoutePremiereTache(idprocessus, idtache);
       return fabrique.getDefinitionProcessusByID(idprocessus);
    }

    /**
     * cette méthode ajoute une transition entre taches ayant les identificateurs idtachesource, et idtachedestination, au processus
     * ayant l'identificateur idprocessus.
     * 
     * Elle attribue à cette transition le fichier de règles nomfichierregles. Notez que ce pour simplifier la saisie
     * du nom de fichier, on donne juste le nom de fichier, sans le chemin (dans notre cas :
     * src/main/resources/ca/uqam/mgl7460/tp3/ressources/drools/) ni l'extension (.drl). Donc il faudra rajouter les deux de part et
     * d'autre de nomfichierregles.
     * 
     * La méthode retourne l'objet DefinitionProcessus ainsi modifié
     * @param idprocessus
     * @param idtachesource
     * @param idtachedestination
     * @param nomfichierregles
     * @return
     */
    @GetMapping("/definitionprocessus/ajoutertransition")
    public DefinitionProcessus ajouteDefinitionTransition(@RequestParam() String idprocessus, @RequestParam() String idtachesource, @RequestParam() String idtachedestination, @RequestParam() String nomfichierregles) {
        builder.ajouteTransition(idprocessus, idtachesource, idtachedestination, nomfichierregles);
        return fabrique.getDefinitionProcessusByID(idprocessus);
    }


}
    

