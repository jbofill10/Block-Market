package me.redstery11.blockmarket.marketcontrol;

import me.redstery11.blockmarket.marketgui.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Market implements CommandExecutor {
    private Gui gui = Gui.getInstance();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){

            try {
                this.gui.openInv((Player) commandSender);
                System.out.println("YOOO");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return true;

        }

        return false;
    }
}
