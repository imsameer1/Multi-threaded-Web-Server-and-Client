//Sameer Dayani 
//1002015854 
//Project 1, NETWORK ORGANIATIONS

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

//Task: To develop a multi-threaded web server which uses HTTPS and TCP to establish a connection with a client. In this case, the client is both a local host and web-browser. 


public final class Server {

	public static void main(String[] args) throws Exception {

		// Set the port number.
		int Port_Number = 8080;
		// Establish the listen socket.
		ServerSocket serverSocket = null;
		//Using try and catch block to handle the exception
		try 
		{
			serverSocket = new ServerSocket(Port_Number);
			System.out.println("TCP server created on port: " + Port_Number);
			// Process HTTP service requests in an infinite loop until and unless, the client is connected.
			while (true)
			{
				Socket clientSocket = null;
				// Listen for a TCP connection request.
				clientSocket = serverSocket.accept();
				//Creating an object of HTTPS to process the request and handle the multi-threading.
				HttpRequest request = new HttpRequest(clientSocket);
				// create a new thread to process the request
				Thread thread = new Thread(request);
				// Start the thread
				thread.start();
			}
		}
		catch (IOException ex) 
		{
			//Closing the socket with an error and printing the error in case the connection was failed.
			System.out.println("Error : The server with port: " + Port_Number + "cannot be created");
		}

		// Closing the socket after eveything is finished and no longer connections are required.
		finally
		{
			try 
			{  
		    	serverSocket.close();
		    } 
			catch(Exception e) 
			{
		    	System.out.println("Error : The server with port: " + Port_Number + "cannot be closed");
		    }
		}
	}
	
}

// Manging the HTTP request and handling the multi-threading which is the most difficult part in this code.
 final class HttpRequest implements Runnable {

	final static String CRLF = "\r\n";
	Socket socket;

	// Passing the socket object in HTTP request Constructor
	public HttpRequest(Socket socket) throws Exception
	{
		this.socket = socket;
	}

	@Override
	public void run() 
	{
		try 
		{
			processRequest();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}


//Process Client Request here and printing the connection information 
	private void processRequest() throws Exception 
	{
		String server_url = "http://localhost:" + socket.getPort() + "/";
		System.out.println("server " + server_url);
		URL url = new URL(server_url);
		InputStream in = socket.getInputStream();
		DataOutputStream op = new DataOutputStream(socket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String requestLine = br.readLine();
		System.out.println();
		// information from the connection objects,
		System.out.println("------------------------------------------");
		System.out.println("Information from the connection objects");
		System.out.println("------------------------------------------");
		System.out.println("RequestLine " + requestLine);
		System.out.println("Connection received from " + socket.getInetAddress().getHostName());
		System.out.println("Port : " + socket.getPort());
		System.out.println("Protocol : " + url.getProtocol());
		System.out.println("TCP No Delay : " + socket.getTcpNoDelay());
		System.out.println("Timeout : " + socket.getSoTimeout());
		System.out.println("------------------------------------------");
		System.out.println();
		String headerLine = null;
		while ((headerLine = br.readLine()).length() != 0)
		{
			System.out.println(headerLine);
		}
        // Creating the StringTokenizer and passing requestline in constructor .
		//tokens object split the requestline and create the token 
		StringTokenizer tokens = new StringTokenizer(requestLine);
		tokens.nextToken();
		String fileName = tokens.nextToken();
		fileName = "." + fileName;
		System.out.println("FileName GET" + fileName);

		// Open the requested file.
		FileInputStream FILE = null;
		boolean fileExists = true;
		try 
		{
			FILE = new FileInputStream(fileName);
		}
		catch (FileNotFoundException e) 
		{
			fileExists = false;
		}
		// Construct the response message.
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		//Check file exist in directory or not
		if (fileExists)
		{
			statusLine = "HTTP/1.1 200 OK";
			contentTypeLine = "Content-Type:" + contentType(fileName) + CRLF;
		} 
		else
		{
			statusLine = "HTTP/1.1 404 Not Found";
			contentTypeLine = "Content-Type: text/html" + CRLF;
			entityBody = "The file you are looking for is not found... Please try again";
		}
		// Send the status line.
		op.writeBytes(statusLine);
		op.writeBytes(CRLF);
		// Send the content type line.
		op.writeBytes(contentTypeLine);

		// Send a blank line to indicate the end of the header lines.
		op.writeBytes(CRLF);
		// Send the entity body.
		if (fileExists)
		{
			sendBytes(FILE, op);
			FILE.close();
		} 
		else
		{
			op.writeBytes(entityBody);
		}
		//close the open objects
		op.close();
		br.close();
		socket.close();
	}
//contentType Method which evalutes the file extension
	private static String contentType(String fileName)
	{
		if (fileName.endsWith(".txt") || fileName.endsWith(".html"))
		{
			return "text/html";
		}
		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
		{
			return "image/jpeg";
		}
		if (fileName.endsWith(".gif")) 
		{
			return "image/gif";
		}
		return "application/octet-stream";
	}



	//This buffer is needed to send the file to the client
	private static void sendBytes(FileInputStream FILE, OutputStream op) throws Exception 
	{
		byte[] buffer = new byte[1024];
		int bytes = 0;
		// Copy requested file into the socket’s output stream.
		while ((bytes = FILE.read(buffer)) != -1)
		{
			op.write(buffer, 0, bytes);
		}
	}
}