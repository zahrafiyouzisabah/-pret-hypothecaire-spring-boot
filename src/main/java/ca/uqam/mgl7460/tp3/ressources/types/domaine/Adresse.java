package ca.uqam.mgl7460.tp3.ressources.types.domaine;

/**
 * Quand on définit une "data class", c-à-d essentiellement des attributs, mais avec pas ou peu de méthode, on utilise
 * un <code>record</code> au lieu de <code>class</code>
 */
public record Adresse(String numeroPorte, String numeroRue, String nomRue, String ville, ProvinceOuTerritoire province, String codePostal) {
    public String toString() {
        return "Porte "+ numeroPorte+ ", "+ numeroRue+ " rue "+nomRue+ ", "+ville+ ", " + codePostal + " "+ province;
    }
}
