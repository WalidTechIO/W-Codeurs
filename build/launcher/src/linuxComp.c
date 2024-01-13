#include "linuxComp.h"

int mainLin(int argc, char *argv[]){
    const char *erreurJava = "Java n'est pas detectable\nInstallez Java et/ou mettez le dans la variable d'environnement PATH.\n";
    char *command = (char *)calloc(250, sizeof(char));
    argv[0][strlen(argv[0])-7] = '\0';
    sprintf(command, "java --module-path '%sjavafx-linux/lib' --add-modules javafx.controls,javafx.fxml -jar %sprogram.jar", argv[0], argv[0]);
    for (int i = 1; i< argc ; i++){
        sprintf(command, "%s %s", command, argv[i]);
    }
    int code = system(command);
    free(command);
    if (code == 127)
    {
        printf("%s", erreurJava);
        return -1;
    }
    return 0;
}
