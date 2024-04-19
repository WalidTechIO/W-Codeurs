package tests;

import formation.UniteEnseignement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests JUnit de la classe {@link formation.UniteEnseignement
 * UniteEnseignement}.
 *
 * @author EL OUAZIZI Walid
 * @see formation.UniteEnseignement
 */
public class TestUniteEnseignement {
  
  /**
   * L'unite d'enseignement a tester.
   */
  private UniteEnseignement ue;
  
  /**
   * Initialisation avant chaque test : creation d'une unite d'enseignement.
   */
  @BeforeEach
  public void setUp() {
    ue = new UniteEnseignement("Mathematiques", "John Doe");
  }
  
  /**
   * Teste le constructeur de l'unite d'enseignement.
   */
  @Test
  public void testConstructeur() {
    assertEquals("Mathematiques", ue.getNom());
    assertEquals("John Doe", ue.getNomResponsable());
    assertEquals(UniteEnseignement.TypeUe.OBG, ue.getType());
  }
  
  /**
   * Verifie que la capacite initiale est correcte.
   */
  @Test
  public void testCapaciteInitiale() {
    assertEquals(-1, ue.getCapacite());
  }
  
  /**
   * Verifie que le type initial est correct.
   */
  @Test
  public void testTypeInitiale() {
    assertEquals(UniteEnseignement.TypeUe.OBG, ue.getType());
  }
  
  /**
   * Teste le changement en optionnel avec une capacite positive.
   */
  @Test
  public void testSetOptionnelCapacitePositive() {
    ue.setOptionnel(30);
    assertEquals(UniteEnseignement.TypeUe.OPT, ue.getType());
    assertEquals(30, ue.getCapacite());
  }
  
  /**
   * Teste le changement en optionnel avec une capacite negative.
   */
  @Test
  public void testSetOptionnelCapaciteNegative() {
    ue.setOptionnel(-5);
    assertEquals(UniteEnseignement.TypeUe.OPT, ue.getType());
    assertEquals(-1, ue.getCapacite());
  }
  
  /**
   * Teste la representation textuelle sans capacite.
   */
  @Test
  public void testToStringSansCapacite() {
    assertEquals("Mathematiques - par John Doe", ue.toString());
  }
  
  /**
   * Teste la representation textuelle avec capacite.
   */
  @Test
  public void testToStringAvecCapacite() {
    // Verifie la representation textuelle avec capacite.
    UniteEnseignement ue = new UniteEnseignement("Mathematiques", "John Doe");
    ue.setOptionnel(25);
    assertEquals("Mathematiques - par John Doe - Limite a 25 places.", ue.toString());
    
    // Verifie egalement le cas ou la capacite est -1.
    ue = new UniteEnseignement("Mathematiques", "John Doe");
    ue.setOptionnel(-1);
    assertEquals("Mathematiques - par John Doe", ue.toString());
  }
  
  /**
   * Teste l'egalite entre deux unites d'enseignement.
   */
  @Test
  public void testEquals() {
    UniteEnseignement autreUe =
        new UniteEnseignement("Mathematiques", "John Doe");
      assertEquals(ue, autreUe);
  }
  
  /**
   * Teste l'egalite avec un objet de type different.
   */
  @Test
  public void testEqualsObjetDifferent() {
      assertNotEquals("Mathematiques", ue);
  }
  
  /**
   * Teste l'egalite avec des noms differents.
   */
  @Test
  public void testEqualsNomDifferents() {
    UniteEnseignement autreUe =
        new UniteEnseignement("Informatique", "John Doe");
      assertNotEquals(ue, autreUe);
  }
  
  /**
   * Teste l'egalite avec des noms de responsables differents.
   */
  @Test
  public void testEqualsNomResponsableDifferents() {
    UniteEnseignement autreUe =
        new UniteEnseignement("Mathematiques", "Jane Doe");
      assertNotEquals(ue, autreUe);
  }
  
  /**
   * Verifie que les objets egaux ont le meme code de hachage.
   */
  @Test
  public void testHashCode() {
    UniteEnseignement autreUe = new UniteEnseignement("Mathematiques", "John Doe");
    assertEquals(ue.hashCode(), autreUe.hashCode());
  }
}
