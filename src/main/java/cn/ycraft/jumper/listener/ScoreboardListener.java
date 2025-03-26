package cn.ycraft.jumper.listener;

import cn.ycraft.jumper.Main;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardListener implements Listener {

    private static final Map<UUID, Sidebar> sidebars = new HashMap<>();
    private static final Map<UUID, Integer> jumpCount = new HashMap<>();

    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {


        jumpCount.put(event.getPlayer().getUniqueId(), 0);
        Sidebar sidebar = Main.getScoreboard().createSidebar();
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sidebar.line(0, Component.text(event.getPlayer().getName()));

        sidebar.line(2, () -> {
            var time = dtf.format(new Date());
            return Component.text(time, NamedTextColor.GRAY);
        });

        sidebar.addPlayer(event.getPlayer());

        sidebars.put(event.getPlayer().getUniqueId(), sidebar);
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent event) {
        Sidebar bar = sidebars.remove(event.getPlayer().getUniqueId());
        if (bar != null) bar.close();
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event){
        jumpCount.put(event.getPlayer().getUniqueId(), jumpCount.get(event.getPlayer().getUniqueId()) + 1) ;
    }

    public static Sidebar getSidebar(Player player) {return sidebars.get(player.getUniqueId());}
    public static Map<UUID, Integer> getJumpCount() {return jumpCount;}
}
