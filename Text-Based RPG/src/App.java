import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sound.sampled.*;
public class App {    
 
    private static Scanner sc = new Scanner(System.in);   
    private static Player player;
    private static Zone startingZone;

    public static void main(String[] args) throws LineUnavailableException, IOException {
        MainFrame frame = new MainFrame("Vaiken: Last Legacy");
        
        startingZone = new Zone(ZoneType.Village, 0, 0);
        //player = Player.createPlayer(sc);
        //player.zone = startingZone;
        
        while (true) {
            //explorePrompt();
        }
    }

    // ================= DISPLAY METHODS =================
    
    public static void displayWelcome() {
        System.out.println("\nWelcome to Text-Based RPG");
        System.out.println("A game where you can explore, fight enemies, and gather loot!");
    }

    public static void displayTutorialChoice() {
        System.out.println("\nWould you like to go through a BRIEF tutorial?");
        System.out.println("=================================================================");
        System.out.println("[1] Yes, I would like to learn about this game as much as I can.");
        System.out.println("[2] No, I'm already a pro at this, why would I need help?");
    }

    public static void displayTutorialResponse(int choice) {
        System.out.println();
        if (choice == 1) {
            System.out.println("Great!");
        } else {
            System.out.println("Understandable, It would seem that thou art confident in your abilities.");
            System.out.println("Have a great time! :)");
        }
    }

    public static void displayError() {
        System.out.println("\nError! Please try again.");
    }

    public static void displayZoneContents(Zone zone) {
        System.out.println("\n====================================");
        System.out.printf("You have entered a %s zone at (%d,%d)%n", 
                         zone.zoneType, zone.xPos, zone.yPos);
        System.out.println("====================================");
        
        switch(zone.zoneType) {
            case Village:
                System.out.println("The peaceful sounds of civilization greet you");
                break;
            case Dungeon:
                System.out.println("A chill runs down your spine as you enter");
                break;
            case Encounter:
                System.out.println("You feel watchful eyes upon you...");
                break;
            case Event:
                System.out.println("Something unusual catches your attention");
                break;
        }
        System.out.println("====================================\n");
    }

    public static void displayPeacefulArea() {
        System.out.println("\nThe area is quiet - no enemies to fight here now.");
    }
    
    public static void displayNothingFound() {
        System.out.println("\nYou search the area but find nothing of interest.");
    }

    public static void displayEventDescription(String description) {
        System.out.println("\n" + description);
    }

    public static void displayEventChoices(String... choices) {
        System.out.println("\nWhat will you do?");
        for (String choice : choices) {
            System.out.println(choice);
        }
        System.out.print("\nEnter your choice: ");
    }

    public static void displayEventOutcome(String outcome) {
        System.out.println("\n" + outcome);
        continuePrompt();
    }

    public static void displayVillageOptions() {
        System.out.println("\nWelcome to the village! What would you like to do?");
        System.out.println("[1] Buy items");
        System.out.println("[2] Sell items");
        System.out.println("[3] Leave village");
        System.out.print("\nEnter choice: ");
    }

    public static void displayShopItems(List<Item> items) {
        System.out.println("\nAvailable items for purchase:");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            System.out.printf("[%d] %s - %s (Price: 100 gold)\n", i + 1, item.name, item.description);
        }
        System.out.println("[0] Exit shop");
        System.out.print("\nSelect item: ");
    }

    public static void displayPlayerInventory(List<Item> inventory) {
        System.out.println();
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
        System.out.print("\nSelect item: ");
    }

    public static void displayCombatStart() {
        System.out.println("\n====================================");
        System.out.println("Prepare for battle!");
        System.out.println("====================================\n");
    }

    public static void displayCombatStatus(int playerHP, int enemyHP, String enemyName) {
        System.out.println("Your HP: " + playerHP);
        System.out.println(enemyName + "'s HP: " + enemyHP + "\n");
    }

    public static void displayCombatOptions() {
        System.out.println("[1] Attack");
        System.out.println("[2] Use Item");
        System.out.print("\nChoose action: ");
    }

    public static void displayEnemyAppearance(String enemyName) {
        System.out.println("\nA wild " + enemyName + " appears!");
    }

    public static void displayBossAppearance(String bossName) {
        System.out.println("\nA fearsome " + bossName + " appears!");
    }

    public static void displayAttackResult(String attacker, String target, int damage) {
        System.out.println("\n" + attacker + " Hits " + target + " for " + damage + " damage!");
    }

    public static void displayVictory(String enemyName) {
        System.out.println("\nYou defeated the " + enemyName + "!");
    }

    public static void displayItemObtained(String itemName) {
        System.out.println("\nYou obtained " + itemName + "!");
    }

    public static void displayExpGained(int amount) {
        System.out.println("\nGained " + amount + " experience points!");
    }

    public static void displayGameOver() {
        System.out.println("\nYou were defeated...");
        System.out.println("Game Over!");
    }

    public static void displayLevelUp(String name, int level, int hp, int atk, int def) {
        System.out.printf("\n%s grew to level %d!%n", name, level);
        System.out.printf("HP: +%d, ATK: +%d, DEF: +%d%n\n", hp, atk, def);
    }

    public static void displayItemUsed(String itemName, String effect, int amount) {
        System.out.println("\nUsing a " + itemName + ", it " + effect + " " + amount + "!");
    }

    public static void displayClassSelection() {
        System.out.println("\nChoose your class:");
    }

    public static void displayClassOption(int num, String className) {
        System.out.printf("[%d] %s%n", num, className);
    }

    public static void promptClassChoice() {
        System.out.print("\nEnter your choice: ");
    }

    public static void displayInputError() {
        System.out.println("\nInvalid input! Please enter a number.");
    }

    public static void promptPlayerName() {
        System.out.print("\nEnter your character's name: ");
    }

    public static void displayChosenClass(String className) {
        System.out.printf("\nYou have chosen to be a %s!%n", className);
    }

    public static void displayClassDescription(String description) {
        System.out.println("\n" + description);
    }

    public static void displayNewZoneDiscovery(String zoneType) {
        System.out.println("\nDiscovered a new " + zoneType + " zone!");
    }

    public static void displayReturningZone(String zoneType) {
        System.out.println("\nReturning to previously visited " + zoneType + " zone");
    }

    public static void displayItemAddedToInventory(String itemName) {
        System.out.println("\nAdded " + itemName + " to your inventory!");
    }

    public static void displayPlayerInventoryForSelling(List<Item> inventory) {
        System.out.println("\nYour items:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.printf("[%d] %s - Sell for 50 gold\n", i + 1, item.name);
        }
        System.out.println("[0] Cancel");
        System.out.print("\nSelect item to sell: ");
    }

    public static void displayItemPurchased(String itemName) {
        System.out.println("\nYou bought " + itemName + "!");
    }

    public static void displayNotEnoughMoney() {
        System.out.println("\nNot enough money!");
    }

    public static void displayEmptyInventory() {
        System.out.println("\nYou have no items to sell!");
    }

    public static void displayItemSold(String itemName) {
        System.out.println("\nSold " + itemName + " for 50 gold!");
    }

    public static void displayStatusEffect(String message) {
        System.out.println("\n[!] " + message);
    }
    
    public static void displayCombatStatus(Player player, Enemy enemy) {
        System.out.println("\n====================");
        System.out.println(player.name + ":");
        System.out.println("HP: " + player.hp + "/" + player.getMaxHp());
        System.out.println("MP: " + player.mp + "/" + player.getMaxMp());
        System.out.println("Status: " + getStatusString(player));
        
        System.out.println("\n" + enemy.name + ":");
        System.out.println("HP: " + enemy.hp);
        System.out.println("Status: " + getStatusString(enemy));
        System.out.println("====================\n");
    }
    
    private static String getStatusString(Entity entity) {
        List<String> statuses = new ArrayList<>();
        if (entity.isPoisoned) statuses.add("Poison");
        if (entity.isSilenced) statuses.add("Silence");
        if (entity.isParalyzed) statuses.add("Paralyze");
        if (entity instanceof Player) {
            Player p = (Player)entity;
            if (p.hasProtect) statuses.add("Protect");
            if (p.hasShell) statuses.add("Shell");
        }
        return statuses.isEmpty() ? "Normal" : String.join(", ", statuses);
    }

    // ================= PROMPT METHODS =================
    
    static void tutorialPrompt() {
        displayTutorialChoice();
        int tutorialChoice = getIntInput("\nEnter choice (1-2): ", 1, 2);
        displayTutorialResponse(tutorialChoice);
    }

    static void continuePrompt() {
        System.out.print("\nPress enter to continue...");
        sc.nextLine();
    }

    static void explorePrompt() {
        System.out.println("\nCurrent Location: (" + player.zone.xPos + "," + player.zone.yPos + ")");
        System.out.println("Where would you like to go?");
        System.out.println("[1] North");
        System.out.println("[2] East");
        System.out.println("[3] South");
        System.out.println("[4] West");
        
        int directionChoice = getIntInput("\nEnter direction (1-4): ", 1, 4);
        
        switch (directionChoice) {
            case 1 -> player.move(0, 1);
            case 2 -> player.move(1, 0);
            case 3 -> player.move(0, -1);
            case 4 -> player.move(-1, 0);
        }
        onZoneEnter();
    }

    static void onZoneEnter() {
        player.zone.handleZoneEvent(player, sc);
    }


    // ================= HELPER METHODS =================
    
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = sc.nextInt();
                sc.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    return input;
                }
                displayError();
            } catch (Exception e) {
                sc.nextLine(); // Clear invalid input
                displayInputError();
            }
        }
    }


    // ================ Music Methods =====================
    

}