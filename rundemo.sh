#!/usr/bin/env bash
cd Demos
javac -cp .:../build/classes/main VolumeCalculator.java
java -cp .:../build/classes/main:/Users/burdettec/  VolumeCalculator 8 9 10 --Color pink --Type hexatagon 
cd ..