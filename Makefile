JC := javac
SOURCES := $(wildcard *.java) $(wildcard system/*.java) $(wildcard scheduling/*.java)
BIN := java

all: build

build:
	$(JC) $(SOURCES)

run:
	$(BIN) Assignment3
