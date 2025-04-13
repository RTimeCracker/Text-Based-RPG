import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.imageio.ImageIO;

public class Enemy extends Entity {
    List<Item> itemDrop;
    private static Random rand = new Random();
    public String imagePath;
    private Image enemyImage;

    // Boss enemies
private static final List<Enemy> BOSSES = List.of(
    new Enemy(List.of(Item.HealingPotion.maxPotion()), "Dragon Lanz", 500, 100, 50, 30, 20, EntityClass.Tank, "Text-Based RPG\\Images\\Enemy\\LanzEnemy.png"),
    new Enemy(List.of(Item.BuffPotion.mindJuice()), "Dark Mage Clark", 300, 40, 30, 150, 50, EntityClass.Mage, "Text-Based RPG\\Images\\Enemy\\ClarkEnemy.png"),
    new Enemy(List.of(Item.CureItem.remedy()), "Demon King Orit", 800, 120, 80, 60, 40, EntityClass.Warrior, "Text-Based RPG\\Images\\Enemy\\OritEnemy.png"),
    new Enemy(List.of(Item.HealingPotion.maxPotion()), "Sung Jin Who?", 700, 90, 70, 100, 60, EntityClass.Summoner, "Text-Based RPG\\Images\\Enemy\\RyanEnemy.png"),
    new Enemy(List.of(Item.HealingPotion.maxPotion()), "Sion The Fell Omen", 600, 50, 50, 30, 30, EntityClass.Warrior, "Text-Based RPG\\Images\\Enemy\\SionEnemy.png"),
    new Enemy(List.of(Item.HybridPotion.highElixir()), "Heavenly Demon King Khervin", 1000, 100, 100, 100, 100, EntityClass.Mage, "Text-Based RPG\\Images\\Enemy\\KhervinEnemy.png")
);

// Regular enemies
private static final List<Enemy> REGULAR_ENEMIES = List.of(
    new Enemy(new ArrayList<>(), "Goblin", 100, 30, 10, 5, 5, EntityClass.Warrior, "Text-Based RPG/Images/Enemy/GoblinEnemy.gif"),
    new Enemy(new ArrayList<>(), "Wolf", 80, 40, 5, 0, 0, EntityClass.Warrior, "Text-Based RPG\\Images\\Enemy\\Spice-And-Wolf.png"),
    new Enemy(new ArrayList<>(), "Slime", 50, 20, 2, 10, 10, EntityClass.Warrior, "Text-Based RPG\\Images\\Enemy\\Slime.png")
);

    public Enemy(List<Item> itemDrop, String name, int hp, int atk, int def, int matk, int mdef, EntityClass entityClass, String imagePath) {
        super(name, hp, atk, def, 0, matk, mdef, entityClass);
        this.itemDrop = itemDrop;
        this.imagePath = imagePath;
    }

    public Enemy(Enemy enemy){
        super(enemy.name, enemy.hp, enemy.atk, enemy.def, 0, enemy.matk, enemy.mdef, enemy.entityClass);
        this.imagePath = enemy.imagePath;
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
                img = createPlaceholderImage();
            }
            
            this.enemyImage = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Critical error loading image: " + e.getMessage());
            this.enemyImage = createPlaceholderImage();
    }
}

private BufferedImage tryLoadImage(String path) {
    // Try as resource
    BufferedImage img = tryLoadResource(path);
    if (img != null) return img;
    
    // Try as file
    img = tryLoadFile(path);
    if (img != null) return img;
    
    // Try alternative path formats
    return tryAlternativePaths(path);
}

private BufferedImage tryLoadResource(String path) {
    try {
        // Remove leading slash if present for resource loading
        String resourcePath = path.startsWith("/") ? path.substring(1) : path;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (stream != null) {
            BufferedImage img = ImageIO.read(stream);
            if (img != null) {
                System.out.println("Successfully loaded as resource: " + resourcePath);
                return img;
            }
        }
    } catch (IOException e) {
        System.err.println("Resource loading failed: " + path);
    }
    return null;
}

private BufferedImage tryLoadFile(String path) {
    try {
        File file = new File(path);
        if (file.exists()) {
            BufferedImage img = ImageIO.read(file);
            if (img != null) {
                System.out.println("Successfully loaded as file: " + path);
                return img;
            }
        }
    } catch (IOException e) {
        System.err.println("File loading failed: " + path);
    }
    return null;
}

private BufferedImage tryAlternativePaths(String originalPath) {
    // Try common alternative path formats
    String[] alternatives = {
        originalPath,
        originalPath.replace("\\", "/"),
        originalPath.replace("Text-Based RPG/", ""),
        "images/" + originalPath.substring(originalPath.lastIndexOf("/") + 1),
        "src/main/resources/" + originalPath
    };
    
    for (String path : alternatives) {
        if (!path.equals(originalPath)) {
            BufferedImage img = tryLoadResource(path);
            if (img != null) return img;
            
            img = tryLoadFile(path);
            if (img != null) return img;
        }
    }
    return null;
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
        System.out.println(this.enemyImage);
        return this.enemyImage;
    }

    public static Enemy generateBoss() {
        return new Enemy(BOSSES.get(rand.nextInt(BOSSES.size())));
    }

    public static Enemy generateRandom() {
        return new Enemy(REGULAR_ENEMIES.get(rand.nextInt(REGULAR_ENEMIES.size())));
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

    public void Death(Player player){
        player.addExp(50);
    }

    public void attackCommand(Player entity){
        entity.takeDamage(this.calculatePhysicalDamage(entity.def));
    }
}