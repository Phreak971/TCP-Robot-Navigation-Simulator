# TCP-Robot-Navigation-Simulator
Robot Client Navigation on Server using TCP in Java

## Overview

The project consists of two applications i.e. the client application, a robot simulation and the server application, area where robots move. The working of the application is that the client acts as a robot and connects to the server. The server shows a robot connected on the graphical user interface. The user can move the robot using buttons on the GUI and the location of client updates every 30 seconds on the server.
Whereas the server shows the movement of the robot on a x, y plane and it can take multiple robots at the same time.

## The Robot Client

Controls the Robot, Acts as a remote control

## The Server Side

The server side accepts connection from the robot client. The server listens for connections on port 5050 of localhost. The GUI of the server shows the movement of the robots in the robots area.

## How to Run
### To run from terminal
#### Server

java robotsServer.java

#### Client

java roboClient.java [robot_name] [address]

java roboClient.java Davy localhost

### To run in IntelliJ IDEA

#### Client
set the arguments in edit configurations and then run

#### Server

Just Push the run button
