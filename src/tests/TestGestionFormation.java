package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import formation.Application;
import formation.Etudiant;
import formation.GestionEtudiant;
import formation.GestionFormation;
import formation.InformationPersonnelle;
import formation.InformationPersonnelleException;
import formation.UniteEnseignement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link formation.GestionFormation GestionFormation}.
 *
 * @author Gloria IGIRANEZA
 * @author EL OUAZIZI Walid
 * @see formation.GestionFormation
 */
public class TestGestionFormation {
  
  /**
   * Application pour les tests.
   */
  Application app;
  
  /**
   * UniteEnseignement pour les tests.
   */
  UniteEnseignement ue;
  
  /**
   * GestionFormation testee.
   */
  GestionFormation gf;
  /**
   * GestionEtudiant testee.
   */
  GestionEtudiant ge;
  
  /**
   * Avant chaque test.
   *
   * @throws Exception ne sera pas levee.
   */
  @BeforeEach
  void setUp() throws Exception {
    app = new Application();
    gf = app.getGestionFormation();
    ge = app.getGestionEtudiant();
    gf.creerFormation("L3 informatique", "Dark Vador", "dark.vador@empire.com");
    UniteEnseignement ue = new UniteEnseignement("Java 2", "Mickaël Kerboeuf");
    gf.ajouterEnseignementObligatoire(ue);
    ue = new UniteEnseignement("Conception d'applications", "Eric Cariou");
    gf.ajouterEnseignementObligatoire(ue);
    ue = new UniteEnseignement("Programmation C avancee", "Stephane Rubini");
    gf.ajouterEnseignementObligatoire(ue);
    ue = new UniteEnseignement("Objets connectes et robotique", "Yvon Autret");
    gf.ajouterEnseignementOptionnel(ue, 3);
    ue = new UniteEnseignement("Administration systeme", "Laurent Nana");
    gf.ajouterEnseignementOptionnel(ue, 3);
    gf.setTailleGroupeDirige(3);
    gf.setTailleGroupePratique(2);
    gf.definirNombreOptions(1);
  }
  
  
  /**
   * Verifie que la formation a correctement ete cree.
   */
  @Test
  public void testCreerFormation() {
    assertEquals("L3 informatique", gf.getNomFormation());
    assertEquals("Dark Vador", gf.getNomResponsableFormation());
    assertEquals("dark.vador@empire.com", gf.getEmailResponsableFormation());
  }
  
  /**
   * Tests pour verifier le comportement de la methode lors 
   * de la creation d'une formation avec des parametres vides.
   */
  @Test
  public void testCreerFormationEchec() {
    // Tentative de creation de la formation avec des parametres vides
    gf.creerFormation("", "Dark Vador", "dark.vador@empire.com");
    assertFalse(gf.getNomFormation().isBlank());

    gf.creerFormation("L3 informatique", "", "dark.vador@empire.com");
    assertFalse(gf.getNomResponsableFormation().isBlank());

    gf.creerFormation("L3 informatique", "Dark Vador", "");
    assertFalse(gf.getEmailResponsableFormation().isBlank());
  }
  
  /**
   * Verifie l'ajout d'une UE obligatoire.
   */
  @Test
  public void testAjouterEnseignementObligatoire() {
    assertEquals(3, gf.enseignementsObligatoires().size());
    UniteEnseignement ueTest =
        new UniteEnseignement("Nouvelle UE", "Nouveau Enseignant");
    gf.ajouterEnseignementObligatoire(ueTest);
    assertEquals(4, gf.enseignementsObligatoires().size());
  }


  /**
   * Verifie l'ajout d'une meme UE obligatoire.
   */
  @Test
  public void testAjouterEnseignementObligatoireEchec() {
    // 1. Tentative d'ajout d'une UE obligatoire (devrait reussir)
    assertTrue("L'ajout d'une UE obligatoire devrait reussir.",
        gf.ajouterEnseignementObligatoire(ue));
    
    // 2. Tentative d'ajout de la meme UE obligatoire (devrait echouer)
    assertFalse("L'ajout de la meme UE obligatoire devrait echouer.",
        gf.ajouterEnseignementObligatoire(ue));
  }
  
  /**
   * Verifie l'ajout d'une UE optionnelle avec une capacite de 3 places
   * fonctionne correctement..
   */
  @Test
  public void testAjouterEnseignementOptionnel() {
    assertEquals(2, gf.enseignementsOptionnels().size());
    UniteEnseignement ueOpt = new UniteEnseignement("Objets connectes et robotique", "Yvon Autret");
    gf.ajouterEnseignementOptionnel(ueOpt, 3);
    assertEquals(3, gf.enseignementsObligatoires().size());
  }
  
  /**
   * Verifie l'ajout d'une UE deja existante.
   */
  @Test
  public void testAjoutUeExistante() {
    assertTrue(gf.ajouterEnseignementObligatoire(ue));
    assertFalse(gf.ajouterEnseignementObligatoire(ue)); // Ajout d'une UE deja existante
  }
  
  
  /**
   * Verifie la definition du nombre d'options requises.
   */
  @Test
  public void testDefinirNombreOptions() {
    gf.creerFormation("Licence 3 Informatique", "Gloria", "glo@resp.fr");
    assertEquals(-1, gf.getNombreOptions());
    gf.definirNombreOptions(3);
    assertEquals(3, gf.getNombreOptions());
  }
  
  
  /**
   * Verifie la definition d'un nombre d'options negatif.
   */
  @Test
  public void testDefinirNombreOptionsNegatif() {
    gf.definirNombreOptions(-1);
    assertNotEquals(-1, gf.getNombreOptions());
  }
  
  /**
   * Verifie la definition d'un nombre d'options deja defini.
   */
  @Test
  public void testDefinirNombreOptionsDejaDefini() {
    gf.creerFormation("Licence 3 Informatique", "Gloria", "glo@resp.fr");
    gf.definirNombreOptions(5);
    // Tentative de redefinir le nombre d'options
    gf.definirNombreOptions(2);
    // Verification que le nombre d'options reste inchange
    assertEquals(5, gf.getNombreOptions());
  }

  
  /**
   * Verifie la definition de la taille d'un groupe de TD.
   */
  @Test
  public void testSetGetTailleGroupeDirige() {
    gf.creerFormation("Licence 3 Informatique", "Gloria", "glo@resp.fr");
    gf.setTailleGroupeDirige(5);
    assertEquals(5, gf.getTailleGroupeDirige());
  }
  
  /**
   * Verifie la definition de la taille d'un groupe de TP.
   */
  @Test
  public void testSetGetTailleGroupePratique() {
    gf.creerFormation("Licence 3 Informatique", "Gloria", "glo@resp.fr");
    gf.setTailleGroupePratique(15);
    assertEquals(15, gf.getTailleGroupePratique());
  }
  
  /**
   * Verifie la definition de la taille d'un groupe de TD negatif.
   */
  @Test
  public void testSetTailleGroupeDirigeNegatif() {
    gf.setTailleGroupeDirige(-5);
    assertEquals(3, gf.getTailleGroupeDirige());
  }
  
  /**
   * Verifie la definition de la taille d'un groupe de TP a 0.
   */
  @Test
  public void testSetTailleGroupePratiqueNull() {
    gf.setTailleGroupePratique(0);
    assertEquals(2, gf.getTailleGroupePratique());
  }
  
  /**
   * Verifie que l'on ne peut definir qu'une fois la taille d'un grp dirige.
   */
  @Test
  public void testSetGetTailleGroupeDirigeDejaDefini() {
    gf.creerFormation("Licence 3 Informatique", "Gloria", "glo@resp.fr");
    gf.setTailleGroupeDirige(5);
    gf.setTailleGroupeDirige(15);
    assertEquals(5, gf.getTailleGroupeDirige());
  }
  
  /**
   * Verifie que l'on ne peut definir qu'une fois la taille d'un grp pratique.
   */
  @Test
  public void testSetGetTailleGroupePratiqueDejaDefini() {
    gf.creerFormation("Licence 3 Informatique", "Gloria", "glo@resp.fr");
    gf.setTailleGroupePratique(3);
    gf.setTailleGroupePratique(10);
    assertEquals(3, gf.getTailleGroupePratique());
  }
  
  
  /**
   * Verifie l'attribution automatique des groupes.
   */
  @Test
  public void testAttribuerAutomatiquementGroupes() {
    gf.setTailleGroupeDirige(3);
    gf.setTailleGroupePratique(2);
    gf.attribuerAutomatiquementGroupes();
    for (Etudiant etu : app.getGestionEtudiant().getEtudiants()) {
      assertTrue(etu.getTd() != -1 && etu.getTp() != -1);
    }
  }
  
  /**
   * Teste le changement manuelle de groupe d'un etudiant.
   *
   * @throws InformationPersonnelleException ne sera pas levee.
   */
  @Test
  public void testChangerGroupe()
      throws InformationPersonnelleException {
    gf.creerFormation("Formation", "Testeur", "testeur@formation.fr");
    gf.setTailleGroupeDirige(2);
    gf.setTailleGroupePratique(1);
    ge.inscription(new InformationPersonnelle("Prenom", "testChangerGroupe1"),
        "00");
    ge.inscription(new InformationPersonnelle("Prenom", "testChangerGroupe2"),
        "00");
    ge.inscription(new InformationPersonnelle("Prenom", "testChangerGroupe3"),
        "00");
    gf.attribuerAutomatiquementGroupes();
    assertEquals(
        gf.changerGroupe(ge.getEtudiants().stream()
            .filter(e -> e.getNumeroEtudiant() == 1).findFirst().get(), 2, -1),
        -2);
  }
  
  
  /**
   * Verifie que le nombre de groupes de TD est a 0 a l'initialisation.
   */
  @Test
  public void testNombreGroupesTravauxDiriges() {
    assertEquals(gf.nombreGroupesTravauxDiriges(), 0);
  }
  
  /**
   * Verifie que le nombre de groupes de TP est a 0 a l'initialisation.
   */
  @Test
  public void testNombreGroupesTravauxPratiques() {
    assertEquals(gf.nombreGroupesTravauxPratiques(), 0);
  }
  
  /**
   * Verifie que la liste des etudiants d'un groupe de TD inexistant est null.
   */
  @Test
  public void testListeEtudiantsGroupeDirige() {
    assertTrue(gf.listeEtudiantsGroupeDirige(0) == null);
  }
  
  /**
   * Verifie que la liste des etudiants d'un groupe de TP inexistant est null.
   */
  @Test
  public void testListeEtudiantsGroupePratique() {
    assertTrue(gf.listeEtudiantsGroupePratique(0) == null);
  }
  
  /**
   * Verifie que la liste des etudiants d'une option inexistante est null.
   */
  @Test
  public void testListeEtudiantsOption() {
    assertTrue(gf.listeEtudiantsOption(ue) == null);
  }
  
  /**
   * Verifie l'ajout d'une UE obligatoire.
   */
  @Test
  public void testEnseignementsObligatoires() {
    UniteEnseignement ue = new UniteEnseignement("UE", "testEnseignementsObligatoires");
    gf.ajouterEnseignementObligatoire(ue);
    assertTrue(gf.enseignementsObligatoires().contains(ue));
  }
  
  /**
   * Verifie l'ajout d'une UE optionnelle.
   */
  @Test
  public void testEnseignementsOptionnels() {
    UniteEnseignement ue = new UniteEnseignement("UE", "testEnseignementsOptionnels");
    gf.ajouterEnseignementOptionnel(ue, -1);
    assertTrue(gf.enseignementsOptionnels().contains(ue));
  }
  
  
  
}
