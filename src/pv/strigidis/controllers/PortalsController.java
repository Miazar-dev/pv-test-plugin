package pv.strigidis.controllers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import pv.strigidis.NoicePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// PortalsController holds the portals data present in the world
public final class PortalsController {
    private static PortalsController instance;
    private Map<UUID, Portals> portals;

    public final static Material PORTAL_1_BLOCK = Material.LAPIS_BLOCK;
    public final static Material PORTAL_2_BLOCK = Material.COPPER_BLOCK;
    public final static Material PORTAL_SURFACE = Material.CALCITE;

    private PortalsController() {
        portals = new HashMap<>();
    }

    public static PortalsController getInstance() {
        if (instance == null) {
            instance = new PortalsController();
        }
        return instance;
    }

    public Map<UUID, Portals> GetPortals()  {
        return portals;
    }

    public static Location getDestinationLocFrom(UUID pID, Portals.Color origin){
        instance = getInstance();
        Portals playerPortals = instance.GetPortals().get(pID);
        if (playerPortals == null) {
            return null;
        }
        if (origin == Portals.Color.COLOR_1){
            return playerPortals.GetLocation(Portals.Color.COLOR_2);
        }
        return playerPortals.GetLocation(Portals.Color.COLOR_1);
    }

    // SetPortal stores the new portal location and return previous one if any
    public static Location SetPortal(UUID pID, Location location, Portals.Color color){
        instance = getInstance();
        Portals playerPortals = instance.GetPortals().get(pID);

        if (playerPortals == null) {
            playerPortals = new Portals();
        }

        Location prevLocation = playerPortals.GetLocation(color);

        playerPortals.SetLocation(color, location);

        instance.portals.put(pID, playerPortals);
        return prevLocation;
    }

    // ClosePortals will close all portals and restore their previous form
    // Without a proper DB, we can't really reset portals at start
    // This is the closest way we can have a clean world on reload
    public static void ClosePortals(NoicePlugin plugin) {
        instance = getInstance();
        for (UUID pID : instance.GetPortals().keySet()) {
            Portals portals = instance.GetPortals().get(pID);
            if (portals != null) {
                removePortal(plugin, portals.GetLocation(Portals.Color.COLOR_1));
                removePortal(plugin, portals.GetLocation(Portals.Color.COLOR_2));
            }
        }
    }

    private static void removePortal(NoicePlugin plugin, Location location) {
        if (location == null){
            return;
        }

        World world = plugin.getServer().getWorld("World");
        if (world == null){
            System.err.println("couldn't retrieve world while cleaning portals");
            return;
        }

        Block block = world.getBlockAt(location);
        block.setMetadata("isPortal", new FixedMetadataValue(plugin, false));
        block.setType(PORTAL_SURFACE);
    }
}
