enum EntityClass {
    Warrior, Mage, Tank, Summoner
}

public abstract class Entity {
    // Basic Info
    protected String name;
    protected EntityClass entityClass;
    protected int level;
    
    // Physical Stats
    protected int hp;
    protected int maxHp;
    protected int atk;    
    protected int def;    
    
    // Magic Stats
    protected int mp;
    protected int maxMp;
    protected int matk;   
    protected int mdef;   
    
    // Status Effects
    protected boolean isPoisoned;
    protected boolean isSilenced;
    protected boolean isParalyzed;
    
    // Buffs
    protected boolean hasProtect;  // Reduces physical damage
    protected boolean hasShell;    // Reduces magic damage

    public Entity(String name, int hp, int atk, int def, int mp, int matk, int mdef, EntityClass entityClass) {
        this.name = name;
        this.entityClass = entityClass;
        this.level = 1;
        
        // Physical stats
        this.hp = this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        
        // Magic stats
        this.mp = this.maxMp = mp;
        this.matk = matk;
        this.mdef = mdef;
        
        // Initialize all status effects to false
        this.isPoisoned = false;
        this.isSilenced = false;
        this.isParalyzed = false;
        this.hasProtect = false;
        this.hasShell = false;
    }

    // Status Methods
    public boolean isPoisoned() { return isPoisoned; }
    public boolean isSilenced() { return isSilenced; }
    public boolean isParalyzed() { return isParalyzed; }
    public boolean hasProtect() { return hasProtect; }
    public boolean hasShell() { return hasShell; }

    public void applyPoison() { this.isPoisoned = true; }
    public void applySilence() { this.isSilenced = true; }
    public void applyParalyze() { this.isParalyzed = true; }
    public void applyProtect() { this.hasProtect = true; }
    public void applyShell() { this.hasShell = true; }

    public void cureAll() {
        this.isPoisoned = false;
        this.isSilenced = false;
        this.isParalyzed = false;
    }

    public void removeBuffs() {
        this.hasProtect = false;
        this.hasShell = false;
    }

    // Stat Methods
    public int getMatk() { return matk; }
    public int getMdef() { return mdef; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMp() { return maxMp; }
    
    public void restoreHp(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }
    
    public void restoreMp(int amount) {
        this.mp = Math.min(this.mp + amount, this.maxMp);
    }

    // Combat Methods
    public int calculatePhysicalDamage(int enemyDef) {
        return Math.max(1, this.atk - enemyDef / 2);
    }
    
    public int calculateMagicDamage(int enemyMdef) {
        return Math.max(1, this.matk - enemyMdef / 2);
    }
}