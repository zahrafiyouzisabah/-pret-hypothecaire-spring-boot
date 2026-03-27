package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.processus.definitions.DefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.ExceptionDefinitionProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.processus.instances.InstanceProcessus;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;
import ca.uqam.mgl7460.tp3.ressources.types.utils.InstanceProcessusLauncher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceProcessusLauncherImpl implements InstanceProcessusLauncher {

    private static InstanceProcessusLauncher instanceProcessusLauncher;
    private final Fabrique fabrique = Fabrique.getSingletonFabrique();

    private InstanceProcessusLauncherImpl(){}

    @Override
    public String creerInstanceProcessus(String idDefinitionProcessus, String idDemandePret) {
        DefinitionProcessus definitionProcessus = fabrique.getDefinitionProcessusByID(idDefinitionProcessus);
        DemandePret demandePret = fabrique.getDemandePretByID(idDemandePret);
        InstanceProcessus instanceProcessus = fabrique.creerInstanceProcessus(definitionProcessus, demandePret);
        return instanceProcessus.getID();
    }

    @Override
    public String lancerInstanceProcessusAvecId(String idInstanceProcessus) {
        InstanceProcessus instanceProcessus = fabrique.getInstanceProcessusByID(idInstanceProcessus);
        try {
            instanceProcessus.demarrer();
            return buildInstanceProcessusResultJson(instanceProcessus);
        } catch (ExceptionDefinitionProcessus e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    private String buildInstanceProcessusResultJson(InstanceProcessus instanceProcessus) throws JsonProcessingException {

        Map<String, Object> root = new HashMap<>();

        root.put("etatProcessus",
                instanceProcessus.getEtatProcessus().etatTraitement());

        Map<String, Object> resultatTraitement = new HashMap<>();
        resultatTraitement.put("resultat",
                instanceProcessus.getDemandePret()
                        .getResultatTraitement()
                        .getResultat());

        List<String> messages = new ArrayList<>();
        instanceProcessus.getDemandePret()
                .getResultatTraitement()
                .getMessages().forEachRemaining(messages::add);

        resultatTraitement.put("messages",messages);

        root.put("resultatTraitement", resultatTraitement);

        // Convert to JSON string and return it
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(root);

    }

    public static InstanceProcessusLauncher getSingleton(){
        if (instanceProcessusLauncher == null) instanceProcessusLauncher = new InstanceProcessusLauncherImpl();
        return instanceProcessusLauncher;
    }
}
