package ca.uqam.mgl7460.tp3.ressources.implementations.domaine;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.Adresse;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;

import java.util.UUID;

public class ProprieteImpl implements Propriete{

    private String ID;
    private static int compteur = 0;

    private Adresse adresse;

    private float valeurDeMarche;

    public ProprieteImpl(Adresse adresse){
        this.ID = prochaineID();
        this.adresse = adresse;
    }

    public ProprieteImpl(Adresse adresse, float valeur){
        this(adresse);
        valeurDeMarche = valeur;
    }

    private String prochaineID(){
        return "PROP" + (++compteur);
    }

    @Override
    public Adresse getAdresse() {
       return adresse;
    }

    @Override
    public float getValeurDeMarche() {
        return valeurDeMarche;
    }

    @Override
    public void setValeurDeMarche(float valeur) {
        valeurDeMarche = valeur;
    }  
    
    public String toString() {
        StringBuilder builder = new StringBuilder("PROPRIETE[Située à: " + adresse);
        builder.append(", Valeur marché : "+valeurDeMarche + "]");
        return builder.toString();
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
