#include "linuxComp.h"
#include "windowsComp.h"

int main(int argc, char *argv[]){
	#ifdef _WIN32
		return mainWin(argc, argv);
	#endif
	#ifdef linux
		return mainLin(argc, argv);
	#endif
}