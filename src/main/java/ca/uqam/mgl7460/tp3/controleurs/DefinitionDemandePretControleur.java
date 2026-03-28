package ca.uqam.mgl7460.tp3.controleurs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7460.tp3.ressources.types.utils.DemandePretBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;

@RestController
public class DefinitionDemandePretControleur {

    private final Fabrique fabrique= Fabrique.getSingletonFabrique();

    private final DemandePretBuilder demandeBuilder = fabrique.getDemandePretBuilder();

    /**
     * cette méthode/route crée un-e demandeur-se de prêt, en prenant en paramètre toutes les données nécessaires.
     * 
     * Elle retourne l'identification du nouvel objet crée (un client REST qui invoque cette méthode va récupérer 
     * l'identificateur pour y faire référence plus tard, par exemple l'associer à une demande de pret )
     * @param prenom
     * @param nom
     * @param nas
     * @param revenuannuel
     * @param obligationsannuelles
     * @param cotecredit
     * @return
     */
	@GetMapping("/demandeurpret/creer")
	public String creerDemandeurPret(@RequestParam() String prenom, @RequestParam() String nom,@RequestParam(defaultValue="999-999-999") String nas, @RequestParam() float revenuannuel,@RequestParam() float obligationsannuelles, @RequestParam() int cotecredit ) {
        return demandeBuilder.creerDemandeurPret(nom, prenom, nas, revenuannuel, cotecredit, obligationsannuelles);
    }        

    /**
     * cette méthode retourne l'objet DemandeurPret ayant comme identificateir id
     * @param id
     * @return
     */
	@GetMapping("/demandeurpret/")
	public DemandeurPret getDemandeurPret(@RequestParam() String id) {
        return fabrique.getDemandeurPretByID(id);
	}

    /**
     * cette méthode crée une propriété, avec toutes les données nécessaire. Notez que pour la province, on utilise
     * la chaine de caractères correspond au nom de la valeur de l'énumération correspondante. Par exemple, pour le
     * Québec, à défaut de passer la valeur de l'énumération ProvinceOuTerritoire.QUEBEC, on passera la chaine de
     * caractères "QUEBEC". À vous de "remonter" de cette chaine de caractères à la valeur correspondante
     * de l'énumération (c'est normalement fait dans l'implementation de DemandePretBuilder).
     * 
     * La méthode retourne l'dentificateur de la nouvelle propriété
     * @param numeroporte
     * @param numerorue
     * @param nomrue
     * @param ville
     * @param codeprovince
     * @param codepostal
     * @param valeurmarche
     * @return
     */
    @GetMapping("/propriete/creer")
    public String creerProprieteAvecAdresse(@RequestParam() String numeroporte, @RequestParam() String numerorue, @RequestParam() String nomrue, 
                                            @RequestParam() String ville, @RequestParam() String codeprovince, @RequestParam() String codepostal,
                                             @RequestParam() float valeurmarche){       
        String proprieteID = demandeBuilder.creerProprieteAvecAdresse(numeroporte,numerorue, nomrue,ville,codeprovince,codepostal);
        demandeBuilder.setValeurMarcheProprieteWithId(proprieteID, valeurmarche);
        return proprieteID;
    }

    /**
     * cette méthode retourne l'objet Propriete correspondant à l'identificateur id
     * @param id
     * @return
     */
    @GetMapping("/propriete/")
    public Propriete getPropriete(@RequestParam() String id) {
        return fabrique.getProprieteByID(id);
    }

    /**
     * Cette méthode crée une demande de pret hypothècaire, à partir de l'identificateur de la/du demandeur-se 
     * (iddemandeurpret), de l'identificateur de la propriété (idpropriete), du montant de misedefonds, et du
     * prixachat. Elle retourne l'identificateur de la demande de prêt nouvellement créée.
     * @param iddemandeurpret
     * @param idpropriete
     * @param misedefonds
     * @param prixachat
     * @return
     */
    @GetMapping("/demandepret/creer")
    public String creerDemandePret(@RequestParam() String iddemandeurpret,@RequestParam() String idpropriete,@RequestParam() float misedefonds, @RequestParam() float prixachat) {
        System.out.println(String.format("Je suis dans la méthode creerDemandePret(id prop: %s, id dem: %s, mise de fonds: %f, prix achat: %f)",iddemandeurpret,idpropriete,misedefonds, prixachat));
        return demandeBuilder.creerDemandePret(iddemandeurpret, idpropriete, misedefonds, prixachat);

    }

    /**
     * cette méthode retourne la demande de pret (objet DemandePret) dont l'identificateur (id)
     * est passé en argument
     * @param id
     * @return
     */
    @GetMapping("/demandepret/")
    public DemandePret getDemandePret(@RequestParam() String id) {
        System.out.println(String.format("Je suis dans la méthode getDemandePret(id : %s)",id));
        return fabrique.getDemandePretByID(id);
    }
}
    

