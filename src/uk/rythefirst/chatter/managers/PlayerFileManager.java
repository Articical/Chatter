package uk.rythefirst.chatter.managers;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerFileManager {

    private final JavaPlugin plugin;
    private final File baseFolder;

    public PlayerFileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.baseFolder = new File(plugin.getDataFolder(), "pdata");

        // Ensure /pdata folder exists on plugin startup
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }
    }

    private File getPlayerFile(UUID uuid) {
        return new File(baseFolder, uuid.toString() + ".yml");
    }

    /** Create a player's file asynchronously, optionally setting a default key */
    public void createFileAsync(UUID uuid, String defaultKey, String defaultValue) {
        CompletableFuture.runAsync(() -> {
            File file = getPlayerFile(uuid);

            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    boolean created = file.createNewFile();

                    if (created && defaultKey != null && defaultValue != null) {
                        YamlConfiguration config = new YamlConfiguration();
                        config.set(defaultKey, defaultValue);
                        config.save(file);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /** Load a player's file asynchronously, then return YamlConfiguration on the main thread */
    public void loadFileAsync(UUID uuid, Consumer<YamlConfiguration> callback) {
        File file = getPlayerFile(uuid);

        CompletableFuture.supplyAsync(() -> {
            if (!file.exists()) return null;
            return YamlConfiguration.loadConfiguration(file);
        }).thenAccept(config -> {
            Bukkit.getScheduler().runTask(plugin, () -> callback.accept(config));
        });
    }

    /** Save a YamlConfiguration asynchronously */
    public void saveFileAsync(UUID uuid, YamlConfiguration config) {
        CompletableFuture.runAsync(() -> {
            File file = getPlayerFile(uuid);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public boolean fileExists(UUID uuid) {
        return getPlayerFile(uuid).exists();
    }
}
