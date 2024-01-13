package formation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Classe representant la Formation.
 *
 * @author EL OUAZIZI Walid
 */
public final class Formation implements Serializable {
  
  /**
   * Identifiant de serialization.
   */
  private static final long serialVersionUID = -617777835036379859L;
  
  /**
   * Nom de la formation.
   */
  private String nomFormation;
  
  /**
   * Nom du responsable de la formation.
   */
  private String nomResponsable;
  
  /**
   * Mail du responsable de la formation.
   */
  private String emailResponsable;
  
  /**
   * Nb max d'etudiants dans un groupe de TD.
   */
  private int tdmax = -1;
  
  /**
   * Nb max d'etudiants dans un groupe de TP.
   */
  private int tpmax = -1;
  
  /**
   * Nb de groupe de TD.
   */
  private int td = 0;
  
  /**
   * Nb de groupe de TP.
   */
  private int tp = 0;
  
  /**
   * Nb min d'options a choisir.
   */
  private int optmin = -1;
  
  /**
   * Liste des UE disponible.
   */
  private Set<UniteEnseignement> ues = new HashSet<>();
  
  /**
   * Initialise une formation.
   *
   * @param nomFormation le nom de la formation
   * @param nomResponsable le nom du responsable de la formation
   * @param email l'email du responsable de la formation
   */
  public Formation(String nomFormation, String nomResponsable, String email) {
    super();
    this.nomFormation =
        (nomFormation == null || nomFormation.isEmpty()) ? "Formation"
            : nomFormation;
    this.nomResponsable =
        (nomResponsable == null || nomResponsable.isEmpty()) ? "Responsable"
            : nomResponsable;
    this.emailResponsable =
        (email == null || email.isEmpty()) ? "responsable@formation.fr" : email;
  }
  
  /**
   * Obtient le nom de la formation.
   *
   * @return Le nom de la formation.
   */
  
  public String getNomFormation() {
    return nomFormation;
  }
  
  /**
   * Obtient le nom du responsable de la formation.
   *
   * @return Le nom du responsable de la formation.
   */
  
  public String getNomResponsable() {
    return nomResponsable;
  }
  
  /**
   * Obtient l'adresse e-mail du responsable de la formation.
   *
   * @return L'adresse e-mail du responsable de la formation.
   */
  
  public String getEmailResponsable() {
    return emailResponsable;
  }
  
  /**
   * Obtient le nombre de groupe de TP de la formation.
   *
   * @return Le nombre de groupes de TP de la formation.
   */
  public int getTp() {
    return tp;
  }
  
  /**
   * Defini le nombre de groupes de TP dans la formation.
   *
   * @param tp Le nombre de groupe de TP.
   */
  public void setTp(int tp) {
    this.tp = tp;
  }
  
  /**
   * Obtient le nombre de groupe de TD de la formation.
   *
   * @return Le nombre de groupes de TD de la formation.
   */
  public int getTd() {
    return td;
  }
  
  /**
   * Defini le nombre de groupes de TD dans la formation.
   *
   * @param td Le nombre de groupe de TD.
   */
  public void setTd(int td) {
    this.td = td;
  }
  
  /**
   * Obtient le nombre d'etudiants maximum dans un groupe de travaux pratiques.
   *
   * @return Le nombre d'etudiants maximum dans un groupe de travaux pratiques.
   */
  public int getTpmax() {
    return tpmax;
  }
  
  /**
   * Definit le nombre d'etudiants maximum dans un groupe de travaux pratiques.
   *
   * @param tpmax Le nombre d'etudiants maximum dans un groupe de travaux
   *        pratiques.
   */
  public void setTpmax(int tpmax) {
    this.tpmax = tpmax;
  }
  
  /**
   * Obtient le nombre d'etudiants maximum dans un groupe de travaux diriges.
   *
   * @return Le nombre d'etudiants maximum dans un groupe de travaux diriges.
   */
  public int getTdmax() {
    return tdmax;
  }
  
  /**
   * Definit le nombre d'etudiants maximum dans un groupe de travaux diriges.
   *
   * @param tdmax Le nombre d'etudiants maximum dans un groupe de travaux
   *        diriges.
   */
  public void setTdmax(int tdmax) {
    this.tdmax = tdmax;
  }
  
  /**
   * Obtient la liste des unites d'enseignement de la formation.
   *
   * @return La liste des unites d'enseignement de la formation.
   */
  public Set<UniteEnseignement> getUes() {
    return ues;
  }
  
  /**
   * Obtient le nombre minimum d'options a choisir.
   *
   * @return Le nombre minimum d'options a choisir.
   */
  public int getOptmin() {
    return optmin;
  }
  
  /**
   * Definit le nombre minimum d'options a choisir.
   *
   * @param optmin Le nombre minimum d'options a choisir.
   */
  public void setOptmin(int optmin) {
    this.optmin = optmin;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(emailResponsable, nomFormation, nomResponsable, optmin,
        tdmax, tpmax, ues);
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
    Formation other = (Formation) obj;
    return Objects.equals(emailResponsable, other.emailResponsable)
        && Objects.equals(nomFormation, other.nomFormation)
        && Objects.equals(nomResponsable, other.nomResponsable)
        && optmin == other.optmin && tdmax == other.tdmax
        && tpmax == other.tpmax && Objects.equals(ues, other.ues);
  }
  
  @Override
  public String toString() {
    return "Formation [nomFormation=" + nomFormation + ", nomResponsable="
        + nomResponsable + ", emailResponsable=" + emailResponsable + "]";
  }
  
}
