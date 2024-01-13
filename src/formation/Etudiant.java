package formation;

import formation.UniteEnseignement.TypeUe;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Classe representant un etudiant.
 *
 * @author EL OUAZIZI Walid
 */
public final class Etudiant implements Serializable {
  
  /**
   * Identifiant de serialization.
   */
  private static final long serialVersionUID = 4585648582485248L;
  
  /**
   * Map des messages.
   */
  private Map<String, Boolean> messages = new LinkedHashMap<>();
  
  /**
   * Liste des UE suivis.
   */
  private Set<UniteEnseignement> ues = new HashSet<>();
  
  /**
   * Les informations personnelles de l'etudiant.
   */
  private InformationPersonnelle infos;
  
  /**
   * Le numero de l'etudiant.
   */
  private int numeroEtudiant;
  
  /**
   * Le mot de passe de l'etudiant.
   */
  private String password;
  
  /**
   * Le groupe de TD de l'etudiant.
   */
  private int td = -1;
  
  /**
   * Le groupe de TP de l'etudiant.
   */
  private int tp = -1;
  
  /**
   * Instancie un etudiant avec ses informations personnelles et un numero
   * etudiant.
   *
   * @param infos les informations personnelles de l'etudiant a instancier
   * @param password le mot de passe de l'etudiant
   * @param numeroEtudiant le numero etudiant a attribuer a l'etudiant
   */
  public Etudiant(InformationPersonnelle infos, String password,
      int numeroEtudiant) {
    super();
    this.infos = infos;
    this.password = password;
    this.numeroEtudiant = numeroEtudiant;
  }
  
  /**
   * Renvoie les informations personnelles de l'etudiant.
   *
   * @return InformationPersonnelle les informations personnelles de l'etudiant
   */
  public InformationPersonnelle getInformationPersonnelle() {
    return this.infos;
  }
  
  /**
   * Renvoie les UEs suivi par un etudiant.
   *
   * @return Set d'UniteEnseignement UE suivi par l'etudiant
   */
  public Set<UniteEnseignement> getUes() {
    return ues;
  }
  
  /**
   * Renvoie le groupe de TD de l'etudiant.
   *
   * @return Groupe de TD de l'etudiant
   */
  public int getTd() {
    return td;
  }
  
  /**
   * Renvoie le groupe de TP de l'etudiant.
   *
   * @return Groupe de TP de l'etudiant
   */
  public int getTp() {
    return tp;
  }
  
  /**
   * Renvoie le mot de passe de l'etudiant.
   *
   * @return Mot de passe de l'etudiant
   */
  public String getPassword() {
    return password;
  }
  
  /**
   * Renvoie le numero de l'etudiant.
   *
   * @return Numero de l'etudiant
   */
  public int getNumeroEtudiant() {
    return numeroEtudiant;
  }
  
  /**
   * Renvoie la map de tout les messages de l'etudiant.
   *
   * @return Map des messages de l'etudiant
   */
  public Map<String, Boolean> getMessages() {
    return messages;
  }
  
  /**
   * Modifie le groupe de TD d'un etudiant.
   *
   * @param td le nouveau groupe de TD
   */
  public void setTd(int td) {
    this.td = td;
  }
  
  /**
   * Modifie le groupe de TP d'un etudiant.
   *
   * @param tp le nouveau groupe de TP
   */
  public void setTp(int tp) {
    this.tp = tp;
  }
  
  /**
   * Permet d'ajouter un message non lus dans les messages de l'etudiant.
   *
   * @param contenu contenu du message a recevoir
   */
  public void recevoirMessage(String contenu) {
    if (!contenu.isBlank()) {
      messages.put(contenu, false);
    }
  }
  
  /**
   * Permet de marquer un message comme lu pour un etudiant.
   *
   * @param message Message a marquer comme lu
   */
  public void marquerMessageLu(String message) {
    messages.replace(message, true);
  }
  
  /**
   * Peremet de savoir si l'inscription d'un etudiant est finalise.
   *
   * @param optreq Nb d'options que l'etudiant doit avoir choisi.
   * @return Booleen indiquant si l'inscription de l'etudiant est finalisee.
   */
  public boolean inscriptionFinalisee(int optreq) {
    return (td != -1 && tp != -1 && nombreOptions(optreq) == 0);
  }
  
  /**
   * Compte le nb d'options que l'etudiant doit encore choisir.
   *
   * @param optreq Nb d'options que l'etudiant doit avoir choisi.
   * @return int indiquant le nombre d'options que l'etudiant doit encore choisir.
   */
  public int nombreOptions(int optreq) {
    Predicate<UniteEnseignement> filter = ue -> ue.getType() == TypeUe.OPT;
    int optCount = (int) getUes().parallelStream().filter(filter).count();
    return (optreq == -1) ? -1 : optreq - optCount;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(infos, messages, numeroEtudiant,
        password, td, tp, ues);
  }
  
  @Override
  public String toString() {
    return infos.toString();
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
    Etudiant other = (Etudiant) obj;
    return Objects.equals(infos, other.infos);
  }
}
