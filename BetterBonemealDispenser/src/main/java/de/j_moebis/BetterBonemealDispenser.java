package de.j_moebis;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterBonemealDispenser extends JavaPlugin {

    private DispenserListener dispenserListener;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Metrics metrics = new Metrics(this, 8207);
        dispenserListener = new DispenserListener(this);
        getServer().getPluginManager().registerEvents(dispenserListener, this);
        getCommand("betterbonemealdispenser").setExecutor(new CommandListener(this));
        getCommand("betterbonemealdispenser").setTabCompleter(new TabCompletion());
    }

    @Override
    public void onDisable() {
        dispenserListener = null;
    }
}
