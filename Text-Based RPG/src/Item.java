public class Item {
    String name;
    String description;
    int healingAmount;
    int buffAmount;
    int hybridAmount;
    int manaAmount;
    int debuffAmount;
    int ailmentAmount;


    public Item(String name, String description, int healingAmount, int buffAmount, int hybridAmount, int manaAmount, int debuffAmount, int ailmentAmount) {
        this.name = name;
        this.description = description;
        this.healingAmount = healingAmount;
        this.buffAmount = buffAmount;
        this.hybridAmount = hybridAmount;
        this.manaAmount = manaAmount;
        this.debuffAmount = debuffAmount;   
        this.ailmentAmount = ailmentAmount;

    }

    public void use(Player player) { 
        player.hp += healingAmount;
        player.mp += manaAmount;
        player.atk += buffAmount;
        player.def += debuffAmount;
        player.atk += ailmentAmount;
        player.hp += hybridAmount;
        player.mp += hybridAmount;

        System.out.println("Using " + name + ": " + description);
        if (healingAmount > 0) System.out.println("It heals " + healingAmount + " HP!");
        if (manaAmount > 0) System.out.println("It restores " + manaAmount + " MP!");
        if (buffAmount > 0) System.out.println("It buffs attack by " + buffAmount + "!");
        if (debuffAmount > 0) System.out.println("It debuffs defense by " + debuffAmount + "!");
        if (ailmentAmount > 0) System.out.println("It applies an ailment effect of " + ailmentAmount + "!");
        if (hybridAmount > 0) System.out.println("It restores " + hybridAmount + " HP & MP!");
    }

    // Healing Potions
    public static Item superPotion(){
        return new  Item ("Super Potion", "Heals for a greater amount.", 100, 0, 0, 0, 0, 0);
    }

    public static Item maxPotion(){
        return new Item ("Max Potion", "Heals for a max amount.", 200 , 0, 0, 0, 0, 0);
    }

    public static Item PhoenixDownPotion(){
        return new Item("Phoenix Down Potion", "Revives a KO'd unit with 10 HP.", 10 , 0, 0, 0, 0, 0);
    } 


    // Mana Potions
    public static Item LowlyFlaskofTears(){
        return new Item("Lowly Flask of Tears", "Restores 20 MP.", 0, 0, 0, 20, 0, 0);
    }

    public static Item GreatFlaskofDemiGodTears(){
        return new Item("Great Flask of DemiGod Tears", "Restores 70 MP.", 0 , 0, 0, 70, 0, 0);
    }

    public static Item AbsoluteFlaskofGodTears(){
        return new Item("Absolute Flask of God Tears", "Restores 150 MP.", 0, 0, 0, 150, 0, 0);
    }


    // Buff Potions
    public static Item ProtectPotion(){
        return new Item("Protect Potion", "Casts Protect (reduces physical damage).", 0 , 50, 0, 0, 0, 0);
    }

    public static Item ShellPotion(){
        return new Item("Shell Potion", "Casts Shell (reduces magic damage).", 0, 50, 0, 0, 0, 0);
    }

    public static Item PowerJuice(){
        return new Item("Power Juice", "Casts Boost (increase attack).", 0, 50, 0, 0, 0, 0);
    }

    public static Item MindJuice(){
        return new Item("Mind Juice", "Casts Boost (increase magic power).", 0 , 50, 0, 0, 0, 0);
    }


    // Debuff Potions
    public static Item Poison(){
        return new Item("Poison", "Inflicts Poison (HP drains over time).", 0, 0, 0, 0, 50, 0);
    }

    public static Item NightShade(){
        return new Item ("Night Shade", "Inflicts Sleep (unable to act).", 0 , 0, 0, 0, 50, 0);
    }

    public static Item BlindWeed(){
        return new Item("Blind Weed", "Inflicts Blind (lowers accuracy).", 0 , 0, 0, 0, 50, 0);
    }

    public static Item SilenceDust(){
        return new Item("Silence Dust", "Inflicts Silence(prevents spellcasting).", 0 , 0, 0, 0, 50, 0);
    }

    public static Item ParalyzeDust(){
        return new Item("Paralyze Dust", "Inflicts Stop(stops all movement/actions)",  0 , 0, 0, 0, 50, 0);
    }

    public static Item ConfusionDust(){
        return new Item ("Confusion Dust", "Inflicts Confusion (randomly attacks allies/enemies).", 0, 0, 0, 0, 50, 0);
    }
    public static Item CharmDust(){
        return new Item("Charm Dust", "Inflicts Charm (makes the unit fight for the enemy).", 0, 0, 0, 0, 50, 0);
    }

    // Ailment Potions
    public static Item Antidote(){
        return new Item("Antidote", "Cures Poison.", 0, 0, 0, 0, 0, 50);
    }

    public static Item EyeDrops(){
        return new Item("EyeDrops", "Cures Blind.", 0, 0, 0, 0, 0, 50);
    }

    public static Item EchoGrass(){
        return new Item("Echo Grass", "Cures Silence.", 0, 0, 0, 0, 0, 50);
    }

    public static Item HolyWater(){
        return new Item ("Holy Water", "Cures Zombie and Curse.", 0, 0, 0, 0, 0, 50);
    }

    public static Item Remedy(){
        return new Item ("Remedy", "Cures most status effects (except Doom and Charm).", 0, 0, 0, 0, 0, 50);
    }

    // Hybrid Potions
    public static Item LesserGradePotion(){
        return new Item("Lesser Grade Elixir", "Restores for a good amount of HP and MP.", 0, 0, 50, 0, 0, 0);
    }

    public static Item MidGradePotion(){
        return new Item("Mid Elixir Potion", "Restores for a greater amount of HP and MP.", 0, 0, 100, 0, 0, 0);
    }

    public static Item HighGradePotion(){
        return new Item("High Elixir Potion", "Restores for a max amount of HP and MP.", 0, 0, 200, 0, 0, 0);
    }

}
