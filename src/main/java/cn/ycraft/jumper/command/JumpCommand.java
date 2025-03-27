package cn.ycraft.jumper.command;

import cn.ycraft.jumper.manager.SidebarManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JumpCommand implements CommandExecutor {

    public JumpCommand() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以使用这个命令！");
            return true;
        }

        Player player = (Player) sender;

        SidebarManager.jumpCountVisibility(player);


        return false;
    }


}
