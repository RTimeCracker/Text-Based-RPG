
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

public class Item {
    String name;
    String description;
    int hpRestore;
    int mpRestore;
    boolean isRevive;
    int reviveAmount;
    boolean grantsProtect;
    boolean grantsShell;

    int atkBoost;
    int matkBoost;

    boolean inflictsPoison;
    boolean inflictsSilence;
    boolean inflictsParalyze;
    boolean curesPoison;
    boolean curesSilence;
    boolean curesParalyze;
    boolean isRemedy;

    Image image;
    String imagePath;


    public Item(Database database, int id) {
       

        try {
            ResultSet rs = database.fetchData("Select * from item where ItemID = " + id);
            this.name = rs.getString(2);
            this.description = rs.getString(3);
            this.hpRestore = rs.getInt(4);
            this.mpRestore = rs.getInt(5);
            this.isRevive = rs.getBoolean(6);
            this.reviveAmount = rs.getInt(7);
            this.grantsProtect = rs.getBoolean(8);
            this.grantsShell = rs.getBoolean(9);
            this.atkBoost = rs.getInt(10);
            this.matkBoost = rs.getInt(11);
            this.inflictsPoison = rs.getBoolean(12);
            this.inflictsSilence = rs.getBoolean(13);
            this.inflictsParalyze = rs.getBoolean(14);
            this.curesPoison = rs.getBoolean(15);
            this.curesSilence = rs.getBoolean(16);
            this.curesParalyze = rs.getBoolean(17);
            this.isRemedy = rs.getBoolean(18);
            this.imagePath = rs.getString(19);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        loadItemImage();
    }
    
    public Item(String name, String description, int hpRestore, int mpRestore, 
        boolean isRevive, int reviveAmount, boolean grantsProtect, 
        boolean grantsShell, int atkBoost, int matkBoost, 
        boolean inflictsPoison, boolean inflictsSilence, 
        boolean inflictsParalyze, boolean curesPoison, 
        boolean curesSilence, boolean curesParalyze, boolean isRemedy, String imagePath) {
            this.name = name;
            this.description = description;
            this.hpRestore = hpRestore;
            this.mpRestore = mpRestore;
            this.isRevive = isRevive;
            this.reviveAmount = reviveAmount;
            this.grantsProtect = grantsProtect;
            this.grantsShell = grantsShell;
            this.atkBoost = atkBoost;
            this.matkBoost = matkBoost;
            this.inflictsPoison = inflictsPoison;
            this.inflictsSilence = inflictsSilence;
            this.inflictsParalyze = inflictsParalyze;
            this.curesPoison = curesPoison;
            this.curesSilence = curesSilence;
            this.curesParalyze = curesParalyze;
            this.isRemedy = isRemedy;
            this.imagePath = imagePath;
            loadItemImage();
    }

    private void loadItemImage() {
        try {
            // Try multiple loading strategies
            BufferedImage img = ImageIO.read(new File(imagePath));
        
            if (img == null) {
                System.err.println("All image loading attempts failed for: " + imagePath);
                img = createPlaceholderImage();
            }
            
            this.image = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Critical error loading image: " + e.getMessage());
            this.image = createPlaceholderImage();
    }
    }

    private BufferedImage createPlaceholderImage() {
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Image Missing", 50, 100);
        g2d.dispose();
        return img;
    }

    public void use(Player player) {
        if (hpRestore > 0) {
            player.hp = Math.min(player.hp + hpRestore, player.getMaxHp());
        }
        if (mpRestore > 0) {
            player.mp = Math.min(player.mp + mpRestore, player.getMaxMp());
        }
        if (isRevive && player.hp <= 0) {
            player.hp = reviveAmount;
        }
        if (grantsProtect) {
            player.hasProtect = true;
        }
        if (grantsShell) {
            player.hasShell = true;
        }
        if (atkBoost > 0) {
            player.atk += atkBoost;
        }
        if (matkBoost > 0) {
            player.matk += matkBoost;
        }
        player.inventory.remove(this);
    }

    public void useOnEnemy(Enemy enemy) {
        if (inflictsPoison) {
            enemy.isPoisoned = true;
            App.displayItemUsed(name, "inflicted Poison on enemy", 0);
        }
        if (inflictsSilence) {
            enemy.isSilenced = true;
            App.displayItemUsed(name, "inflicted Silence on enemy", 0);
        }
        if (inflictsParalyze) {
            enemy.isParalyzed = true;
            App.displayItemUsed(name, "inflicted Paralyze on enemy", 0);
        }
    }

    public void cure(Player player) {
        if (curesPoison) {
            player.isPoisoned = false;
            App.displayItemUsed(name, "cured Poison", 0);
        }
        if (curesSilence) {
            player.isSilenced = false;
            App.displayItemUsed(name, "cured Silence", 0);
        }
        if (curesParalyze) {
            player.isParalyzed = false;
            App.displayItemUsed(name, "cured Paralyze", 0);
        }
        if (isRemedy) {
            player.isPoisoned = false;
            player.isSilenced = false;
            player.isParalyzed = false;
            App.displayItemUsed(name, "cured all status effects", 0);
        }
    }

    // Healing Potions
    public static class HealingPotion {
        public static Item potion(Database database) {
            return new Item(database, 1);
        }

        public static Item superPotion(Database database) {
            return new Item(database, 2);
        }

        public static Item maxPotion(Database database) {
            return new Item(database, 3);
        }

        public static Item phoenixDown(Database database) {
            return new Item(database, 4);
        }
    }

    // Mana Potions
    public static class ManaPotion {
        public static Item lowlyFlask(Database database) {
            return new Item(database, 5);
        }

        public static Item greatFlask(Database database) {
            return new Item(database, 6);
        }

        public static Item absoluteFlask(Database database) {
            return new Item(database, 7);
        }
    }

    // Hybrid Potions
    public static class HybridPotion {
        public static Item lesserElixir(Database database) {
            return new Item(database, 8);
        }

        public static Item midElixir(Database database) {
            return new Item(database, 9);
        }

        public static Item highElixir(Database database) {
            return new Item(database, 10);
        }
    }

    // Buff Potions
    public static class BuffPotion {
        public static Item protectPotion(Database database) {
            return new Item(database, 11);
        }

        public static Item shellPotion(Database database) {
            return new Item(database, 12);
        }

        public static Item powerJuice(Database database) {
            return new Item(database, 13);
        }

        public static Item mindJuice(Database database) {
            return new Item(database, 14);
        }
    }

    // Debuff Potions
    public static class DebuffPotion {
        public static Item poison(Database database) {
            return new Item(database, 15);
        }

        public static Item silenceDust(Database database) {
            return new Item(database, 16);
        }

        public static Item paralyzeDust(Database database) {
            return new Item(database, 17);
        }
    }

    // Cure Items
    public static class CureItem {
        public static Item antidote(Database database) {
            return new Item(database, 18);
        }

        public static Item echoGrass(Database database) {
            return new Item(database, 19);
        }

        public static Item paralyzeHeal(Database database) {
            return new Item(database, 20);
        }

        public static Item remedy(Database database) {
            return new Item(database, 21);
        }
    }
}