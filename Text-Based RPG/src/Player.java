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
    private List<Skill> skills;
    private List<Skill> unlockedSkills;
    public int money;
    public boolean hasWolfCompanion;
    public boolean hasPhoenixBuff;
    public boolean hasLastStand;
    public List<Item> inventory = new ArrayList<>(Arrays.asList(
        Item.HealingPotion.maxPotion(), Item.BuffPotion.mindJuice(), Item.HealingPotion.phoenixDown(), Item.DebuffPotion.poison()
    ));
    Zone zone;
    public int xPos = 0, yPos = 0;
    private static Random random = new Random();
    private MainFrame frame;
    private File attackSFX = new File("Text-Based RPG\\SFX\\Attack.wav");
    private Clip SFXClip;
    public Enemy currentEnemy;

    public ArrayList<String> GoodDialogue = new ArrayList<>(Arrays.asList(
        "Hey, I just wanted to tell you that your clothing looks really good on you and fits your whole vibe.",
        "Hii, your hair looks really good and smells like a field surrounded by beautiful and vibrant flowers.",
        "Your voice sounds so eloquent, every word from your mouth is a sweet rhythm that blesses my ears and fills my stomach with butterflies.",
        "Hi, your eyes look really good and equally as beautiful as the clear blue skies."
    ));
    public ArrayList<String> BadDialogue = new ArrayList<>(Arrays.asList(
        "Oh hey, I just wanted to tell you that your fashion sense doesn't make sense and your clothes are as ugly as you.",
        "Err, how much did it cost to get a haircut as ugly as yours? I want to avoid your barber.",
        "Can you please stop looking at me with those weird eyes? It's uncomfortable that a swine like you is staring at me.",
        "Please stop talking, your breath stinks and feels like two clown horns honking in both my ears."
    ));

    public Player(String name, int money, EntityClass playerClass, MainFrame frame) {
        super(name, 0, 0, 0, 0, 0, 0, playerClass);
        this.frame = frame;
        this.exp = 0;
        this.expToNextLevel = 100;
        this.money = money;
        this.skills = new ArrayList<>();
        this.unlockedSkills = new ArrayList<>();
        this.zone = new Zone(ZoneType.Village, 0, 0);
        try {
            SFXClip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
        }
        initializeClassStats(playerClass);
        initializeClassSkills();
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }
    
    public MainFrame getFrame() {
        return frame;
    }

    public static Player createPlayer(String name, int classChosen, MainFrame frame) {
        EntityClass chosenClass = EntityClass.values()[classChosen];
        return new Player(name, 100, chosenClass, frame);
    }

    private void initializeClassStats(EntityClass playerClass) {
        switch (playerClass) {
            case Warrior:
                this.maxHp = 120; this.atk = 15; this.def = 10;
                this.matk = 5; this.mdef = 5; this.maxMp = 30;
                break;
            case Mage:
                this.maxHp = 80; this.atk = 5; this.def = 5;
                this.matk = 20; this.mdef = 10; this.maxMp = 100;
                break;
            case Tank:
                this.maxHp = 150; this.atk = 1000; this.def = 20;
                this.matk = 0; this.mdef = 15; this.maxMp = 20;
                break;
            case Summoner:
                this.maxHp = 90; this.atk = 12; this.def = 8;
                this.matk = 15; this.mdef = 10; this.maxMp = 80;
                break;
        }
        this.hp = maxHp;
        this.mp = maxMp;
    }

    private void initializeClassSkills() {
        this.skills = Skill.initializeClassSkills(this.entityClass);
        updateUnlockedSkills();
    }

    public void addExp(int amount) {
        this.exp += amount;
        App.displayExpGained(amount);
        while (this.exp >= this.expToNextLevel) {
            levelUp();
        }
        updateUnlockedSkills();
    }

    private void updateUnlockedSkills() {
        unlockedSkills.clear();
        for (Skill skill : skills) {
            if (level >= skill.getUnlockLevel()) {
                unlockedSkills.add(skill);
            }
        }
    }

    private int mainBossesDefeated = 0;

    public void defeatMainBoss() {
    mainBossesDefeated++;
    if (mainBossesDefeated >= 3 && frame != null) {
        frame.updateGameState(MainFrame.GameState.Victory);}
    }

    public void levelUp() {
        level++;
        exp -= expToNextLevel;
        expToNextLevel = (int)(expToNextLevel * 1.5);
        int hpIncrease = 0, atkIncrease = 0, defIncrease = 0, matkIncrease = 0, mdefIncrease = 0, mpIncrease = 0;

        switch (entityClass) {
            case Warrior:
                hpIncrease = 15; atkIncrease = 2; matkIncrease = 1; defIncrease = 3; mdefIncrease = 2; mpIncrease = 5;
                break;
            case Mage:
                hpIncrease = 8; atkIncrease = 1; matkIncrease = 5; defIncrease = 1; mdefIncrease = 3; mpIncrease = 15;
                break;
            case Tank:
                hpIncrease = 20; atkIncrease = 1; defIncrease = 5; mdefIncrease = 3; mpIncrease = 3;
                break;
            case Summoner:
                hpIncrease = 10; atkIncrease = 1; matkIncrease = 3; defIncrease = 2; mdefIncrease = 3; mpIncrease = 10;
                break;
        }

        maxHp += hpIncrease; hp = maxHp;
        atk += atkIncrease; def += defIncrease;
        matk += matkIncrease; mdef += mdefIncrease;
        maxMp += mpIncrease; mp = maxMp;

        cureAll(); removeBuffs();
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

    public void attackCommand() {
        currentEnemy.takeDamage(this.calculatePhysicalDamage(currentEnemy.def));
        PlayMusic(attackSFX, SFXClip);
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public void useItem(Item item) {
        item.use(this);
    }


    public static void PlayMusic(File musicPath, Clip clip) {
        try {
            if (musicPath.exists()) {
                if (clip.isOpen()) {
                    clip.setFramePosition(0);
                    clip.start();
                    return;
                }
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void StopMusic(Clip clip) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.stop();
            } else {
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getExp() { return exp; }
    public int getExpToNextLevel() { return expToNextLevel; }
    public List<Skill> getUnlockedSkills() { return unlockedSkills; }
}