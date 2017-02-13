package sample;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Omid Khataee on 07/02/17.
 * Client.java
 * Assignment 1
 * A lot of the code in here and Server are the same
 */
public class Client {

    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static String chatServer;
    private static Socket client;
    private static String message;

    public static String runClient(String Message){
        try
        {
            message = Message;
            connectToServer();
            getStreams();
            processConnection();
        }
        catch ( EOFException eofException )
        {
            System.out.println("Client terminated connection");
        }
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        }
        finally
        {
            closeConnection();
            return message;
        }
    }

    private static void connectToServer() throws IOException {
        System.out.println("CLIENT >>> Attempting connection");
        client = new Socket(InetAddress.getByName( chatServer ), 12345);
        System.out.println("CLIENT >>> Connected to: " + client.getInetAddress().getHostName() );
    }

    private static void getStreams() throws IOException {
        output = new ObjectOutputStream( client.getOutputStream() );
        output.flush();

        input = new ObjectInputStream( client.getInputStream() );
        System.out.println("Got I/O Streams");        System.out.println("CLIENT>>> " + message);

    }

    private static void processConnection() throws IOException {
        // Proccess for the Client (Different than the server)
        // The client SENDS information first, then reads back a double sent back by the server
        sendData(message);

        try
        {
            message = (String) input.readObject();
            System.out.println("SERVER>>> " + message);
        }
        catch ( ClassNotFoundException classNotFoundException )
        {
            System.out.println("Unknown object type received");
        }
    }

    private static void closeConnection(){
        System.out.println("Closing connection");
        try {
            output.close();
            input.close();
            client.close();
        }
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        }
    }

    private static void sendData(String message){
        try{
        output.writeObject(message);
        output.flush();
        System.out.println("CLIENT>>> " + message);
        }
        catch(IOException ex){
           System.out.println("Error writing object");
        }
    }
}
