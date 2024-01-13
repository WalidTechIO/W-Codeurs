package formation;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Classe pour la Gestion des Etudiants.
 *
 * @author EL OUAZIZI Walid
 * @see formation.Etudiant
 */
public class GestionEtudiant implements InterEtudiant, Serializable {
  
  /**
   * Attribut de serialisation.
   */
  private static final long serialVersionUID = 4567854459974L;
  
  /**
   * Attribut pour les numero etudiant.
   */
  private int id = 0;
  
  /**
   * Etudiant connecte.
   */
  private Etudiant logged = null;
  
  /**
   * Liste des Etudiants.
   */
  private Set<Etudiant> etudiants = new LinkedHashSet<>();
  
  /**
   * Application.
   */
  private Application app = null;
  
  /**
   * Constructeur Gestion Etudiant.
   *
   * @param app instance d'Application utilisant ce gestionnaire
   */
  public GestionEtudiant(Application app) {
    super();
    this.app = app;
  }
  
  /**
   * Obtient tout les etudiants inscrits dans le systeme.
   *
   * @return Le set de tout les Etudiants inscrits.
   */
  public Set<Etudiant> getEtudiants() {
    return etudiants;
  }
   
  /**
   * Obtient les informations personnelles de l'etudiant connecte au systeme.
   *
   * @return Les informations personnelles de l'etudiant connecte
   * @throws NonConnecteException si aucun etudiant n'est connecte
   */
  public InformationPersonnelle getInformationPersonnelle()
      throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    return logged.getInformationPersonnelle();
  }
  
  /**
   * Obtient l'etudiant connecte.
   *
   * @return Etudiant|null etudiant connecte ou null
   */
  public Etudiant getLogged() {
    return logged;
  }
  
  /**
   * Condition de refus d'une option pour un etudiant connecte.
   *
   * @param ue l'option que l'etudiant veut choisir.
   * @return boolean Vrai si l'option doit etre refusee a l'etudiant.
   * @throws NonConnecteException si aucun etudiant n'est connecte.
   */
  private boolean conditionRefusOption(UniteEnseignement ue) throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    if (!app.getGestionFormation().enseignementsOptionnels().contains(ue)) {
      return true;
    }
    int etuInscrits = app.getGestionFormation().listeEtudiantsOption(ue).size();
    return (ue.getCapacite() != -1 && etuInscrits >= ue.getCapacite())
        || logged.getUes().contains(ue) 
        || nombreOptions() < 1;
  }
  
  @Override
  public int inscription(InformationPersonnelle infos, String motDePasse) {
    if (infos == null || motDePasse == null || motDePasse.isBlank()) {
      return -1;
    }
    Etudiant etu = new Etudiant(infos, motDePasse, id + 1);
    for (Etudiant etudiant : etudiants) {
      if (etu.equals(etudiant)) {
        return -1;
      }
    }
    etu.getUes().addAll(app.getGestionFormation().enseignementsObligatoires());
    etudiants.add(etu);
    id++;
    etu.recevoirMessage(
        "Bonjour\nBienvenue sur le portail d'inscription a la formation\n"
        + "Votre numero etudiant est: " + id + "\nVous pouvez des a present choisir vos options\n");
    return id;
  }
  
  @Override
  public boolean connexion(int numero, String motDePasse) {
    if (numero <= 0) {
      return false;
    }
    for (Etudiant etudiant : etudiants) {
      if (numero == etudiant.getNumeroEtudiant()
          && motDePasse.equals(etudiant.getPassword())) {
        logged = etudiant;
        return true;
      }
    }
    return false;
  }
  
  @Override
  public void deconnexion() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    } else {
      logged = null;
    }
  }
  
  @Override
  public int nombreOptions() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    return logged.nombreOptions(app.getGestionFormation().getNombreOptions());
  }
  
  @Override
  public boolean choisirOption(UniteEnseignement ue)
      throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    
    if (conditionRefusOption(ue)) {
      return false;
    }
    
    logged.getUes().add(ue);
    return true;
  }
  
  @Override
  public int getNumeroGroupeTravauxDiriges() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    return logged.getTd();
  }
  
  @Override
  public int getNumeroGroupeTravauxPratiques() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    return logged.getTp();
  }
  
  @Override
  public Set<UniteEnseignement> enseignementsSuivis()
      throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    return logged.getUes();
  }
  
  @Override
  public List<String> listeTousMessages() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    
    Function<Map.Entry<String, Boolean>, String> mapper = e -> {
      if (e.getValue() == false) {
        return "♦ " + e.getKey();
      } else {
        return e.getKey();
      }
    };
    
    return logged
        .getMessages()
        .entrySet()
        .parallelStream()
        .map(mapper)
        .toList();
  }
  
  @Override
  public List<String> listeMessageNonLus() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    
    // Filtre s'assurant qu'un message est non lu
    Predicate<Map.Entry<String, Boolean>> filter = e -> e.getValue() == false;
    
    //Mapper ajoutant un carreau suivi d'un espace devant chaque message
    Function<Map.Entry<String, Boolean>, String> mapper = e -> "♦ " + e.getKey(); 
    
    return logged // Etudiant connecte
        .getMessages() //Map des messages de forme: "Message" -> booleen (statut de lecture Vrai=lu)
        .entrySet() //Set des entree de la map
        .parallelStream() // Stream des entree de la map
        .filter(filter) //Filtrage sur la valeur des entree pour avoir un stream de msg non lus
        .map(mapper) //Mappage de chaque message avec le mapper ci-dessus
        .toList(); //Transformation en liste exploitable
  }
  
  @Override
  public boolean inscriptionFinalisee() throws NonConnecteException {
    if (logged == null) {
      throw new NonConnecteException();
    }
    if (logged.inscriptionFinalisee(app.getGestionFormation().getNombreOptions())) {
      if (!logged.getMessages().keySet().contains(
          "Bonjour\nVous avez bel et bien valide votre inscription !\n")) {
        logged.recevoirMessage(
            "Bonjour\nVous avez bel et bien valide votre inscription !\n");
      }
      return true;
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(etudiants, id);
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
    GestionEtudiant other = (GestionEtudiant) obj;
    return Objects.equals(etudiants, other.etudiants) && id == other.id;
  }
  
}
