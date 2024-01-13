#include "windowsComp.h"

int mainWin(int argc, char *argv[]) {
    FreeConsole();
    int show = SW_HIDE;
    const char *erreurJava = "Java n'est pas detectable\nInstallez Java et/ou mettez le dans la variable d'environnement PATH.\n";
    char *command = (char *)calloc(250, sizeof(char));
    _pgmptr[strlen(_pgmptr)-9] = '\0';
    sprintf(command, "java --module-path \"%sjavafx-win\\lib\" --add-modules javafx.controls,javafx.fxml -jar \"%sprogram.jar\"", _pgmptr, _pgmptr);
    for (int i = 1; i<argc; i++)
    {
        sprintf(command, "%s %s", command, argv[i]);
        if (strcmp(argv[i], "--console") == 0 || strcmp(argv[i], "-c") == 0 || strcmp(argv[i], "-no-gui") == 0)
        {
            show = SW_SHOW;
        }
        if (strcmp(argv[i], "-h") == 0 || strcmp(argv[i], "--help") == 0)
        {
            MessageBox(NULL, "Aide WG-Codeurs\n-no-gui | -c | --console: Lance le programme en mode console\n-h | --help: Vous affiche cette aide", "Aide", MB_OK);
        }
    }
    int code = WinExec(command, show);
    free(command);
    if (code == ERROR_FILE_NOT_FOUND)
    {
        MessageBox(NULL, erreurJava, NULL, MB_OK);
        return -1;
    }
    return 0;
}
