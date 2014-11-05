JC := javac
CLASSPATH := ./*.jar
JCFLAGS := -cp $(CLASSPATH)
JC.link := $(JC) $(JCFLAGS)
SOURCES := $(wildcard *.java) $(wildcard system/*.java) $(wildcard scheduling/*.java)
BIN := java

all: build

build:
	$(JC.link) $(SOURCES)

run:
	$(BIN) -cp * Assigment3
