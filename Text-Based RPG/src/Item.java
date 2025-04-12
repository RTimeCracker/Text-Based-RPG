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

    public Item(String name, String description, int hpRestore, int mpRestore, 
                boolean isRevive, int reviveAmount, boolean grantsProtect, 
                boolean grantsShell, int atkBoost, int matkBoost, 
                boolean inflictsPoison, boolean inflictsSilence, 
                boolean inflictsParalyze, boolean curesPoison, 
                boolean curesSilence, boolean curesParalyze, boolean isRemedy) {
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
        public static Item potion() {
            return new Item("Potion", "Restores 50 HP.", 50, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item superPotion() {
            return new Item("Super Potion", "Restores 150 HP.", 150, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item maxPotion() {
            return new Item("Max Potion", "Restores 250 HP.", 250, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item phoenixDown() {
            return new Item("Phoenix Down", "Revives a KO'd unit with 10 HP.", 0, 0, 
                          true, 10, false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }
    }

    // Mana Potions
    public static class ManaPotion {
        public static Item lowlyFlask() {
            return new Item("Lowly Flask of Tears", "Restores 20 MP.", 0, 20, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item greatFlask() {
            return new Item("Great Flask of DemiGod Tears", "Restores 70 MP.", 0, 70, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item absoluteFlask() {
            return new Item("Absolute Flask of God Tears", "Restores 150 MP.", 0, 150, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }
    }

    // Hybrid Potions
    public static class HybridPotion {
        public static Item lesserElixir() {
            return new Item("Lesser Grade Elixir", "Restores HP and MP.", 50, 50, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item midElixir() {
            return new Item("Mid Grade Elixir", "Restores more HP and MP.", 100, 100, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item highElixir() {
            return new Item("High Grade Elixir", "Restores max HP and MP.", 200, 200, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }
    }

    // Buff Potions
    public static class BuffPotion {
        public static Item protectPotion() {
            return new Item("Protect Potion", "Reduces physical damage.", 0, 0, false, 0, 
                          true, false, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item shellPotion() {
            return new Item("Shell Potion", "Reduces magic damage.", 0, 0, false, 0, 
                          false, true, 0, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item powerJuice() {
            return new Item("Power Juice", "Increases Attack.", 0, 0, false, 0, 
                          false, false, 30, 0, false, false, false, 
                          false, false, false, false);
        }

        public static Item mindJuice() {
            return new Item("Mind Juice", "Increases Magic Power.", 0, 0, false, 0, 
                          false, false, 0, 30, false, false, false, 
                          false, false, false, false);
        }
    }

    // Debuff Potions
    public static class DebuffPotion {
        public static Item poison() {
            return new Item("Poison", "Inflicts HP drain over time.", 0, 0, false, 0, 
                          false, false, 0, 0, true, false, false, 
                          false, false, false, false);
        }

        public static Item silenceDust() {
            return new Item("Silence Dust", "Prevents spellcasting.", 0, 0, false, 0, 
                          false, false, 0, 0, false, true, false, 
                          false, false, false, false);
        }

        public static Item paralyzeDust() {
            return new Item("Paralyze Dust", "Stops all movement/actions.", 0, 0, false, 0, 
                          false, false, 0, 0, false, false, true, 
                          false, false, false, false);
        }
    }

    // Cure Items
    public static class CureItem {
        public static Item antidote() {
            return new Item("Antidote", "Cures Poison.", 0, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          true, false, false, false);
        }

        public static Item echoGrass() {
            return new Item("Echo Grass", "Cures Silence.", 0, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, true, false, false);
        }

        public static Item paralyzeHeal() {
            return new Item("Paralyze Heal", "Cures Paralysis.", 0, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          false, false, true, false);
        }

        public static Item remedy() {
            return new Item("Remedy", "Cures most status effects.", 0, 0, false, 0, 
                          false, false, 0, 0, false, false, false, 
                          true, true, true, true);
        }
    }
}