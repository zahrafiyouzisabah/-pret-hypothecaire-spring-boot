package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.*;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DemandePretBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class DemandePretBuilderImpl implements DemandePretBuilder {

    private static DemandePretBuilder demandePretBuilder;

    private final Fabrique fabrique = Fabrique.getSingletonFabrique();

    private DemandePretBuilderImpl() {
    }

    @Override
    public String creerProprieteAvecAdresse(String numeroPorte, String numeroRue, String nomRue, String ville, String codeProvince, String codePostal) {
        Propriete propriete = fabrique.creerPropriete(new Adresse(numeroPorte,numeroRue, nomRue, ville, ProvinceOuTerritoire.valueOf(codeProvince), codePostal));

        return propriete.getID();
    }

    @Override
    public float setValeurMarcheProprieteWithId(String idPropriete, float valeurMarche) {
        Propriete propriete = fabrique.getProprieteByID(idPropriete);
        propriete.setValeurDeMarche(valeurMarche);
        return propriete.getValeurDeMarche();
    }

    @Override
    public String creerDemandeurPret(String nom, String prenom, String NAS) {
        DemandeurPret demandeurPret = fabrique.creerDemandeurPret(prenom, nom, NAS );
        return demandeurPret.getID();
    }

    @Override
    public String creerDemandeurPret(String nom, String prenom, String NAS, float revenuAnnuel, int coteCredit, float obligationsAnnuelles) {
        DemandeurPret demandeurPret = fabrique.creerDemandeurPret(prenom, nom, NAS, revenuAnnuel,
        obligationsAnnuelles, coteCredit);

        return demandeurPret.getID();
    }

    @Override
    public int setCoteCreditDemandeurWithId(String idDemamdeur, int coteCredit) {
        DemandeurPret demandeurPret = fabrique.getDemandeurPretByID(idDemamdeur);
        demandeurPret.setScoreCredit(coteCredit);
        return demandeurPret.getScoreCredit();
    }

    @Override
    public float setRevenuAnnuelDemandeurWithId(String idDemandeur, float revenuAnnuel) {
        DemandeurPret demandeurPret = fabrique.getDemandeurPretByID(idDemandeur);
        demandeurPret.setRevenuAnnuel(revenuAnnuel);
        return demandeurPret.getRevenuAnnuel();
    }

    @Override
    public float setObligationsAnnuellesWithId(String idDemandeur, float obligationsAnnuelle) {
        DemandeurPret demandeurPret = fabrique.getDemandeurPretByID(idDemandeur);
        demandeurPret.setObligationsAnnuelles(obligationsAnnuelle);
        return demandeurPret.getObligationsAnnuelles();
    }

    @Override
    public String creerDemandePret(String idDemandeurPret, String idPropriete, float miseDeFonds, float prixAchat) {
        Propriete propriete = fabrique.getProprieteByID(idPropriete);
        DemandeurPret demandeurPret = fabrique.getDemandeurPretByID(idDemandeurPret);

        DemandePret demandePret = fabrique.creerDemandePret(propriete, demandeurPret, prixAchat, miseDeFonds);
        return demandePret.getID();
    }

    @Override
    public float setMiseDeFondsDemandePretWithId(String idDemandePret, float miseDeFonds) {
        DemandePret demandePret = fabrique.getDemandePretByID(idDemandePret);
        demandePret.setMontantMiseDeFonds(miseDeFonds);
        return demandePret.getMontantMiseDeFonds();
    }

    @Override
    public float setPrixAchatDemandePretWithId(String idDemandePret, float prixAchat) {
        DemandePret demandePret = fabrique.getDemandePretByID(idDemandePret);
        demandePret.setPrixAchat(prixAchat);
        return demandePret.getPrixAchat();
    }

    public static DemandePretBuilder getSingleton(){
        if (demandePretBuilder == null) {
            demandePretBuilder = new DemandePretBuilderImpl();
        }
        return demandePretBuilder;
    }
}
