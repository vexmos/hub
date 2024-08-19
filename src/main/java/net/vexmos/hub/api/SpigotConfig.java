package net.vexmos.hub.api;

import net.vexmos.hub.VexmosHub;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SpigotConfig {

    private File file;

    private FileConfiguration config;


    public SpigotConfig(String name) {
        file = new File(VexmosHub.get().getDataFolder(), name);
        reloadConfig();
    }

    public FileConfiguration getConfig() {

        return config;
    }

    public File getFile() {

        return file;
    }

    public void reloadConfig() {

        config = YamlConfiguration.loadConfiguration(file);
        InputStream imputStream = VexmosHub.get().getResource(file.getName());
        if (imputStream != null) {
            YamlConfiguration imputConfig =
                    YamlConfiguration.loadConfiguration(imputStream);
            getConfig().setDefaults(imputConfig);
        }
    }

    public void saveConfig() {

        try {
            getConfig().save(file);
        } catch (IOException ex) {
        }
    }

    public void saveDefault() {

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void saveDefaultConfig() {
        if (!file.exists()) {
            VexmosHub.get().saveResource(file.getName(), true);
        }
    }

}