package net.vexmos.lobby.systems;

import net.vexmos.lobby.database.ConnectSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

public class JoinMessage implements Listener  {


    public final Random random;

    public JoinMessage(Random random) {

        this.random = random;
    }

    public String getRandomMessage() {
        int randomNumber = random.nextInt(100) + 1;  // Gera um número entre 1 e 100

        if (randomNumber <= 10) {
            return " §eentrou na §d§lFESTA§e!";
        } else if (randomNumber <= 30) {
            return " §echegou na parada!";
        } else if (randomNumber <= 55) {
            return " §ebrotou no pedaço!";
        } else if (randomNumber <= 85) {
            return " §eapareceu pra §c§lESTREMECER§e!";
        } else {
            return " §ecaiu de §b§lPARAQUEDAS§e!";
        }
    }

    @EventHandler
    public void onJoinMessagePremium(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ConnectSpigot db = new ConnectSpigot();

        // Verifica se o jogador tem a join message ativada
        if (db.hasJoinMessage(p.getName())) {
            // Instancia o seletor de mensagens

            // Exibe a mensagem
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(" §7" + p.getPlayerListName() + getRandomMessage());
            Bukkit.broadcastMessage(" ");
        }
    }


}
