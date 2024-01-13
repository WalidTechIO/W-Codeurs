CC = gcc
WIN_CC = i686-w64-mingw32-gcc

SRC_DIR = src
BUILD_DIR = build

C_FLAGS = -Iinclude

WSRC = $(SRC_DIR)/main.c $(SRC_DIR)/windowsComp.c $(SRC_DIR)/icon.o
LSRC = $(SRC_DIR)/main.c $(SRC_DIR)/linuxComp.c

all: $(BUILD_DIR)/linux32 $(BUILD_DIR)/win32.exe

$(BUILD_DIR)/linux32: $(LSRC)
	$(CC) $(C_FLAGS) $(LSRC) -o $@

$(BUILD_DIR)/win32.exe: $(WSRC)
	$(WIN_CC) $(C_FLAGS) $(WSRC) -o $@

$(SRC_DIR)/icon.o: ../icon.ico
	i686-w64-mingw32-windres $(SRC_DIR)/icon.rc $@
