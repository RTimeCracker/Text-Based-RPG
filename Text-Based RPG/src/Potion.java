class Potion extends Item {
    private final int healingAmount;

    private Potion(String name, String description, int healingAmount) {
        super(name, description);
        this.healingAmount = healingAmount;
    }

    public void use() {
        System.out.println("Using a " + name + ", it heals " + healingAmount + " HP!");
    }

    public static Potion normalPotion(){
        return new Potion("Normal Potion", "Heals for a good amount.", 50);
    }
}
