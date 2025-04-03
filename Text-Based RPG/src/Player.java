import java.util.*;

public class Player extends Entity{
    int money;
    Zone zone;
    List<Item> inventory = new ArrayList<>();

    private int xPos = 0, yPos = 0;
    
    public Player(String name, int hp, int atk, int def, EntityClass entityClass, int money, List<Item> inventory){
        super(name, hp, atk, def, entityClass);
        this.name = name;
        this.money = money;
        this.inventory = inventory;
    }

    public void Move(int xPos, int yPos){
        //moves the player;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
