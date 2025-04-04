import java.util.List;
import java.util.Scanner;

public class App {
    private static Scanner sc = new Scanner(System.in);   
    private static Player player;
    private static Zone startingZone;

    public static void main(String[] args) {
        displayWelcome();
        continuePrompt();
        tutorialPrompt();
        
        startingZone = new Zone(ZoneType.Village, 0, 0);
        player = Player.createPlayer(sc);
        player.zone = startingZone;
        
        while (true) {
            explorePrompt();
        }
    }

    //DISPLAY METHODS
    
    public static void displayWelcome() {
        System.out.println("Welcome to Text-Based RPG");
        System.out.println("A game where you can explore, fight enemies, and gather loot!");
    }

    public static void displayTutorialChoice() {
        System.out.println("Would you like to go through a BRIEF tutorial?");
        System.out.println("[1] Yes, I would like to learn about this game as much as I can.");
        System.out.println("[2] No, I'm already a pro at this, why would I need help?");  
    }

    public static void displayTutorialResponse(int choice) {
        if (choice == 1) {
            System.out.println("Great!");
        } else {
            System.out.println("Understandable, It would seem that thou art confident in your abilities.");
            System.out.println("Have a great time! :)");
        }
    }

    public static void displayError() {
        System.out.println("Error! Please try again.");
    }

    public static void displayZoneEnter(Zone zone) {
        System.out.println("You have entered Zone " + zone.zoneNumber);
        int[] zonePosition = Zone.getZonePosition(zone);
        System.out.println("Zone position " + "x: " + zonePosition[0] + " y: " + zonePosition[1]);
    }

    public static void displayZoneType(ZoneType zoneType) {
        System.out.println("\nYou have entered a " + zoneType + " zone!");
    }


    public static void displayEventDescription(String description) {
        System.out.println("\n" + description);
    }

    public static void displayEventChoices(String... choices) {
        System.out.println("\nWhat will you do?");
        for (String choice : choices) {
            System.out.println(choice);
        }
        System.out.print("Enter your choice: ");
    }

    public static void displayEventOutcome(String outcome) {
        System.out.println("\n" + outcome);
        continuePrompt();
    }

    public static void displayVillageOptions() {
        System.out.println("Welcome to the village! What would you like to do?");
        System.out.println("[1] Buy items");
        System.out.println("[2] Sell items");
        System.out.println("[3] Leave village");
    }

    public static void displayShopItems(List<Item> items) {
        System.out.println("Available items for purchase:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("[%d] %s - %s (Price: 100 gold)\n", i + 1, item.name, item.description);
        }
        System.out.println("[0] Exit shop");
    }

    public static void displayPlayerInventory(List<Item> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }

        System.out.println("Your inventory:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.printf("[%d] %s - %s%n", i + 1, item.name, item.description);
        }
        System.out.println("[0] Back");
    }

    public static void displayCombatStart() {
        System.out.println("Prepare for battle!");
    }

    public static void displayCombatStatus(int playerHP, int enemyHP, String enemyName) {
        System.out.println("\nYour HP: " + playerHP);
        System.out.println(enemyName + "'s HP: " + enemyHP);
    }

    public static void displayCombatOptions() {
        System.out.println("[1] Attack");
        System.out.println("[2] Use Item");
    }

    public static void displayEnemyAppearance(String enemyName) {
        System.out.println("A wild " + enemyName + " appears!");
    }

    public static void displayBossAppearance(String bossName) {
        System.out.println("A fearsome " + bossName + " appears!");
    }

    public static void displayAttackResult(String attacker, String target, int damage) {
        System.out.println(attacker + " hits " + target + " for " + damage + " damage!");
    }

    public static void displayVictory(String enemyName) {
        System.out.println("You defeated the " + enemyName + "!");
    }

    public static void displayItemObtained(String itemName) {
        System.out.println("You obtained " + itemName + "!");
    }

    public static void displayExpGained(int amount) {
        System.out.println("Gained " + amount + " experience points!");
    }

    public static void displayGameOver() {
        System.out.println("You were defeated...");
        System.out.println("Game Over!");
    }

    public static void displayLevelUp(String name, int level, int hp, int atk, int def) {
        System.out.printf("%s grew to level %d!%n", name, level);
        System.out.printf("HP: +%d, ATK: +%d, DEF: +%d%n", hp, atk, def);
    }

    public static void displayItemUsed(String itemName, String effect, int amount) {
        System.out.println("Using a " + itemName + ", it " + effect + " " + amount + "!");
    }

    public static void displayClassSelection() {
        System.out.println("Choose your class:");
    }

    public static void displayClassOption(int num, String className) {
        System.out.printf("[%d] %s%n", num, className);
    }

    public static void promptClassChoice() {
        System.out.print("Enter your choice: ");
    }

    public static void displayInputError() {
        System.out.println("Invalid input! Please enter a number.");
    }

    public static void promptPlayerName() {
        System.out.print("Enter your character's name: ");
    }

    public static void displayChosenClass(String className) {
        System.out.printf("You have chosen to be a %s!%n", className);
    }

    public static void displayClassDescription(String description) {
        System.out.println(description);
    }

    public static void displayNewZoneDiscovery(String zoneType) {
        System.out.println("Discovered a new " + zoneType + " zone!");
    }

    public static void displayReturningZone(String zoneType) {
        System.out.println("Returning to previously visited " + zoneType + " zone");
    }

    public static void displayItemAddedToInventory(String itemName) {
        System.out.println("Added " + itemName + " to your inventory!");
    }

    public static void promptItemSelection() {
        System.out.print("Select an item to use: ");
    }

    public static void displayPlayerInventoryForSelling(List<Item> inventory) {
        System.out.println("Your items:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.printf("[%d] %s - Sell for 50 gold\n", i + 1, item.name);
        }
        System.out.println("[0] Cancel");
    }

    public static void promptSellChoice() {
        System.out.print("Select an item to sell: ");
    }

    public static void displayItemPurchased(String itemName) {
        System.out.println("You bought " + itemName + "!");
    }

    public static void displayNotEnoughMoney() {
        System.out.println("Not enough money!");
    }

    public static void displayEmptyInventory() {
        System.out.println("You have no items to sell!");
    }

    public static void displayItemSold(String itemName) {
        System.out.println("Sold " + itemName + " for 50 gold!");
    }

    //PROMPT METHODS
    
    static void tutorialPrompt() {
        int tutorialChoice;
        displayTutorialChoice();
        tutorialChoice = sc.nextInt();

        while(tutorialChoice != 1 && tutorialChoice != 2) {
            displayError();
            displayTutorialChoice();
            tutorialChoice = sc.nextInt();
        }
        displayTutorialResponse(tutorialChoice);
    }

    static void continuePrompt() {
        System.out.println("Press enter to continue.");
        sc.nextLine();
    }

    static void explorePrompt() {
        System.out.println("Where would you like to go?");
        System.out.println("[1] North");
        System.out.println("[2] East");
        System.out.println("[3] South");
        System.out.println("[4] West");

        int directionChoice = sc.nextInt();

        while(directionChoice != 1 && directionChoice != 2 && directionChoice != 3 && directionChoice != 4) {
            System.out.println("Invalid choice please choose again.");
            System.out.println("[1] North");
            System.out.println("[2] East");
            System.out.println("[3] South");
            System.out.println("[4] West");
            directionChoice = sc.nextInt();
        }

        switch (directionChoice) {
            case 1 -> player.move(0, 1);
            case 2 -> player.move(1, 0);
            case 3 -> player.move(0, -1);
            case 4 -> player.move(-1, 0);
        }

        onZoneEnter();
    }

    static void onZoneEnter() {
        displayZoneEnter(player.zone);
        displayZoneType(player.zone.zoneType);  //pass the enum directly
        player.zone.handleZoneEvent(player, sc);
    }
}