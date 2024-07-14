package pv.strigidis.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pv.strigidis.NoicePlugin;
import pv.strigidis.items.PortalGun;

import static org.bukkit.event.block.Action.LEFT_CLICK_BLOCK;
import static org.bukkit.event.player.PlayerAnimationType.ARM_SWING;

// Listen to player clicks to execute some actions
public class ItemActionsListeners implements Listener {

    private NoicePlugin plugin;

    ItemActionsListeners(NoicePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()){
            return;
        }

        String itemName = item.getItemMeta().getDisplayName();
        switch (itemName) {
            case PortalGun.NAME:
                PortalGun.Use(player, event.getAction(), plugin);
                break;
            default:
                break;
        }
    }


    // This is workaround because onPlayerUse does not seem to handle the LEFT_CLICK_BLOCK action
    // Literature says no packet is sent to the server when this happens so the server can't really know that
    // Listening on the right arm animation does the trick though
    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerAnimation(PlayerAnimationEvent event){
        if (event.getAnimationType() != ARM_SWING){
            return;
        }
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()){
            return;
        }

        String itemName = item.getItemMeta().getDisplayName();
        switch (itemName) {
            case PortalGun.NAME:
                PortalGun.Use(player, LEFT_CLICK_BLOCK, plugin);
                break;
            default:
                break;
        }
    }
}
