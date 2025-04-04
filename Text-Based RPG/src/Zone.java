import java.util.*;

enum ZoneType {
    Encounter, Village, Dungeon, Event
}

public class Zone {
    ZoneType zoneType;
    int zoneNumber;
    static List<Zone> zones = new ArrayList<>();
    private int xPos, yPos;

    private static final List<Item> SHOP_ITEMS = List.of(
        HealingPotion.normalPotion(),
        HealingPotion.superPotion(),
        BuffPotion.PowerJuice(),
        AilmentPotion.Antidote()
    );

    public Zone(ZoneType zoneType, int xPos, int yPos) {
        this.zoneType = zoneType;
        this.zoneNumber = zones.size();
        this.xPos = xPos;
        this.yPos = yPos;
        zones.add(this);
    }

    public static Zone getZoneFromPosition(int xPos, int yPos) {
        for (Zone z : zones) {
            if (z.xPos == xPos && z.yPos == yPos) {
                return z;
            }
        }
        return null;
    }

    public static int[] getZonePosition(Zone zone) {
        return new int[]{zone.xPos, zone.yPos};
    }

    public void handleZoneEvent(Player player, Scanner sc) {
    App.displayZoneType(zoneType);  //passing ZoneType enum directly
    
    switch (zoneType) {
        case Village:
            handleVillage(player, sc);
            break;
        case Dungeon:
            Enemy boss = Enemy.generateBoss();
            App.displayBossAppearance(boss.name);
            boss.handleCombat(player, sc);
            break;
        case Encounter:
            Enemy enemy = Enemy.generateRandom();
            App.displayEnemyAppearance(enemy.name);
            enemy.handleCombat(player, sc);
            break;
    }
}

    private void handleVillage(Player player, Scanner sc) {
        App.displayVillageOptions();
        int choice = sc.nextInt();
        
        while (choice < 1 || choice > 3) {
            App.displayError();
            App.displayVillageOptions();
            choice = sc.nextInt();
        }
        
        if (choice == 1) {
            buyItems(player, sc);
        } else if (choice == 2) {
            sellItems(player, sc);
        }
    }

    private void buyItems(Player player, Scanner sc) {
        App.displayShopItems(SHOP_ITEMS);
        
        int choice = sc.nextInt();
        while (choice < 0 || choice > SHOP_ITEMS.size()) {
            App.displayError();
            App.displayShopItems(SHOP_ITEMS);
            choice = sc.nextInt();
        }
        
        if (choice > 0) {
            Item selected = SHOP_ITEMS.get(choice - 1);
            if (player.money >= 100) {
                player.money -= 100;
                player.addItemToInventory(selected);
                App.displayItemPurchased(selected.name);
            } else {
                App.displayNotEnoughMoney();
            }
        }
    }

    private void sellItems(Player player, Scanner sc) {
        if (player.inventory.isEmpty()) {
            App.displayEmptyInventory();
            return;
        }
        
        App.displayPlayerInventoryForSelling(player.inventory);
        App.promptSellChoice();
        
        int choice = sc.nextInt();
        while (choice < 0 || choice > player.inventory.size()) {
            App.displayError();
            App.displayPlayerInventoryForSelling(player.inventory);
            choice = sc.nextInt();
        }
        
        if (choice > 0) {
            Item sold = player.inventory.remove(choice - 1);
            player.money += 50;
            App.displayItemSold(sold.name);
        }
    }
}