
package net.vexmos.hub.listeners;


import net.vexmos.hub.VexmosHub;
import net.vexmos.hub.api.LinkWithSystem;
import net.vexmos.hub.database.ConnectSpigot;
import net.vexmos.hub.listeners.gui.*;
import net.vexmos.hub.systems.JoinMessage;
import net.vexmos.hub.systems.PlayerVisible;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Listeners extends JavaPlugin implements Listener {

    public static void setup() {

        VexmosHub.get().getServer().getPluginManager().registerEvents(new ManagementListeners(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new PlayerVisible(new ConnectSpigot()), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new ServerSelectorGUI(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new ProfileGUI(new ConnectSpigot()), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new FlyListener(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new CollectablesGUI(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new LobbiesGUI(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new HatsGUI(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new AjustesGUI(), VexmosHub.get());
<<<<<<< HEAD
        VexmosHub.get().getServer().getPluginManager().registerEvents(new ItemFramesListener(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new PortalListener(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new PetsGUI(), VexmosHub.get());
        VexmosHub.get().getServer().getPluginManager().registerEvents(new LangGUI(), VexmosHub.get());
=======

>>>>>>> 8eb97429a1abe52224e9bc81017f46717a7589df
        Random random = new Random();  // Cria uma instância de Random
        VexmosHub.get().getServer().getPluginManager().registerEvents(new JoinMessage(random), VexmosHub.get());
        LinkWithSystem linkWithSystem = new LinkWithSystem();
        ConnectSpigot database = new ConnectSpigot();

        LobbyEnvironment lobbyEnvironment = new LobbyEnvironment(linkWithSystem, database);

        lobbyEnvironment.startGlobalScoreboardUpdate();
        VexmosHub.get().getServer().getPluginManager().registerEvents(new LobbyEnvironment(linkWithSystem, database), VexmosHub.get());


    }

}

