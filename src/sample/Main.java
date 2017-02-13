package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Main extends Application {

    private ObjectOutputStream output; // output stream to server
    private ObjectInputStream input; // input stream from server
    private String chatServer; // host server for this application
    private Socket client; // socket to communicate with server
    private String message;

    @FXML private TextField textAccountID;
    @FXML private TextField textAccountPIN;
    @FXML private TextField textAmount;
    @FXML private TextField textBalance;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Opens the GUI for the ATM
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ATM");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML private void handleDepositButton(ActionEvent event){
        // The deposit button sends the String with the header Deposit.
        // It then expects a double back after opening Client.runClient()
        // After each proccess it clears the PIN and AMOUNT field so the user is left with a Balance and AccountNumber
        message = "Deposit/" + textAccountID.getText() + "/" + textAccountPIN.getText() + "/" + textAmount.getText();
        System.out.println(message);
        textBalance.setText(Client.runClient(message));
        resetFields();
    }

    @FXML private void handleWithdrawButton(ActionEvent event){
        // The withdraw button sends the String with the header Withdraw.
        // It then expects a double back after opening Client.runClient()
        // After each proccess it clears the PIN and AMOUNT field so the user is left with a Balance and AccountNumber
        message = "Withdraw/" + textAccountID.getText() + "/" + textAccountPIN.getText() + "/" + textAmount.getText();
        textBalance.setText(Client.runClient(message));
        resetFields();
    }

    @FXML private void handleCheckButton(ActionEvent event){
        // The Check button sends the String with the header Balance.
        // It then expects a double back after opening Client.runClient()
        // After each proccess it clears the PIN and AMOUNT field so the user is left with a Balance and AccountNumber
        message = "Balance/" + textAccountID.getText() + "/" + textAccountPIN.getText() + "/" + textAmount.getText();
        textBalance.setText(Client.runClient(message));
        resetFields();
    }

    private void resetFields(){
        textAccountPIN.setText("");
        textAmount.setText("");
    }
}
