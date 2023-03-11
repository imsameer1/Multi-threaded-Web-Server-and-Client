
// Sameer Dayani
// 1002015854
// COMPUTER NETWORKS AND ORGANIZATIONS
// Spring 2023

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

//Defining my Client class
public class Client {

	//Setting the socket to null to prevent any errors.
	private static Socket socket = null;
	final static String CRLF = "\r\n";
	private static String fileName;
	private static int Port_Number = 8080;
	BufferedReader bufferedreader = null;
	InputStreamReader inputstreamreader = null;
	BufferedWriter bufferedwriter = null;
	OutputStreamWriter outputstreamwriter = null;
	public static void main(String args[]) throws Exception 
	{
		long Start_time;
		long Finish_time;
		long Duration;
		Start_time = System.nanoTime();
		// Setting connection with the server
		String server_url = "http://" + "localhots" + ":" + Port_Number + "/";
		URL url = new URL(server_url);
		socket = new Socket("localhost", Port_Number);
		System.out.println("Connection successfully established with the server");
		// requesting file from the server

		System.out.println();
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter the file you want to search: ");
		fileName = input.nextLine();
		PrintStream printstream = new PrintStream(socket.getOutputStream());
		printstream.println("GET" + " /" + fileName + " " + "HTTP/1.1" + CRLF);
		System.out.println("------------------------------------------");
		System.out.println("Server_IPaddress: " + socket.getInetAddress().getHostName());
		System.out.println("port_no: " + socket.getPort());
		System.out.println("Protocol:" + url.getProtocol());
		System.out.println("requested_file_name: " + fileName);
		System.out.println("Timeout : " + socket.getSoTimeout());
		System.out.println("------------------------------------------");

		try
		{
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder stringbuilder = new StringBuilder();
			String get_String;
			while ((get_String = bufferedreader.readLine()) != null) 
			{
				stringbuilder.append(get_String + "\n");
			}
			bufferedreader.close();
			System.out.println(stringbuilder.toString());
			socket.close();
			Finish_time = System.nanoTime();
			Duration = (Finish_time - Start_time) / 1000000;
			// Calulation of RTT
			System.out.println("RTT for the client is " + (double) Math.round(Duration * 100) / 100 + " ms");
		} 
		catch (IOException ex) 
		{
			System.out.println(ex.getMessage());
			input.close();
		}
	}
}
