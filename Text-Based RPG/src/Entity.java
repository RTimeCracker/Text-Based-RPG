enum EntityClass {
    Warrior, Mage, Healer, Summoner
}

public abstract class Entity {
    String name;
    int[] stats = {
        10, //HP
        10, //ATK
        10, //DEF
    };

    public void Attack(){
        //makes the entity damage it's enemy
    }

    public void Defend(){
        //raises the entity's defense
    }

    public void TakeDamage(){
        //reduces the damage of the entity based on the dmg taken
    }
}
