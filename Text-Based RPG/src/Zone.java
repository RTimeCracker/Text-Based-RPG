import java.util.*;
enum ZoneType{
    Encounter, Village, Dungeon
}


public class Zone {
    ZoneType zoneType;
    int zoneNumber;
    static List<Zone> zones = new ArrayList<>();

    private int xPos, yPos;


    public Zone(ZoneType zoneType, int xPos, int yPos){
        this.zoneType = zoneType;
        this.zoneNumber = zones.size();
        this.xPos = xPos;
        this.yPos = yPos;
        zones.add(this);
    }

    public static Zone getZoneFromPosition(int xPos, int yPos){
        for(Zone z: zones){
            if(z.xPos == xPos && z.yPos == yPos){
                return z;
            }
        }
        return null;
    }

    public static int[] getZonePosition(Zone zone){
        return new int[]{zone.xPos, zone.yPos};
    }

}
