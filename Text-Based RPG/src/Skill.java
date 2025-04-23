import java.util.Random;
import java.util.*;

public class Skill {
    private static final Random rand = new Random();
    
    private String name;
    private int unlockLevel;
    private int mpCost;
    private String description;
    private EntityClass requiredClass;
    
    public Skill(String name, int unlockLevel, int mpCost, String description, EntityClass requiredClass) {
        this.name = name;
        this.unlockLevel = unlockLevel;
        this.mpCost = mpCost;
        this.description = description;
        this.requiredClass = requiredClass;
    }
    
    // Getters
    public String getName() { return name; }
    public int getUnlockLevel() { return unlockLevel; }
    public int getMpCost() { return mpCost; }
    public String getDescription() { return description; }
    public EntityClass getRequiredClass() { return requiredClass; }
    
    // Main skill activation method
    public String use(Player player, Enemy enemy) {
        StringBuilder result = new StringBuilder();
        
        switch(name) {
            // Warrior Skills
            case "Power Strike":
                int powerDamage = (int)(player.getAtk() * 1.5 - (enemy.getDef() / 2));
                enemy.takeDamage(powerDamage);
                result.append(String.format("%s uses Power Strike! Deals %d damage!", player.name, powerDamage));
                break;
                
            case "Whirlwind":
                int whirlDamage = (int)(player.getAtk() * 1.2 - (enemy.getDef() / 2));
                enemy.takeDamage(whirlDamage);
                result.append(String.format("%s spins wildly with Whirlwind! Deals %d damage to all enemies!", 
                    player.name, whirlDamage));
                // Note: For multiple enemies, you'd loop through them
                break;
                
            case "Berserk":
                player.atk *= 2;
                player.def /= 2;
                result.append(String.format("%s enters Berserk mode! ATK doubled but DEF halved!", player.name));
                break;
                
            // Mage Skills    
            case "Fireball":
                int fireDamage = player.getMatk() + 20 - (enemy.getMdef() / 2);
                enemy.takeDamage(fireDamage);
                result.append(String.format("%s hurls a Fireball! Deals %d fire damage!", player.name, fireDamage));
                break;
                
            case "Ice Shard":
                int iceDamage = player.getMatk() + 15 - (enemy.getMdef() / 2);
                enemy.takeDamage(iceDamage);
                if(rand.nextInt(100) < 30) { // 30% chance
                    enemy.isParalyzed = true;
                    result.append(String.format("%s freezes the enemy with Ice Shard! Deals %d damage and paralyzes!", 
                        player.name, iceDamage));
                } else {
                    result.append(String.format("%s shoots Ice Shard! Deals %d damage!", player.name, iceDamage));
                }
                break;
                
            case "Arcane Blast":
                int arcaneDamage = (int)(player.getMatk() * 2.5 - (enemy.getMdef() / 2));
                enemy.takeDamage(arcaneDamage);
                player.mp = Math.max(0, player.mp - 10); // Extra MP cost
                result.append(String.format("%s unleashes Arcane Blast! Deals %d massive damage!", 
                    player.name, arcaneDamage));
                break;
                
            // Tank Skills
            case "Shield Bash":
                int bashDamage = player.getAtk() - (enemy.getDef() / 3);
                enemy.takeDamage(bashDamage);
                if(rand.nextInt(100) < 40) { // 40% chance
                    enemy.isParalyzed = true;
                    result.append(String.format("%s bashes with shield! Deals %d damage and stuns!", 
                        player.name, bashDamage));
                } else {
                    result.append(String.format("%s bashes with shield! Deals %d damage!", player.name, bashDamage));
                }
                break;
                
            case "Taunt":
                enemy.hasTaunted = true; // You'd need to add this flag to Enemy class
                player.def += 5; // Temporary defense boost
                result.append(String.format("%s taunts the enemy! Enemy is forced to attack %s!", 
                    player.name, player.name));
                break;
                
            case "Last Stand":
                player.hasLastStand = true; // Add this flag to Player
                result.append(String.format("%s prepares for a Last Stand! Will survive one fatal blow!", player.name));
                break;
                
            // Summoner Skills
            case "Summon Wolf":
                player.hasWolfCompanion = true; // Add this flag
                result.append(String.format("%s summons a wolf companion! The wolf will assist in battle!", player.name));
                break;
                
            case "Healing Spirit":
                int healAmount = player.getMatk() + 30;
                player.hp = Math.min(player.getMaxHp(), player.hp + healAmount);
                result.append(String.format("%s calls a Healing Spirit! Restores %d HP!", player.name, healAmount));
                break;
                
            case "Phoenix Call":
                if(player.hp <= 0) {
                    player.hp = player.getMaxHp() / 2;
                    result.append(String.format("%s is revived by Phoenix Call at half HP!", player.name));
                } else {
                    player.hasPhoenixBuff = true; // Add this flag
                    result.append(String.format("%s channels phoenix energy! Will automatically revive if defeated!", 
                        player.name));
                }
                break;
                
            default:
                result.append("Skill not implemented yet!");
        }
        
        return result.toString();
    }
    
    // Static method to initialize all class skills
    public static List<Skill> initializeClassSkills(EntityClass entityClass) {
        List<Skill> skills = new ArrayList<>();
        
        switch(entityClass) {
            case Warrior:
                skills.add(new Skill("Power Strike", 5, 10, "Deals 150% physical damage", EntityClass.Warrior));
                skills.add(new Skill("Whirlwind", 10, 15, "Hits all enemies for 120% damage", EntityClass.Warrior));
                skills.add(new Skill("Berserk", 15, 20, "Doubles ATK but halves DEF for 3 turns", EntityClass.Warrior));
                break;
                
            case Mage:
                skills.add(new Skill("Fireball", 5, 10, "Deals fire magic damage", EntityClass.Mage));
                skills.add(new Skill("Ice Shard", 10, 15, "Deals ice damage with chance to paralyze", EntityClass.Mage));
                skills.add(new Skill("Arcane Blast", 15, 25, "Massive magic damage (high MP cost)", EntityClass.Mage));
                break;
                
            case Tank:
                skills.add(new Skill("Shield Bash", 5, 10, "Deals damage with chance to stun", EntityClass.Tank));
                skills.add(new Skill("Taunt", 10, 15, "Forces enemies to attack you", EntityClass.Tank));
                skills.add(new Skill("Last Stand", 15, 20, "Survive one fatal hit with 1 HP", EntityClass.Tank));
                break;
                
            case Summoner:
                skills.add(new Skill("Summon Wolf", 5, 15, "Summons wolf companion", EntityClass.Summoner));
                skills.add(new Skill("Healing Spirit", 10, 20, "Heals substantial HP", EntityClass.Summoner));
                skills.add(new Skill("Phoenix Call", 15, 30, "Revives if defeated (or prevents death)", EntityClass.Summoner));
                break;
        }
        
        return skills;
    }
}