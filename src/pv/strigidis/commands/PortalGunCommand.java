package pv.strigidis.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import pv.strigidis.items.PortalGun;

// PortalGunCommand gives a player a portal gun if it does not have one already
public class PortalGunCommand {

    public static void Execute(Player player) {
        PlayerInventory inv = player.getInventory();
        PortalGun gun = new PortalGun();
        if (inv.contains(gun)){
            player.sendMessage("You already own a portal gun. Don't be greedy those things are rare !");
            return;
        }
        inv.addItem(gun);
        player.sendMessage("Portal gun added. Time to shoot.");
    }

}
