import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

enum ZoneType {
    Encounter, Village, Dungeon, Event
}

public class Zone {
    ZoneType zoneType;
    int zoneNumber;
    static List<Zone> zones = new ArrayList<>();
    public int xPos, yPos;
    private static Random random = new Random();
    private Set<Integer> usedChoices = new HashSet<>();
    private Enemy lastEncounteredEnemy = null;

    // Sample items for shops
    private static final List<Item> SHOP_ITEMS = List.of(
        Item.HealingPotion.potion(),
        Item.HealingPotion.superPotion(),
        Item.ManaPotion.lowlyFlask(),
        Item.BuffPotion.protectPotion(),
        Item.BuffPotion.shellPotion(),
        Item.CureItem.antidote(),
        Item.CureItem.remedy()
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
        App.displayZoneContents(this);
        
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
                handleEncounter(player, sc);
                break;
            case Event:
                handleEvent(player, sc);
                break;
        }
    }

    private void handleEncounter(Player player, Scanner sc) {
        // 70% chance to encounter new enemy if none encountered before
        // 30% chance to encounter new enemy if previous one was defeated
        if (lastEncounteredEnemy == null || 
           (player.hp > 0 && random.nextDouble() < 0.3)) {
            
            Enemy enemy = Enemy.generateRandom();
            lastEncounteredEnemy = enemy;
            App.displayEnemyAppearance(enemy.name);
            enemy.handleCombat(player, sc);
        } else {
            if (lastEncounteredEnemy != null && lastEncounteredEnemy.hp <= 0) {
                App.displayEventDescription("The remains of " + lastEncounteredEnemy.name + 
                                          " litter the ground.");
                App.displayEventOutcome("No living enemies remain in this area.");
            } else {
                App.displayEventDescription("The area appears quiet and peaceful.");
                App.displayEventOutcome("You find nothing of interest here.");
            }
        }
    }

    private void handleEvent(Player player, Scanner sc) {
        usedChoices.clear();
        int eventType = random.nextInt(3);
        
        switch (eventType) {
            case 0:
                mysteriousShackEvent(player, sc);
                break;
            case 1:
                woundedTravelerEvent(player, sc);
                break;
            case 2:
                ancientTreasureEvent(player, sc);
                break;
        }
    }

    private void mysteriousShackEvent(Player player, Scanner sc) {
        AtomicBoolean staying = new AtomicBoolean(true);
        App.displayEventDescription(
            "Mysterious Shack\n\n" +
            "A creaky wooden structure stands before you.\n" +
            "Faint candlelight flickers through the broken windows."
        );
        
        while (staying.get()) {
            List<String> options = new ArrayList<>();
            List<Runnable> actions = new ArrayList<>();
            
            if (!usedChoices.contains(1)) {
                options.add("[1] Enter the shack");
                actions.add(() -> {
                    if (random.nextBoolean()) {
                        Enemy enemy = Enemy.generateRandom();
                        App.displayEventOutcome("A " + enemy.name + " ambushes you!");
                        enemy.handleCombat(player, sc);
                    } else {
                        Item reward = List.of(
                            Item.HealingPotion.potion(),
                            Item.ManaPotion.lowlyFlask(),
                            Item.CureItem.antidote()
                        ).get(random.nextInt(3));
                        player.addItemToInventory(reward);
                        App.displayEventOutcome("You found: " + reward.name);
                    }
                    usedChoices.add(1);
                });
            }
            
            if (!usedChoices.contains(2)) {
                options.add("[2] Search the perimeter");
                actions.add(() -> {
                    Item reward = List.of(
                            Item.HealingPotion.potion(),
                            Item.ManaPotion.lowlyFlask(),
                            Item.CureItem.antidote()
                        ).get(random.nextInt(3));
                        player.addItemToInventory(reward);
                    App.displayEventOutcome("Discovered hidden " + reward.name + "!");
                    usedChoices.add(2);
                });
            }
            
            options.add("[3] Leave this place");
            actions.add(() -> {
                App.displayEventOutcome("You depart from the eerie shack.");
                staying.set(false);
            });
            
            App.displayEventChoices(options.toArray(new String[0]));
            int choice = getValidChoice(sc, options.size());
            actions.get(choice - 1).run();
        }
    }

    private void woundedTravelerEvent(Player player, Scanner sc) {
        AtomicBoolean staying = new AtomicBoolean(true);
        App.displayEventDescription(
            "Wounded Traveler\n\n" +
            "A bloodied adventurer leans against a tree,\n" +
            "clutching their side and gasping for help."
        );
        
        while (staying.get()) {
            List<String> options = new ArrayList<>();
            List<Runnable> actions = new ArrayList<>();
            
            if (!usedChoices.contains(1)) {
                options.add("[1] Offer medical aid");
                actions.add(() -> {
                    player.hp -= 15;
                    Item reward = List.of(
                            Item.HealingPotion.potion(),
                            Item.ManaPotion.lowlyFlask(),
                            Item.CureItem.antidote()
                        ).get(random.nextInt(3));
                        player.addItemToInventory(reward);
                    App.displayEventOutcome(
                        "You sacrifice 15 HP to help them.\n" +
                        "They reward you with " + reward.name + "."
                    );
                    usedChoices.add(1);
                });
            }
            
            if (!usedChoices.contains(2)) {
                options.add("[2] Rob the traveler");
                actions.add(() -> {
                    player.money += 75;
                    App.displayEventOutcome(
                        "You steal 75 gold!\n" +
                        "Suddenly, guards appear!"
                    );
                    Enemy enemy = new Enemy(List.of(), "Town Guard Ryan", 120, 35, 15, 5, 10, EntityClass.Warrior);
                    enemy.handleCombat(player, sc);
                    usedChoices.add(2);
                });
            }
            
            options.add("[3] Continue your journey");
            actions.add(() -> {
                App.displayEventOutcome("You ignore their pleas and move on.");
                staying.set(false);
            });
            
            App.displayEventChoices(options.toArray(new String[0]));
            int choice = getValidChoice(sc, options.size());
            actions.get(choice - 1).run();
        }
    }

    private void ancientTreasureEvent(Player player, Scanner sc) {
        AtomicBoolean staying = new AtomicBoolean(true);
        App.displayEventDescription(
            "Ancient Treasure Chest\n\n" +
            "An ornate chest covered in strange markings\n" +
            "lies half-buried in the dirt."
        );
        
        while (staying.get()) {
            List<String> options = new ArrayList<>();
            List<Runnable> actions = new ArrayList<>();
            
            if (!usedChoices.contains(1)) {
                options.add("[1] Pick the lock carefully");
                actions.add(() -> {
                    if (random.nextInt(100) < player.level * 10) {
                        Item reward = List.of(
                            Item.HealingPotion.potion(),
                            Item.ManaPotion.lowlyFlask(),
                            Item.CureItem.antidote()
                        ).get(random.nextInt(3));
                        player.addItemToInventory(reward);
                        App.displayEventOutcome("Success! Found: " + reward.name);
                    } else {
                        App.displayEventOutcome("The lock resists your attempts.");
                    }
                    usedChoices.add(1);
                });
            }
            
            if (!usedChoices.contains(2)) {
                options.add("[2] Force it open");
                actions.add(() -> {
                    if (random.nextBoolean()) {
                        Item reward = List.of(
                            Item.HealingPotion.potion(),
                            Item.ManaPotion.lowlyFlask(),
                            Item.CureItem.antidote()
                        ).get(random.nextInt(3));
                        player.addItemToInventory(reward);
                        App.displayEventOutcome("The chest breaks open! Found: " +  reward.name);
                    } else {
                        player.hp -= 40;
                        App.displayEventOutcome("A trap explodes! (-40 HP)");
                    }
                    usedChoices.add(2);
                });
            }
            
            options.add("[3] Walk away");
            actions.add(() -> {
                App.displayEventOutcome("You leave the mysterious chest behind.");
                staying.set(false);
            });
            
            App.displayEventChoices(options.toArray(new String[0]));
            int choice = getValidChoice(sc, options.size());
            actions.get(choice - 1).run();
        }
    }

    private int getValidChoice(Scanner sc, int maxChoice) {
        while (true) {
            try {
                int choice = sc.nextInt();
                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                }
                App.displayError();
            } catch (InputMismatchException e) {
                sc.next();
                App.displayError();
            }
        }
    }

    private void handleVillage(Player player, Scanner sc) {
        App.displayVillageOptions();
        int choice = getValidChoice(sc, 3);
        
        if (choice == 1) {
            buyItems(player, sc);
        } else if (choice == 2) {
            sellItems(player, sc);
        }
    }

    private void buyItems(Player player, Scanner sc) {
        App.displayShopItems(SHOP_ITEMS);
        
        int choice = getValidChoice(sc, SHOP_ITEMS.size() + 1);
        
        if (choice > 0 && choice <= SHOP_ITEMS.size()) {
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
        int choice = getValidChoice(sc, player.inventory.size() + 1);
        
        if (choice > 0 && choice <= player.inventory.size()) {
            Item sold = player.inventory.remove(choice - 1);
            player.money += 50;
            App.displayItemSold(sold.name);
        }
    }
}