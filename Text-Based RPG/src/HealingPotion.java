/*class HealingPotion extends Item{
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
        return new HealingPotion("Super Potion", "Heals for a greater amount.", 100);
    }

    public static HealingPotion maxPotion(){
        return new HealingPotion("Max Potion", "Heals for a max amount.", 200);
    }

    public static HealingPotion PhoenixDownPotion(){
        return new HealingPotion("Phoenix Down Potion", "Revives a KO'd unit with 10 HP.", 10);
    } 
}
*/