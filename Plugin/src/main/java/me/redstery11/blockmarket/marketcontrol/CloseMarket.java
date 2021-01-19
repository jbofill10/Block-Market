package me.redstery11.blockmarket.marketcontrol;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseMarket implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            MarketControl marketControl = MarketControl.getInstance();

            marketControl.closeMarket();

            return true;
        }
        return false;
    }
}
