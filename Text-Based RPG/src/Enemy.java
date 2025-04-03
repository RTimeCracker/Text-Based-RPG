import java.util.*;

public class Enemy  extends Entity{
    List<Item> itemDrop = new ArrayList<>();

    public Enemy(List<Item> itemDrop, String name, int[] stats) {
        this.itemDrop = itemDrop;
        this.name = name;
        this.stats = stats;
    }

    
}
