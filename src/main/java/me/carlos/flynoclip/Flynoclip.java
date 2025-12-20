package me.carlos.flynoclip;
import org.bukkit.plugin.java.JavaPlugin;

public final class Flynoclip extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ç");
        getCommand("jogar").setExecutor(new ComHand(this));
        getCommand("fly").setExecutor(new ComHand(this));
        getCommand("noclip").setExecutor(new ComHand(this));
    }

}
