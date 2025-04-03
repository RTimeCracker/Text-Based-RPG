import java.util.*;

public class Player extends Entity{
    int money;
    List<Item> inventory = new ArrayList<>();
    
    public Player(String name, int hp, int atk, int def, EntityClass entityClass, int money, List<Item> inventory){
        super(name, hp, atk, def, entityClass);
        this.name = name;
        this.money = money;
        this.inventory = inventory;
    }

    public void Move(){
        //moves the player;
    }

    
}
