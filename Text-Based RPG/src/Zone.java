enum ZoneType{
    Encounter, Village, Dungeon
}

public class Zone {
    ZoneType zoneType;
    int areaNumber;


    public Zone(ZoneType zoneType, int  areaNumber){
        this.zoneType = zoneType;
        this.areaNumber = areaNumber;
    }
}
