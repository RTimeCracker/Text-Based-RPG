/*class DebuffPotion extends Item{
    private final int debuffAmount;


    private DebuffPotion(String name, String description, int debuffAmount ) {
        super(name, description);
        this.debuffAmount = debuffAmount;
    }

    // This method is used to apply the debuff to the player
    public void use (Player player) {
        player.atk += debuffAmount;

        System.out.println("Using a " + name + ", it debuff " + debuffAmount + " DEBUFF!");
    }

    public static DebuffPotion Poison(){
        return new DebuffPotion("Poison", "Inflicts Poison (HP drains over time).", 50);
    }

    public static DebuffPotion NightShade(){
        return new DebuffPotion("Night Shade", "Inflicts Sleep (unable to act).", 50);
    }

    public static DebuffPotion BlindWeed(){
        return new DebuffPotion("Blind Weed", "Inflicts Blind (lowers accuracy).", 50);
    }

    public static DebuffPotion SilenceDust(){
        return new DebuffPotion("Silence Dust", "Inflicts Silence(prevents spellcasting).", 50);
    }

    public static DebuffPotion ParalyzeDust(){
        return new DebuffPotion("Paralyze Dust", "Inflicts Stop(stops all movement/actions)", 50);
    }

    public static DebuffPotion ConfusionDust(){
        return new DebuffPotion("Confusion Dust", "Inflicts Confusion (randomly attacks allies/enemies).", 50);
    }
    public static DebuffPotion CharmDust(){
        return new DebuffPotion("Charm Dust", "Inflicts Charm (makes the unit fight for the enemy).", 50);
    }
}
*/