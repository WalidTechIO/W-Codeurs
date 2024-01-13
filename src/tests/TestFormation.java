package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import formation.Formation;
import formation.UniteEnseignement;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link formation.Formation Formation}.
 *
 * @author EL OUAZIZI Walid
 * @see formation.Formation
 */
public class TestFormation {
  
  /**
   * Formation testee.
   */
  private Formation formation;
  
  /**
   * Avant chaque test, initialise une nouvelle instance de Formation.
   */
  @BeforeEach
  public void setUp() {
    formation =
        new Formation("Informatique", "John Doe", "john.doe@formation.fr");
  }
  
  /**
   * Verifie que la formation a correctement ete creee.
   */
  @Test
  public void testConstructeur() {
    assertEquals("Informatique", formation.getNomFormation());
  }
  
  /**
   * Verifie la bonne recuperation du nom du responsable de la formation.
   */
  @Test
  public void testNomResponsable() {
    assertEquals("John Doe", formation.getNomResponsable());
  }
  
  /**
   * Verifie la bonne recuperation de l'email du responsable de la formation.
   */
  @Test
  public void testEmailResponsable() {
    assertEquals("john.doe@formation.fr", formation.getEmailResponsable());
  }
  
  /**
   * Verifie la valeur initiale de TP.
   */
  @Test
  public void testTpInitial() {
    assertEquals(0, formation.getTp());
  }
  
  /**
   * Verifie la valeur initiale de TD.
   */
  @Test
  public void testTdInitial() {
    assertEquals(0, formation.getTd());
  }
  
  /**
   * Verifie la valeur initiale de TPmax.
   */
  @Test
  public void testTpmaxInitial() {
    assertEquals(-1, formation.getTpmax());
  }
  
  /**
   * Verifie la valeur initiale de TDmax.
   */
  @Test
  public void testTdmaxInitial() {
    assertEquals(-1, formation.getTdmax());
  }
  
  /**
   * Verifie la valeur initiale de Optmin.
   */
  @Test
  public void testOptminInitial() {
    assertEquals(-1, formation.getOptmin());
  }
  
  /**
   * Verifie que la liste des UE n'est pas nulle.
   */
  @Test
  public void testUesNonNul() {
    assertNotNull(formation.getUes());
  }
  
  /**
   * Verifie que la liste des UE est vide initialement.
   */
  @Test
  public void testUesVide() {
    assertTrue(formation.getUes().isEmpty());
  }
  
  /**
   * Modifie la valeur de TP.
   */
  @Test
  public void testSetTp() {
    formation.setTp(2);
    assertEquals(2, formation.getTp());
  }
  
  /**
   * Modifie la valeur de TD.
   */
  @Test
  public void testSetTd() {
    formation.setTd(3);
    assertEquals(3, formation.getTd());
  }
  
  /**
   * Modifie la valeur de TPmax.
   */
  @Test
  public void testSetTpmax() {
    formation.setTpmax(30);
    assertEquals(30, formation.getTpmax());
  }
  
  /**
   * Modifie la valeur de TDmax.
   */
  @Test
  public void testSetTdmax() {
    formation.setTdmax(40);
    assertEquals(40, formation.getTdmax());
  }
  
  /**
   * Modifie la valeur de Optmin.
   */
  @Test
  public void testSetOptmin() {
    formation.setOptmin(1);
    assertEquals(1, formation.getOptmin());
  }
  
  /**
   * Ajoute des UE a la liste et verifie si elles sont correctement recuperees.
   */
  @Test
  public void testListeUe() {
    UniteEnseignement ue1 =
        new UniteEnseignement("Mathematiques", "Responsable");
    UniteEnseignement ue2 =
        new UniteEnseignement("Informatique", "Responsable");
    
    formation.getUes().add(ue1);
    formation.getUes().add(ue2);
    
    Set<UniteEnseignement> uesAttendues = new HashSet<>();
    uesAttendues.add(ue1);
    uesAttendues.add(ue2);
    
    assertEquals(uesAttendues, formation.getUes());
  }
}
