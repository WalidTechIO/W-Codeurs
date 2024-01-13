package ui;

import formation.Etudiant;
import formation.UniteEnseignement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * Le controleur associe a la fenetre definie dans formation.fxml.
 *
 * @author Eric Cariou
 * @author EL OUAZIZI Walid
 */
public class FormationControleur {
  
  /**
   * Instance de l'application que gere le systeme.
   */
  private formation.Application app;
  
  /**
   * Ressource du controleur.
   */
  @FXML
  private ResourceBundle resources;
  
  /**
   * Location du controleur.
   */
  @FXML
  private URL location;
  
  /**
   * TextField Inscription finalisee.
   */
  @FXML
  private CheckBox checkInscriptionFinalisee;
  
  /**
   * TextField Adresse etudiant.
   */
  @FXML
  private TextField entreeAdresseEtudiant;
  
  /**
   * TextField Age etudiant.
   */
  @FXML
  private TextField entreeAgeEtudiant;
  
  /**
   * TextField Capacite UE.
   */
  @FXML
  private TextField entreeCapaciteAccueil;
  
  /**
   * TextField Mail reponsable de la formation.
   */
  @FXML
  private TextField entreeEmailResponsableFormation;
  
  /**
   * TextField Groupe TD etudiant.
   */
  @FXML
  private TextField entreeGroupeTDEtudiant;
  
  /**
   * TextField Groupe TP etudiant.
   */
  @FXML
  private TextField entreeGroupeTPEtudiant;
  
  /**
   * TextField Nom etudiant.
   */
  @FXML
  private TextField entreeNomEtudiant;
  
  /**
   * TextField Nom formation.
   */
  @FXML
  private TextField entreeNomFormation;
  
  /**
   * TextField Nom responsable de formation.
   */
  @FXML
  private TextField entreeNomResponsableFormation;
  
  /**
   * TextField Nom responsable d'UE.
   */
  @FXML
  private TextField entreeNomResponsableUE;
  
  /**
   * TextField Nom d'UE.
   */
  @FXML
  private TextField entreeNomUE;
  
  /**
   * TextField Nombre choix options.
   */
  @FXML
  private TextField entreeNombreChoixOptions;
  
  /**
   * TextField Prenom etudiant.
   */
  @FXML
  private TextField entreePrenomEtudiant;
  
  /**
   * TextField Taille groupe de TD.
   */
  @FXML
  private TextField entreeTailleGroupeTD;
  
  /**
   * TextField Taille groupe de TP.
   */
  @FXML
  private TextField entreeTailleGroupeTP;
  
  /**
   * Label Nom de la liste affiche.
   */
  @FXML
  private Label labelListeEtudiants;
  
  /**
   * Label Nb de groupes de TD.
   */
  @FXML
  private Label labelNbGroupesTD;
  
  /**
   * Label Nb de groupes de TP.
   */
  @FXML
  private Label labelNbGroupesTP;
  
  /**
   * ListView Liste d'etudiants.
   */
  @FXML
  private ListView<String> listeEtudiants;
  
  /**
   * ListView Liste UE Obligatoire.
   */
  @FXML
  private ListView<String> listeUEObligatoires;
  
  /**
   * ListView Liste UE Optionnelles.
   */
  @FXML
  private ListView<String> listeUEOptionnelles;
  
  /**
   * ToggleGroup Regroupement des boutons type UE.
   */
  @FXML
  private ToggleGroup obligation;
  
  /**
   * RadioButton Bouton UE Obligatoire.
   */
  @FXML
  private RadioButton radioBoutonObligatoire;
  
  /**
   * RadioButton Bouton UE Optionnelle.
   */
  @FXML
  private RadioButton radioBoutonOptionnelle;
  
  /**
   * Methode d'affectation auto des groupes (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonAffectationAutomatique(ActionEvent event) {
    app.getGestionFormation().attribuerAutomatiquementGroupes();
    if (app.getGestionFormation().nombreGroupesTravauxDiriges() != 0 
        && app.getGestionFormation().nombreGroupesTravauxPratiques() != 0) {
      new AlertIcon(Alert.AlertType.INFORMATION, "Affectation automatique effectue").showAndWait();
      rendu();
    } else {
      new AlertIcon(Alert.AlertType.ERROR, 
          "Aucun etudiant ou taille des groupes non defini !"
      ).showAndWait();
    }
  }
  
  /**
   * Methode d'affectation manuelle a un groupe (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonAffectationManuelleGroupes(ActionEvent event) {
    try {
      int td = Integer.parseInt(entreeGroupeTDEtudiant.getText());
      int tp = Integer.parseInt(entreeGroupeTPEtudiant.getText());
      String etudiantSelected = listeEtudiants.getSelectionModel().getSelectedItem();
      for (Etudiant etu : app.getGestionEtudiant().getEtudiants()) {
        if (etu.toString().equals(etudiantSelected)) {
          int res = app.getGestionFormation().changerGroupe(etu, td, tp);
          Alert alert;
          switch (res) {
            case 0:
              alert = new AlertIcon(Alert.AlertType.CONFIRMATION,
                  "Changement effectue");
              break;
            case -1:
              alert = new AlertIcon(Alert.AlertType.INFORMATION,
                  "Seul le changement de TP a ete effectue !");
              break;
            case -2:
              alert = new AlertIcon(Alert.AlertType.INFORMATION,
                  "Seul le changement de TD a ete effectue !");
              break;
            case -3:
              alert = new AlertIcon(Alert.AlertType.ERROR,
                  "Aucun changement n'a pu etre effectue !");
              break;
            default:
              alert = new AlertIcon(Alert.AlertType.ERROR, "Erreur");
              break;
          }
          alert.showAndWait();
        }
      }
    } catch (Exception e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Le nombre entre est invalide.");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'affichage de tout les etudiants d'un TD (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonAfficherEtudiantsGroupeTD(ActionEvent event) {
    try {
      int td = Integer.parseInt(entreeGroupeTDEtudiant.getText());
      listeEtudiants.getItems().clear();
      labelListeEtudiants.setText("Liste etudiants groupe TD numero " + td);
      for (Etudiant etu : app.getGestionFormation()
          .listeEtudiantsGroupeDirige(td)) {
        listeEtudiants.getItems().add(etu.toString());
      }
    } catch (Exception e) {
      Alert alert =
          new AlertIcon(Alert.AlertType.ERROR, "Le nombre entre est invalide.");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'affichage de tout les etudiants d'un TP (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonAfficherEtudiantsGroupeTP(ActionEvent event) {
    try {
      int tp = Integer.parseInt(entreeGroupeTPEtudiant.getText());
      listeEtudiants.getItems().clear();
      labelListeEtudiants.setText("Liste etudiants groupe TP numero " + tp);
      for (Etudiant etu : app.getGestionFormation()
          .listeEtudiantsGroupePratique(tp)) {
        listeEtudiants.getItems().add(etu.toString());
      }
    } catch (Exception e) {
      Alert alert =
          new AlertIcon(Alert.AlertType.ERROR, "Le nombre entre est invalide.");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'affichage de tout les etudiants d'une option (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonAfficherEtudiantsUEOptionnelle(ActionEvent event) {
    String ueSelected = listeUEOptionnelles.getSelectionModel().getSelectedItem();
    for (UniteEnseignement ue : app.getGestionFormation().enseignementsOptionnels()) {
      if (ue.toString().equals(ueSelected)) {
        listeEtudiants.getItems().clear();
        labelListeEtudiants.setText("Liste etudiants UE " + ueSelected);
        for (Etudiant etu : app.getGestionFormation().listeEtudiantsOption(ue)) {
          listeEtudiants.getItems().add(etu.toString());
        }
      }
    }
  }
  
  /**
   * Methode d'affichage de tout les etudiants (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonAfficherTousEtudiants(ActionEvent event) {
    listeEtudiants.getItems().clear();
    if (app.getGestionEtudiant().getEtudiants().size() > 0) {
      labelListeEtudiants.setText("Liste de tout les etudiants ");
    }
    for (Etudiant etu : app.getGestionEtudiant().getEtudiants()) {
      listeEtudiants.getItems().add(etu.toString());
    }
  }
  
  /**
   * Methode de creation d'une formation (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonCreerFormation(ActionEvent event) {
    String nom = entreeNomFormation.getText();
    String nomResp = entreeNomResponsableFormation.getText();
    String mailResp = entreeEmailResponsableFormation.getText();
    Alert alert;
    if (nom == null || nomResp == null || mailResp == null|| nom.isBlank() || nomResp.isBlank() || mailResp.isBlank()) {
      alert = new AlertIcon(Alert.AlertType.ERROR, 
          "Le nom de la formation et/ou du responsable et son adresse e-mail ne peuvent etre vide"
      );
    } else {
      app.getGestionFormation().creerFormation(nom, nomResp, mailResp);
      if (app.getGestionFormation().isFormationDefined()) {
        alert = new AlertIcon(Alert.AlertType.INFORMATION, "La formation a bien ete cree !");
        rendu();
      } else {
        alert = new AlertIcon(Alert.AlertType.ERROR, 
            "L'adresse e-mail du responsable est incorrect"
        );
      }
    }
    alert.showAndWait();
  }
  
  /**
   * Methode de definition du nombre d'options a choisir (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonNombreChoixOptions(ActionEvent event) {
    try {
      int optmin = Integer.parseInt(entreeNombreChoixOptions.getText());
      app.getGestionFormation().definirNombreOptions(optmin);
      rendu();
    } catch (Exception e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Le nombre entre est invalide.");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode de definition de la taille d'un groupe de TD (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonSetTailleGroupeTD(ActionEvent event) {
    try {
      int td = Integer.parseInt(entreeTailleGroupeTD.getText());
      app.getGestionFormation().setTailleGroupeDirige(td);
      rendu();
    } catch (Exception e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Le nombre entre est invalide.");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode de definition de la taille d'un groupe de TP (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonSetTailleGroupeTP(ActionEvent event) {
    try {
      int tp = Integer.parseInt(entreeTailleGroupeTP.getText());
      app.getGestionFormation().setTailleGroupePratique(tp);
      rendu();
    } catch (Exception e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Le nombre entre est invalide.");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'affichage du menu a propos de l'application (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionMenuApropos(ActionEvent event) {
    Alert about = new AlertIcon(Alert.AlertType.INFORMATION, 
        "Logiciel de gestion gérant une année de formation avec\n"
        + "une infinité d'etudiants par fichier.\n"
        + "\nLes fichiers de sauvegardes de cette applicaiton ont pour extension '.wc'.\n"
        + "\nCredits:\nEL OUAZIZI WALID\nCARIOU ERIC"
    );
    about.setTitle("A propos de W-Codeurs");
    about.setHeaderText("W-Codeurs");
    about.showAndWait();
  }
  
  /**
   * Methode de chargement de l'etat de l'application (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionMenuCharger(ActionEvent event) {
    FileChooser fc = new FileChooser();
    fc.setTitle("Selectionner une sauvegarde");
    fc.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("W-Codeurs Save (*.wc)", "*.wc")
    );
    File file = fc.showOpenDialog(null);
    if (file != null) {
      try {
        app.getSave().chargerDonnees(file.getAbsolutePath());
        rendu();
      } catch (IOException e) {
        Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Impossible de charger le fichier");
        alert.showAndWait();
      }
    }
  }
  
  /**
   * Methode de fin de l'application (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    System.exit(0);
  }
  
  /**
   * Methode de sauvegarde de l'etat de l'application (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    FileChooser fc = new FileChooser();
    fc.setTitle("Choisissez un nom pour votre sauvegarde");
    fc.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("W-Codeurs Save (*.wc)", "*.wc")
    );
    File file = fc.showSaveDialog(null);
    if (file != null) {
      Alert alert;
      try {
        app.getSave().sauvegarderDonnees(file.getAbsolutePath());
        alert = new AlertIcon(Alert.AlertType.INFORMATION,
            "Le fichier a bien ete sauvegarder !");
      } catch (IOException e) {
        alert = new AlertIcon(Alert.AlertType.ERROR,
            "Le fichier n'a pas pu etre sauvegarder");
      }
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'affichage de l'etudiant selectionne (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionSelectionEtudiant(MouseEvent event) {
    String etudiantSelected =
        listeEtudiants.getSelectionModel().getSelectedItem();
    for (Etudiant etu : app.getGestionEtudiant().getEtudiants()) {
      if (etu.toString().equals(etudiantSelected)) {
        entreeNomEtudiant.setText(etu.getInformationPersonnelle().getNom());
        
        entreePrenomEtudiant.setText(etu.getInformationPersonnelle().getPrenom());
        
        entreeAdresseEtudiant.setText(etu.getInformationPersonnelle().getAdresse());
        
        entreeAgeEtudiant.setText(
            (etu.getInformationPersonnelle().getAge() == 0) 
                ? "Non defini"
                : "" + etu.getInformationPersonnelle().getAge()
        );
        
        checkInscriptionFinalisee.setSelected(etu.inscriptionFinalisee(
            app.getGestionFormation().getNombreOptions())
        );
        
        entreeGroupeTDEtudiant.setText("" + etu.getTd());
        
        entreeGroupeTPEtudiant.setText("" + etu.getTp());
      }
    }
  }
  
  /**
   * Methode d'affichage d'une UE obligatoire selectionee (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionSelectionUEObligatoire(MouseEvent event) {
    entreeCapaciteAccueil.clear();
    String ueSelected = listeUEObligatoires.getSelectionModel().getSelectedItem();
    for (UniteEnseignement ue : app.getGestionFormation().enseignementsObligatoires()) {
      if (ue.toString().equals(ueSelected)) {
        entreeNomUE.setText(ue.getNom());
        entreeNomResponsableUE.setText(ue.getNomResponsable());
        radioBoutonObligatoire.setSelected(true);
      }
    }
  }
  
  /**
   * Methode d'affichage d'une UE optionnelle selectionee (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionSelectionUEOptionnelle(MouseEvent event) { 
    String ueSelected = listeUEOptionnelles.getSelectionModel().getSelectedItem();
    for (UniteEnseignement ue : app.getGestionFormation().enseignementsOptionnels()) {
      if (ue.toString().equals(ueSelected)) {
        entreeNomUE.setText(ue.getNom());
        entreeNomResponsableUE.setText(ue.getNomResponsable());
        entreeCapaciteAccueil.setText((ue.getCapacite() == -1) ? "Infini" : "" + ue.getCapacite());
        radioBoutonOptionnelle.setSelected(true);
      }
    }
  }
  
  /**
   * Methode de creation de nouvelle UE (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonCreerNouvelleUE(ActionEvent event) {
    String nomUe = entreeNomUE.getText();
    String respUe = entreeNomResponsableUE.getText();
    String capacite = entreeCapaciteAccueil.getText();
    if (nomUe.isBlank() || respUe.isBlank()) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Les informations de l'UE sont incorrects");
      alert.showAndWait();
    } else {
      UniteEnseignement ue = new UniteEnseignement(nomUe, respUe);
      if (radioBoutonObligatoire.isSelected()) {
        app.getGestionFormation().ajouterEnseignementObligatoire(ue);
      } else {
        try {
          int capa = Integer.parseInt(capacite);
          app.getGestionFormation().ajouterEnseignementOptionnel(ue, capa);
        } catch (Exception e) {
          Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Capacite d'acceuil incorrect");
          alert.showAndWait();
        }
      }
    }
    rendu();
  }
  
  /**
   * Methode d'initialisation de la fenetre formation (UI).
   */
  @FXML
  void initialize() {
    checkInscriptionFinalisee.setDisable(true);
    entreeTailleGroupeTD.setDisable(true);
    entreeTailleGroupeTP.setDisable(true);
    entreeNombreChoixOptions.setDisable(true);
  }
  
  /**
   * Methode de rendu de la fenetre (UI).
   */
  void rendu() {
    entreeGroupeTDEtudiant.clear();
    labelListeEtudiants.setText("...");
    entreeGroupeTPEtudiant.clear();
    entreeNombreChoixOptions.clear();
    entreeNomUE.clear();
    entreeNomResponsableUE.clear();
    entreeCapaciteAccueil.clear();
    listeEtudiants.getItems().clear();
    entreeTailleGroupeTD.clear();
    entreeTailleGroupeTP.clear();
    listeUEObligatoires.getItems().clear();
    listeUEOptionnelles.getItems().clear();
    entreeTailleGroupeTD.setDisable(false);
    entreeTailleGroupeTP.setDisable(false);
    entreeNombreChoixOptions.setDisable(false);
    entreeNomFormation.setText(app.getGestionFormation().getNomFormation());
    entreeNomResponsableFormation.setText(app.getGestionFormation().getNomResponsableFormation());
    entreeEmailResponsableFormation.setText(
        app.getGestionFormation().getEmailResponsableFormation()
    );
    if (app.getGestionFormation().getTailleGroupeDirige() != -1) {
      entreeTailleGroupeTD.setText("" + app.getGestionFormation().getTailleGroupeDirige());
      entreeTailleGroupeTD.setDisable(true);
    }
    if (app.getGestionFormation().getTailleGroupePratique() != -1) {
      entreeTailleGroupeTP.setText("" + app.getGestionFormation().getTailleGroupePratique());
      entreeTailleGroupeTP.setDisable(true);
    }
    if (app.getGestionFormation().getNombreOptions() != -1) {
      entreeNombreChoixOptions.setText("" + app.getGestionFormation().getNombreOptions());
      entreeNombreChoixOptions.setDisable(true);
    }
    labelNbGroupesTD.setText("" + app.getGestionFormation().nombreGroupesTravauxDiriges());
    labelNbGroupesTP.setText("" + app.getGestionFormation().nombreGroupesTravauxPratiques());
    for (UniteEnseignement ue : app.getGestionFormation().enseignementsObligatoires()) {
      listeUEObligatoires.getItems().add(ue.toString());
    }
    for (UniteEnseignement ue : app.getGestionFormation().enseignementsOptionnels()) {
      listeUEOptionnelles.getItems().add(ue.toString());
    }
  }
  
  /**
   * Definition de l'application courante.
   *
   * @param app L'application a utiliser dans le systeme.
   */
  void setApp(formation.Application app) {
    this.app = app;
    rendu();
  }
}
