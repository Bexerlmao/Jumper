package cn.ycraft.jumper.listener;

import cn.ycraft.jumper.manager.JumpManager;
import cn.ycraft.jumper.manager.SidebarManager;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SidebarManager.createSidebarManagerIntoList(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Sidebar bar = SidebarManager.searchSidebarByPlayer(event.getPlayer()).getSidebar();
        if (bar != null) {bar.close();}
        SidebarManager.removeSidebarManagerFromList(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event){
        JumpManager.jumpCountIncrement(event.getPlayer());
        SidebarManager.jumpCountVisibility(event.getPlayer());
    }


}
