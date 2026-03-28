package ca.uqam.mgl7460.tp3.ressources.implementations.domaine;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;

import java.util.UUID;

public class DemandeurPretImpl implements DemandeurPret{

    private String ID;
    private static int compteur = 0;

    private String prenom;

    private String nom;

    private String nas;

    private float revenuAnnuel;

    private float obligationsAnnuelles;

    private int scoreCredit;

    public DemandeurPretImpl(String prenom, String nom, String nas) {
        this.ID = prochaineID();
        this.prenom = prenom;
        this.nom = nom;
        this.nas = nas;
    }

    private String prochaineID(){
        return "DEM" + (++compteur);
    }


    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getPrenom() {
        return prenom;
    }

    @Override
    public String getNas() {
        return nas;
    }

    @Override
    public float getRevenuAnnuel() {
        return revenuAnnuel;
    }

    @Override
    public void setRevenuAnnuel(float revenu) {
        this.revenuAnnuel = revenu;
    }

    @Override
    public float getObligationsAnnuelles() {
        return obligationsAnnuelles;
    }

    @Override
    public void setObligationsAnnuelles(float obligations) {
        obligationsAnnuelles = obligations;
    }

    @Override
    public float getTauxEndettement() {
        return obligationsAnnuelles/revenuAnnuel;
    }

    @Override
    public int getScoreCredit() {
        return scoreCredit;
    }

    @Override
    public void setScoreCredit(int score) {
        this.scoreCredit = score;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("DEMANDEUR[");
        builder.append(nom + ", " + prenom).append(", NAS: " + nas);
        builder.append(", Revenu annuel: "+revenuAnnuel);
        builder.append(", Total obligations ann: "+obligationsAnnuelles);
        builder.append(", Score credit: "+scoreCredit).append("]");
        return builder.toString();
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
