package cn.ycraft.jumper.command;

import cn.ycraft.jumper.listener.ScoreboardListener;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JumpCommand implements CommandExecutor {

    public JumpCommand() {}
    protected static Map<UUID, Boolean> sidebarStates = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("只有玩家可以使用这个命令！");
            return true;
        }

        Player player = (Player) sender;
        Sidebar sidebar = ScoreboardListener.getSidebar(player);

        sidebarStates.putIfAbsent(player.getUniqueId(), true);

        if(sidebarStates.get(player.getUniqueId())){
            sidebarStates.replace(player.getUniqueId(), (!sidebarStates.get(player.getUniqueId())));
        }

        Map<UUID, Integer> jumpCount = ScoreboardListener.getJumpCount();
        boolean state = getSidebarStates(player);
        if(state){
            sidebar.line(1, () -> Component.text(jumpCount.get(player.getUniqueId())));
        }else{
            sidebar.line(1,null);
        }

        return false;
    }

    public static boolean getSidebarStates(Player player){
        return sidebarStates.get(player.getUniqueId());
    }

}
