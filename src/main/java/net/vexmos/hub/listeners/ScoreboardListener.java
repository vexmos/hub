package net.vexmos.hub.listeners;

import net.vexmos.hub.api.scoreboard.common.EntryBuilder;
import net.vexmos.hub.api.scoreboard.common.Strings;
import net.vexmos.hub.api.scoreboard.common.animate.HighlightedString;
import net.vexmos.hub.api.scoreboard.common.animate.ScrollableString;
import net.vexmos.hub.api.scoreboard.setupScore;
import net.vexmos.hub.api.scoreboard.type.Entry;
import net.vexmos.hub.api.scoreboard.type.Scoreboard;
import net.vexmos.hub.api.scoreboard.type.ScoreboardHandler;
import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScoreboardListener implements Listener {

    public ScoreboardListener() {
        activateScore();
        Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("VexmosHub"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Scoreboard scoreboard = setupScore.createScoreboard(p);
        scoreboard.deactivate();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        activateScore();
    }


    public static String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Define o formato da data
        return sdf.format(new Date()); // Retorna a data atual formatada como uma string
    }

    public static String replaceGroupNames(String input) {
        return input
                .replace("diretor", "&4&lDIRETOR")
                .replace("admin", "&c&lADMIN")
                .replace("suporte", "&9&lSUPORTE")
                .replace("mod", "&2&LMODERADOR")
                .replace("emerald", "&a&lEMERALD")
                .replace("gold", "&6&LGOLD")
                .replace("membro", "&7Membro")
                .replace("diamond", "&b&lDIAMOND")
                .replace("construtor", "&e&lCONSTRUTOR");
    }



    public void activateScore() {
        for (Player player: Bukkit.getOnlinePlayers()) {
            ConnectSpigot db = new ConnectSpigot();
            String cristais = String.valueOf(db.getCristais(player.getName()));
            String group = db.getPlayerGroup(player.getName());
            String servername = Bukkit.getServer().getName().replace("Lobby", "").trim();
            String players = String.valueOf(Bukkit.getOnlinePlayers().size());

            Scoreboard scoreboard = setupScore.createScoreboard(player)
                    .setHandler(new ScoreboardHandler() {

                        private final ScrollableString scroll = new ScrollableString(Strings.format("VEXMOS"), 13, 0);
                        private final HighlightedString highlighted = new HighlightedString("VEXMOS", "&6&l", "&e&l");

                        @Override
                        public String getTitle(Player player) {
                            return "&8&l>§6§l " + scroll.next().replace("§r", "") + " &8&l<";
                        }

                        @Override
                        public List<Entry> getEntries(Player player) {
                            return new EntryBuilder()
                                    .next("&8" + getCurrentDateString())
                                    .blank()
                                    .next(" &fGrupo: " + replaceGroupNames(group))
                                    .next(" &fCristais: &b" + cristais)
                                    .blank()
                                    .next(" &fLobby: &e#1")
                                    .next(" &fJogadores: &b" + players)
                                    .blank()
                                    .next("&6www.vexmos.net")
                                    .build();
                        }

                    })
                    .setUpdateInterval(11L); // Atualiza a cada segundo
            scoreboard.activate();
        }
    }
}
