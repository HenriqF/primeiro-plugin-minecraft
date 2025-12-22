package me.carlos.flynoclip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.util.RayTraceResult;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.HandlerList;

import java.util.*;

public class ComHand implements CommandExecutor {

    private final JavaPlugin plugin;
    public ComHand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final Map<String, String> gabarito = new HashMap<>(Map.of(
            "Pedra","Papel",
            "Papel","Tesoura",
            "Tesoura","Pedra"
    ));
    private final Map<UUID, Boolean> noclip = new HashMap<>();
    private final Map<UUID, NoClip> nctasks = new HashMap<>();
    private final Map<UUID, BukkitTask> gods = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equals("jogar")){
            if (args.length == 0){
                sender.sendMessage("use dioreito porra");
                return true;
            }
            if (gabarito.containsKey(args[0])){
                sender.sendMessage(gabarito.get(args[0]) + ". ganhei porra se fuder");
                return true;
            }
            else{
                sender.sendMessage("Mas assim tambem qualquer um né");
                return true;
            }
        }

        if (cmd.getName().equals("fly")){
            Player player = (Player)sender;

            if (!player.isOp()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cVOCE NAO PODE!"));
                return true;
            }
            else{
                player.setAllowFlight(!player.getAllowFlight());
                if(player.getAllowFlight()){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Voe voe"));
                }
                else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cCaia caia"));
                }
                return true;
            }
        }

        if (cmd.getName().equals("noclip")){
            Player player = (Player)sender;
            UUID uuid = player.getUniqueId();
            if (!player.isOp()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cVOCE NAO PODE!"));
                return true;
            }

            if (!noclip.containsKey(uuid)) noclip.put(uuid, true);
            else noclip.put(uuid, !noclip.get(uuid));


            if (noclip.get(uuid)){
                NoClip nclist = new NoClip(uuid);
                Bukkit.getPluginManager().registerEvents(nclist , plugin);

                nctasks.put(uuid, nclist);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2noclip on"));
                return true;
            }
            else{
                NoClip nc = nctasks.remove(uuid);
                if (nc != null) {
                    HandlerList.unregisterAll(nc);
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cnoclip off"));
                return true;
            }
        }

        if (cmd.getName().equals("inventory")){
            Player player = (Player)sender;

            if (!player.isOp()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cVOCE NAO PODE!"));
                return true;
            }
            if (args.length == 0){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cvamos usar direito ne tambem..."));
                return true;
            }
            Player analisando = plugin.getServer().getPlayer(args[0]);
            if (analisando == null){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cpequeno coagulo nao exziste,.."));
                return true;
            }

            Inventory inv = Bukkit.createInventory(null, 45, "Inventario de " + args[0]);

            for (int i = 0; i < 36; i++){
                if (analisando.getInventory().getItem(i) != null) {
                    inv.setItem(i+9, analisando.getInventory().getItem(i));
                }
            }
            inv.setItem(0, analisando.getInventory().getHelmet());
            inv.setItem(1, analisando.getInventory().getChestplate());
            inv.setItem(2, analisando.getInventory().getLeggings());
            inv.setItem(3, analisando.getInventory().getBoots());
            inv.setItem(4, analisando.getInventory().getItemInOffHand());

            player.openInventory(inv);
            return true;
        }

        if (cmd.getName().equals("god")){
            Player player = (Player)sender;

            if (!player.isOp()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cVOCE NAO PODE!"));
                return true;
            }
            if (gods.containsKey(player.getUniqueId())){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cVOCE NAO MAIS É UMA DEIDADE"));
                gods.get(player.getUniqueId()).cancel();

                gods.remove(player.getUniqueId());
                return true;
            }

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2VOCE E GOD"));

            BukkitTask newgod = new BukkitRunnable() {
                @Override
                public void run() {
                    RayTraceResult r = player.rayTraceEntities(100);

                    if (r != null && r.getHitEntity() != null) {
                        Entity entity = r.getHitEntity();
                        World world = entity.getWorld();
                        world.createExplosion(entity.getLocation(), 20);

                    }
                }
            }.runTaskTimer(plugin, 0L, 1L);
            gods.put(player.getUniqueId(), newgod);

            return true;
        }

        return false;
    }

}

