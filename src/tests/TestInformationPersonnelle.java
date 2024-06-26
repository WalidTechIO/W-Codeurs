package tests;

import formation.InformationPersonnelle;
import formation.InformationPersonnelleException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests JUnit de la classe {@link formation.InformationPersonnelle InformationPersonnelle}.
 *
 * @author Eric Cariou
 * @see formation.InformationPersonnelle
 */
public class TestInformationPersonnelle {
  
  /**
   * Une information basique : prenom et nom.
   */
  private InformationPersonnelle infoBasique;
  /**
   * Une information complete : prenom, nom, adresse et age.
   */
  private InformationPersonnelle infoComplete;
  
  /**
   * Instancie une information basique et une complete pour les tests.
   *
   * @throws Exception ne peut pas etre levee ici
   */
  @BeforeEach
  void setUp() throws Exception {
    infoBasique = new InformationPersonnelle("Skywalker", "Luke");
    infoComplete = new InformationPersonnelle("Skywalker", "Luke", "Planete Tatooine", 20);
  }
  
  /**
   * Ne fait rien apres les tests : a modifier au besoin.
   *
   */
  @AfterEach
  void tearDown() {}
  
  /**
   * Verifie que l'on peut positionner un age de 25 ans.
   */
  @Test
  void testAge25Basique() {
    infoBasique.setAge(25);
    assertEquals(infoBasique.getAge(), 25);
  }
  
  /**
   * Verifie qu'on ne peut pas positionner un age negatif sur une information basique.
   */
  @Test
  void testAgeNegatifBasique() {
    infoBasique.setAge(-20);
    assertTrue(infoBasique.getAge() != -20);
  }
  
  /**
   * Verifie qu'on ne peut pas positionner un age negatif sur une 
   * information complete : l'age reste le meme qu'avant.
   */
  @Test
  void testAgeNegatifComplet() {
    int age = infoComplete.getAge();
    infoComplete.setAge(-20);
    assertEquals(infoComplete.getAge(), age);
  }
  
  /**
   * Verifie qu'une adresse n'est pas null quand on cree une information personnelle.
   */
  @Test
  void testAdresseNonNull() {
      assertNotNull(infoBasique.getAdresse());
      assertNotNull(infoComplete.getAdresse());
  }
  
  /**
   * Verifie qu'on ne peut pas positionner une adresse null sur une information existante.
   */
  @Test
  void testSetterAdresseNull() {
    infoComplete.setAdresse(null);
      assertNotNull(infoComplete.getAdresse());
  }
  
  /**
   * Verifie que les parametres des constructeurs sont correctement geres.
   *
   * @throws InformationPersonnelleException Si le nom ou le prenom sont vides
   */
  @Test
  void testConstructeur() throws InformationPersonnelleException {
    InformationPersonnelle inf = new InformationPersonnelle("Vador", "Dark", null, -30);
    assertEquals(inf.getNom(), "Vador");
    assertEquals(inf.getPrenom(), "Dark");
      assertNotNull(inf.getAdresse());
    assertTrue(inf.getAge() >= 0);
  }
  
  /**
   * Verifie que le constructeur lance correctement une exception lorsque le nom
   * ou le prenom est vide.
   * Teste la creation d'une InformationPersonnelle avec un nom vide, ce qui devrait
   * lever une exception.
   */
  @Test
  void testConstructeurException() {
    assertThrows(InformationPersonnelleException.class, () -> infoBasique = new InformationPersonnelle("", null));
  }
}
