package me.carlos.flynoclip;
import org.bukkit.plugin.java.JavaPlugin;

public final class Flynoclip extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("çGDFSAKNFLKASDFNASDLKF");

        ComHand handler = new ComHand(this);
        getCommand("jogar").setExecutor(handler);
        getCommand("fly").setExecutor(handler);
        getCommand("noclip").setExecutor(handler);
        getCommand("inventory").setExecutor(handler);
        getCommand("v").setExecutor(handler);


        getServer().getPluginManager().registerEvents(new Chat(), this);

    }

}
