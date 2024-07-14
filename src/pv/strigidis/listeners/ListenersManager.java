package pv.strigidis.listeners;

import org.bukkit.event.Listener;
import pv.strigidis.NoicePlugin;

// ListenersManager manage all event listeners
public class ListenersManager implements Listener {

    private final NoicePlugin plugin;

    public ListenersManager(NoicePlugin plugin) {
        this.plugin = plugin;
    }

    // initialize registers all event listeners
    // It also registers itself if we need some more "global" listeners
    public void initialize() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(new ItemActionsListeners(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMovementListener(), plugin);
    }
}
