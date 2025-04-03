import java.util.*;

public class Player extends Entity{
    int money;
    List<Item> inventory = new ArrayList<>();
    
    public Player(String name, int[] stats, int money, List<Item> inventory){
        this.name = name;
        this.stats = stats;
        this.money = money;
        this.inventory = inventory;
    }

    public void Move(){
        //moves the player;
    }

    
}
