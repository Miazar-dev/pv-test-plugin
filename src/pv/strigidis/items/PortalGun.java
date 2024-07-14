package pv.strigidis.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import pv.strigidis.NoicePlugin;
import pv.strigidis.controllers.Portals;
import pv.strigidis.controllers.PortalsController;

import java.util.Arrays;

public class PortalGun extends ItemStack {

    public final static String NAME = "Portal Gun";
    private final static String DESCRIPTION = "I swear it is a portal gun";

    public PortalGun() {
        super(Material.STICK, 1);
        ItemMeta meta = this.getItemMeta();
        if (meta == null) {
            System.err.println("could not instantiate new portal gun meta");
            return;
        }

        meta.setDisplayName(NAME);
        String[] description = {DESCRIPTION};
        meta.setLore(Arrays.asList(description));
        this.setItemMeta(meta);
    }

    public static void Use(Player player, Action action, NoicePlugin plugin){
        switch (action) {
            case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR -> setPortal(plugin, player, PortalsController.PORTAL_1_BLOCK, Portals.Color.COLOR_1);
            case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR -> setPortal(plugin, player, PortalsController.PORTAL_2_BLOCK, Portals.Color.COLOR_2);
            default -> System.out.println("unknown action : " + action);
        }
    }

    private static void setPortal(NoicePlugin plugin, Player player, Material block, Portals.Color color){
        Block target = player.getTargetBlock(null, 50);
        if (target.isEmpty() || target.getType() != PortalsController.PORTAL_SURFACE) {
            return;
        }
        Location prevLocation = PortalsController.SetPortal(player.getUniqueId(), target.getLocation(), color);
        if (prevLocation != null) {
            prevLocation.getBlock().setType(PortalsController.PORTAL_SURFACE);
            prevLocation.getBlock().setMetadata("isPortal", new FixedMetadataValue(plugin, false));
        }
        target.setType(block);
        target.setMetadata("isPortal", new FixedMetadataValue(plugin, true));
        target.setMetadata("portalColor", new FixedMetadataValue(plugin, color.toInt()));
        target.setMetadata("playerID", new FixedMetadataValue(plugin, player.getUniqueId().toString()));
    }
}
