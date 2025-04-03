class MiscellaneousItems extends Item{
    private final int MiscellaneousAmount;


    private MiscellaneousItems(String name, String description, int MiscellaneousAmount ) {
        super(name, description);
        this.MiscellaneousAmount = MiscellaneousAmount;
    }


    public void use (Player player) {
        player.atk += MiscellaneousAmount;

        System.out.println("Using a " + name + ", it MiscellaneousItem " + MiscellaneousAmount + " Miscellaneous!");
    }

    public static MiscellaneousItems Antidote(){
        return new MiscellaneousItems("Antidote", "Cures Poison.", 50 );
    }

    public static MiscellaneousItems EyeDrops(){
        return new MiscellaneousItems("EyeDrops", "Cures Blind.", 50);
    }

    public static MiscellaneousItems EchoGrass(){
        return new MiscellaneousItems("Echo Grass", "Cures Silence.", 50);
    }

    public static MiscellaneousItems HolyWater(){
        return new MiscellaneousItems("Holy Water", "Cures Zombie and Curse.", 50);
    }

    public static MiscellaneousItems Remedy(){
        return new MiscellaneousItems("Remedy", "Cures most status effects (except Doom and Charm).", 50);
    }

}