import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player extends Entity {
    private int level;
    private int exp;
    private int expToNextLevel;
    private int money;
    private List<Item> inventory;
    private Zone currentZone;

    public Player(String name, int money, EntityClass playerClass) {
        super(name, 0, 0, 0, playerClass); // Initialize with 0 stats, will be set based on class
        this.level = 1;
        this.exp = 0;
        this.expToNextLevel = 100;
        this.money = money;
        this.inventory = new ArrayList<>();
        initializeClassStats(playerClass);
    }

    private void initializeClassStats(EntityClass playerClass) {
        switch (playerClass) {
            case Warrior:
                this.hp = 120;
                this.atk = 15;
                this.def = 10;
                break;
            case Mage:
                this.hp = 80;
                this.atk = 20;
                this.def = 5;
                break;
            case Healer:
                this.hp = 100;
                this.atk = 8;
                this.def = 12;
                break;
            case Summoner:
                this.hp = 90;
                this.atk = 12;
                this.def = 8;
                break;
            default:
                this.hp = 100;
                this.atk = 10;
                this.def = 10;
        }
    }

    public static Player createPlayer(Scanner scanner) {
        System.out.println("Choose your class:");
        for (int i = 0; i < EntityClass.values().length; i++) {
            System.out.printf("[%d] %s%n", i + 1, EntityClass.values()[i]);
        }

        int classChoice = 0;
        while (classChoice < 1 || classChoice > EntityClass.values().length) {
            System.out.print("Enter your choice: ");
            try {
                classChoice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (Exception e) {
                scanner.nextLine(); 
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine();

        EntityClass chosenClass = EntityClass.values()[classChoice - 1];
        System.out.printf("You have chosen to be a %s!%n", chosenClass);
        System.out.println(getClassDescription(chosenClass));

        return new Player(name, 100, chosenClass); 
    }

    private static String getClassDescription(EntityClass playerClass) {
        switch (playerClass) {
            case Warrior:
                return "Warrior: A strong melee fighter with high HP and balanced attack.";
            case Mage:
                return "Mage: A powerful spellcaster with high damage but low defense.";
            case Healer:
                return "Healer: A supportive class with good survivability and healing abilities.";
            case Summoner:
                return "Summoner: A versatile class that commands creatures to fight for them.";
            default:
                return "Adventurer: A well-rounded beginner class.";
        }
    }

    protected void levelUp() {
        level++;
        exp -= expToNextLevel;
        expToNextLevel = (int)(expToNextLevel * 1.5);
        
        // Class-specific stat growth
        switch (entityClass) {
            case Warrior:
                hp += 15;
                atk += 2;
                def += 3;
                break;
            case Mage:
                hp += 8;
                atk += 5;
                def += 1;
                break;
            case Healer:
                hp += 12;
                atk += 1;
                def += 2;
                break;
            case Summoner:
                hp += 10;
                atk += 3;
                def += 2;
                break;
        }
        
        System.out.printf("%s grew to level %d!%n", name, level);
        System.out.printf("HP: +%d, ATK: +%d, DEF: +%d%n", hp, atk, def);
    }
}