package sample;

/**
 * Created by maverickxm2 on 08/02/17.
 */
public class BankDatabase {

    private static Account accounts[];
    private static int accountNoLoggedIn;
    private static double newBalance;

    // The public database for accounts
    public BankDatabase()
    {
        accounts = new Account[5];
        accounts[0] = new Account(12345,12345,100.0);
        accounts[1] = new Account(23456,23456,200.0);
        accounts[2] = new Account(34567,34567,300.0);
        accounts[3] = new Account(45678,45678,400.0);
        accounts[4] = new Account(56789,56789,500.0);
    }

    public double getAvailableBalance(String operation, String accountNo, String accountPIN, String accountOperation){
        newBalance = 0.0;
        for (Account currentAccount : accounts){
            // I need to find the account it's referring to
            if (currentAccount.ValidateAccount(Integer.parseInt(accountNo), Integer.parseInt(accountPIN))){
                // Once I find the account, switch case for Operation
                switch(operation){
                    case "Deposit":
                        System.out.println("SERVER>>> Depositing money");
                        newBalance = currentAccount.Deposit(Double.parseDouble(accountOperation));
                        System.out.println(newBalance);
                        break;
                    case "Withdraw":
                        System.out.println("SERVER>>> Withdrawing money");
                        newBalance = currentAccount.Withdraw(Double.parseDouble(accountOperation));
                        System.out.println(newBalance);
                        break;
                    case "Balance":
                        System.out.println("SERVER>>> Checking Balance");
                        newBalance = currentAccount.GetAccountBalance();
                        break;
                    default:
                        System.out.println("SERVER>>> Invalid account");
                        break;
                }

                // Always returns newBalance after the operation
                return newBalance;
            }
        }
        // Returns 0 new balance in case there is no account found
        return newBalance;
    }
}
