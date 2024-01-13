# WG-Codeurs

#### Informations sur le logiciel
Les fichiers de sauvegarde du logiciel sont au format .wgc

#### Build depuis Eclipse IDE
- Télécharger la librairie javafx (Win ou Linux): [Page de téléchargement du SDK](https://gluonhq.com/products/javafx/)
- Décompresser le contenu
- Déplacer le résultat dans le dossier build de l'application
- Renomer le dossier en : "javafx-win" sur Windows / "javafx-linux" sur Linux
- Ouvrir le projet sur Eclipse
- Dans proprietes du projet Java Buildpath
	- Ajouter librairie -> JUnit -> Version 5
	- Ajouter librairie -> User Library
		- Creer une User Library
		- Nom que vous souhaitez
		- Ajouter jar externes
		- Selectionner tout les jars dans javafx-linux/lib ou javafx-win/lib
	- Veillez a ce que le JDK v17 soit selectionné
- Definir l'encodage du projet en UTF-8
- Selectionner le projet dans le Project Explorer
- Créer une configuration de lancement pour la classe src/MainFormation
- Clic droit -> Export -> Java -> Runnable jar -> Destination conseille: build/program.jar

#### Une fois le projet construit
Vous devriez avoir dans le dossier build au minimum:
- program.jar
- win32.exe
- linux32
- Dossier javafx-linux|javafx-win (Au moins un des deux OU les deux)

Ceci constitue votre application si vous compressez dans un installer custom par exemple
program.jar, win32.exe, et javafx-win en respectant la meme architecture que dans le dossier build
alors win32.exe sera le point d'entrée de l'application.

PS: L'application nécessite Java pour fonctionner.