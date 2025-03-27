package cn.ycraft.jumper.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class JumpManager {
    private UUID uuid;
    private Integer jumpCount;
    private static Map<Boolean, List<Player>> states = Map.of(true, new  ArrayList<Player>(), false, new ArrayList<Player>());
    private static List<JumpManager> jumpCommandInfos = new ArrayList<>();

    public JumpManager(UUID uuid){
        this.uuid = uuid;
        this.jumpCount = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Integer getJumpCount() {
        return jumpCount;
    }

    public List<JumpManager> getJumpCommandInfos() {
        return jumpCommandInfos;
    }

    public Boolean getState(Player player) {
        AtomicReference<Boolean> state = new AtomicReference<>();
        states.forEach((k, v) -> {
            for(Player p : v){
                if(p.getUniqueId() == player.getUniqueId()){
                    state.set(k);
                }
            }
        });
        if(state.get() == null){
            states.get(true).add(player);
            state.set(true);
        }
        return state.get();
    }

    public void setReverseState(Player player) {
        states.forEach((k, v) -> {
            for (Player p : v){
                if(p.getUniqueId() == player.getUniqueId()){
                    states.get(k).remove(p);
                    states.get(!k).add(player);
                }
            }
        });
    }

    public static void jumpCountIncrement(Player player){
        searchJumpManagerByUuid(player.getUniqueId()).jumpCount++;
    }

    public static JumpManager searchJumpManagerByUuid(UUID uuid){
        JumpManager result = null;

        for(JumpManager jumpCommandInfo : jumpCommandInfos){
            if(jumpCommandInfo.getUuid() == uuid){
                result = jumpCommandInfo;
                break;
            }
        }

        if(result == null){
            result = new JumpManager(uuid);
            jumpCommandInfos.add(result);
        }

        return result;
    }

    public static void createJumpManagerIntoList(UUID uuid){
        jumpCommandInfos.add(new JumpManager(uuid));
    }

    public void removeJumpManagerFromList(UUID uuid){
        jumpCommandInfos.remove(searchJumpManagerByUuid(uuid));
    }

}
