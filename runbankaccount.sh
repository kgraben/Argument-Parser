#!/usr/bin/env bash
cd Demos
javac -cp .:../build/classes/main BankAccount.java
java -cp .:../build/classes/main:/Users/burdettec/  BankAccount 
cd ..