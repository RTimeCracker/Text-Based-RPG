import java.util.*;

public class Enemy  extends Entity{
    List<Item> itemDrop = new ArrayList<>();


    public Enemy(List<Item> itemDrop,String name, int hp, int atk, int def, EntityClass entityClass) {
        super(name, hp, atk, def, entityClass);
        this.itemDrop = itemDrop;
    }

    
}
