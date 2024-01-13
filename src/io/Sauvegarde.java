package io;

import formation.Application;
import formation.GestionEtudiant;
import formation.GestionFormation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe gerant la Sauvegarde et le Chargement de l'etat de l'application.
 *
 * @author EL OUAZIZI Walid
 */
public class Sauvegarde implements InterSauvegarde, Serializable {
  
  /**
   * Attribut de serialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Instance de l'application dont on veut controler l'etat.
   */
  public Application app;
  
  /**
   * Constructeur.
   *
   * @param app L'application.
   */
  public Sauvegarde(Application app) {
    this.app = app;
  }
  
  @Override
  public void sauvegarderDonnees(String nomFichier) throws IOException {
    if (!nomFichier.substring(
        nomFichier.lastIndexOf('.') == -1 ? 0 : nomFichier.lastIndexOf('.')
       ).equals(".wc")) {
      nomFichier += ".wc";
    }
    FileOutputStream fos = new FileOutputStream(nomFichier);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(app.getGestionFormation());
    oos.writeObject(app.getGestionEtudiant());
    oos.close();
    fos.close();
  }
  
  @Override
  public void chargerDonnees(String nomFichier) throws IOException {
    if (!nomFichier.substring(
        nomFichier.lastIndexOf('.') == -1 ? 0 : nomFichier.lastIndexOf('.')
       ).equals(".wc")) {
      throw new IOException("L'application ne prend pas en charge ce type de fichier");
    }
    Object a;
    Object b;
    GestionFormation gf = null;
    GestionEtudiant ge = null;
    FileInputStream fis = new FileInputStream(nomFichier);
    ObjectInputStream ois = new ObjectInputStream(fis);
    try {
      a = ois.readObject();
      b = ois.readObject();
    } catch (ClassNotFoundException e) {
      ois.close();
      throw new IOException("Erreur lors de la lecture de l'objet", e);
    }
    
    if (a != null && a instanceof GestionFormation) {
      gf = (GestionFormation) a;
    }
    
    if (b != null && b instanceof GestionEtudiant) {
      ge = (GestionEtudiant) b;
    }
    ois.close();
    fis.close();
    app.setGestionEtudiant(ge);
    app.setGestionFormation(gf);
  }
  
}
