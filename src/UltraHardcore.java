import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UltraHardcore extends JavaPlugin {

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new MainEventHandler(this), this);
    }

}
