package pv.strigidis.controllers;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class Portals {
    public enum Color {
        COLOR_1, COLOR_2;

        public int toInt(){
            if (this == COLOR_1) {
                return 0;
            }
            return 1;
        }

        public static Color fromInt(int c){
            if (c == 0) {
                return COLOR_1;
            }
            // not safe but enough
            return COLOR_2;
        }
    }

    public Map<Color, Location> locations;

    public Portals(){
        locations = new HashMap<>();
        locations.put(Color.COLOR_1, null);
        locations.put(Color.COLOR_2, null);
    }

    public Location GetLocation(Color color){
        return locations.get(color);
    }

    public void SetLocation(Color color, Location location){
        locations.put(color, location);
    }
}
