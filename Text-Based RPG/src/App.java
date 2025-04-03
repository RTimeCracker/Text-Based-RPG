import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);   

        System.out.println("Welcome to Text-Based RPG");
        System.out.println("A game where you can explore, fight enemies, and gather loot!");
        continuePrompt(sc);

        tutorialPrompt(sc);
        

    }

    static void tutorialPrompt(Scanner sc){
        int tutorialChoice;

        System.out.println("Would you like to go through a BRIEF tutorial?");
        System.out.println("[1] Yes, I would like to learn about this game as much as I can.");
        System.out.println("[2] No, I'm already a pro at this, why would I need help?");  

        tutorialChoice = sc.nextInt();

        while(tutorialChoice != 1 && tutorialChoice != 2){
            System.out.println("Error! Please try again.");
            System.out.println("[1] Yes, I would like to learn about this game as much as I can.");
            System.out.println("[2] No, I'm already a pro at this, why would I need help?");   

            tutorialChoice = sc.nextInt();
        }

        if(tutorialChoice == 1){
            System.out.println("Great!");
            //Kinda dont know how the game would work, so im planning on leaving this as is until our game idea is clear. -K
        }else{
            System.out.println("Understandable, It would seem that thou art confident in your abilities.");
            System.out.println("Have a great time! :)");
        }
        
    }

    static void continuePrompt(Scanner sc){
        System.out.println("Press enter to continue.");
        sc.nextLine();
    }

    static void playerInfoPrompt(Scanner sc){
        System.out.println("I know you're raring to go, but first we must know your name.");
        String name = sc.next();
        System.out.println("Great! So you're name is " + name);
        continuePrompt(sc);
    }

    static void explorePrompt(Scanner sc, Player player){
        System.out.println("Where would you like to go?");
        System.out.println("[1] North");
        System.out.println("[2] East");
        System.out.println("[3] South");
        System.out.println("[4] West");

        int directionChoice = sc.nextInt();

        while(directionChoice != 1 && directionChoice != 2 && directionChoice != 3 && directionChoice != 4){
            System.out.println("Invalid choice please choose again.");
            System.out.println("[1] North");
            System.out.println("[2] East");
            System.out.println("[3] South");
            System.out.println("[4] West");
        }

        switch (directionChoice) {
            case 1 -> { 
                //Move North
                player.move(0, 1);
            }
            case 2 -> {
                //Move East
                player.move(1, 0);
            }
            case 3 -> {
                //Move South
                player.move(0, -1);
            }
            case 4 -> {
                //Move West
                player.move(-1, 0);
            }
            default -> {
            }
        }
    }

}
