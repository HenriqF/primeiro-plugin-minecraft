package me.carlos.flynoclip;
import org.bukkit.plugin.java.JavaPlugin;

public final class Flynoclip extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ç");
        getCommand("jogar").setExecutor(new ComHand());
    }

    @Override
    public void onDisable() {
        getLogger().info("c (perdeu a perna)");
    }


}
