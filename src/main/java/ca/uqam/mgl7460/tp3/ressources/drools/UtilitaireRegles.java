package ca.uqam.mgl7460.tp3.ressources.drools;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

/**
 * classes qui contient les seuils utilisés dans les règles
 * SonarQube n'a pas aimé que j'utilise une interface
 * ne contenant que des constantes
 */
public class UtilitaireRegles {

    private UtilitaireRegles() {}

    public static final float SALAIRE_MINIMAL = 35000f;
    public static final String SALAIRE_TROP_FAIBLE = "Le salaire annuel est trop faible";

    public static final int SCORE_CREDIT_MINIMAL = 700;
    public static final String SCORE_CREDIT_TROP_FAIBLE = "Le score de credit est trop faible";

    public static final float SEUIL_TAUX_ENDETTEMENT = 0.37f;
    public static final String TAUX_ENDETTEMENT_TROP_ELEVE = "Le taux d'endettement est trop élevé";

    public static final String PROPRIETE_HORS_QUEBEC = "La propriété est hors Québec";

    public static final float MAX_RATIO_EMPRUNT_VALEUR = 0.95f;
    public static final String RATIO_EMPRUNT_VALEUR_TROP_ELEVE = "Le ratio emprunt valeur est trop élevé";

    public static final float MIN_POURCENTAGE_MISE_FONDS = 0.05f;
    public static final String TAUX_MISE_FONDS_TROP_FAIBLE = "Le taux de mise de fonds est trop faible";

    private static Map<String,KieContainer> rulesets = new HashMap<>();

    public static KieSession getKieSessionPourFichier(String nomFichierRegles){

        // 1.   Si on a dejà un KieContainer pour ce fichier de
        //      règles, pour lui demander de créér une KieSession
        KieContainer kieContainer = rulesets.get(nomFichierRegles);

        if (kieContainer == null) {
            // 2.   Sinon, en créer un
            // 2.1  D'abord, créer un KieServices

            KieServices kieServices = KieServices.Factory.get();

            // 2.2  Ensuite, créer un nouveau KieFileSystem
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

            // 2.3. Ajouter le fichier de règles comme ressource
            kieFileSystem.write(ResourceFactory.newFileResource(nomFichierRegles));

            // 2.4. Construire le KieModule
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();

            // 2.5. Vérifier les erreurs de compilation
            if (kieBuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
                throw new RuntimeException("Build Errors:\n" + kieBuilder.getResults().toString());
            }

            // 2.6. Pas d'erreurs, alors obtenir un KieModule pour créer un KieContainer
            KieModule kieModule = kieBuilder.getKieModule();
            kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

            // 2.7. L'insérer dans rulesets
            rulesets.put(nomFichierRegles, kieContainer);
        }

        // 3.   Demander au KieContainer de nous créer une KieSession, et
        //      la retourner
        return kieContainer.newKieSession();
    }
}
