import java.util.*;
enum ZoneType{
    Encounter, Village, Dungeon
}


public class Zone {
    ZoneType zoneType;
    int areaNumber;
    static List<Zone> zones = new ArrayList<>();

    int xPos, yPos;


    public Zone(ZoneType zoneType){
        this.zoneType = zoneType;
        this.areaNumber = zones.size();
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

}
