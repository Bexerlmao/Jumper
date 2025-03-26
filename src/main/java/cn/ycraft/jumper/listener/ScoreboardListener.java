package cn.ycraft.jumper.listener;

import cn.ycraft.jumper.Main;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    private final Map<UUID, Sidebar> sidebars = new HashMap<>();

    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {

        Sidebar sidebar = Main.getScoreboard().createSidebar();
        sidebar.title(Component.text("YourCraft"));
        sidebar.line(0, Component.text(event.getPlayer().getName()));

        sidebar.addPlayer(event.getPlayer());

        sidebars.put(event.getPlayer().getUniqueId(), sidebar);
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Sidebar bar = sidebars.remove(event.getPlayer().getUniqueId());
        if (bar != null) bar.close();
    }

}
