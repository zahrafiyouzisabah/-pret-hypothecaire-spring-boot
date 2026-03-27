package ca.uqam.mgl7460.tp3.ressources.implementations.domaine;

import java.time.Instant;
import java.util.UUID;

import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandePret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.DemandeurPret;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.ResultatTraitement;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Propriete;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.Resultat;
import ca.uqam.mgl7460.tp3.ressources.types.domaine.TermesPret;
import ca.uqam.mgl7460.tp3.ressources.types.utils.Fabrique;

public class DemandePretImpl implements DemandePret {

    private String ID;

    private Instant dateDemande;

    private DemandeurPret demandeurPret;

    private Propriete propriete;

    private float montantMiseDeFonds;

    private float prixAchat;

    private TermesPret termesPret;

    private ResultatTraitement resultatTraitement;

    private String numeroDemande;

    private static int numeroSequence = 0;

    private static String prochainNumeroDemande() {
        numeroSequence++;
        return "DPH" + numeroSequence;
    }

    public DemandePretImpl(DemandeurPret demandeur, Propriete propriete){
        this.ID = UUID.randomUUID().toString();
        this.dateDemande = Instant.now();
        this.numeroDemande = prochainNumeroDemande();
        this.demandeurPret = demandeur;
        this.propriete = propriete;
        this.resultatTraitement = Fabrique.getSingletonFabrique().creerResultatTraitement(Resultat.NONDETERMINE);
    }

    public DemandePretImpl(DemandeurPret demandeur, Propriete propriete, float prixAchat, float montantMiseDeFonds){
        this(demandeur,propriete);
        this.montantMiseDeFonds = montantMiseDeFonds;
        this.prixAchat = prixAchat;
    }

    @Override
    public Instant getDateDemande() {
        return dateDemande;
    }

    @Override
    public DemandeurPret getDemandeurPret() {
        return demandeurPret;
    }

    @Override
    public Propriete getPropriete() {
        return propriete;
    }

    @Override
    public float getMontantPret() {
        return prixAchat - montantMiseDeFonds;
    }

    @Override
    public float getMontantMiseDeFonds() {
        return montantMiseDeFonds;
    }

    @Override
    public void setMontantMiseDeFonds(float montant) {
        montantMiseDeFonds = montant;
    }

    @Override
    public ResultatTraitement getResultatTraitement() {
        return resultatTraitement;
    }

    @Override
    public void setResultatTraitement(ResultatTraitement resultat) {
        resultatTraitement = resultat;
    }

    @Override
    public float getRatioEmpruntValeur() {
        return (prixAchat - montantMiseDeFonds)/propriete.getValeurDeMarche();
    }

    @Override
    public TermesPret getTermesPret() {
        return termesPret;
    }

    @Override
    public void setTermesPret(TermesPret terms) {
        termesPret = terms;
    }

    @Override
    public float getPrixAchat() {
        return prixAchat;
    }

    @Override
    public void setPrixAchat(float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Demande Pret Hypothecaire [Numéro: " + numeroDemande + ", Date: " + dateDemande +", ");
        builder.append("Prix achat: "+prixAchat).append(", Mise de fonds: "+montantMiseDeFonds+"\n\t");
        builder.append(demandeurPret+"\n\t");
        builder.append(propriete + "\n]");
        return builder.toString();
    }

    @Override
    public String getNumeroDemande() {
        return numeroDemande;
    }

    @Override
    public String getID() {
        return this.ID;
    }
}
