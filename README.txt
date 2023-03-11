



Assignment1- Computer Networking-Simple Webserver and Client


Description of the project:
(A) You will be developing a multi-threaded Web server which interacts with any standard Web Clients ( You may use any web browser of your choice to test the functionality however you should also submit the a client as given in (B) below ). The Web server and Web client communicate using a text-based protocol called HTTP (Hypertext Transfer Protocol)
(B) Build a single threaded Web Client on your own which interacts with your Web Server, and downloads a file from the server
(C) Display the essential connection parameters of connection for both the Web client ( on the server side ) and for the Web Server ( on the client side )

Tools and Software Used:
Eclipse IDE
Java 1.8

Packages Required to run the program:
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

Following are the steps to run program:

Client-Server communication via sockets:
1) Build the project Eclipse IDE and add the source code files.
2)Run webServer class first
3)Run Client class
4)Enter the file Name in console(Client Java program Console)which you want search in server(Files should be stored project directory).

Client-Server communication via HTTP Request:
1) Run the webserver class
2)open the browser and you should enter into the browser's address text box the IP address of your running server.
For example, if your machine name is host.someschool.edu, and you ran the server with port number 3030,

Reference:
https://stackoverflow.com/search?q=MultiThread+Server+Java
https://www.youtube.com/watch?v=qgFZEiwFFvg
https://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
