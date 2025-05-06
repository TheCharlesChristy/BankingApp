package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

import src.Structs.Accounts;
import src.Structs.Users;
import src.Structs.Transactions;

import java.util.Collections;
import java.util.List;

public class AdminViewAccount extends CommandLineFunctions {
    public AdminViewAccount(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public void run(Accounts account) {
        int invalidChoices = 0;
        final int max_invalid_attempts = 3;
        boolean running = true;
        
        while (running && invalidChoices < max_invalid_attempts) {
            get_prompt_print("AdminViewAccount");
            String choice_str = get_prompt_enter("AdminViewAccountChoice");
            int choice;
            try {
                choice = Integer.parseInt(choice_str);
            } catch (NumberFormatException e) {
                get_prompt_print("InvalidChoice");
                invalidChoices++;
                continue;
            }
            
            if (choice == 4) {
                // Exit condition
                running = false;
            } else if (choice < 1 || choice > 3) {
                get_prompt_print("InvalidChoice");
                invalidChoices++;
            } else {
                // Valid choice, reset invalid counter
                invalidChoices = 0;
                
                switch (choice) {
                    case 1:
                        // View account details such as acc number, associated users and their details
                        view_account_details(account);
                        break;
                    case 2:
                        // View account balance
                        view_account_balance(account);
                        break;
                    case 3:
                        // View transaction history
                        view_transaction_history(account);
                        break;
                }
            }
        }
        
        if (invalidChoices >= max_invalid_attempts) {
            get_prompt_print("TooManyInvalidChoices");
        }
    }

    private void view_account_details(Accounts account) {
        // Get the username of the account owner
        Users account_owner = db_interface.user_interface.get_user(account.user_id);
        String owner_username = account_owner != null ? account_owner.get_username() : "Unknown User";

        
        // Display account details in a formatted manner
        io.println("\n---- Account Details ----");
        io.println("Account ID: " + account.get_account_id());
        io.println("Owner Username: " + owner_username);
        io.println("Interest Rate: " + (account.interest_rate * 100) + "%");
        io.println("Created At: " + account.created_at);
        io.println("------------------------\n");
        
        // Pause to let the user read the information
        get_prompt_enter("PressEnterToContinue");
        
    }

    private void view_account_balance(Accounts account) {
        // Implementation for viewing account balance
        ViewBalance viewBalance = new ViewBalance(db_interface);
        viewBalance.run(account);
    }

    private void view_transaction_history(Accounts account) {
        // Get all transactions for this account
        List<Transactions> transactions = get_account_transactions(account);
        
        // Sort the transactions by date (most recent first)
        Collections.sort(transactions, (t1, t2) -> t2.created_at.compareTo(t1.created_at));
        
        if (transactions.isEmpty()) {
            io.println("\n---- Transaction History ----");
            io.println("No transactions found for this account.");
            io.println("----------------------------\n");
            get_prompt_enter("PressEnterToContinue");
            return;
        }
        
        int startIndex = 0;
        int pageSize = 5;
        boolean showMore = true;
        
        while (showMore && startIndex < transactions.size()) {
            // Display a header for the transaction list
            io.println("\n---- Transaction History ----");
            
            // Calculate how many transactions to display (min of pageSize or remaining transactions)
            int endIndex = Math.min(startIndex + pageSize, transactions.size());
            
            // Display the current page of transactions
            for (int i = startIndex; i < endIndex; i++) {
                Transactions transaction = transactions.get(i);
                io.println(String.format("ID: %-5d | Date: %-19s | Type: %-10s | Amount: $%.2f", 
                    transaction.id, 
                    transaction.created_at.toString(), 
                    transaction.type, 
                    transaction.amount));
            }
            
            io.println("----------------------------");
            
            // Check if there are more transactions to display
            if (endIndex < transactions.size()) {
                // Ask if the user wants to see more transactions
                String response = get_prompt_enter("DisplayMore");
                showMore = response.equalsIgnoreCase("y");
                
                if (showMore) {
                    startIndex += pageSize;
                }
            } else {
                // No more transactions to display
                io.println("End of transaction history.");
                get_prompt_enter("PressEnterToContinue");
                showMore = false;
            }
        }
    }
}
