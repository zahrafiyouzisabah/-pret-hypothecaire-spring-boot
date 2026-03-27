package ca.uqam.mgl7460.tp3.ressources.implementations.utils;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.Adresse;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.ProvinceOuTerritoire;
import ca.uqam.mgl7460.tp3.ressources.types.utils.DemandePretBuilder;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class CreationDemandesPretAvecBuilder {

    private static Fabrique fabrique = Fabrique.getSingletonFabrique();

    private static DemandePretBuilder builder = fabrique.getDemandePretBuilder();


    private CreationDemandesPretAvecBuilder() {}

    public static String getDemandePretOuiOuiOui() {

        // DemandeurPret demandeur = fabrique.creerDemandeurPret("Jeanne","Bergeron","123-456-789",120000f,40000f,765);
        String idDemandeur = builder.creerDemandeurPret("Bergeron", "Jeanne", "123-456-789");
        builder.setRevenuAnnuelDemandeurWithId(idDemandeur, 120000f);
        builder.setObligationsAnnuellesWithId(idDemandeur, 40000f);
        builder.setCoteCreditDemandeurWithId(idDemandeur, 765);

        
        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montreal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);


        // return fabrique.creerDemandePret(propriete, demandeur,405000f,30000f);
        String idDemandeOuiOuiOui = builder.creerDemandePret(idDemandeur, idPropriete, 380000f, 405000f);


        return idDemandeOuiOuiOui;

    }

    public static String getDemandePretOuiOuiNon() {

        // DemandeurPret demandeur = fabrique.creerDemandeurPret("Mamadou","Diallo","321-654-987",120000f,40000f,765);
        String idDemandeur=builder.creerDemandeurPret("Diallo", "Mamadou", "321-654-987");
        builder.setRevenuAnnuelDemandeurWithId(idDemandeur, 120000f);
        builder.setObligationsAnnuellesWithId(idDemandeur, 40000f);
        builder.setCoteCreditDemandeurWithId(idDemandeur, 765);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("1504", "1800", "Bleury", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2Y2Y5"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("1504", "1800", "Bleury", "Montréal", "QUEBEC", "H2Y2Y5");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, demandeur,405000f,5000f);
        String idDemandeOuiOuiNon = builder.creerDemandePret(idDemandeur, idPropriete, 5000f, 405000f);


        return idDemandeOuiOuiNon;
    }

    public static String getDemandePretOuiNonNon() {

        // DemandeurPret demandeur = fabrique.creerDemandeurPret("Jorge","Riviera","987-654-123",150000f,50000f,780);
        String idDemandeur = builder.creerDemandeurPret("Riviera", "Jorge", "987-654-123", 150000f, 780,50000f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("1504", "1800", "Bleury", "Calgary", ProvinceOuTerritoire.ALBERTA, "J3H3G5"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("1504", "1800", "Bleury", "Calgary", "ALBERTA", "J3H3G5");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, demandeur,405000f,5000f);
        String idDemandeOuiNonNon = builder.creerDemandePret(idDemandeur, idPropriete, 5000f, 405000f);

        return idDemandeOuiNonNon;
    }

    public static String getDemandePretNonNonNon() {

        // DemandeurPret trump = fabrique.creerDemandeurPret("Donald","Trump", "666-666-666",600000f,83000000f,520);
        String idDemandeur = builder.creerDemandeurPret("Trump", "Donald", "666-666-666", 600000f, 520, 83000000f);

        // Propriete trumpTower = fabrique.creerPropriete(new Adresse("", "325", "Bay Street", "Toronto", ProvinceOuTerritoire.ONTARIO, "M5H 4G3"),100000f);
        String idPropriete = builder.creerProprieteAvecAdresse("", "325", "Bay Street", "Toronto", "ONTARIO", "M5H 4G3");
        builder.setValeurMarcheProprieteWithId(idPropriete, 100000f);

        // return fabrique.creerDemandePret(trumpTower, trump,45000000f,5000f);
        String idDemandeNonNonNon = builder.creerDemandePret(idDemandeur, idPropriete, 5000f, 45000000f);

        return idDemandeNonNonNon;
    }

    public static String getDemandePretOuiNonOui() {

        // DemandeurPret legault = fabrique.creerDemandeurPret("François","Legault", "555-555-555",275000,75000f,765);
        String idDemandeur = builder.creerDemandeurPret("Legault","François", "555-555-555 ", 275000f, 765, 75000f);

        // Propriete siegeNorthVolt = fabrique.creerPropriete(new Adresse("", "20", "Alströmergatan", "Stockholm", ProvinceOuTerritoire.NEWFOUNDLAND_AND_LABRADOR, "M5H 4G3"),480000000f);
        String idPropriete = builder.creerProprieteAvecAdresse("", "20", "Alströmergatan", "Stockholm", "NEWFOUNDLAND_AND_LABRADOR", "M5H 4G3");
        builder.setValeurMarcheProprieteWithId(idPropriete, 480000000f);

        String idDemandeOuiNonOui = builder.creerDemandePret(idDemandeur, idPropriete, 275000000f, 450000000f);

        return idDemandeOuiNonOui;
    }

    public static String getDemandeRevenuTropFaible() {

        // DemandeurPret medecin = fabrique.creerDemandeurPret("Jean","Medecin", "123-456-789",27500,7500f,765);
        String idDemandeurPret = builder.creerDemandeurPret("Medecin", "Jean", "123-456-789",27500f,765,7500f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, medecin,405000f,30000f);
        String idDemandeRevenuTropFaible = builder.creerDemandePret(idDemandeurPret, idPropriete, 30000f, 405000f);

        return idDemandeRevenuTropFaible;
    }

    public static String getDemandeScoreCreditTropFaible() {

        // DemandeurPret mauvaisPayeur = fabrique.creerDemandeurPret("Jean","Medecin", "123-456-789",75000,7500f,625);
        String idDemandeur = builder.creerDemandeurPret("Mdecin", "Jean", "123-456-789", 75000f, 625, 7500f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, mauvaisPayeur,405000f,30000f);
        String idDemandeScoreCreditTropFaible = builder.creerDemandePret(idDemandeur, idPropriete, 30000f, 405000f);

        return idDemandeScoreCreditTropFaible;
    }

    public static String getDemandeTauxEndettementTropEleve() {

        // DemandeurPret mauvaisPayeur = fabrique.creerDemandeurPret("Jean","Medecin", "123-456-789",100000,50000f,725);        
        String idDemandeur = builder.creerDemandeurPret("Medecin", "Jean", "123-456-789", 100000f, 725, 50000f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, mauvaisPayeur,405000f,30000f);
        String idDemandeTauxEndettementTropEleve = builder.creerDemandePret(idDemandeur, idPropriete, 30000f, 405000f);

        return idDemandeTauxEndettementTropEleve;
    }

    public static String getDemandeProprieteHorsQuebec() {

        // DemandeurPret legault = fabrique.creerDemandeurPret("François","Legault", "555-555-555",275000,75000f,765);
        String idDemandeur = builder.creerDemandeurPret("Legault", "François", "555-555-555", 275000f, 765, 75000);

        // Propriete siegeNorthVolt = fabrique.creerPropriete(new Adresse("", "20", "Alströmergatan", "Stockholm", ProvinceOuTerritoire.NEWFOUNDLAND_AND_LABRADOR, "M5H 4G3"),480000000f);
        String idPropriete = builder.creerProprieteAvecAdresse("", "20", "Alströmergatan", "Stockholm", "NEWFOUNDLAND_AND_LABRADOR", "M5H 4G3");
        builder.setValeurMarcheProprieteWithId(idPropriete, 480000000f);

        //  return fabrique.creerDemandePret(siegeNorthVolt, legault,450000000f,275000000f);
        String idDemandeProprieteHorsQuebec =  builder.creerDemandePret(idDemandeur, idPropriete, 275000000f, 450000000f);

        return idDemandeProprieteHorsQuebec;
    }

    public static String getDemandeMiseDeFondsTropFaible() {

        //  DemandeurPret partyAnimal = fabrique.creerDemandeurPret("Bob","Leponge", "123-456-789",100000,35000f,725);
        String idDemandeur = builder.creerDemandeurPret("Leponge", "Bob", "123-456-789", 100000f, 725, 35000f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),440000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 440000f);

       // return fabrique.creerDemandePret(propriete, partyAnimal,405000f,3000f);
       String idDemandeMiseDeFondsTropFaible = builder.creerDemandePret(idDemandeur, idPropriete, 3000f, 405000f);

       return idDemandeMiseDeFondsTropFaible;
    }

    public static String getDemandeRatioEmpruntValeurMarcheTropEleve() {

        // DemandeurPret cestFaitAvoir = fabrique.creerDemandeurPret("Jean","Naif", "123-456-789",100000,35000f,725);
        String idEmprunteur = builder.creerDemandeurPret("Naif", "Jean", "123-456-789", 100000f, 725,35000f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),380000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 380000f);
        
        // return fabrique.creerDemandePret(propriete, cestFaitAvoir,400000f,30000f);
        String idDemandeRatioEmpruntValeurMarcheTropEleve = builder.creerDemandePret(idEmprunteur, idPropriete, 30000f, 400000f);

        return idDemandeRatioEmpruntValeurMarcheTropEleve;
    }

    public static String getDemandeRevenuCoteCreditFaibles() {

        // DemandeurPret pasFort = fabrique.creerDemandeurPret("Pierre","Poilievre", "123-456-789",25000,5000f,625);
        String idEmprunteur = builder.creerDemandeurPret("Poilièvre", "Pierre", "123-456-789",25000f, 625,5000f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, pasFort,405000f,30000f);
        String idDemandeRevenuCoteCreditFaibles = builder.creerDemandePret(idEmprunteur, idPropriete, 30000f, 405000f);

        return idDemandeRevenuCoteCreditFaibles;
    }

    public static String getDemandeCoteCreditMiseDefondsFaibles() {

        // DemandeurPret depensier = fabrique.creerDemandeurPret("Pierre","Poilievre", "123-456-789",125000,35000f,625);
        String idEmprunteur = builder.creerDemandeurPret("Poilièvre", "Pierre", "123-456-789", 125000f,625,35000f);

        // Propriete propriete = fabrique.creerPropriete(new Adresse("201A", "2100", "Saint-Urbain", "Montréal", ProvinceOuTerritoire.QUEBEC, "H2X2XH"),400000f);
        String idPropriete = builder.creerProprieteAvecAdresse("201A", "2100", "Saint-Urbain", "Montréal", "QUEBEC", "H2X2XH");
        builder.setValeurMarcheProprieteWithId(idPropriete, 400000f);

        // return fabrique.creerDemandePret(propriete, depensier,395000f,15000f);
        String idDemandeCoteCreditMiseDefondsFaibles =  builder.creerDemandePret(idEmprunteur, idPropriete, 15000f, 395000f);

        return idDemandeCoteCreditMiseDefondsFaibles;
    }

}
