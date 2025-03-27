package cn.ycraft.jumper.manager;

import cn.ycraft.jumper.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;


public class SidebarManager {
    private static final SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    protected ScoreboardLibrary scoreboardLibrary;
    protected final Map<UUID, Sidebar> bars = new HashMap<>();

    public SidebarManager(Main plugin) {
        //注册计数器
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(plugin);
        } catch (NoPacketAdapterAvailableException e) {
            scoreboardLibrary = new NoopScoreboardLibrary();
            plugin.getLogger().warning("No scoreboard packet adapter available!");
        }
    }

    public ScoreboardLibrary api() {
        return scoreboardLibrary;
    }

    public void shutdown() {
        scoreboardLibrary.close();
    }

    public @NotNull Sidebar get(@NotNull Player player) {
        return bars.computeIfAbsent(player.getUniqueId(), uuid -> {
            Sidebar sidebar = api().createSidebar();
            sidebar.addPlayer(player);
            return sidebar;
        });
    }

    public void destroy(@NotNull Player player) {
        Sidebar bar = this.bars.remove(player.getUniqueId());
        if (bar != null) {
            bar.close();
        }
    }

    public void update(@NotNull Player player, Consumer<Sidebar> editor) {
        editor.accept(get(player));
    }

    public void updateJump(@NotNull Player player, int current) {
        Main.getSidebarManager().update(player, sidebar -> {
            if (current > 0) {
                sidebar.line(1, Component.text("Jumps: " + current));
            } else {
                sidebar.line(1, null);
            }
        });
    }

    public void tick() {
        String time = dtf.format(new Date());
        Component line = Component.text(time, NamedTextColor.GRAY);
        bars.values().forEach(b -> b.line(2, line));
    }


}
