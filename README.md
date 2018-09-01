# Chess

This is a simple chess application that is written in java. The program can be used in two-player or single-player mode(default build is in single-player mode). 
In single player mode the player plays against the stockfish engine. Hence you need to have stockfish installed in the computer (and in your path as well).

# How it looks

![Chess](https://raw.githubusercontent.com/GnikDroy/chess/master/screenshots/screenshot.png)

# Requirements

The program requires the following packages to be installed

- stockfish

In Linux `sudo apt-get install stockfish` should install stockfish to your system. For windows user you can go [here](https://stockfishchess.org/download/) to download
the latest version of stockfish to your computer.

# Building

The project uses ANT build scripts (Netbeans) to build. If you have Netbeans, simply import the project and run from there. If you donot have Netbeans, go [here](https://ant.apache.org/) and get yourself a copy of the ANT executable.
To build the project, go inside the project directory and simply run `ant`. ANT must be in your path. The built jar file will be under the dist folder.
Execute the jar file to run the program.

# Running

__Linux/Windows/Mac__

There is a built .jar file inside the dist folder.
To run the program simply execute `java -jar Chess.jar` in the terminal or command prompt. Java must be in your path for this to work.

# About the code.

I made this program while learning OOP in java so the implementation of the game itself is very properly done. Sadly, I had no idea about writing GUI apps in java during that time.
So, the code for the GUI is horrible but the rest is really well written (I hope so).
The Java-Doc for the project is also really good (except for the GUI classes).
