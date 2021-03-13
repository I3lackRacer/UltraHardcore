import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MainEventHandler implements Listener {

    private static final String FOOD_LIST_KEY = "food";
    private final Map<String, Integer> foodList;

    public MainEventHandler(JavaPlugin ultraHardcore) {
        ConfigurationSection tmpFoodList = ultraHardcore.getConfig().getConfigurationSection(FOOD_LIST_KEY);
        assert tmpFoodList != null : "Failed load food from config";
        foodList = new HashMap<>();
        for (String foodName : tmpFoodList.getKeys(false)) {
            Integer healthRate = (int) tmpFoodList.get(foodName);
            foodList.put(foodName.toLowerCase(), healthRate);
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) e.setCancelled(true);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        String itemType = e.getItem().getType().name().toLowerCase();
        if (!foodList.containsKey(itemType)) return;
        double playerHealth = e.getPlayer().getHealth();
        playerHealth += foodList.get(itemType);
        if (playerHealth > e.getPlayer().getHealthScale()) {
            playerHealth = e.getPlayer().getHealthScale();
        }

        e.getPlayer().setHealth(playerHealth);
    }
}
