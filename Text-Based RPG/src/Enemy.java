import java.util.*;

public class Enemy extends Entity {
    List<Item> itemDrop;
    private static Random rand = new Random();

    // Boss enemies
private static final List<Enemy> BOSSES = List.of(
    new Enemy(List.of(Item.HealingPotion.maxPotion()), "Dragon", 500, 100, 50, 30, 20, EntityClass.Warrior),
    new Enemy(List.of(Item.BuffPotion.mindJuice()), "Dark Mage", 300, 40, 30, 150, 50, EntityClass.Mage),
    new Enemy(List.of(Item.CureItem.remedy()), "Demon King", 800, 120, 80, 60, 40, EntityClass.Warrior),
    new Enemy(List.of(Item.HealingPotion.maxPotion()), "Sung Jin Who?", 700, 90, 70, 100, 60, EntityClass.Summoner)
);

// Regular enemies
private static final List<Enemy> REGULAR_ENEMIES = List.of(
    new Enemy(new ArrayList<>(), "Goblin", 100, 30, 10, 5, 5, EntityClass.Warrior),
    new Enemy(new ArrayList<>(), "Wolf", 80, 40, 5, 0, 0, EntityClass.Warrior),
    new Enemy(new ArrayList<>(), "Slime", 50, 20, 2, 10, 10, EntityClass.Warrior)
);

    public Enemy(List<Item> itemDrop, String name, int hp, int atk, int def, int matk, int mdef, EntityClass entityClass) {
        super(name, hp, atk, def, 0, matk, mdef, entityClass);
        this.itemDrop = itemDrop;
    }

    public Enemy(Enemy enemy){
        super(enemy.name, enemy.hp, enemy.atk, enemy.def, 0, enemy.matk, enemy.mdef, enemy.entityClass);
        
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

    public void attackCommand(Player entity){
        entity.takeDamage(atk);
    }
}