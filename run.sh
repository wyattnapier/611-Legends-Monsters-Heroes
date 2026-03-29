#!/bin/bash
set -e

echo "Compiling..."
mkdir -p bin
javac -d bin $(find . -name "*.java")

echo "Running..."
java -cp bin Structure.Main