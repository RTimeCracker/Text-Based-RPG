import java.util.*;

public class Enemy extends Entity {
    List<Item> itemDrop;
    private static Random rand = new Random();

    // Boss enemies
    private static final List<Enemy> BOSSES = List.of(
        new Enemy(List.of(HealingPotion.maxPotion()), "Dragon", 500, 100, 50, EntityClass.Warrior),
        new Enemy(List.of(BuffPotion.MindJuice()), "Dark Mage", 300, 150, 30, EntityClass.Mage),
        new Enemy(List.of(AilmentPotion.Remedy()), "Demon King", 800, 120, 80, EntityClass.Warrior)
    );
    
    // Regular enemies
    private static final List<Enemy> REGULAR_ENEMIES = List.of(
        new Enemy(new ArrayList<>(), "Goblin", 100, 30, 10, EntityClass.Warrior),
        new Enemy(new ArrayList<>(), "Wolf", 80, 40, 5, EntityClass.Warrior),
        new Enemy(new ArrayList<>(), "Slime", 50, 20, 2, EntityClass.Warrior)
    );

    public Enemy(List<Item> itemDrop, String name, int hp, int atk, int def, EntityClass entityClass) {
        super(name, hp, atk, def, entityClass);
        this.itemDrop = itemDrop;
    }

    public static Enemy generateBoss() {
        return BOSSES.get(rand.nextInt(BOSSES.size()));
    }

    public static Enemy generateRandom() {
        return REGULAR_ENEMIES.get(rand.nextInt(REGULAR_ENEMIES.size()));
    }

    public void handleCombat(Player player, Scanner sc) {
        System.out.println();
        App.displayCombatStart();
        
        while (player.hp > 0 && this.hp > 0) {
            App.displayCombatStatus(player.hp, this.hp, this.name);
            App.displayCombatOptions();
            
            int choice = sc.nextInt();
            if (choice == 1) {
                int damage = Math.max(1, player.atk - this.def / 2);
                this.hp -= damage;
                App.displayAttackResult(player.name, this.name, damage);
            } else if (choice == 2) {
                player.showInventory(sc);
            }
            
            if (this.hp <= 0) break;
            
            int damage = Math.max(1, this.atk - player.def / 2);
            player.hp -= damage;
            App.displayAttackResult(this.name, player.name, damage);
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
}