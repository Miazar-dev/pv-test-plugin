package pv.strigidis.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import pv.strigidis.NoicePlugin;
import pv.strigidis.controllers.PortalsController;
import pv.strigidis.items.PortalGun;

// PortalTestStartCommand starts (or reset) the test for a player
public class PortalTestStartCommand {

    private static double TEST_START_X = 73.5;
    private static double TEST_START_Y = 63.5;
    private static double TEST_START_Z = 274.5;

    public static void Execute(NoicePlugin plugin, Player player) {
        // player world doesn't really watter here
        Location dest = new Location(player.getWorld(), TEST_START_X, TEST_START_Y, TEST_START_Z);
        PortalsController.ClosePortals(plugin);
        player.teleport(dest);
        player.sendMessage("Let's start this test");
    }

}
