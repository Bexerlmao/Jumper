package cn.ycraft.jumper;

import cn.ycraft.jumper.command.JumpCommand;
import cn.ycraft.jumper.listener.ScoreboardListener;
import cn.ycraft.jumper.manager.JumpManager;
import cn.ycraft.jumper.manager.SidebarManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends JavaPlugin {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    protected static Main plugin;

    protected JumpManager jumpManager;
    protected SidebarManager sidebarManager;

    @Override
    public void onEnable() {
        Main.plugin = this;

        this.jumpManager = new JumpManager();
        this.sidebarManager = new SidebarManager(this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, sidebarManager::tick, 0, 1);
        //注册监听器
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        //注册指令
        getCommand("jump").setExecutor(new JumpCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        this.sidebarManager.shutdown();
    }

    public static Main getInstance() {
        return plugin;
    }


    public static JumpManager getJumpManager() {
        return getInstance().jumpManager;
    }


    public static SidebarManager getSidebarManager() {
        return getInstance().sidebarManager;
    }

}
