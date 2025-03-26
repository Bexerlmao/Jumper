package cn.ycraft.jumper.manager;

import cn.ycraft.jumper.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SidebarManager {
    private Player player;
    private static Sidebar sidebar;
    private static final List<SidebarManager> sidebarInfos = new ArrayList<>();
    private static final SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SidebarManager(Player player){
        this.player = player;
        sidebar = Main.getScoreboard().createSidebar();

        sidebar.line(0, Component.text(player.getName()));
        sidebar.line(2, () -> {
            var time = dtf.format(new Date());
            return Component.text(time, NamedTextColor.GRAY);
        });
    }



    public Player getPlayer() {
        return player;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public static SidebarManager searchSidebarByPlayer(Player player){
        SidebarManager result = null;

        for(SidebarManager sidebarInfo : sidebarInfos){
            if(sidebarInfo.getPlayer().getUniqueId() == player.getUniqueId()){
                result = sidebarInfo;
                break;
            }
        }

        if(result == null){
            result = new SidebarManager(player);
            sidebarInfos.add(result);
        }

        return result;
    }

    public static void createSidebarManagerIntoList(Player player){
        Sidebar bar = searchSidebarByPlayer(player).getSidebar();
        bar.addPlayer(player);
    }

    public static void removeSidebarManagerFromList(Player player){
        sidebarInfos.remove(searchSidebarByPlayer(player));
    }

    public static void jumpCountVisibility(Player player){
        JumpManager jumpManager = JumpManager.searchJumpManagerByUuid(player.getUniqueId());
        if(jumpManager.getState()){
            sidebar.line(1, () -> Component.text(JumpManager.searchJumpManagerByUuid(player.getUniqueId()).getJumpCount()));
        }else{
            sidebar.line(1,null);
        }
        jumpManager.setState(!jumpManager.getState());

    }

}
