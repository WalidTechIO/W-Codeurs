import formation.Application;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import ui.MainInterfaceUtilisateur;

/**
 * Classe principale du programme.
 *
 * @author EL OUAZIZI Walid.
 */
public class MainFormation {
  
  /**
   * Starting method.
   *
   * @param args Arguments de lancement
   */
  public static void main(String[] args) {
    
    String startingFile = "";
    
    Set<String> consoleFlags = new HashSet<>();
    consoleFlags.add("-c");
    consoleFlags.add("--console");
    consoleFlags.add("-no-gui");
    
    Set<String> helpFlags = new HashSet<>();
    helpFlags.add("-h");
    helpFlags.add("--help");
    
    int action = 0;
    
    for (String arg : args) {
      
      if (consoleFlags.contains(arg)) {
        action = 1;
      }
      
      if (helpFlags.contains(arg)) {
        action = 2;
      }
      
      if(new File(arg).exists()) {
        MainInterfaceUtilisateur.ficDebut = arg;
        startingFile = arg;
      }
      
    }
    
    switch (action) {
      case 0:
        MainInterfaceUtilisateur.main(args);
        break;
        
      case 1:
        Application app = new Application();
        if(!startingFile.isEmpty())
          try {
            app.getSave().chargerDonnees(startingFile);
          } catch (IOException e) {
            System.exit(-1);
          }
          
        app.run();
        break;
        
      case 2:
        System.out.println("Aide W-Codeurs\n");
        System.out.println("-no-gui | -c | --console: Lance le programme en mode console");
        System.out.println("-h | --help: Vous affiche ce message");
        break;
        
      default:
        System.out.println("Unkown program mode.");
        break;
    }
    
  }
}
