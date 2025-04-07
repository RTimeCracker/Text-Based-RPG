/*class HybridPotion extends Item{
    private final int hybridAmount;

    private HybridPotion(String name, String description, int hybridAmount) {
        super(name, description);
        this.hybridAmount = hybridAmount;
    }

    
    public void use (Player player) {
        player.hp += hybridAmount;
        player.mp += hybridAmount;

        System.out.println("Using a " + name + ", it restores HP and MP " + hybridAmount + " HP & MP!");
    }

    public static HybridPotion LesserGradePotion(){
        return new HybridPotion("Lesser Grade Elixir", "Restores for a good amount of HP and MP.", 50);
    }

    public static HybridPotion MidGradePotion(){
        return new HybridPotion("Mid Elixir Potion", "Restores for a greater amount of HP and MP.", 100);
    }

    public static HybridPotion HighGradePotion(){
        return new HybridPotion("High Elixir Potion", "Restores for a max amount of HP and MP.", 200);
    }
}
*/