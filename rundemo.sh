#!/usr/bin/env bash
cd Demos
javac -cp .:../build/classes/main VolumeCalculator.java
java -cp .:../build/classes/main:/Users/burdettec/  VolumeCalculator 7 --Type circle --Color Blue 
cd ..