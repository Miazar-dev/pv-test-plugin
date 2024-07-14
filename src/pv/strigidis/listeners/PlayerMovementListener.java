package pv.strigidis.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import pv.strigidis.controllers.Portals;
import pv.strigidis.controllers.PortalsController;

import java.util.UUID;

// Listen to player movements to detect portals
public class PlayerMovementListener implements Listener {

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
        Location dest = event.getTo();
        if (dest == null) {
            return;
        }

        if (event.getFrom().getBlockX() == dest.getBlockX()
                && event.getFrom().getBlockY() == dest.getBlockY()
                && event.getFrom().getBlockZ() == dest.getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();
        Block block = player.getWorld().getBlockAt(dest).getRelative(BlockFace.DOWN);

        if (block.getMetadata("isPortal").isEmpty() || !block.getMetadata("isPortal").get(0).asBoolean()) {
            return;
        }

        // Player stands on a portal. Retrieving portal destination
        Portals.Color color = Portals.Color.fromInt(block.getMetadata("portalColor").get(0).asInt());
        UUID playerID = UUID.fromString(block.getMetadata("playerID").get(0).asString());
        Location portalDest = PortalsController.getDestinationLocFrom(playerID, color);
        if (portalDest == null) {
            // destination portal not set
            return;
        }

        player.teleport(portalDest.clone().add(new Vector(0, 1, 0)));
    }

}
