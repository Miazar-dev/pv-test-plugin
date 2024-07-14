package pv.strigidis.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import pv.strigidis.NoicePlugin;

// Manages all the console commands
public class CommandsManager implements CommandExecutor {

    private final static String COMMAND_PORTAL = "portal";
    private final static String COMMAND_TEST_START = "start";

    private final NoicePlugin plugin;

    public CommandsManager(NoicePlugin plugin) {
        this.plugin = plugin;
    }

    // initialize registers all commands
    public void initialize() {
        String[] commands = {COMMAND_PORTAL, COMMAND_TEST_START};
        for (String commandName : commands) {
            PluginCommand command = this.plugin.getCommand(commandName);
            if (command == null){
                System.err.println("Command " + commandName + " not found!");
                continue;
            }

            System.out.println("Command " + commandName + " initialized");

            command.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player!");
            return false;
        }

        return dispatchCommand(cmd.getName(), player, label, args);
    }

    // dispatchCommand finds the right command to execute
    private boolean dispatchCommand(String name, Player sender, String label, String[] args) {
        switch (name) {
            case COMMAND_PORTAL:
                PortalGunCommand.Execute(sender);
                break;
            case COMMAND_TEST_START:
                PortalTestStartCommand.Execute(plugin, sender);
                break;
            default:
                // Command does not exist
                return false;
        }
        return true;
    }
}
