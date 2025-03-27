package cn.ycraft.jumper.manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class JumpManager {

    protected final Map<UUID, Integer> jumps = new HashMap<>();
    protected final Set<UUID> enabled = new HashSet<>();

    public int get(@NotNull Player player) {
        return jumps.getOrDefault(player.getUniqueId(), 0);
    }

    public int add(@NotNull Player player, int amount) {
        int after = get(player) + amount;
        jumps.put(player.getUniqueId(), amount);
        return after;
    }

    public boolean isEnabled(@NotNull Player player) {
        return enabled.contains(player.getUniqueId());
    }

    public void setEnabled(@NotNull Player player, boolean state) {
        if (state) enabled.add(player.getUniqueId());
        else enabled.remove(player.getUniqueId());
    }


}
