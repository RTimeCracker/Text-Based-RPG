class HealingPotion extends Item{
    private final int healingAmount;

    private HealingPotion(String name, String description, int healingAmount) {
        super(name, description);
        this.healingAmount = healingAmount;
    }

    
    public void use (Player player) {
        player.hp += healingAmount;

        System.out.println("Using a " + name + ", it heals " + healingAmount + " HP!");
    }

    public static HealingPotion normalPotion(){
        return new HealingPotion("Normal Potion", "Heals for a good amount.", 50);
    }

    public static HealingPotion superPotion(){
        return new HealingPotion("Super Potion", "Heals for a greater amount.", 150);
    }

    public static HealingPotion maxPotion(){
        return new HealingPotion("Max Potion", "Heals for a max amount.", 250);
    }

}
