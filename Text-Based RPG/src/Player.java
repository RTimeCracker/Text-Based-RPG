import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Player extends Entity {
    public int level;
    private int exp;
    private int expToNextLevel;
    public int money;
    public List<Item> inventory;
    
    Zone zone;
    private int xPos = 0, yPos = 0;
    private static Random random = new Random();

    public Player(String name, int money, EntityClass playerClass) {
        super(name , 0, 0, 0, 0, playerClass);
        this.level = 1;
        this.exp = 0;
        this.expToNextLevel = 100;
        this.money = money;
        this.inventory = new ArrayList<>();
        this.zone = new Zone(ZoneType.Village, 0, 0);
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
            case Tank:
                this.hp = 150;
                this.atk = 8000;
                this.def = 20;
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
        App.displayClassSelection();
        for (int i = 0; i < EntityClass.values().length; i++) {
            App.displayClassOption(i + 1, EntityClass.values()[i].toString());
        }

        int classChoice = 0;
        while (classChoice < 1 || classChoice > EntityClass.values().length) {
            App.promptClassChoice();
            try {
                classChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                App.displayInputError();
            }
        }

        App.promptPlayerName();
        String name = scanner.nextLine();

        EntityClass chosenClass = EntityClass.values()[classChoice - 1];
        App.displayChosenClass(chosenClass.toString());
        App.displayClassDescription(getClassDescription(chosenClass));

        return new Player(name, 100, chosenClass);
    }

    private static String getClassDescription(EntityClass playerClass) {
        switch (playerClass) {
            case Warrior:
                return "Warrior: A strong melee fighter with high HP and balanced attack.";
            case Mage:
                return "Mage: A powerful spellcaster with high damage but low defense.";
            case Tank:
                return "Tank: C H U N K Y.";
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
        
        int hpIncrease = 0, atkIncrease = 0, defIncrease = 0; 
        
        switch (entityClass) {
            case Warrior:
                hpIncrease = 15;
                atkIncrease = 2;
                defIncrease = 3;
                break;
            case Mage:
                hpIncrease = 8;
                atkIncrease = 5;
                defIncrease = 1;
                break;
            case Tank:
                hpIncrease = 12;
                atkIncrease = 1;
                defIncrease = 7;
                break;
            case Summoner:
                hpIncrease = 10;
                atkIncrease = 3;
                defIncrease = 2;
                break;
        }
        
        hp += hpIncrease;
        atk += atkIncrease;
        def += defIncrease;
        
        App.displayLevelUp(name, level, hpIncrease, atkIncrease, defIncrease);
    }

    public void move(int dx, int dy) {
        this.xPos += dx;
        this.yPos += dy;
        changeZone();
    }

    private void changeZone() {
        Zone existingZone = Zone.getZoneFromPosition(this.xPos, this.yPos);
        
        if (existingZone == null) {
            ZoneType[] zoneTypes = ZoneType.values();
            ZoneType randomType = zoneTypes[random.nextInt(zoneTypes.length)];
            
            this.zone = new Zone(randomType, this.xPos, this.yPos);
            App.displayNewZoneDiscovery(randomType.toString());
        } else {
            this.zone = existingZone;
            App.displayReturningZone(this.zone.zoneType.toString());
        }
    }

    public int getExp() {
        return exp;
    }

    public int getExpToNextLevel() {
        return expToNextLevel;
    }

    public void addExp(int amount) {
        this.exp += amount;
        App.displayExpGained(amount);
        
        if (this.exp >= this.expToNextLevel) {
            levelUp();
        }
    }

    public void useItem(Item item) {
        item.use(this);
        inventory.remove(item);
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
        App.displayItemAddedToInventory(item.name);
    }

    public void showInventory(Scanner sc) {
        App.displayPlayerInventory(inventory);
        if (inventory.isEmpty()) return;

        int choice = App.getIntInput("\nSelect an item to use (0 to cancel): ", 0, inventory.size());
        
        if (choice > 0 && choice <= inventory.size()) {
            useItem(inventory.get(choice - 1));
        }
    }
}