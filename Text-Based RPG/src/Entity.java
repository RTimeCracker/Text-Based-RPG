enum EntityClass {
    Warrior, Mage, Tank, Summoner
}

public abstract class Entity {
    String name;
    int hp;
    int atk;
    int def;
    int mp;
    EntityClass entityClass;

    public Entity(String name, int hp, int atk, int def, int mp, EntityClass entityClass) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.mp = mp;
        this.entityClass = entityClass;
    }
}
