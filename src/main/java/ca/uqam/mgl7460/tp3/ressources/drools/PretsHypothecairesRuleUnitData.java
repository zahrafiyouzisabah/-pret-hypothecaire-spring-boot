package ca.uqam.mgl7460.tp3.ressources.drools;

import java.util.HashSet;
import java.util.Set;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;

public class PretsHypothecairesRuleUnitData implements RuleUnitData {
    private final DataStore<DemandePret> demandesPrets;

    private final Set<String> controlSet = new HashSet<>();

    public PretsHypothecairesRuleUnitData() {
        this(DataSource.createStore());
    }

    public PretsHypothecairesRuleUnitData(DataStore<DemandePret> demandesPrets) {
        this.demandesPrets = demandesPrets;
    }

    public DataStore<DemandePret> getDemandesPrets() {
        return demandesPrets;
    }

    public Set<String> getControlSet() {
        return controlSet;
    }    
}
