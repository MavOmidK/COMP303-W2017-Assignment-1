package sample;

/**
 * Created by Omid Khataee on 08/02/17.
 * Account.java
 * Assignment 1
 */


public class Account {
    // This is the Account class
    private int accountNo;
    private int accountPIN;
    private double accountBalance;

    // The default constructor
    public Account(int accountNo, int accountPIN, double accountBalance)
    {
        this.accountNo = accountNo;
        this.accountPIN = accountPIN;
        this.accountBalance = accountBalance;
    }

    // Validate account method that returns true if the values being passed into it match it's accountNo and accountPIN
    public boolean ValidateAccount (int accountNo, int accountPIN){
        if(this.accountNo == accountNo){
            if (this.accountPIN == accountPIN){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }

    // Returns the current accountBalance without any operations
    public double GetAccountBalance(){
        return this.accountBalance;
    }

    // Returns the accountBalance after the deposit operation
    public double Deposit(double accountOperation){
        this.accountBalance += accountOperation;
        return this.accountBalance;
    }

    // Returns the accountBalance after the withdraw operation
    public double Withdraw(double accountOperation){
        // Simple check to make sure you are not overdrawing the accountBalance if the account you want to withdraw is bigger
        if(this.accountBalance >= accountOperation){
            this.accountBalance -= accountOperation;
            return this.accountBalance;
        }
        else{
            System.out.println("SYSTEM>>> User doesn't have enough money to withdraw");
            return this.accountBalance;
        }
    }


    public int getAccountNumber()
    {
        return accountNo;
    } // end method getAccountNumber
}
