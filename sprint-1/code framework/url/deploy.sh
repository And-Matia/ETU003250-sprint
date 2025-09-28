#!/bin/bash

# Si aucun nom n'est donné, on prend "myframework.jar"
JAR_NAME="test.jar"

# Nettoyer anciennes classes
rm -rf classes
mkdir classes

# Compiler les sources
javac -cp "lib/servlet-api.jar" -d classes src/FrontServlet.java

# Créer le JAR avec nom paramétré
jar cf "$JAR_NAME" -C classes .

echo "==> JAR généré : $JAR_NAME"
