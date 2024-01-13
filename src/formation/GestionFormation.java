package formation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Classe pour la Gestion de la formation.
 *
 * @author EL OUAZIZI Walid
 * @see formation.Formation
 */
public class GestionFormation implements InterGestionFormation, Serializable {
  
  /**
   * Attribut de serialization.
   */
  private static final long serialVersionUID = 5302568898700117316L;
  
  /**
   * Formation geree par le systeme.
   */
  private Formation formation = null;
  
  /**
   * Application.
   */
  private Application app = null;
  
  /**
   * Constructeur.
   *
   *  @param app instance d'Application utilisant ce gestionnaire
   */
  public GestionFormation(Application app) {
    super();
    this.app = app;
  }
  
  /**
   * Condition qui verifie si un etudiant peut etre deplacer vers un TD.
   *
   * @param td le numero de TD dans lequel on veut deplacer l'etudiant
   * @return true si le groupe existe et qu'il y reste de la place false sinon
   */
  private boolean changementTdOk(int td) {
    if (formation != null && td > 0 && td <= nombreGroupesTravauxDiriges()
        && listeEtudiantsGroupeDirige(td).size() < getTailleGroupeDirige()) {
      return true;
    }
    return false;
  }
  
  /**
   * Condition qui verifie si un etudiant peut etre deplacer vers un TP.
   *
   * @param tp le numero de TP dans lequel on veut deplacer l'etudiant
   * @return true si le groupe existe et qu'il y reste de la place false sinon
   */
  private boolean changementTpOk(int tp) {
    if (formation != null && tp > 0 && tp <= nombreGroupesTravauxPratiques()
        && listeEtudiantsGroupePratique(tp)
            .size() < getTailleGroupePratique()) {
      return true;
    }
    return false;
  }
  
  /**
   * Obtient le nombre d'options requises pour l'annee pour un etudiant.
   *
   * @return nb d'options a choisir
   */
  public int getNombreOptions() {
    if (formation != null) {
      return formation.getOptmin();
    }
    return -1;
  }
  
  /**
   * Verifie si la formation existe.
   *
   * @return true si la formation est definie false sinon
   */
  public boolean isFormationDefined() {
    return formation != null;
  }
  
  @Override
  public void creerFormation(String nomFormation, String nomResponsable,
      String email) {
    final String mailRegex = "[\\w\\-\\.]+@[\\w\\-\\.]+\\.[a-z]{2,}";
    if (Pattern.matches(mailRegex, email)) {
      formation = new Formation(nomFormation, nomResponsable, email.toLowerCase());
      app.getGestionEtudiant().getEtudiants().forEach(etu -> etu.getUes().clear());
    }
  }
  
  @Override
  public String getNomResponsableFormation() {
    if (formation != null) {
      return formation.getNomResponsable();
    }
    return null;
  }
  
  @Override
  public String getEmailResponsableFormation() {
    if (formation != null) {
      return formation.getEmailResponsable();
    }
    return null;
  }
  
  @Override
  public String getNomFormation() {
    if (formation != null) {
      return formation.getNomFormation();
    }
    return null;
  }
  
  @Override
  public boolean ajouterEnseignementObligatoire(UniteEnseignement ue) {
    if (formation != null && !formation.getUes().contains(ue)) {
      formation.getUes().add(ue);
      app.getGestionEtudiant().getEtudiants().forEach(etudiant -> {
        etudiant.getUes().add(ue);
      });
      return true;
    }
    return false;
  }
  
  @Override
  public boolean ajouterEnseignementOptionnel(UniteEnseignement ue,
      int nbPlaces) {
    if (formation != null && !formation.getUes().contains(ue)) {
      ue.setOptionnel(nbPlaces);
      formation.getUes().add(ue);
      return true;
    }
    return false;
  }
  
  @Override
  public void definirNombreOptions(int nombre) {
    if (formation != null && nombre >= 1 && formation.getOptmin() == -1) {
      formation.setOptmin(nombre);
    }
  }
  
  @Override
  public void setTailleGroupeDirige(int taille) {
    if (formation != null && taille >= 1 && formation.getTdmax() == -1) {
      formation.setTdmax(taille);
    }
  }
  
  @Override
  public void setTailleGroupePratique(int taille) {
    if (formation != null && taille >= 1 && formation.getTpmax() == -1) {
      formation.setTpmax(taille);
    }
  }
  
  @Override
  public int getTailleGroupeDirige() {
    if (formation != null) {
      return formation.getTdmax();
    }
    return -1;
  }
  
  @Override
  public int getTailleGroupePratique() {
    if (formation != null) {
      return formation.getTpmax();
    }
    return -1;
  }
  
  @Override
  public void attribuerAutomatiquementGroupes() {
    if (app.getGestionEtudiant() != null && formation != null
        && formation.getTdmax() != -1 && formation.getTpmax() != -1) {
      int nbEtudiants = app.getGestionEtudiant().getEtudiants().size();
      formation.setTp((int) nbEtudiants / formation.getTpmax());
      formation.setTd((int) nbEtudiants / formation.getTdmax());
      if (nbEtudiants != 0 && nbEtudiants % formation.getTpmax() != 0) {
        formation.setTp(formation.getTp() + 1);
      }
      if (nbEtudiants != 0 && nbEtudiants % formation.getTdmax() != 0) {
        formation.setTd(formation.getTd() + 1);
      }
      int i = 0;
      int j = 0;
      Iterator<Etudiant> it =
          app.getGestionEtudiant().getEtudiants().iterator();
      while (it.hasNext()) {
        Etudiant etu = it.next();
        i %= formation.getTd();
        j %= formation.getTp();
        etu.setTd(++i);
        etu.setTp(++j);
        etu.recevoirMessage(
            "Bonjour\nVous etes maintenant attribue au groupe de TD numero "
                + i + " et au groupe de TP numero " + j + ".\n");
      }
    }
  }
  
  @Override
  public int changerGroupe(Etudiant etudiant, int groupeDirige,
      int groupePratique) {
    boolean changementTd =
        changementTdOk(groupeDirige) && etudiant.getTd() != groupeDirige;
    boolean changementTp =
        changementTpOk(groupePratique) && etudiant.getTp() != groupePratique;
    if (changementTd) {
      etudiant.setTd(groupeDirige);
    }
    if (changementTp) {
      etudiant.setTp(groupePratique);
    }
    
    if (changementTd && changementTp) {
      etudiant.recevoirMessage(
          "Bonjour\nVous etes maintenant attribue au groupe de TD numero "
              + groupeDirige + " et au groupe de TP numero " + groupePratique
              + ".\n");
      return 0;
    } else if (changementTp) {
      etudiant.recevoirMessage(
          "Bonjour\nVous etes maintenant attribue au groupe de TP numero "
              + groupePratique + ".\n");
      return -1;
    } else if (changementTd) {
      etudiant.recevoirMessage(
          "Bonjour\nVous etes maintenant attribue au groupe de TD numero "
              + groupeDirige + ".\n");
      return -2;
    } else {
      return -3;
    }
  }
  
  @Override
  public int nombreGroupesTravauxDiriges() {
    if (formation != null) {
      return formation.getTd();
    } else {
      return 0;
    }
  }
  
  @Override
  public int nombreGroupesTravauxPratiques() {
    if (formation != null) {
      return formation.getTp();
    } else {
      return 0;
    }
  }
  
  @Override
  public Set<Etudiant> listeEtudiantsGroupeDirige(int groupe) {
    if (formation == null || groupe < 1 || groupe > formation.getTd()) {
      return null;
    }
    
    Predicate<Etudiant> filter = e -> e.getTd() == groupe;
    
    return app
        .getGestionEtudiant()
        .getEtudiants()
        .parallelStream()
        .filter(filter)
        .collect(Collectors.toSet());
  }
  
  @Override
  public Set<Etudiant> listeEtudiantsGroupePratique(int groupe) {
    if (formation == null || groupe < 1 || groupe > formation.getTp()) {
      return null;
    }
    
    Predicate<Etudiant> filter = e -> e.getTp() == groupe;
    
    return app
        .getGestionEtudiant()
        .getEtudiants()
        .parallelStream()
        .filter(filter)
        .collect(Collectors.toSet());
  }
  
  @Override
  public Set<Etudiant> listeEtudiantsOption(UniteEnseignement option) {
    if (formation == null || !formation.getUes().contains(option)) {
      return null;
    }
    
    Predicate<Etudiant> filter = e -> e.getUes().contains(option);
    
    return app
        .getGestionEtudiant()
        .getEtudiants()
        .parallelStream()
        .filter(filter)
        .collect(Collectors.toSet());
    
  }
  
  @Override
  public Set<UniteEnseignement> enseignementsObligatoires() {
    if (formation == null) {
      return new HashSet<>();
    }
    
    Predicate<UniteEnseignement> filter = ue -> ue.getType() == UniteEnseignement.TypeUe.OBG;
    
    return formation
        .getUes()
        .parallelStream()
        .filter(filter)
        .collect(Collectors.toSet());
  }
  
  @Override
  public Set<UniteEnseignement> enseignementsOptionnels() {
    if (formation == null) {
      return new HashSet<>();
    }
    
    Predicate<UniteEnseignement> filter = ue -> ue.getType() == UniteEnseignement.TypeUe.OPT;
    
    return formation
        .getUes()
        .parallelStream()
        .filter(filter)
        .collect(Collectors.toSet());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(formation);
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
    GestionFormation other = (GestionFormation) obj;
    return Objects.equals(formation, other.formation);
  }
  
}
