package me.rewu.monslaves.Commands;

import me.rewu.monslaves.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class HeadCommand implements CommandExecutor {

    private String COMMAND_USAGE = "/mons <collection:create:clash:spectate>";

    private HashMap<String, MonslavesCommand> commands = new HashMap<>();

    public void registerMonslavesCommands() {
        commands.put("collection", new CollectionCommand());

        CreateCommand createCommand = new CreateCommand();
        Bukkit.getPluginManager().registerEvents(createCommand, Main.getInstance());
        commands.put("create", createCommand);

        commands.put("spectate", new SpectateCommand());

        ClashCommand clashCommand = new ClashCommand();
        Bukkit.getPluginManager().registerEvents(clashCommand, Main.getInstance());
        commands.put("clash", clashCommand);

        commands.put("test", new TestCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§fUsage: §c" + COMMAND_USAGE);

            return true;
        }

        String permission = "monslaves";

        for (String arg : args)
            permission += "." + arg;

        if (!sender.hasPermission(permission)) {
            sender.sendMessage("You are missing the permission §c" + permission + "§f required to use this command.");

            return true;
        }

        name = args[0];

        if (!commands.containsKey(name)) {
            sender.sendMessage("§fUsage: §c" + COMMAND_USAGE);

            return true;
        }

        MonslavesCommand command = commands.get(name);

        command.Execute(sender, args);

        return true;
    }

}
