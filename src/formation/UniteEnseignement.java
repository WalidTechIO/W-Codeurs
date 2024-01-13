package formation;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe representant les unites d'enseignement.
 *
 * @author EL OUAZIZI Walid
 */
public class UniteEnseignement implements Serializable {

  /**
   * Identifiant de serialziation.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Capacite d'acceuil (-1 pour infini).
   */
  private int capacite = -1;
  
  /**
   * Nom.
   */
  private final String nom;
  
  /**
   * Nom du responsable de l'UE.
   */
  private final String nomResponsable;
  
  /**
   * Type de l'UE.
   */
  private TypeUe type = TypeUe.OBG;
  
  /**
   * Enum Type d'UE.
   */
  public static enum TypeUe {
    /**
     * UE Obligatoire.
     */
    OBG, 
    /**
     * UE Optionnel.
     */
    OPT
  }
    
  /**
   * Constructeur UE. 
   *
   * @param nom Nomde l'UE.
   * @param nomResponsable Nom du responsable de l'UE.
   */
  public UniteEnseignement(String nom, String nomResponsable) {
    this.nom = nom;
    this.nomResponsable = nomResponsable;
  }
  
  /**
   * Renvoie la capacite.
   *
   * @return capacite de l'UE
   */
  public int getCapacite() {
    return capacite;
  }
  
  /**
   * Renvoie le type de l'UE.
   *
   * @return type de l'UE
   */
  public TypeUe getType() {
    return type;
  }
  
  /**
   * Renvoie le nom de l'UE.
   *
   * @return Nom de l'UE
   */
  public String getNom() {
    return nom;
  }
  
  /**
   * Defini une UE comme optionnel.
   *
   * @param capa Capacite d'acceuil de l'UE optionnel.
   */
  public void setOptionnel(int capa) {      
    type = TypeUe.OPT;
    if (capa > 0 && capacite == -1) {
      capacite = capa;
    }
  }

  @Override
  public String toString() {
    if (capacite != -1) {
      return nom + " - par " + nomResponsable + " - Limite a " + capacite + " places.";
    }
    return nom + " - par " + nomResponsable;
  }

  @Override
  public int hashCode() {
    return Objects.hash(nom, nomResponsable);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UniteEnseignement other = (UniteEnseignement) obj;
    return Objects.equals(nom, other.nom)
        && Objects.equals(nomResponsable, other.nomResponsable);
  }

  /**
   * Renvoie le nom du responsable de l'UE.
   *
   * @return Nom responsable de l'UE.
   */
  public String getNomResponsable() {
    return nomResponsable;
  }
  

}
