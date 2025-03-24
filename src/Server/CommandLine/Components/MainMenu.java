package src.Server.CommandLine.Components;

import src.Server.DataBaseInterface;
import src.Server.CommandLine.CommandLineFunctions;

public class MainMenu extends CommandLineFunctions {
    
    public MainMenu(DataBaseInterface db_interface) {
        super(db_interface);
    }

    public int run() {
        return prompt_main_menu();
    }

    public void prompt_welcome() {
        get_prompt_print("Welcome");
    }

    private boolean verify_input(String input) {
        if (input.equals("1") || input.equals("2") || input.equals("3")) {
            return true;
        }
        return false;
    }

    public int prompt_main_menu() {
        get_prompt_print("MainMenu");
        int incorrect_attempts = 0;
        while (incorrect_attempts < 3) {
            String choice = get_prompt_enter("MainMenuChoice");
            if (!verify_input(choice)) {
                io.println("Invalid input");
                incorrect_attempts++;
                continue;
            }else{
                return Integer.parseInt(choice);
            }
        }
        return 3; // exit
    }
}
