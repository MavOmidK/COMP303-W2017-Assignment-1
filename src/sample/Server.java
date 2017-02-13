package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Omid Khataee on 07/02/17.
 * Server.Java
 * Assignment 1 - Networking
 */

public class Server {
    private static ObjectOutputStream output; // output    stream to client
    private boolean userAuthenticated;
    private static ObjectInputStream input; // input stream from client
    private static ServerSocket server; // server socket
    private static Socket connection; // connection to client
    private static String message = "";
    private static String messageSplit[];
    private static String operation;
    private static String accountNo;
    private static String accountPIN;
    private static String accountOperation;
    private static int counter = 1; // counter of number of connections
    private static double balance;
    private static BankDatabase bankDatabase = new BankDatabase();

    public static void main( String[] args ){
        // Calls the runServer method
        runServer();
    }

    public static void runServer(){
        try{
            // Create the Server socket and assign it to port 12345
            server = new ServerSocket(12345);
            System.out.println("Server assigned to port 12345");

            // Keeps the program from closing after a single operation
            while(true)

            try{
                waitForConnection();
                getStreams();
                processConnection();
            }
            catch(IOException ex) {
                // If the client suddenly disconnects from the server
                System.out.println("Server Connection Terminated");
            }
            finally{
                closeConnection();
            }
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
    }


    public static void waitForConnection() throws IOException{
        // Wait's for a connection
        System.out.println("Waiting for a connection");
        connection = server.accept();
        System.out.println( "Connection " + counter + " received from: " +
                connection.getInetAddress().getHostName());
    }

    public static void getStreams() throws IOException{
        // Get's the input and output streams
        output = new ObjectOutputStream(connection.getOutputStream() );
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        System.out.println("Got I/O streams");
    }

    public static void processConnection() throws IOException{
        // Recieves data from the client first
        // It is receiving a message that is going to contain Operation/AccountNo/AccountPIN/AccountOperation
        try
        {
            // Cast the message object into a String
           message = (String) input.readObject();
           System.out.println("SERVER>>> " + message);
        }
        catch ( ClassNotFoundException classNotFoundException )
        {
            System.out.println("Unknown object type received");
        }
        finally{
            // Using the .split() method using / as the delimiter
            messageSplit = message.split("/");
            operation = messageSplit[0];
            accountNo = messageSplit[1];
            accountPIN = messageSplit[2];
            // If the account operation is Balance, there won't be a third value
            if (!operation.equals("Balance")){
                accountOperation = messageSplit[3];
            }
            // But because the bankdatabase getAvailableBalance method requires 4 fields, we set this to 0 if it is just a Balance check
            else{
                accountOperation = "0";
            }
            //System.out.println("SERVER>>> " + operation + " " + accountNo + " " + accountPIN + " " + accountOperation);
        }

        // I get a balance back.
        // What values do I want to pass in. The command, Deposit, Withdraw or Check

        balance = bankDatabase.getAvailableBalance(operation, accountNo, accountPIN, accountOperation);
        // All of these return the balance
        // Send the balance back to the client
        sendData(Double.toString(balance));
    }


    public static void closeConnection(){
        // Method that properly closes the connection
        System.out.println("Terminating Connection");

        try
        {
            // Close the output stream
            output.close();
            // Close the input stream
            input.close();
            // Close the socket
            connection.close();
        }
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        }
    }

    public static void sendData(String message){
        //
        try
        {
            // Prepares to write the object
            output.writeObject(message);
            // Sends the object
            output.flush();
            System.out.println("CLIENT>>> " + message);
        }
        catch ( IOException ioException )
        {
            System.out.println("Error writing object");
        }
    }
}
