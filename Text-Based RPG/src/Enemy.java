import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Enemy extends Entity {
    List<Item> itemDrop;
    private static Random rand = new Random();
    public String imagePath;
    private String folderPath;
    private Image enemyImage;

    private File defaultMusicFile;
    private File musicFile;
    private Clip BGMclip;

    private File currentlyPlayingSFX;
    private File attackSFX = new File("Text-Based RPG\\SFX\\Attack.wav");
    private File dialogueSFX;
    private Clip SFXClip;

    private Character gender;
    private int hearts = 0;


    public boolean hasTaunted;

    public Enemy(List<Item> itemDrop, String name, int hp, int atk, int def, int matk, int mdef, EntityClass entityClass, String folderPath) {
        super(name, hp, atk, def, 0, matk, mdef, entityClass);
        String imagePath = folderPath + "\\Image\\" + name + ".png"; 
        this.level = rand.nextInt(5) + 1; // Random level 1-5
        this.maxHp = hp * level;
        this.hp = this.maxHp;
        this.atk = atk * level;
        this.itemDrop = itemDrop;
        this.imagePath = imagePath;
        this.folderPath = folderPath;
        this.defaultMusicFile = new File("Text-Based RPG\\Music\\In combat music.WAV");
        try {
            System.out.println("GettingCLip");
            this.BGMclip = AudioSystem.getClip();
            this.SFXClip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
        }
    }   
    
    public Enemy(List<Item> itemDrop, String name, int hp, int atk, int def, int matk, int mdef, EntityClass entityClass, Character gender, String folderPath) {
        super(name, hp, atk, def, 0, matk, mdef, entityClass);
        String imagePath = folderPath + "\\Image\\" + name + ".png";
        File musicFile  = new File(folderPath + "\\Sound\\" + name + " Theme.wav");

        this.itemDrop = itemDrop;
        this.imagePath = imagePath;
        this.folderPath = folderPath;
        if (musicFile.exists()){
            this.musicFile = musicFile;                
        }else{
            this.defaultMusicFile = new File("Text-Based RPG\\Music\\In combat music.WAV");
        }
                                                   
        this.gender = gender;
        try {
            this.BGMclip = AudioSystem.getClip();
            this.SFXClip = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
        }
    } 

    public Enemy(Enemy enemy){
        super(enemy.name, enemy.hp, enemy.atk, enemy.def, 0, enemy.matk, enemy.mdef, enemy.entityClass);
        this.imagePath = enemy.imagePath;
        this.SFXClip = enemy.SFXClip;
        this.gender = enemy.gender;
        this.folderPath = enemy.folderPath;
        if(enemy.musicFile != null){
            this.musicFile = enemy.musicFile;
            this.BGMclip = enemy.BGMclip;
        }else{
            this.musicFile = enemy.defaultMusicFile;
            this.BGMclip = enemy.BGMclip;
        }
        
        
        PlayMusic(musicFile, BGMclip);
        loadEnemyImage();
        
    }

    private void loadEnemyImage() {
        System.out.println(this.imagePath);
        System.out.println(new File(imagePath).canRead());
        try {
            // Try multiple loading strategies
            BufferedImage img = ImageIO.read(new File(imagePath));
        
            if (img == null) {
                System.err.println("All image loading attempts failed for: " + imagePath);
                this.enemyImage = createPlaceholderImage();
                return;
            }
            
            this.enemyImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Critical error loading image: " + e.getMessage());
            this.enemyImage = createPlaceholderImage();
        }
    }

    private BufferedImage createPlaceholderImage() {
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 200, 200);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Image Missing", 50, 100);
        g2d.dispose();
        return img;
    }


    public Image getEnemyImage() {
        return this.enemyImage;
    }

    public static Enemy generateBoss(Database database) {
        try {
            int randomNumber = rand.nextInt(database.fetchData("select count(*) from bossenemy").getInt(1)) + 1;
            ResultSet enemyData = database.fetchData("select * from bossenemy where EnemyID = " + 5);

            Enemy enemy = new Enemy(List.of(Item.HealingPotion.maxPotion()), enemyData.getString(2),enemyData.getInt(3), enemyData.getInt(4),enemyData.getInt(5), enemyData.getInt(6),enemyData.getInt(7),EntityClass.valueOf(enemyData.getString(8)), enemyData.getString(9).charAt(0),enemyData.getString(10));
            return new Enemy(enemy);
        } catch (SQLException ex) {

        }

        return  null;
    }

    public static Enemy generateRandom(Database database) {
        
        try {
            int randomNumber = rand.nextInt(database.fetchData("select count(*) from regularenemy").getInt(1)) + 1;
            System.out.println(randomNumber);
            ResultSet enemyData = database.fetchData("select * from regularenemy where regularenemyID = " + randomNumber);
            Enemy enemy = new Enemy(List.of(Item.HealingPotion.maxPotion()), enemyData.getString(2),enemyData.getInt(3), enemyData.getInt(4),enemyData.getInt(5), enemyData.getInt(6),enemyData.getInt(7),EntityClass.valueOf(enemyData.getString(8)),enemyData.getString(9));
            
            return new Enemy(enemy);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return  null;
    }

    public void handleCombat(Player player, Scanner sc) {
        System.out.println();
        App.displayCombatStart();
        
        while (player.hp > 0 && this.hp > 0) {
            App.displayCombatStatus(player.hp, this.hp, this.name);
            App.displayCombatOptions();

            if (!player.isParalyzed) {
                int choice = sc.nextInt();
                if (choice == 1) {
                    int damage = player.calculatePhysicalDamage(this.def);
                    this.hp -= damage;
                    App.displayAttackResult(player.name, this.name, damage);
                } else if (choice == 2) {
                    player.showInventory(sc);
                }
            } else {
                App.displayStatusEffect(player.name + " is paralyzed and can't move!");
            }

            if (this.hp > 0 && !this.isParalyzed) {
                if (this.isPoisoned) {
                    int poisonDmg = Math.max(1, this.hp / 10);
                    this.hp -= poisonDmg;
                    App.displayStatusEffect(name + " takes " + poisonDmg + " poison damage!");
                }

                int damage = this.calculatePhysicalDamage(player.def);
                player.hp -= damage;
                App.displayAttackResult(this.name, player.name, damage);
            }
        }
        
        if (player.hp > 0) {
            App.displayVictory(this.name);
            if (!this.itemDrop.isEmpty()) {
                Item drop = this.itemDrop.get(0);
                player.addItemToInventory(drop);
                App.displayItemObtained(drop.name);
            }
            player.addExp(50);
        } else {
            App.displayGameOver();
            System.exit(0);
        }
    }

    public void takeDamage(int damage){
        this.hp -= damage;
    }

    public void onDeath(Player player){
        int expGain = 50 * this.level; // Scale EXP with enemy level
        player.addExp(expGain);
        StopMusic(BGMclip);
    }

    public void attackCommand(Player entity){
        entity.takeDamage(this.calculatePhysicalDamage(entity.def));
        PlaySFX(attackSFX, SFXClip);
    }

    
    public int getHearts(){
        return hearts;
    }

    public void increaseHearts(int amount){
        if(amount > 5){
            hearts = 5;
        }else{
            hearts += amount;
        }
    }
    
    public void decreaseHearts(int amount){
        if(hearts > 0 && amount <= 5){
            hearts -= amount;
        }else{
            hearts = 0;
        }
    }

    public String respondToTalk(boolean isGood, Database database){
        this.dialogueSFX = getDialogueSFX(isGood);
        if(this.gender.equals('F')){
            if(isGood){ 
                try {        
                    int randomNumber =  database.fetchData("select DialogueID from FemaleBossEnemyDialogues where IsGood = true ORDER BY RAND() LIMIT 1").getInt(1);
                    
                    ResultSet dialogue = database.fetchData("select *from FemaleBossEnemyDialogues where DialogueID =" + randomNumber);
    
                    return dialogue.getString(2);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                try {
                    int randomNumber =  database.fetchData("select DialogueID from FemaleBossEnemyDialogues where IsGood = false ORDER BY RAND() LIMIT 1").getInt(1);
                    
                    ResultSet dialogue = database.fetchData("select *from FemaleBossEnemyDialogues where DialogueID =" + randomNumber);
    
                    return dialogue.getString(2);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }else{
            if(isGood){ 
                try {
                    int randomNumber =  database.fetchData("select DialogueID from MaleBossEnemyDialogues where IsGood = true ORDER BY RAND() LIMIT 1").getInt(1);
                    
                    ResultSet dialogue = database.fetchData("select *from MaleBossEnemyDialogues where DialogueID =" + randomNumber);
    
                    return dialogue.getString(2);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                try {
                    int randomNumber =  database.fetchData("select DialogueID from MaleBossEnemyDialogues where IsGood = false ORDER BY RAND() LIMIT 1").getInt(1);
                    
                    ResultSet dialogue = database.fetchData("select *from MaleBossEnemyDialogues where DialogueID =" + randomNumber);
                    return dialogue.getString(2);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
       
        return null;
    }

    private File getDialogueSFX(boolean isGood){
        int rnd;
        if(isGood){
            File[] dialoguesSFX = new File(this.folderPath + "\\Sound\\Dialogue SFX\\Good").listFiles();
            System.out.println(this.folderPath);

            if(dialoguesSFX != null){
                rnd = new Random().nextInt(dialoguesSFX.length);

                return dialoguesSFX[rnd];
            }
            
            return null;
        }else{
            File[] dialoguesSFX = new File(this.folderPath + "\\Sound\\Dialogue SFX\\Bad").listFiles();
            System.out.println(this.folderPath);
            if(dialoguesSFX != null){
                rnd = new Random().nextInt(dialoguesSFX.length);

                return dialoguesSFX[rnd];
            }
            
            return null;
        }
    }

    public void playDialogueSFX(){
        PlaySFX(this.dialogueSFX, SFXClip); 
    }

    public void stopDialogueSFX(){
        StopSFX(SFXClip); 
    }


    public void PlaySFX(File musicPath, Clip clip){
        try {

            if(musicPath.exists()){
                if(clip.isOpen() && musicPath.equals(currentlyPlayingSFX)){
                    clip.setFramePosition(0);
                    clip.start();
                    return;
                }

                clip.close();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);

                clip.open(audioInput);
                clip.start();
                this.currentlyPlayingSFX = musicPath;
            }else{
                System.out.println("Can't find file.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void StopSFX(Clip clip){
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
                clip.loop(Clip.LOOP_CONTINUOUSLY);
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