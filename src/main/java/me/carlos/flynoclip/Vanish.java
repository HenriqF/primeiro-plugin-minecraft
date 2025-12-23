package me.carlos.flynoclip;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Vanish implements Listener {
    private final Set<UUID> vanished = new HashSet<>();
    private final JavaPlugin plugin;


    public Vanish(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (vanished.contains(p.getUniqueId())) {
            on(p);
            return;
        }
        if (p.isOp()){
            return;
        }
        for (UUID uuid : vanished) {
            if (Bukkit.getPlayer(uuid) != null) {
                p.hidePlayer(plugin, Bukkit.getPlayer(uuid));
            }
        }
    }

    private void on(Player player){
        for(Player o : plugin.getServer().getOnlinePlayers()){
            if (!o.isOp()) {
                o.hidePlayer(plugin, player);
            }
        }
    }
    private void off(Player player){
        for(Player o : plugin.getServer().getOnlinePlayers()){
            o.showPlayer(plugin, player);
        }
    }

    public boolean vanish(Player player) {
        UUID uuid = player.getUniqueId();
        if (vanished.contains(uuid)) {
            off(player);
            vanished.remove(uuid);
            return false;
        }
        on(player);
        vanished.add(uuid);
        return true;
    }
}
