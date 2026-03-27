package ca.uqam.mgl7460.tp3.controleurs;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

@SpringBootTest
@AutoConfigureMockMvc
public class RestServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    @Test
    public void testCreationTache() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/definitiontache/creer?name=Eligibilte%20Emprunteur&description=Tache%20de%20verification%20emprunteur&nomfichierregles=regles_emprunteur").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DEFTAC")));
    }

    @Test
    public void testCreationDefinitionProcessus() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/definitionprocessus/creer?name=Souscription&description=hypothecaire").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DEFPROC")));
    }

    @Test
    public void testCreationDemandeurPret() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/demandeurpret/creer?prenom=Jean&nom=Privat&nas=999-999-999&revenuannuel=130000&obligationsannuelles=35000&cotecredit=780").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DEM")));
    }

    @Test
    public void testCreationPropriete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/propriete/creer?numeroporte=101A&numerorue=201&nomrue=SaintUrbain&ville=montreal&codeprovince=QUEBEC&codepostal=H2T2T3&valeurmarche=400000f").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("PROP")));
    }

    @Test
    public void testCreationDemandePret() throws Exception {
        // d'abord, voir si on une propriété et un demandeur de prêt. Si on les a, on les utilise
        // pour créer une nouvelle demande de prêt, sinon, on les créé à partir de zéro
        String idDemandeur= "DEM1";
        DemandeurPret demandeur = fabrique.getDemandeurPretByID(idDemandeur);
        if (demandeur == null) {
            idDemandeur = fabrique.getDemandePretBuilder().creerDemandeurPret("Poilievre", "Pierre", "666-666-666", 27500f, 530, 3000f);

        }

        String idPropriete = "PROP1";
        Propriete propriete = fabrique.getProprieteByID(idPropriete);
        if (propriete == null) {
            idPropriete = fabrique.getDemandePretBuilder().creerProprieteAvecAdresse("12C", "2100", "Saint Urbain", "Montréal", "QUEBEC", "H2X2X3");
            fabrique.getDemandePretBuilder().setValeurMarcheProprieteWithId(idPropriete, 450000);
        }
        String uriTemplate = "/demandepret/creer?iddemandeurpret="+idDemandeur+"&idpropriete="+idPropriete+"&misedefonds=30000&prixachat=405000";
        mvc.perform(MockMvcRequestBuilders.get(uriTemplate).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DPH")));
    }

    @Test
    public void testExecutionLineaireOuiOuiOui() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=lineaire&typedemandepret=ouiouioui").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ACCEPTEE")));
    }

    @Test
    public void testExecutionLineaireOuiOuiNon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=lineaire&typedemandepret=ouiouinon").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")))
                .andExpect(content().string(containsString("Le ratio emprunt valeur est trop élevé")))
                .andExpect(content().string(containsString("Le taux de mise de fonds est trop faible")));
    }

    @Test
    public void testExecutionComplexeOuiOuiNon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=complexe&typedemandepret=ouiouinon").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")))
                .andExpect(content().string(containsString("Le ratio emprunt valeur est trop élevé")))
                .andExpect(content().string(containsString("Le taux de mise de fonds est trop faible")));
    }

    @Test
    public void testExecutionLineaireOuiNonNon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=lineaire&typedemandepret=ouinonnon").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")))
                .andExpect(content().string(containsString("La propriété est hors Québec")))
                .andExpect(content().string(containsString("Le ratio emprunt valeur est trop élevé")))
                .andExpect(content().string(containsString("Le taux de mise de fonds est trop faible")));
    }

    @Test
    public void testExecutionComplexeOuiNonNon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=complexe&typedemandepret=ouinonnon").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")))
                .andExpect(content().string(containsString("La propriété est hors Québec")));
    }


    @Test
    public void testExecutionLineaireNonNonNon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=lineaire&typedemandepret=nonnonnon").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")));
    }

    @Test
    public void testExecutionComplexeNonNonNon() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=complexe&typedemandepret=nonnonnon").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")));
    }

    @Test
    public void testExecutionComplexeOuiNonOui() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/instanceprocessus/tester?typeprocessus=complexe&typedemandepret=ouinonoui").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("REFUSEE")))
                .andExpect(content().string(containsString("La propriété est hors Québec")));
    }


    @Test
	public void contextLoads() {
	}

}
