package cn.ycraft.jumper;

import cn.ycraft.jumper.listener.ScoreboardListener;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    protected static Main plugin;
    protected ScoreboardLibrary scoreboardLibrary;

    @Override

    public void onEnable() {
        Main.plugin = this;

        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(plugin);
        } catch (NoPacketAdapterAvailableException e) {
            // If no packet adapter was found, you can fallback to the no-op implementation:
            scoreboardLibrary = new NoopScoreboardLibrary();
            plugin.getLogger().warning("No scoreboard packet adapter available!");
        }

        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
    }

    @Override
    public void onDisable() {
        scoreboardLibrary.close();
    }

    public static Main getInstance(){
        return plugin;
    }

    public static ScoreboardLibrary getScoreboard(){
        return getInstance().scoreboardLibrary;
    }


}
