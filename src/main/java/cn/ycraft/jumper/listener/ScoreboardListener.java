package cn.ycraft.jumper.listener;

import cn.ycraft.jumper.Main;
import cn.ycraft.jumper.manager.JumpManager;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Sidebar bar = Main.getSidebarManager().get(event.getPlayer());
        bar.title(Component.text("YourCraft"));
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Main.getSidebarManager().destroy(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        JumpManager man = Main.getJumpManager();
        if (man.isEnabled(event.getPlayer())) {
            int after = man.add(event.getPlayer(), 1);
            Main.getSidebarManager().updateJump(event.getPlayer(), after);
        } else {
            Main.getSidebarManager().updateJump(event.getPlayer(), -1);
        }
    }


}
