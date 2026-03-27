package ca.uqam.mgl7460.tp3.ressources.types.domaine;

/**
 * Un "record", consistant en deux attributs: 1) 'amortissement', qui est la durée de remboursement en mois, et 2) tauxInteret
 */
public record TermesPret(int amortissement, float tauxInteret) {

}
