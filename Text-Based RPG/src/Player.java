import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Player extends Entity {
    private int exp;
    private int expToNextLevel;
    public int money;
    public List<Item> inventory = new ArrayList<>(Arrays.asList(
        Item.HealingPotion.maxPotion(), Item.BuffPotion.mindJuice(), Item.HealingPotion.phoenixDown(), Item.DebuffPotion.poison()
    ));
    Zone zone;
    public int xPos = 0, yPos = 0;
    private static Random random = new Random();
    private transient MainFrame frame;

    private File attackSFX = new File("Text-Based RPG\\SFX\\Attack.wav");
    private Clip SFXClip;

    public Enemy currentEnemy;

    public Player(String name, int money, EntityClass playerClass, MainFrame frame) {
        super(name, 0, 0, 0, 0, 0, 0, playerClass);
        this.frame = frame;
        this.exp = 0;
        this.expToNextLevel = 100;
        this.money = money;
        //this.inventory = new ArrayList<>();
        this.zone = new Zone(ZoneType.Village, 0, 0);
        try {
            SFXClip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
        }
        initializeClassStats(playerClass);
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    private void initializeClassStats(EntityClass playerClass) {
        switch (playerClass) {
            case Warrior:
                this.maxHp = 120; this.hp = maxHp;
                this.atk = 15; this.matk = 5;
                this.def = 10; this.mdef = 5;
                this.maxMp = 30; this.mp = maxMp;
                break;
            case Mage:
                this.maxHp = 80; this.hp = maxHp;
                this.atk = 5; this.matk = 20;
                this.def = 5; this.mdef = 10;
                this.maxMp = 100; this.mp = maxMp;
                break;
            case Tank:
                this.maxHp = 150; this.hp = maxHp;
                this.atk = 1000; this.matk = 0;
                this.def = 20; this.mdef = 15;
                this.maxMp = 20; this.mp = maxMp;
                break;
            case Summoner:
                this.maxHp = 90; this.hp = maxHp;
                this.atk = 12; this.matk = 15;
                this.def = 8; this.mdef = 10;
                this.maxMp = 80; this.mp = maxMp;
                break;
        }
    }

    public static Player createPlayer(String name, int classChosen, MainFrame frame) {
        EntityClass chosenClass = EntityClass.values()[classChosen];
        return new Player(name, 100, chosenClass, frame);
    } 

    private static String getClassDescription(EntityClass playerClass) {
        switch (playerClass) {
            case Warrior:
                return "Warrior: A strong melee fighter with high HP and balanced attack.";
            case Mage:
                return "Mage: A powerful spellcaster with high damage but low defense.";
            case Tank:
                return "Tank: Extremely durable with high defense but low magic ability.";
            case Summoner:
                return "Summoner: A versatile class that commands creatures to fight for them.";
            default:
                return "Adventurer: A well-rounded beginner class.";
        }
    }

    public void levelUp() {
        level++;
        exp -= expToNextLevel;
        expToNextLevel = (int)(expToNextLevel * 1.5);
        
        int hpIncrease = 0, atkIncrease = 0, defIncrease = 0;
        int matkIncrease = 0, mdefIncrease = 0, mpIncrease = 0;
        
        switch (entityClass) {
            case Warrior:
                hpIncrease = 15;
                atkIncrease = 2;
                matkIncrease = 1;
                defIncrease = 3;
                mdefIncrease = 2;
                mpIncrease = 5;
                break;
            case Mage:
                hpIncrease = 8;
                atkIncrease = 1;
                matkIncrease = 5;
                defIncrease = 1;
                mdefIncrease = 3;
                mpIncrease = 15;
                break;
            case Tank:
                hpIncrease = 20;
                atkIncrease = 1;
                matkIncrease = 0;
                defIncrease = 5;
                mdefIncrease = 3;
                mpIncrease = 3;
                break;
            case Summoner:
                hpIncrease = 10;
                atkIncrease = 1;
                matkIncrease = 3;
                defIncrease = 2;
                mdefIncrease = 3;
                mpIncrease = 10;
                break;
        }
        
        maxHp += hpIncrease;
        hp = maxHp;
        atk += atkIncrease;
        matk += matkIncrease;
        def += defIncrease;
        mdef += mdefIncrease;
        maxMp += mpIncrease;
        mp = maxMp;
        
        cureAll();
        removeBuffs();
        
        App.displayLevelUp(name, level, hpIncrease, atkIncrease, defIncrease);
    }

    public void move(int dx, int dy) {
        this.xPos += dx;
        this.yPos += dy;
        changeZone();
        
        if (frame != null && zone != null && zone.zoneType == ZoneType.Village) {
            frame.updateGameState(MainFrame.GameState.Village);
        }
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

    public void addExp(int amount) {
        this.exp += amount;
        App.displayExpGained(amount);
        
        if (this.exp >= this.expToNextLevel) {
            levelUp();
        }
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
            Item selected = inventory.get(choice - 1);
            
            if (selected.hpRestore > 0 || selected.mpRestore > 0 || selected.isRevive ||  
                selected.grantsProtect || selected.grantsShell || selected.atkBoost > 0 || 
                selected.matkBoost > 0) {
                selected.use(this);
                inventory.remove(selected);
            } else if (selected.inflictsPoison || selected.inflictsSilence || selected.inflictsParalyze) {
                // Handle enemy-targeted items
            } else if (selected.curesPoison || selected.curesSilence || selected.curesParalyze || selected.isRemedy) {
                selected.cure(this);
                inventory.remove(selected);
            }
        }
    }

    public void attackCommand(){
        currentEnemy.takeDamage(this.calculatePhysicalDamage(currentEnemy.def));
        PlayMusic(attackSFX, SFXClip);
    }

    public void takeDamage(int damage){
        this.hp -= damage;
    }

    public void useItem(Item item){
        item.use(this);
    }

    public static void PlayMusic(File musicPath, Clip clip){
        try {

            if(musicPath.exists()){
                if(clip.isOpen()){
                    clip.setFramePosition(0);
                    clip.start();
                    return;
                }
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                clip.open(audioInput);
                clip.start();
            }else{
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void StopMusic(Clip clip){
        try {
            
            if(clip != null){
                if(clip.isOpen()){
                    clip.stop();
                }
            }else{
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}