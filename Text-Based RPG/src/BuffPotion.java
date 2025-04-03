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

    public static BuffPotion ProtectPotion(){
        return new BuffPotion("Protect Potion", "Casts Protect (reduces physical damage).", 50 );
    }

    public static BuffPotion ShellPotion(){
        return new BuffPotion("Shell Potion", "Casts Shell (reduces magic damage).", 50);
    }

    public static BuffPotion PowerJuice(){
        return new BuffPotion("Power Juice", "Casts Boost (increase attack).", 50);
    }

    public static BuffPotion MindJuice(){
        return new BuffPotion("Mind Juice", "Casts Boost (increase magic power).", 50);
    }

}