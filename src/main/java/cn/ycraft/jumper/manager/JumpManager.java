package cn.ycraft.jumper.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JumpManager {
    private UUID uuid;
    private Integer jumpCount;
    private boolean state;
    private static List<JumpManager> jumpCommandInfos = new ArrayList<>();

    public JumpManager(UUID uuid){
        this.uuid = uuid;
        this.jumpCount = 0;
        this.state = true;
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public static void jumpCountIncrement(UUID uuid){
        searchJumpManagerByUuid(uuid).jumpCount++;
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
