package net.vexmos.hub.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class LinkWithSystem {


    public void teleportToLobby(Player player) {
        Plugin lunikSystem = Bukkit.getPluginManager().getPlugin("VexmosNET");

        if (lunikSystem != null && lunikSystem.isEnabled()) {
            File positionsFile = new File(lunikSystem.getDataFolder(), "positions.yml");

            if (positionsFile.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(positionsFile);

                String worldName = config.getString("lobby.world");
                double x = config.getDouble("lobby.x");
                double y = config.getDouble("lobby.y");
                double z = config.getDouble("lobby.z");
                float yaw = (float) config.getDouble("lobby.yaw");
                float pitch = (float) config.getDouble("lobby.pitch");

                World world = Bukkit.getWorld(worldName);
                if (world != null) {
                    Location lobbyLocation = new Location(world, x, y, z, yaw, pitch);
                    player.teleport(lobbyLocation);
                } else {
                    player.sendMessage("World " + worldName + " is not loaded!");
                }
            } else {
                player.sendMessage("positions.yml not found in VexmosNET!");
            }
        } else {
            player.sendMessage("VexmosNET plugin is not available!");
        }
    }

    public void checkDistanceToLobby(Player player) {
        Plugin lunikSystem = Bukkit.getPluginManager().getPlugin("VexmosNET");

        if (lunikSystem != null && lunikSystem.isEnabled()) {
            File positionsFile = new File(lunikSystem.getDataFolder(), "positions.yml");

            if (positionsFile.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(positionsFile);

                String worldName = config.getString("lobby.world");
                double lobbyX = config.getDouble("lobby.x");
                double lobbyY = config.getDouble("lobby.y");
                double lobbyZ = config.getDouble("lobby.z");

                World world = Bukkit.getWorld(worldName);

                if (world != null) {
                    Location playerLocation = player.getLocation();
                    Location lobbyLocation = new Location(world, lobbyX, lobbyY, lobbyZ);

                    double distance = playerLocation.distance(lobbyLocation);

                    if (distance >= 120.0) { // adjust the distance as needed
                        teleportToLobby(player);
                    }
                }
            } else {
                player.teleport(player.getWorld().getSpawnLocation());
            }
        }
    }
}

