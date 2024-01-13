package formation;

import io.Sauvegarde;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe representant l'application.
 *
 * @author EL OUAZIZI Walid
 */
public class Application implements Serializable {
  
  /**
   * Attribut de serialisation.
   */
  private static final long serialVersionUID = 7600307293144460393L;
  
  /**
   * Scanner pour la version console.
   */
  private static Scanner in = new Scanner(System.in);
  
  /**
   * Gestionnaire de formation.
   */
  private GestionFormation gf;
  
  /**
   * Gestionnaire d'etudiants.
   */
  private GestionEtudiant ge;
  
  /**
   * Gestionnaire de sauvegarde d'etat.
   */
  private Sauvegarde save;
  
  /**
   * Constructeur principale de l'application.
   */
  public Application() {
    this.gf = new GestionFormation(this);
    this.ge = new GestionEtudiant(this);
    this.save = new Sauvegarde(this);
  }
  
  /**
   * Renvoie l'instance de GestionFormation de notre application.
   *
   * @return GestionFormation gestionnaire de formation du systeme
   */
  public GestionFormation getGestionFormation() {
    return gf;
  }
  
  /**
   * Renvoie l'instance de GestionEtudiant de notre application.
   *
   * @return GestionFormation gestionnaire d'etudiants du systeme
   */
  public GestionEtudiant getGestionEtudiant() {
    return ge;
  }
  
  /**
   * Renvoie l'instance de Sauvegarde de notre application.
   *
   * @return Sauvegarde gestionnaire de sauvegarde d'etat du systeme
   */
  public Sauvegarde getSave() {
    return save;
  }
  
  /**
   * Defini l'instance de GestionFormation de notre application.
   *
   * @param gf Le gestionnaire de formation a definir
   */
  public void setGestionFormation(GestionFormation gf) {
    this.gf = gf;    
  }
  
  /**
   * Defini l'instance de GestionEtudiant de notre application.
   *
   * @param ge Le gestionnaire d'etudiants a definir
   */
  public void setGestionEtudiant(GestionEtudiant ge) {
    this.ge = ge;
  }
  
  /**
   * Methode de sauvegarde version console (UI).
   */
  public void save() {
    System.out.println("Chemin de sauvegarde ?");
    String path = "";
    while (path.isBlank()) {
      path = in.nextLine();
    }
    try {
      save.sauvegarderDonnees(path);
      System.out.println("Les donnees ont bien ete sauvegardees !");
    } catch (IOException e) {
      System.out.println("Impossible de sauvegarder a cet emplacement !");
    }
  }
  
  /**
   * Methode de chargement version console (UI).
   */
  public void load() {
    System.out.println("Chemin de sauvegarde ?");
    String path = "";
    while (path.isBlank()) {
      path = in.nextLine();
    }
    try {
      save.chargerDonnees(path);
      in = new Scanner(System.in);
      System.out.println("Les donnees ont ete chargees correctement !");
    } catch (IOException e) {
      System.out
          .println("Impossible de charger les donnees a cet emplacement !");
    }
  }
  
  /**
   * Methode d'inscription version console (UI).
   */
  public void creerCompte() {
    String nom = "";
    System.out.println("Nom ?");
    while (nom.isBlank()) {
      nom = in.nextLine();
    }
    String prenom = "";
    System.out.println("Prenom ?");
    while (prenom.isBlank()) {
      prenom = in.nextLine();
    }
    String adresse = "";
    System.out.println("Adresse ?");
    adresse = in.nextLine();
    
    int age;
    System.out.println("Age ?");
    try {
      age = Integer.parseInt(in.nextLine());
    } catch (NumberFormatException e) {
      age = 0;
    }
    
    String motDePasse = "";
    System.out.println("Mot de passe ?");
    motDePasse = in.nextLine();
    
    InformationPersonnelle infos;
    try {
      infos = new InformationPersonnelle(nom, prenom, adresse, age);
    } catch (InformationPersonnelleException e) {
      System.out.println("Vous ne pouvez creer un compte sans nom ni prenom");
      infos = null;
    }
    int res = ge.inscription(infos, motDePasse);
    if (res > 0) {
      System.out.println("Numero etudiant : " + res + "\n");
    } else {
      System.out.println("Inscription echouee");
    }
  }
  
  /**
   * Methode de connexion version console (UI). 
   *
   * @return Booleen indiquant si la connexion a reussi.
   */
  public boolean connexion() {
    int numEtudiant = 0;
    System.out.println("Votre numero etudiant ?");
    String line = in.nextLine();
    if (line != "") {
      try {
        numEtudiant = Integer.parseInt(line);
      } catch (NumberFormatException e) {
        System.out.println("Num etudiant incorrect");
      }
    } else {
      try {
        numEtudiant = Integer.parseInt(in.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Num etudiant incorrect");
      }
    }
     
    String motDePasse;
    System.out.println("Votre mot de passe ?");
    motDePasse = in.nextLine();
    
    if (ge.connexion(numEtudiant, motDePasse)) {
      System.out.println("Vous etes bien connecte !");
      try {
        ge.inscriptionFinalisee();
      } catch (NonConnecteException e) {
        System.out.println("Error");
      }
      return true;
    } else {
      System.out.println("Identifiants incorrect !");
      return false;
    }
  }
  
  /**
   * Methode de deconnexion version console (UI).
   */
  public void deconnexion() {
    try {
      ge.deconnexion();
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
    }
  }
  
  /**
   * Methode d'affichage du profil d'un etuidnat connecte (UI).
   */
  public void afficherProfil() {
    try {
      System.out.println("Bonjour, " + ge.getInformationPersonnelle() + "\n");
      
      System.out.println("Options validees : \n");
      
      for (UniteEnseignement ue : ge.enseignementsSuivis()) {
        if (ue.getType() == UniteEnseignement.TypeUe.OPT) {
          System.out.println(ue + "\n");
        }
      }
      afficherTdTp();
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
      return;
    }
  }
  
  /**
   * Methode de choix d'options version console (UI).
   */
  public void choisirOptions() {
    try {
      if (gf.enseignementsOptionnels().isEmpty() || ge.nombreOptions() <= 0) {
        System.out.println("Vous ne pouvez pas choisir d'options");
        return;
      }
      int i = 1;
      Map<Integer, UniteEnseignement> mapUe = new HashMap<>();
      System.out.println(
          "Il vous reste " + ge.nombreOptions() + " option(s) a choisir !");
      for (UniteEnseignement ue : gf.enseignementsOptionnels()) {
        System.out.println(i + " - " + ue.getNom());
        mapUe.put(i, ue);
        i++;
      }
      System.out.println(
          "Votre choix ? (definitif si validee) (-1 pour quitter le choix)");
      i = in.nextInt();
      if (i == -1) {
        return;
      }
      if (ge.choisirOption(mapUe.get(i)) == false) {
        System.out.println("Impossible de selectionner cette UE !");
      } else {
        System.out
            .println("Vous etes desormais inscrit a " + mapUe.get(i).getNom());
      }
      
      choisirOptions();
      ge.inscriptionFinalisee();
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
      return;
    }
  }
  
  /**
   * Methode de lecture de tout les messages version console (UI).
   */
  public void listeTousMessages() {
    try {
      for (String s : ge.listeTousMessages()) {
        System.out.println(s);
      }
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
    }
  }
  
  /**
   * Methode de lecture des messages non lus version console (UI).
   */
  public void listeMessagesNonLus() {
    try {
      for (String s : ge.listeMessageNonLus()) {
        System.out.println(s);
      }
      for (String s : ge.listeTousMessages()) {
        ge.getLogged().getMessages().replace(s, true);
      }
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
    }
  }
  
  /**
   * Methode de creation de formation version console (UI).
   */
  public void creerFormation() {
    while (!gf.isFormationDefined()) {
      System.out.println("Creation de formation\nNom de la formation ?");
      String nomForma = "";
      while (nomForma.isBlank()) {
        nomForma = in.nextLine();
      }
      System.out.println("Nom responsable de la formation ?");
      String nomResp = "";
      while (nomResp.isBlank()) {
        nomResp = in.nextLine();
      }
      System.out.println("Mail du responsable de la formation ?");
      String mailResp = "";
      while (mailResp.isBlank()) {
        mailResp = in.nextLine();
      }
      gf.creerFormation(nomForma, nomResp, mailResp);
      if (!gf.isFormationDefined()) {
        System.out.println("Erreur lors de la creation de la formation !");
      }
    }
  }
  
  /**
   * Methode de defintion du nb d'options version console (UI).
   */
  public void definirNbOptions() {
    if (gf.getNombreOptions() == -1) {
      int nb = -1;
      while (nb == -1) {
        System.out.println("Entrez un nb min d'options a choisir");
        try {
          nb = in.nextInt();
        } catch (InputMismatchException e) {
          continue;
        }
      }
      gf.definirNombreOptions(nb);
    } else {
      System.out.println("Nombre actuel: " + gf.getNombreOptions());
    }
  }
  
  /**
   * Methode de definition du nb max d'etu dans un tp version console (UI).
   */
  public void definirTpMax() {
    if (gf.getTailleGroupePratique() == -1) {
      int nb = -1;
      while (nb == -1) {
        System.out.println("Entrez un nb max d'etudiant dans un groupe de TP");
        try {
          nb = in.nextInt();
        } catch (InputMismatchException e) {
          continue;
        }
      }
      gf.setTailleGroupePratique(nb);
    } else {
      System.out.println("Nombre actuel: " + gf.getTailleGroupePratique());
    }
  }
  
  /**
   * Methode de definition du nb max d'etu dans un td version console (UI).
   */
  public void definirTdMax() {
    if (gf.getTailleGroupeDirige() == -1) {
      int nb = -1;
      while (nb == -1) {
        System.out.println("Entrez un nb max d'etudiant dans un groupe de TD");
        try {
          nb = in.nextInt();
        } catch (InputMismatchException e) {
          continue;
        }
      }
      gf.setTailleGroupeDirige(nb);
    } else {
      System.out.println("Nombre actuel: " + gf.getTailleGroupeDirige());
    }
  }
  
  /**
   * Methode d'attribution auto des groupes (UI).
   */
  public void attribuerGroupeAuto() {
    gf.attribuerAutomatiquementGroupes();
    System.out.println("Attribution auto effectuee !");
  }
  
  /**
   * Methode d'affichage de liste de tout les etudiants (UI).
   */
  public void listeTotal() {
    System.out.println("Liste de touts les etudiants");
    if (ge.getEtudiants().size() == 0) {
      System.out.println("Aucun etudiant !");
    }
    for (Etudiant etu : ge.getEtudiants()) {
      System.out.println(etu.getInformationPersonnelle());
    }
  }
  
  /**
   * Methode d'affichage de liste de TD (UI).
   */
  public void listeTd() {
    System.out.println("Il y a " + gf.nombreGroupesTravauxDiriges()
        + " TD sur l'annee.\nLequel souhaite vous lister ?");
    int td = 0;
    try {
      td = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Mauvais choix !");
      return;
    }
    if (gf.listeEtudiantsGroupeDirige(td) == null) {
      System.out.println("TD inexistant !");
    } else {
      System.out.println("Liste TD numero " + td);
      for (Etudiant etu : gf.listeEtudiantsGroupeDirige(td)) {
        System.out.println(etu.getInformationPersonnelle());
      }
    }
  }
  
  /**
   * Methode d'affichage de liste de TP (UI).
   */
  public void listeTp() {
    System.out.println("Il y a " + gf.nombreGroupesTravauxPratiques()
        + " TP sur l'annee.\nLequel souhaite vous lister ?");
    int tp = 0;
    try {
      tp = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Mauvais choix !");
      return;
    }
    if (gf.listeEtudiantsGroupePratique(tp) == null) {
      System.out.println("TP inexistant !");
    } else {
      System.out.println("Liste TP numero " + tp);
      for (Etudiant etu : gf.listeEtudiantsGroupePratique(tp)) {
        System.out.println(etu.getInformationPersonnelle());
      }
    }
  }
  
  /**
   * Methode d'affichage de liste d'options (UI).
   */
  public void listeOption() {
    System.out.println("Il y a " + gf.enseignementsOptionnels().size()
        + " options sur l'annee.\nLaquel souhaite vous lister ?");
    int i = 1;
    Map<Integer, UniteEnseignement> mapUe = new HashMap<>();
    for (UniteEnseignement ue : gf.enseignementsOptionnels()) {
      System.out.println(i + " - " + ue.getNom());
      mapUe.put(i, ue);
      i++;
    }
    try {
      i = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Mauvais choix !");
      return;
    }
    UniteEnseignement ue = mapUe.get(i);
    if (ue == null) {
      System.out.println("UE inexistante");
    } else {
      System.out.println("Liste UE " + ue.getNom());
      for (Etudiant etu : gf.listeEtudiantsOption(ue)) {
        System.out.println(etu.getInformationPersonnelle());
      }
    }
  }
  
  /**
   * Methode d'affichage des UE obligatoires de la formation (UI).
   */
  public void listeUeObligatoire() {
    System.out.println("Liste UE obligatoires : \n");
    for (UniteEnseignement ue : gf.enseignementsObligatoires()) {
      System.out.println(ue);
    }
  }
  
  /**
   * Methode d'affichage des UE optionnels de la formation (UI).
   */
  public void listeUeOptionnel() {
    System.out.println("Liste UE optionnels : \n");
    for (UniteEnseignement ue : gf.enseignementsOptionnels()) {
      System.out.println(ue);
    }
  }
  
  /**
   * Methode d'ajout d'une UE obligatoire a la formation (UI).
   */
  public void ajoutUeObligatoire() {
    System.out.println("Nom de l'Unite d'Enseignement ?");
    String nom = "";
    while (nom.isBlank()) {
      nom = in.nextLine();
    }
    System.out.println("Nom du responsable de l'Unite d'Enseignement ?");
    String nomResp = "";
    while (nomResp.isBlank()) {
      nomResp = in.nextLine();
    }
    gf.ajouterEnseignementObligatoire(new UniteEnseignement(nom, nomResp));
    System.out.println("UE ajoute !");
    
  }
  
  /**
   * Methode d'affichage des UE suivi d'un etudiant connecte (UI).
   */
  private void afficherUeSuivis() {
    try {
      System.out.println("Vos enseignements suivi : ");
      for (UniteEnseignement ue : ge.enseignementsSuivis()) {
        System.out.println(ue);
      }
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
    }
  }
  
  /**
   * Methode d'affichage du TD et TP d'un etudiant connecte (UI).
   */
  private void afficherTdTp() {
    try {
      System.out.println("Vos groupes de TD/TP : ");
      if (ge.getNumeroGroupeTravauxDiriges() != -1
          && ge.getNumeroGroupeTravauxPratiques() != -1) {
        System.out.println("TD: " + ge.getNumeroGroupeTravauxDiriges()
            + " | TP: " + ge.getNumeroGroupeTravauxPratiques());
      } else {
        System.out.println("Non defini");
      }
    } catch (NonConnecteException e) {
      System.out.println("Vous n'etes pas connecte !");
    }
  }
  
  /**
   * Methode d'ajout d'une UE optionnel a la formation (UI).
   */
  public void ajoutUeOptionnel() {
    int capa = 0;
    System.out.println("Nom de l'Unite d'Enseignement optionnel ?");
    String nom = "";
    while (nom.isBlank()) {
      nom = in.nextLine();
    }
    System.out
        .println("Nom du responsable de l'Unite d'Enseignement optionnel ?");
    String nomResp = "";
    while (nomResp.isBlank()) {
      nomResp = in.nextLine();
    }
    System.out.println(
        "Capacite de l'Unite d'Enseignement optionnel ? (-1 pour ne pas definir de capacite)");
    try {
      capa = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Erreur capacite UE non cree");
      return;
    }
    gf.ajouterEnseignementOptionnel(new UniteEnseignement(nom, nomResp), capa);
    System.out.println("UE ajoute !");
  }
  
  /**
   * Methode de deplacement d'un etudiant particulier (UI).
   */
  public void deplacerEtudiant() {
    System.out.println("Il y a " + gf.nombreGroupesTravauxDiriges() + " TD et "
        + gf.nombreGroupesTravauxPratiques() + " TP sur l'annee.\n");
    int i = 1;
    Map<Integer, Etudiant> mapEtu = new HashMap<>();
    for (Etudiant etu : ge.getEtudiants()) {
      System.out.println(i + " - " + etu.getInformationPersonnelle() + " TD: "
          + etu.getTd() + " | TP: " + etu.getTp());
      mapEtu.put(i, etu);
      i++;
    }
    try {
      i = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Mauvais choix !");
      return;
    }
    final Etudiant etu = mapEtu.get(i);
    System.out.println(
        "Quelle TD souhaitez vous lui attribuer ? (-1 pour qu'il conserve celui qu'il possede)");
    try {
      i = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Mauvais choix !");
      return;
    }
    int td = i;
    System.out.println(
        "Quelle TP souhaitez vous lui attribuer ? (-1 pour qu'il conserve celui qu'il possede)");
    try {
      i = in.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Mauvais choix !");
      return;
    }
    int tp = i;
    switch (gf.changerGroupe(etu, td, tp)) {
      case 0:
        System.out.println("Attribution effectue !");
        break;
      
      case -1:
        System.out.println("Attribution du TP uniquement");
        break;
      
      case -2:
        System.out.println("Attribution du TD uniquement");
        break;
      
      case -3:
        System.out.println("Attribution non effectuee");
        break;
      
      default:
        break;
    }
  }
  
  /**
   * Methode de lancement de la version console (1er menu) (UI).
   */
  public void run() {
    int choix = 0;
    while (choix != 6) {
      System.out.println(
          "\nMenu principal\n1- Connexion\n2- Inscription\n" 
          + "3- Gestion formation\n4- Sauvegarder\n5- Charger\n6- Fermer l'application"
      );
      try {
        choix = in.nextInt();
      } catch (InputMismatchException e) {
        in.nextLine();
        choix = -1;
      }
      switch (choix) {
        case 1:
          if (connexion()) {
            afficherProfil();
            menuEtu();
          }
          break;
        
        case 2:
          creerCompte();
          break;
        
        case 3:
          menuForma();
          break;
        
        case 4:
          save();
          break;
        
        case 5:
          load();
          break;
        
        case 6:
          break;
        
        default:
          System.out.println("Mauvais choix !");
          break;
      }
      
    }
    
  }
  
  /**
   * Methode affichant le menu d'un etudiant connecte (UI).
   */
  public void menuEtu() {
    int choix = 0;
    while (choix != 7) {
      System.out.println(
          "\nMenu Etudiant\n1- Afficher profil\n2- Choisir options\n3- Lire messages non lus\n"
          + "4- Lire tout les messages\n5- Groupe de TP/TD\n6- Unite d'Enseignement suivis"
          + "\n7- Deconnexion"
      );
      try {
        choix = in.nextInt();
      } catch (InputMismatchException e) {
        in.nextLine();
        choix = -1;
      }
      
      switch (choix) {
        case 1:
          afficherProfil();
          break;
        
        case 2:
          choisirOptions();
          break;
        
        case 3:
          listeMessagesNonLus();
          break;
        
        case 4:
          listeTousMessages();
          break;
        
        case 5:
          afficherTdTp();
          break;
        
        case 6:
          afficherUeSuivis();
          break;
        
        case 7:
          break;
        
        default:
          System.out.println("Mauvais choix !");
          break;
      }
      
      System.out.print("\n");
    }
    deconnexion();
  }
  
  /**
   * Methode affichant le menu du responsable de la formation (UI).
   */
  public void menuForma() {
    if (!getGestionFormation().isFormationDefined()) {
      creerFormation();
    }
    int choix = 0;
    while (choix != 8) {
      System.out.println(
          "\nMenu Formation\n1- Attribuer groupe auto\n"
          + "2- Definir nb options\n3- Definir nb max TP\n4- Definir nb max TD\n"
          + "5- Liste\n6- Unite d'Enseignement\n7- Deplacer un etudiant\n8- Menu principal"
      );
      try {
        choix = in.nextInt();
      } catch (InputMismatchException e) {
        in.nextLine();
        choix = -1;
      }
      
      switch (choix) {
        case 1:
          attribuerGroupeAuto();
          break;
        
        case 2:
          definirNbOptions();
          break;
        
        case 3:
          definirTpMax();
          break;
        
        case 4:
          definirTdMax();
          break;
        
        case 5:
          menuFormaList();
          break;
        
        case 6:
          menuUe();
          break;
        
        case 7:
          deplacerEtudiant();
          break;
        
        case 8:
          break;
        
        default:
          System.out.println("Mauvais choix !");
          break;
      }
      
      System.out.print("\n");
    }
    
  }
  
  /**
   * Methode affichant le menu des listes pour le responsable de la formation (UI).
   */
  public void menuFormaList() {
    int choix = 0;
    while (choix != 5) {
      System.out.println(
          "\nMenu Formation -> Liste\n1- Liste tout les etudiants\n2- Liste TD"
          + "\n3- Liste TP\n4- Liste Options\n5- Retour"
      );
      try {
        choix = in.nextInt();
      } catch (InputMismatchException e) {
        in.nextLine();
        choix = -1;
      }
      
      switch (choix) {
        
        case 1:
          listeTotal();
          break;
        
        case 2:
          listeTd();
          break;
        
        case 3:
          listeTp();
          break;
        
        case 4:
          listeOption();
          break;
        
        case 5:
          break;
        
        default:
          System.out.println("Mauvais choix !");
          break;
      }
      
      System.out.print("\n");
    }
  }
  
  /**
   * Methode affichant le menu des UE pour le responsable de la formation (UI).
   */
  public void menuUe() {
    int choix = 0;
    while (choix != 5) {
      System.out.println(
          "\nMenu Formation -> Unite d'Enseignement\n1- Liste obligatoire\n2- Liste optionnel\n"
          + "3- Ajouter obligatoire\n4- Ajouter optionnel\n5- Retour");
      try {
        choix = in.nextInt();
      } catch (InputMismatchException e) {
        in.nextLine();
        choix = -1;
      }
      
      switch (choix) {
        case 1:
          listeUeObligatoire();
          break;
        
        case 2:
          listeUeOptionnel();
          break;
        
        case 3:
          ajoutUeObligatoire();
          break;
        
        case 4:
          ajoutUeOptionnel();
          break;
          
        case 5:
          break;
        
        default:
          System.out.println("Mauvais choix !");
          break;
      }
      
      System.out.print("\n");
    }
  }
  
}
