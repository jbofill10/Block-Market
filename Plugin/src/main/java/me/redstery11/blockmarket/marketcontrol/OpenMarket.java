package me.redstery11.blockmarket.marketcontrol;

import me.redstery11.blockmarket.BlockMarket;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class OpenMarket implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        MarketControl marketControl = MarketControl.getInstance();

            marketControl.openMarket();

            Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(
                    (BlockMarket.getInstance())),
                    marketControl,
                    0, 600);


        return true;
    }
}
