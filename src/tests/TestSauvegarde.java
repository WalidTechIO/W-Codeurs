package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import formation.Application;
import formation.GestionEtudiant;
import formation.GestionFormation;
import io.Sauvegarde;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests JUnit de la classe {@link io.Sauvegarde Sauvegarde}.
 *
 * @author EL OUAZIZI Walid
 * @see io.Sauvegarde
 */
public class TestSauvegarde {
  
  /**
   * Application testee.
   */
  private Application app;
  
  /**
   * GestionFormation sauvegarde.
   */
  private GestionFormation gf;
  
  /**
   * GestionEtudiant sauvegarde.
   */
  private GestionEtudiant ge;
  
  /**
   * Classe testee.
   */
  private Sauvegarde sauvegarde;
  
  /**
   * Avant chaque test, instancie une application, une gestion de formation,
   * une gestion d'etudiants et une sauvegarde.
   */
  @BeforeEach
  public void setUp() {
    app = new Application();
    gf = new GestionFormation(app);
    ge = new GestionEtudiant(app);
    sauvegarde = new Sauvegarde(app);
  }
  
  /**
   * Teste la sauvegarde et le chargement des donnees.
   */
  @Test
  public void testSauvegarderEtChargerDonnees() {
    try {
      sauvegarde.sauvegarderDonnees("test.wc");
      Sauvegarde nouvelleSauvegarde =
          new Sauvegarde(app);
      nouvelleSauvegarde.chargerDonnees("test.wc");
      assertEquals(gf, nouvelleSauvegarde.app.getGestionFormation());
      assertEquals(ge, nouvelleSauvegarde.app.getGestionEtudiant());
      new File("test.wc").delete();
    } catch (IOException e) {
      fail("Erreur lors de la sauvegarde ou du chargement des donnees.");
    }
  }
  
  /**
   * Teste le chargement des donnees avec un fichier inexistant.
   */
  @Test
  public void testChargerDonneesAvecFichierInexistant() {
    assertThrows(IOException.class,
        () -> sauvegarde.chargerDonnees("fichier_inexistant.dat"));
  }
}
