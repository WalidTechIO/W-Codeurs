package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import formation.Application;
import formation.Etudiant;
import formation.GestionEtudiant;
import formation.InformationPersonnelle;
import formation.InformationPersonnelleException;
import formation.NonConnecteException;
import formation.UniteEnseignement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link formation.GestionEtudiant GestionEtudiant}.
 *
 * @author IGIRANEZA Gloria
 * @author EL OUAZIZI Walid
 * @see formation.GestionEtudiant
 */
public class TestGestionEtudiant {
  
  /**
   * Application testee.
   */
  private Application app;
  
  /**
   * GestionEtudiant testee.
   */
  private GestionEtudiant gestionEtudiant;
  
  /**
   * Infos de test.
   */
  private InformationPersonnelle infos;
  
  /**
   * Numerto etudiant de test.
   */
  private int numero;
  
  /**
   * Avant tout les test on instancie une app personnelle.
   *
   * @throws Exception ne peut pas etre levee ici
   */
  @BeforeEach
  public void setUp() throws Exception {
    app = new Application();
    gestionEtudiant = app.getGestionEtudiant();
    
    // Ajout d'un etudiant pour les tests necessitant une connexion
    infos = new InformationPersonnelle("Nom", "Prenom");
    numero = gestionEtudiant.inscription(infos, "motDePasse");
  }
  
  /**
   * Teste le processus d'inscription d'un nouvel etudiant. Verifie que le
   * numero d'etudiant attribue est positif et que l'etudiant est ajoute a
   * l'ensemble des etudiants. Teste egalement l'echec de l'inscription avec des
   * informations invalides (mot de passe nul ou vide).
   */
  @Test
  public void testInscriptionOk() {
    // Test inscription reussie
    assertTrue(numero > 0);
    assertTrue(gestionEtudiant.getEtudiants()
        .contains(new Etudiant(infos, "motDePasse", numero)));
  }
  
  /**
   * Teste la connexion et la deconnexion d'un etudiant. Verifie si l'etudiant
   * peut se connecter avec les bonnes informations d'identification, et si
   * l'etudiant peut se deconnecter correctement.
   *
   * @throws NonConnecteException Si l'etudiant n'est pas connecte.
   */
  @Test
  public void testConnexionEtDeconnexion() throws NonConnecteException {
    assertTrue(gestionEtudiant.connexion(numero, "motDePasse"));
    assertNotNull(gestionEtudiant.getLogged());
    gestionEtudiant.deconnexion();
    assertNull(gestionEtudiant.getLogged());
  }
  
  /**
   * Test de connexion avec des identifiants non present.
   */
  @Test
  public void testConnexionAvecIdentifiantsInexistants() {
    boolean connexionReussie =
        gestionEtudiant.connexion(9999, "motDePasseIncorrect"); 
    assertFalse(connexionReussie,
        "La connexion devrait echouer avec des identifiants inexistants."
        );
  }
  
  
  /**
   * Teste la fonctionnalite de choix d'option par un etudiant connecte. Verifie
   * que l'etudiant peut choisir une option et que cette option est ensuite
   * listee parmi les enseignements suivis par l'etudiant.
   *
   * @throws NonConnecteException ne sera pas levee.
   */
  @Test
  public void testChoisirOptionAvecConnexion() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    UniteEnseignement ue = new UniteEnseignement("UE Test", "Responsable Test");
    // S'assurer que l'option est disponible dans la formation
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(1);
    app.getGestionFormation().ajouterEnseignementOptionnel(ue, 1);
    assertTrue(gestionEtudiant.choisirOption(ue));
    assertTrue(gestionEtudiant.enseignementsSuivis().contains(ue));
  }
  
  /**
   * Teste le comportement de la methode choisirOption lorsqu'aucun etudiant
   * n'est connecte. Verifie qu'une NonConnecteException est lancee dans ce cas.
   */
  @Test
  public void testChoisirOptionSansConnexion() {
    UniteEnseignement ue = new UniteEnseignement("UE Test", "Responsable Test");
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.choisirOption(ue));
  }
  
  /**
   * Teste le cas ou un etudiant connecte tente de choisir une option qu'il a
   * deja choisie. Verifie que la methode retourne false dans ce cas.
   *
   * @throws NonConnecteException Si l'etudiant n'est pas connecte.
   */
  @Test
  public void testChoisirOptionDejaChoisie() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    UniteEnseignement ue = new UniteEnseignement("UE Test", "Responsable Test");
    // L'option est disponible dans la formation
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(1);
    app.getGestionFormation().ajouterEnseignementOptionnel(ue, 1);
    gestionEtudiant.choisirOption(ue);
    assertFalse(gestionEtudiant.choisirOption(ue));
  }
  
  /**
   * Test choisir option non proposees.
   *
   * @throws NonConnecteException ne sera pas levee.
   */
  @Test
  public void testChoisirOptionAvecUeNonDisponible()
      throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    UniteEnseignement ueNonDisponible =
        new UniteEnseignement("UE Non Dispo", "Responsable Test");
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(1);
    assertFalse(gestionEtudiant.choisirOption(ueNonDisponible));
  }
  
  /**
   * Teste le comportement de la methode choisirOption de la classe GestionEtudiant
   * lorsqu'un etudiant tente de choisir une option deja choisie.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte.
   */  
  @Test
  public void testChoisirOptionInterditePourMemeOption()
      throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    UniteEnseignement ueMaxOpt =
        new UniteEnseignement("UE Max Options", "Responsable Test");
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(1);
    app.getGestionFormation().ajouterEnseignementOptionnel(ueMaxOpt, 1);
    // S'assurer que l'option est disponible dans la formation
    assertTrue(
        app.getGestionFormation().enseignementsOptionnels().contains(ueMaxOpt));
    // Simuler le choix d'options jusqu'a atteindre la limite
    assertTrue(gestionEtudiant.choisirOption(ueMaxOpt));
    assertFalse(gestionEtudiant.choisirOption(ueMaxOpt));
  }
  
  
  /**
   * Teste le comportement de la methode choisirOption de la classe GestionEtudiant
   * lorsque l'etudiant tente de choisir plusieurs options differentes atteignant la limite.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte.
   */
  @Test
  public void testChoisirOptionAvecUeMaxOptionsAtteint()
      throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    // Les deux options differentes
    UniteEnseignement ueOpt1 =
        new UniteEnseignement("UE Option 1", "Responsable Test 1");
    UniteEnseignement ueOpt2 =
        new UniteEnseignement("UE Option 2", "Responsable Test 2");
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(1);
    app.getGestionFormation().ajouterEnseignementOptionnel(ueOpt1, 1);
    app.getGestionFormation().ajouterEnseignementOptionnel(ueOpt2, 2);
    // S'assurer que les options sont disponibles dans la formation
    assertTrue(
        app.getGestionFormation().enseignementsOptionnels().contains(ueOpt1));
    assertTrue(
        app.getGestionFormation().enseignementsOptionnels().contains(ueOpt2));
    // Simuler le choix d'options jusqu'a atteindre la limite
    assertTrue(gestionEtudiant.choisirOption(ueOpt1));
    assertFalse(gestionEtudiant.choisirOption(ueOpt2));
    assertFalse(gestionEtudiant.choisirOption(ueOpt1));
    assertFalse(gestionEtudiant.choisirOption(ueOpt2));
  }


  
  /**
   * Teste lorsqu'un etudiant tente de choisir une unite d'enseignement (UE)
   * dont la capacite maximale d'inscription est deja atteinte. Ce test simule
   * une situation ou l'UE choisie par l'etudiant a une capacite d'accueil
   * definie a zero, indiquant qu'aucun etudiant supplementaire ne peut s'y
   * inscrire. Le test verifie ensuite que la tentative de l'etudiant de choisir
   * cette UE echoue comme prevu.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte au moment de
   *         choisir l'UE.
   * 
   * @throws InformationPersonnelleException si les informations de l'etudiant
   *         sont creer de maniere incorrect.
   */
  @Test
  public void testChoisirOptionAvecCapaciteMaximaleAtteinte()
      throws InformationPersonnelleException, NonConnecteException {
    UniteEnseignement ue = new UniteEnseignement("UE Test", "Responsable Test");
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
            "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().ajouterEnseignementOptionnel(ue, 1);
    InformationPersonnelle infEtud1 = new InformationPersonnelle("Nom1",  "Pren1");
    int numEtud1 = gestionEtudiant.inscription(infEtud1, "MotDePasse1");
    gestionEtudiant.connexion(numEtud1, "MotDePasse1");
    gestionEtudiant.choisirOption(ue);
    
    InformationPersonnelle infEtud2 = new InformationPersonnelle("Nom2",  "Pren2");
    int numEtud2 = gestionEtudiant.inscription(infEtud2, "MotDePasse2");
    gestionEtudiant.connexion(numEtud2, "MotDePasse2");
    gestionEtudiant.choisirOption(ue);
    boolean choixReussi = gestionEtudiant.choisirOption(ue);
    assertFalse(choixReussi,
        "Le choix de l'UE devrait echouer si la capacite maximale est atteinte."
        );
  }
  
  
  
  /**
   * Verifie si l'etudiant est connecte pour acceder au numero de TD Une
   * exception est levee si non.
   */
  @Test
  public void testGetNumeroGroupeTravauxDiriges() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.getNumeroGroupeTravauxDiriges());
  }
  
  /**
   * Verifie si l'etudiant est connecte pour acceder au numero de TP.
   */
  @Test
  public void testGetNumeroGroupeTravauxPratiques() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.getNumeroGroupeTravauxPratiques());
  }
  
  
  /**
   * Verifie si l'etudiant est connecte pour acceder aux differentes UEs Verifie
   * si l'exception est levee si non.
   *
   * @throws NonConnecteException si besoin.
   */
  @Test
  public void testEnseignementsSuivis() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    UniteEnseignement ueObligatoire =
        new UniteEnseignement("UE Obligatoire", "Responsable Test");
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().ajouterEnseignementObligatoire(ueObligatoire);
    //Test presence ue obligatoire
    assertTrue(gestionEtudiant.enseignementsSuivis().contains(ueObligatoire)); //
    
    app.getGestionFormation().definirNombreOptions(1);
    UniteEnseignement ueOptionnel =
        new UniteEnseignement("UE Optionnel", "Responsable Test");
    app.getGestionFormation().ajouterEnseignementOptionnel(ueOptionnel, 0);
    gestionEtudiant.choisirOption(ueOptionnel);
    // Test presence ue optionnel
    assertTrue(gestionEtudiant.enseignementsSuivis().contains(ueOptionnel)); 
    //Test que  seulement ces 2 ues sont presentes
    assertEquals(gestionEtudiant.enseignementsSuivis().size(), 2); 
    // Test exception si l'etudiant est deconnecte
    gestionEtudiant.deconnexion();
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.enseignementsSuivis());
  }
  
  /**
   * Verifie si l'etudiant est connecte pour acceder a la liste de tous les
   * messages.
   */
  @Test
  public void testListeTousMessages() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.listeTousMessages());
  }
  
  /**
   * Verifie si l'etudiant est connecte pour acceder a la liste des messages non
   * lus.
   */
  @Test
  public void testListeMessageNonLus() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.listeMessageNonLus());
  }
  
  /**
   * Verifie si l'etudiant connecte a finalise son inscription Verifie si
   * l'exception est levee lorsqu'un etudiant n'est connecte.
   */
  @Test
  public void testInscriptionFinalisee() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.inscriptionFinalisee());
  }
  
  /**
   * Teste l'inscription d'un nouvel etudiant avec des informations personnelles
   * contenant des noms tres longs et des caracteres speciaux. Ce test verifie
   * que le systeme peut gerer correctement des noms d'etudiant inhabituels et
   * des caracteres speciaux dans les prenoms. Le test s'assure que le numero
   * d'etudiant attribue est positif, ce qui indique une inscription reussie, et
   * que l'etudiant est effectivement ajoute a la liste des etudiants
   * enregistres dans le systeme.
   *
   * @throws InformationPersonnelleException si les informations personnelles
   *         fournies sont invalides
   */
  @Test
  public void testInscriptionAvecNomsLongsEtCaracteresSpeciaux()
      throws InformationPersonnelleException {
    String nomLong = "NomTresTreslololololololoLong";
    String prenomAvecCaracteresSpeciaux = "Prenom@#$%^&*()!";
    InformationPersonnelle infos =
        new InformationPersonnelle(nomLong, prenomAvecCaracteresSpeciaux);
    int numero = gestionEtudiant.inscription(infos, "motDePasse123");
    
    assertTrue(numero > 0,
        "Le numero d'etudiant devrait etre positif pour une inscription valide.");
    assertNotNull(gestionEtudiant.getEtudiants().stream()
                    .filter(e -> e.getNumeroEtudiant() == numero).findFirst()
                    .orElse(null),
            "L'etudiant devrait etre ajoute a la liste des etudiants."
        );
  }
  
  /**
   * Test recuperation des informations personnelles sans connexion.
   */
  @Test
  public void testGetInformationPersonnelle() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.getInformationPersonnelle());
  }
  
  /**
   * La liste des enseignements suivis doit etre vide pour un nouvel etudiant
   * connecte.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte.
   */
  @Test
  public void testEnseignementsSuivisCo() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    assertTrue(gestionEtudiant.enseignementsSuivis().isEmpty());
  }
  
  /**
   * Verifie si la liste de tous les messages d'un nouvel etudiant connecte
   * contient un element.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte
   */
  @Test
  public void testListeTousMessagesCo() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    assertEquals(gestionEtudiant.listeTousMessages().size(), 1);
  }
  
  /**
   * Teste si la liste des messages non lus d'un nouvel etudiant connecte
   * contient un element.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte
   */
  @Test
  public void testListeMessagesNonLusCo() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
      assertEquals(1, gestionEtudiant.listeMessageNonLus().size());
  }
  
  /**
   * Verifie que l'inscription ne doit pas etre finalisee pour un nouvel
   * etudiant connecte.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte
   */
  @Test
  public void testInscriptionFinaliseeCo() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    assertFalse(gestionEtudiant.inscriptionFinalisee());
  }
  
  /**
   * Teste l'inscription avec un mot de passe vide.
   *
   * @throws InformationPersonnelleException si les informations sont invalides.
   */
  
  @Test
  public void testInscriptionMdpVide() throws InformationPersonnelleException {
    InformationPersonnelle infosInvalides =
        new InformationPersonnelle("NomInval", "PrenomInval");
    int numInvalide = gestionEtudiant.inscription(infosInvalides, "");
    assertEquals(-1, numInvalide);
  }
  
  /**
   * Teste l'inscription avec un mot de passe null.
   *
   * @throws InformationPersonnelleException si les informations sont invalides.
   */
  @Test
  public void testInscriptionMdpNull() throws InformationPersonnelleException {
    InformationPersonnelle infosInvalides =
        new InformationPersonnelle("NomInval", "PrenomInval");
    int numInvalide = gestionEtudiant.inscription(infosInvalides, null);
    assertEquals(-1, numInvalide);
  }
  
  
  /**
   * Teste si les informations personnelles de l'etudiant connecte sont
   * correctes.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte
   */
  @Test
  public void testGetInformationPersonnelleCo() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    assertEquals(gestionEtudiant.getInformationPersonnelle(), infos);
  }
  
  
  /**
   * Teste la coherence du nombre d'options disponibles pour un etudiant apres
   * la creation d'une formation. Ce test assure que le nombre d'options obtenu
   * via la methode nombreOptions correspond au nombre defini.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte.
   */
  @Test
  public void testNombreOptionsAvecConnexion() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    // Creation d'une nouvelle formation
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    int nombreOptionsAttendues = 2;
    app.getGestionFormation().definirNombreOptions(nombreOptionsAttendues);
    int nombreOptionsObtenues = gestionEtudiant.nombreOptions();
    assertEquals(nombreOptionsAttendues, nombreOptionsObtenues);
  }
  
  /**
   * Teste le comportement de la methode nombreOptions lorsque l'etudiant n'est
   * pas connecte. NonConnecteException est censee etre lever.
   */
  @Test
  public void testNombreOptionsSansConnexion() {
    assertThrows(NonConnecteException.class,
        () -> gestionEtudiant.nombreOptions());
  }
  
  /**
   * Teste le comportement de nombreOptions apres la modification du nombre
   * d'options dans la gestion de la formation. Verifie si le nombre d'options
   * reflete correctement les changements effectues.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte.
   */
  @Test
  public void testNombreOptionsApresModification() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    // Creation d'une nouvelle formation
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(5);
    app.getGestionFormation().definirNombreOptions(3);
    assertEquals(5, gestionEtudiant.nombreOptions());
  }
  
  /**
   * Teste la definition d'un nombre negatif d'options pour une formation.
   *
   * @throws NonConnecteException si l'etudiant n'est pas connecte.
   */
  @Test
  public void testNombreOptionsNegatif() throws NonConnecteException {
    gestionEtudiant.connexion(numero, "motDePasse");
    // Creation d'une nouvelle formation
    app.getGestionFormation().creerFormation("Licence 3 Informatique",
        "IGIRANEZA Gloria", "gloria@resp.fr");
    app.getGestionFormation().definirNombreOptions(-5);
    assertEquals(-1, gestionEtudiant.nombreOptions());
  }
  
  
  
}
