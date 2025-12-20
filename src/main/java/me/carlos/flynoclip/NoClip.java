package me.carlos.flynoclip;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

public class NoClip implements Listener {
    private final UUID player;
    private final Set<Material> tra = new HashSet<>();

    public NoClip(UUID player) {
        this.player = player;

        for(Material mat : Material.values()) {
            if (!mat.isSolid()) {
                tra.add(mat);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (!e.getPlayer().getUniqueId().equals(player)) return;

        Player p = e.getPlayer();
        Block b = p.getTargetBlock(tra, 1);
        Block d = p.getLocation().subtract(0, 1, 0).getBlock();

        if (b.getType().isSolid() || (d.getType().isSolid() && p.isSneaking()) ) {
            p.setGameMode(GameMode.SPECTATOR);
        }
        else{
            p.setGameMode(GameMode.CREATIVE);
        }
    }
}
