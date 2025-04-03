class BuffPotion extends Item{
    private final int buffAmount;


    private BuffPotion(String name, String description, int buffAmount ) {
        super(name, description);
        this.buffAmount = buffAmount;
    }


    public void use (Player player) {
        player.atk += buffAmount;

        System.out.println("Using a " + name + ", it buff " + buffAmount + " BUFF!");
    }

    public static BuffPotion buffPotion(){
        return new BuffPotion("Protect Potion", "Casts Protect (Reduces physical damage).", 50 );
    }

    public static BuffPotion buffPotion(){
        return new BuffPotion("Shell Potion", ".", 150);
    }

    public static HealingPotion maxPotion(){
        return new HealingPotion("Max Potion", "Heals for a max amount.", 250);
    }

}