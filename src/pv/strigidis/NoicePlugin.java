package pv.strigidis;

import org.bukkit.plugin.java.JavaPlugin;
import pv.strigidis.commands.CommandsManager;
import pv.strigidis.controllers.PortalsController;
import pv.strigidis.listeners.ListenersManager;

// That's really noice stuff
public class NoicePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        CommandsManager commandsManager = new CommandsManager(this);
        commandsManager.initialize();
        ListenersManager listenersManager = new ListenersManager(this);
        listenersManager.initialize();
        System.out.println("Strigidis Plugin Enabled");
    }


    @Override
    public void onDisable() {
        PortalsController.ClosePortals(this);

        super.onDisable();
        System.out.println("Strigids plugin stopped running");
    }
}
