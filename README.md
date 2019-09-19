# Password-Recovery-Program
program that will attempt to recover a salted and hashed password using a file of candidate passwords

Hello,


This program has been set up using visual studio code. 
As such, the .vscode folder uses .json files to automatically launch arguments 0 and 1. arg[0] == pwFile.txt , arg[1] = 9560204. 
These arguments are necessary to open the password file and insert the salted pw hash value.

This program will also run on its own using the command line as long as pwFile.txt and PasswordRecovery.java are in the same folder. The commands to run this program are as follows:

: javac PasswordRecovery.java

: java PasswordRecovery pwFile.txt 9560204

Thanks for reading,
Neal 
