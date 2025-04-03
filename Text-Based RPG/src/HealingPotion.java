class HealingPotion extends Item{
    private final int healingAmount;

    private HealingPotion(String name, String description, int healingAmount) {
        super(name, description);
        this.healingAmount = healingAmount;
    }

    @Override
    public void use() {
        System.out.println("Using a " + name + ", it heals " + healingAmount + " HP!");
    }

    public static HealingPotion normalPotion(){
        return new HealingPotion("Normal Potion", "Heals for a good amount.", 50);
    }
}
