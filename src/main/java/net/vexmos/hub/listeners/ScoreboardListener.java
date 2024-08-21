package net.vexmos.hub.listeners;

import net.vexmos.hub.VexmosHub;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScoreboardListener implements Listener {

    public ScoreboardListener() {
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
        scoreUpdate();
    }







    public static String getCurrentDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Define o formato da data
        return sdf.format(new Date()); // Retorna a data atual formatada como uma string
    }

    public static String replaceGroupNamesptBR(String input) {
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

    public static String replaceGroupNamesenUS(String input) {
        return input
                .replace("diretor", "&4&lDIRECTOR")
                .replace("admin", "&c&lADMIN")
                .replace("suporte", "&9&lSUPPORT")
                .replace("mod", "&2&LMODERATOR")
                .replace("emerald", "&a&lEMERALD")
                .replace("gold", "&6&LGOLD")
                .replace("membro", "&7MembER")
                .replace("diamond", "&b&lDIAMOND")
                .replace("construtor", "&e&lBUILDER");
    }


    public static String formatCrystals(int crystals) {
        if (crystals < 1000) {
            return String.valueOf(crystals);
        } else if (crystals < 100000) {
            return (crystals / 1000) + "," + (crystals % 1000 / 100) + "k";
        } else if (crystals < 1000000) {
            return (crystals / 1000) + "k";
        } else {
            return (crystals / 1000000) + "kk";
        }
    }



    public void scoreUpdate() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOnline()) {
                ConnectSpigot db = new ConnectSpigot();
                setupScore setupScore = new setupScore();

                Scoreboard scoreboard = setupScore.createScoreboard(player)
                        .setHandler(new ScoreboardHandler() {

                            private final ScrollableString scroll = new ScrollableString(Strings.format("VEXMOS"), 13, 0);
                            private final HighlightedString highlighted = new HighlightedString("www.vexmos.net", "&6", "&e");

                            @Override
                            public String getTitle(Player player) {
                                return "&8&l>§6§l " + scroll.next().replace("§r", "") + " &8&l<";
                            }

                            @Override
                            public List<Entry> getEntries(Player player) {
                                int cristais = db.getCristais(player.getName());
                                String group = db.getPlayerGroup(player.getName());
                                String servername = Bukkit.getServer().getName().replace("Lobby", "").trim();
                                String players = String.valueOf(Bukkit.getOnlinePlayers().size());
                                if (db.getPlayerLanguage(player.getName()).equals("pt_BR")) {
                                    return new EntryBuilder()

                                            .next("&8" + getCurrentDateString())
                                            .blank()
                                            .next(" &fGrupo: " + replaceGroupNamesptBR(group))
                                            .next(" &fCristais: &b" + formatCrystals(cristais))
                                            .blank()
                                            .next(" &fLobby: &e#1")
                                            .next(" &fJogadores: &b" + players)
                                            .blank()
                                            .next(highlighted.next())
                                            .build();
                                } else if (db.getPlayerLanguage(player.getName()).equals("en_US")) {

                                    return new EntryBuilder()

                                            .next("&8" + getCurrentDateString())
                                            .blank()
                                            .next(" &fGroup: " + replaceGroupNamesenUS(group))
                                            .next(" &fCrystals: &b" + formatCrystals(cristais))
                                            .blank()
                                            .next(" &fLobby: &e#1")
                                            .next(" &fPlayers: &b" + players)
                                            .blank()
                                            .next(highlighted.next())
                                            .build();
                                }

                                return java.util.Collections.emptyList();
                            }

                        })
                        .setAsync(true);

                scoreboard.activate();

            }
        }
    }


}
