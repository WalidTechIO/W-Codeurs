package ui;

import formation.Application;
import formation.InformationPersonnelle;
import formation.NonConnecteException;
import formation.UniteEnseignement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Le controleur associe a la fenetre definie dans etudiants.fxml.
 *
 * @author Eric Cariou
 * @author EL OUAZIZI Walid
 */
public class EtudiantsControleur {
  
  /**
   * Instance de l'application qui gere le systeme.
   */
  private Application app;
  
  /**
   * Resource du controleur.
   */
  @FXML
  private ResourceBundle resources;
  
  /**
   * Location du controleur.
   */
  @FXML
  private URL location;
  
  /**
   * CheckBox indiquant si l'inscription de l'etudiant est finalise.
   */
  @FXML
  private CheckBox checkInscriptionFinalisee;
  
  /**
   * Champ de saisie adresse de l'etudiant.
   */
  @FXML
  private TextField entreeAdresseEtudiant;
  
  /**
   * Champ de saisie age de l'etudiant.
   */
  @FXML
  private TextField entreeAgeEtudiant;
  
  /**
   * Utilise comme label groupe de TD de l'etudiant.
   */
  @FXML
  private TextField entreeGroupeTD;
  
  /**
   * Utilise comme label groupe de TP de l'etudiant.
   */
  @FXML
  private TextField entreeGroupeTP;
  
  /**
   * Champ de saisie mot de passe de l'etudiant.
   */
  @FXML
  private TextField entreeMotDePasseEtudiant;
  
  /**
   * Champ de saisie nom etudiant.
   */
  @FXML
  private TextField entreeNomEtudiant;
  
  /**
   * Utilise comme label pour nb d'options a choisir pour l'etudiant.
   */
  @FXML
  private TextField entreeNombreOptions;
  
  /**
   * Champ de saisie numero etudiant.
   */
  @FXML
  private TextField entreeNumeroEtudiant;
  
  /**
   * Champ de saisie prenom etudiant.
   */
  @FXML
  private TextField entreePrenomEtudiant;
  
  /**
   * ListView des messages non lus de l'etudiant.
   */
  @FXML
  private ListView<String> listeMessagesNonLus;
  
  /**
   * ListView de tout les messages de l'etudiant.
   */
  @FXML
  private ListView<String> listeTousMessages;
  
  /**
   * ListView des UE optionnelles de la formation courante.
   */
  @FXML
  private ListView<String> listeUEOptionnellesFormation;
  
  /**
   * ListView des UE suivi par l'etudiant connecte.
   */
  @FXML
  private ListView<String> listeUESuiviesEtudiant;
  
  /**
   * Zone affichage du contenu d'un message.
   */
  @FXML
  private TextArea zoneTexteContenuMessage;
  
  /**
   * Methode de choix d'une option pour un etudiant (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonChoisirOption(ActionEvent event) {
    try {
      String ueSelected = listeUEOptionnellesFormation.getSelectionModel().getSelectedItem();
      for (UniteEnseignement ue : app.getGestionFormation().enseignementsOptionnels()) {
        if (ueSelected.equals(ue.toString())) {
          if (app.getGestionEtudiant().choisirOption(ue)) {
            Alert alert = new AlertIcon(Alert.AlertType.INFORMATION, "UE selectionne !");
            alert.showAndWait();
            rendu();
          } else {
            Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Impossible de selectionner cette UE");
            alert.showAndWait();
          }
        }
      }
    } catch (NonConnecteException e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Vous n'etes pas connecte");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode de connexion d'un etudiant (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonConnexion(ActionEvent event) {
    String numEtudiant = entreeNumeroEtudiant.getText();
    String motDePasse = entreeMotDePasseEtudiant.getText();
    int num;
    try {
      num = Integer.parseInt(numEtudiant);
    } catch (Exception e) {
      num = -1;
    }
    if (app.getGestionEtudiant().connexion(num, motDePasse)) {
      Alert alert = new AlertIcon(Alert.AlertType.INFORMATION, "Connecte !");
      alert.showAndWait();
      rendu();
    } else {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Identifiants invalides");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode de deconnexion d'un etudiant (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonDeconnexion(ActionEvent event) {
    try {
      app.getGestionEtudiant().deconnexion();
      zoneTexteContenuMessage.clear();
      entreeNomEtudiant.clear();
      entreePrenomEtudiant.clear();
      entreeAgeEtudiant.clear();
      entreeAdresseEtudiant.clear();
      entreeNumeroEtudiant.clear();
      entreeMotDePasseEtudiant.clear();
      entreeGroupeTD.clear();
      entreeGroupeTP.clear();
      entreeNombreOptions.clear();
      listeUESuiviesEtudiant.getItems().clear();
      listeUEOptionnellesFormation.getItems().clear();
      listeMessagesNonLus.getItems().clear();
      listeTousMessages.getItems().clear();
    } catch (NonConnecteException e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Vous n'etes pas connecte");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'inscritpion d'un etudiant (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonInscription(ActionEvent event) {
    InformationPersonnelle infos;
    String nom = entreeNomEtudiant.getText();
    String prenom = entreePrenomEtudiant.getText();
    String adresse = entreeAdresseEtudiant.getText();
    String age = entreeAgeEtudiant.getText();
    String motDePasse = entreeMotDePasseEtudiant.getText();
    try {
      if (age.isBlank() || adresse.isBlank()) {
        infos = new InformationPersonnelle(nom, prenom);
      } else {
        infos = new InformationPersonnelle(nom, prenom, adresse, Integer.parseInt(age));
      }
      int num = app.getGestionEtudiant().inscription(infos, motDePasse);
      if (num == -1) {
        Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Votre inscription a echoue");
        alert.showAndWait();
      } else {
        Alert alert = new AlertIcon(Alert.AlertType.INFORMATION, 
            "Votre inscription a ete effectue, num etudiant: " + num
        );
        alert.showAndWait();
        entreeNomEtudiant.clear();
        entreePrenomEtudiant.clear();
        entreeAdresseEtudiant.clear();
        entreeAgeEtudiant.clear();
        entreeMotDePasseEtudiant.clear();
        entreeNumeroEtudiant.setText("" + num);
      }
    } catch (Exception e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, 
          "Les informations que vous avez entre sont incorrects"
      );
      alert.showAndWait();
    }
  }
  
  /**
   * Methode de rerendu lors du click sur rafraichir les messages (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionBoutonRafraichirListesMessages(ActionEvent event) {
    rendu();
  }
  
  /**
   * Methode d'affichage message non lus (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionSelectionMessageListeMessagesNonLus(MouseEvent event) {
    try {
      String selectedMsg = listeMessagesNonLus.getSelectionModel().getSelectedItem();
      for (String s : app.getGestionEtudiant().listeMessageNonLus()) {
        if (selectedMsg != null 
            && selectedMsg.equals(s.replace("\n", " - ").substring(0, 45) + "...")) {
          zoneTexteContenuMessage.setText(s);
          app.getGestionEtudiant().getLogged().marquerMessageLu(s.replace("♦ ", ""));
        }
      }
    } catch (NonConnecteException e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Vous n'etes pas connecte !");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'affichage message quelconque (UI).
   *
   * @param event L'evenement a l'origine de l'action.
   */
  @FXML
  void actionSelectionMessageListeTousMessages(MouseEvent event) {
    try {
      String selectedMsg = listeTousMessages.getSelectionModel().getSelectedItem();
      for (String s : app.getGestionEtudiant().listeTousMessages()) {
        if (selectedMsg != null 
            && selectedMsg.equals(s.replace("\n", " - ").substring(0, 45) + "...")) {
          zoneTexteContenuMessage.setText(s);
          app.getGestionEtudiant().getLogged().marquerMessageLu(s.replace("♦ ", ""));
        }
      }
    } catch (NonConnecteException e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Vous n'etes pas connecte !");
      alert.showAndWait();
    }
  }
  
  /**
   * Methode d'initialisation de la fenetre etudiant (UI).
   */
  @FXML
  void initialize() {
    checkInscriptionFinalisee.setDisable(true);
  }
    
  /**
   * Methode de rendu de la fenetre etudiant (UI).
   */
  void rendu() {
    try {
      entreeMotDePasseEtudiant.clear();
      zoneTexteContenuMessage.clear();
      listeMessagesNonLus.getItems().clear();
      listeTousMessages.getItems().clear();
      listeUESuiviesEtudiant.getItems().clear();
      listeUEOptionnellesFormation.getItems().clear();
      
      entreeNomEtudiant.setText(
          app.getGestionEtudiant().getInformationPersonnelle().getNom()
      );
      
      entreePrenomEtudiant.setText(
          app.getGestionEtudiant().getInformationPersonnelle().getPrenom()
      );
      
      entreeAdresseEtudiant.setText(
          app.getGestionEtudiant().getInformationPersonnelle().getAdresse()
      );
      
      entreeAgeEtudiant.setText(
          (app.getGestionEtudiant().getInformationPersonnelle().getAge() == 0)
              ? "Non defini" 
              : "" + app.getGestionEtudiant().getInformationPersonnelle().getAge()
      );
      
      entreeGroupeTD.setText(
          (app.getGestionEtudiant().getNumeroGroupeTravauxDiriges() == -1) 
              ? "Non defini" 
              : "" + app.getGestionEtudiant().getNumeroGroupeTravauxDiriges()
      );
      
      entreeGroupeTP.setText(
          (app.getGestionEtudiant().getNumeroGroupeTravauxPratiques() == -1) 
              ? "Non defini" 
              : "" + app.getGestionEtudiant().getNumeroGroupeTravauxPratiques()
      );
      
      entreeNombreOptions.setText(
          (app.getGestionEtudiant().nombreOptions() == -1) 
              ? "Non defini" 
              : "" + app.getGestionEtudiant().nombreOptions()
      );
      
      checkInscriptionFinalisee.setSelected(app.getGestionEtudiant().inscriptionFinalisee());
      
      for (UniteEnseignement ue : app.getGestionEtudiant().enseignementsSuivis()) {
        listeUESuiviesEtudiant.getItems().add(ue.toString());
      }
      
      for (UniteEnseignement ue : app.getGestionFormation().enseignementsOptionnels()) {
        listeUEOptionnellesFormation.getItems().add(ue.toString());
      }
      
      for (String msg : app.getGestionEtudiant().listeTousMessages()) {
        listeTousMessages.getItems().add(msg.replace("\n", " - ").substring(0, 45) + "...");
      }
      
      for (String msg : app.getGestionEtudiant().listeMessageNonLus()) {
        listeMessagesNonLus.getItems().add(msg.replace("\n", " - ").substring(0, 45) + "...");
      }
      
    } catch (NonConnecteException e) {
      Alert alert = new AlertIcon(Alert.AlertType.ERROR, "Vous n'etes pas connecte");
      alert.showAndWait();
    }
  }
  
  /**
   * Definition de l'application courante.
   *
   * @param app L'application a utiliser dans le systeme.
   */
  void setApp(Application app) {
    this.app = app;
  }
  
}
