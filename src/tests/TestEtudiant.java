package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import formation.Etudiant;
import formation.InformationPersonnelle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link formation.Etudiant Etudiant}.
 *
 * @author EL OUAZIZI Walid
 * @see formation.Etudiant
 */

public class TestEtudiant {
  
  /**
   * Infos de test.
   */
  private InformationPersonnelle infos;
  
  /**
   * Etudiant de test.
   */
  private Etudiant etudiant;
  
  /**
   * Avant tout les test on instancie une information personnelle et un etudiant.
   *
   * @throws Exception ne peut pas etre levee ici
   */
  @BeforeEach
  void setUp() throws Exception {
    infos = new InformationPersonnelle("Doe", "John");
    etudiant = new Etudiant(infos, "0000", 1);
  }
  
  /**
   * Verifie que le constructeur initialise correctement un etudiant.
   */
  @Test
  void testConstructeur() {
    InformationPersonnelle infos = etudiant.getInformationPersonnelle();
    assertTrue(infos.equals(this.infos) 
        && infos.getPrenom() == "John" 
        && infos.getNom() == "Doe" 
        && etudiant.getPassword() == "0000" 
        && etudiant.getNumeroEtudiant() == 1);
  }
  
  /**
   * Verifie que les UE sont vides a l'initialisation.
   */
  @Test
  void testGetUes() {
    assertTrue(etudiant.getUes().isEmpty());
  }
  
  /**
   * Verifie que Td est initialise a -1.
   */
  @Test
  void testGetTd() {
    assertEquals(-1, etudiant.getTd());
  }
  
  /**
   * Verifie que Tp est initialise a -1.
   */
  @Test
  void testGetTp() {
    assertEquals(-1, etudiant.getTp());
  }
  
  /**
   * Verifie que la liste des messages est vide a l'initialisation.
   */
  @Test
  void testGetMessages() {
    assertTrue(etudiant.getMessages().isEmpty());
  }
  
  /**
   * Verifie que l'etudiant reçoit correctement un message.
   */
  @Test
  void testRecevoirMessage() {
    String s = "Message";
    etudiant.recevoirMessage(s);
    assertTrue(etudiant.getMessages().keySet().contains(s));
  }
  
  /**
   * Verifie que l'etudiant peut marquer un message comme lu.
   */
  @Test
  void testMarquerMessageLu() {
    String s = "Message";
    etudiant.recevoirMessage(s);
    etudiant.marquerMessageLu(s);
    assertTrue(!etudiant.getMessages().values().contains(false));
  }
  
  /**
   * Modifie le groupe de TD de l'etudiant a 10.
   */
  @Test
  void testSetTd() {
    etudiant.setTd(10);
    assertEquals(etudiant.getTd(), 10);
  }
  
  /**
   * Modifie le groupe de TP de l'etudiant a 10.
   */
  @Test
  void testSetTp() {
    etudiant.setTp(10);
    assertEquals(etudiant.getTp(), 10);
  }
   
  

  
}
