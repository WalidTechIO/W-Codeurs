package ui;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Classe qui lance l'interface graphique.
 *
 * @author Eric Cariou
 */
public final class MainInterfaceUtilisateur extends javafx.application.Application {  
  
  /**
   * Instance de l'application utilisee par le systeme.
   */
  private formation.Application app;
  
  /**
   * Fic debut
   */
  public static String ficDebut = "";
  
  /**
   * Affiche la fenetre de gestion des etudiants.
   */
  public void startFenetreEtudiants() {
    try {
      URL url = getClass().getResource("etudiants.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(url);
      VBox root = (VBox) fxmlLoader.load();
      EtudiantsControleur ec = fxmlLoader.getController();
      ec.setApp(app);
      
      Scene scene = new Scene(root, 920, 500);
      
      Stage stage = new Stage();
      
      stage.setOnCloseRequest(e -> {
        System.exit(0);
      });
      stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
      
      stage.setResizable(true);
      stage.setTitle("Gestion des etudiants");
      
      stage.setScene(scene);
      stage.show();
      
    } catch (IOException e) {
      System.err.println("Erreur au chargement de la fenetre etudiants : " + e);
    }
  }
  
  /**
   * Affiche la fenetre de gestion de formation.
   *
   * @param primaryStage le param�tre pass� par JavaFX pour la fen�tre
   *        principale
   */
  public void startFenetreFormation(Stage primaryStage) {
    try {
      URL url = getClass().getResource("formation.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader(url);
      VBox root = (VBox) fxmlLoader.load();
      FormationControleur fc = fxmlLoader.getController();
      fc.setApp(app);
      
      Scene scene = new Scene(root, 930, 590);
      
      primaryStage.setOnCloseRequest(e -> {
        System.exit(0);
      });
      primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
      
      primaryStage.setScene(scene);
      primaryStage.setResizable(true);
      primaryStage.setTitle("Gestion de formation");
      primaryStage.show();
      
    } catch (IOException e) {
      System.err.println("Erreur au chargement de la fenetre formation : " + e);
    }
  }
  
  @Override
  public void start(Stage primaryStage) {
    app = new formation.Application();
    if(!ficDebut.isEmpty())
      try {
        app.getSave().chargerDonnees(ficDebut);
      } catch (IOException e) {
        System.exit(-1);
      }
    // Lancement des 2 fenetres de l'application
    this.startFenetreFormation(primaryStage);
    this.startFenetreEtudiants();
  }
  
  /**
   * Methode de lancement de l'UI.
   *
   * @param args arguments de lancement.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
