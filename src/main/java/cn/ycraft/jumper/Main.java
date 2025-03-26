package cn.ycraft.jumper;

import cn.ycraft.jumper.command.JumpCommand;
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
        //注册计数器
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(plugin);
        } catch (NoPacketAdapterAvailableException e) {
            scoreboardLibrary = new NoopScoreboardLibrary();
            plugin.getLogger().warning("No scoreboard packet adapter available!");
        }
        //注册监听器
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        //注册指令
        getCommand("jump").setExecutor(new JumpCommand());
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
