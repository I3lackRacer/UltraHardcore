import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MainEventHandler implements Listener {


    private final int APPLE_REG;
    private final int CARROT_REG;

    private static final String APPLE_REG_KEY = "apple_reg";
    private static final String CARROT_REG_KEY = "carrot_reg";

    public MainEventHandler(JavaPlugin ultraHardcore) {
        APPLE_REG = (int) ultraHardcore.getConfig().get(APPLE_REG_KEY);
        CARROT_REG = (int) ultraHardcore.getConfig().get(CARROT_REG_KEY);
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) e.setCancelled(true);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        Material type = e.getItem().getType();
        double playerHealth = e.getPlayer().getHealth();
        if (type.compareTo(Material.GOLDEN_APPLE) == 0)
            playerHealth += APPLE_REG;
        else if (type.compareTo(Material.GOLDEN_CARROT) == 0)
            playerHealth += CARROT_REG;
        else if (type.compareTo(Material.ENCHANTED_GOLDEN_APPLE) == 0)
            playerHealth += e.getPlayer().getHealthScale();
        else return;
        if (playerHealth > e.getPlayer().getHealthScale()) {
            playerHealth = e.getPlayer().getHealthScale();
        }
        e.getPlayer().setHealth(playerHealth);
    }
}
