package net.vexmos.hub.listeners.gui;

import net.vexmos.hub.VexmosHub;
import net.vexmos.hub.api.CustomHeads;
import net.vexmos.hub.api.GuiAPI;
import net.vexmos.hub.api.ItemAPI;
import net.vexmos.hub.database.ConnectSpigot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PetsGUI implements Listener {

    private final HashMap<String, Entity> playerPets = new HashMap<>();

    private GuiAPI gui;
    private Player player;

    // gui

    public void openPetsMenu(Player player) {
        this.player = player;
        this.gui = new GuiAPI("Seus Pets", 5); // 3 rows
        gui.clearInventorySlots(gui.getInventory());
        ItemAPI cachorro = new ItemAPI(Material.BONE).setName("§eCachorro").addLore("\n§eO melhor amigo do homem...\n\n§8- §bRaridade: §7Comum\n\n§eClique para interagir.");
        ItemAPI jaguatirica = new ItemAPI(Material.LEASH).setName("§eJaguatirica").addLore("\n§e'Pra mim é uma onça'...\n\n§8- §bRaridade: §7Comum\n\n§eClique para interagir.");
        ItemAPI bonecodeneve = new ItemAPI(Material.SNOW_BALL).setName("§eBoneco de Neve").addLore("\n§eVocê quer brincar na neve?\n\n§8- §bRaridade: §7Comum\n\n§eClique para interagir.");
        ItemAPI irongolem = new ItemAPI(Material.IRON_INGOT).setName("§eIron Golem").addLore("\n§eBoladão com a vida...\n\n§8- §bRaridade: §7Comum\n\n§eClique para interagir.");


        // Adicionar opção de adquirir cristais
        String top = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTVmZDY3ZDU2ZmZjNTNmYjM2MGExNzg3OWQ5YjUzMzhkNzMzMmQ4ZjEyOTQ5MWE1ZTE3ZThkNmU4YWVhNmMzYSJ9fX0=";
        List<String> loreTop = Arrays.asList(" §8- §aClique AQUI para adquirir cristais", " §8- §aVocê pode adquirir diversas coisas com isso, hein...", "", "", "§bloja.vexmos.net");
        ItemStack customHead = CustomHeads.create(top);
        ItemStack item = customHead;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§LⓋ §6Cristais");
        meta.setLore(loreTop);
        item.setItemMeta(meta);
        gui.getInventory().setItem(4, item);

        String avancarText2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWM5YzY3YTlmMTY4NWNkMWRhNDNlODQxZmU3ZWJiMTdmNmFmNmVhMTJhN2UxZjI3MjJmNWU3ZjA4OThkYjlmMyJ9fX0=";

        // Create the custom head ItemStack
        ItemStack cabecaAvancar2 = CustomHeads.create(avancarText2);
        ItemStack itemAvancar2 = cabecaAvancar2;
        ItemMeta avancar2 = itemAvancar2.getItemMeta();
        avancar2.setDisplayName("§eAvançar §8[1/1]");

        avancar2.setLore(Arrays.asList("§aClique para avançar de página."));

        itemAvancar2.setItemMeta(avancar2);

        String backText = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWExZWYzOThhMTdmMWFmNzQ3NzAxNDUxN2Y3ZjE0MWQ4ODZkZjQxYTMyYzczOGNjOGE4M2ZiNTAyOTdiZDkyMSJ9fX0=";
        // Create the custom head ItemStack
        ItemStack cabecaVoltar = CustomHeads.create(backText);
        ItemStack itemVoltar = cabecaVoltar;
        ItemMeta voltar = itemVoltar.getItemMeta();
        voltar.setDisplayName("§eVoltar §8[2/2]");

        voltar.setLore(Arrays.asList("§aClique para voltar de página."));

        itemVoltar.setItemMeta(voltar);
        gui.getInventory().setItem(0, itemVoltar);

        gui.setItem(20, cachorro);
        gui.setItem(21, irongolem);
        gui.setItem(22, bonecodeneve);
        gui.setItem(23, jaguatirica);

        gui.getInventory().setItem(8, itemAvancar2);

        gui.openInventory(player);
    }

    @EventHandler
    public void pets(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String displayName = clickedItem.getItemMeta().getDisplayName();

        if (displayName.equals("§eCachorro")) {
            event.setCancelled(true);
            player.closeInventory();
            givePet(player, "cachorro");
        }
        if (displayName.equals("§eBoneco de Neve")) {
            event.setCancelled(true);
            player.closeInventory();
            givePet(player, "bonecodeneve");
        }
        if (displayName.equals("§eIron Golem")) {
            event.setCancelled(true);
            player.closeInventory();
            givePet(player, "irongolem");
        }
        if (displayName.equals("§eJaguatirica")) {
            event.setCancelled(true);
            player.closeInventory();
            givePet(player, "jaguatirica");
        }
    }

    @EventHandler
    public void snow(EntityBlockFormEvent e)
    {
        if(e.getEntity() instanceof Snowman) {

            e.setCancelled(true);
        }
    }














    // create pets function

    public void createPet(Player player, EntityType petType) {
        if (playerPets.containsKey(player.getName())) {
            player.sendMessage(ChatColor.RED + "Você já tem um pet ativo!");
            return;
        }

        Entity pet = player.getWorld().spawnEntity(player.getLocation(), petType);
        pet.setCustomName("§cApagar pet");
        pet.setCustomNameVisible(true);


        playerPets.put(player.getName(), pet);
        startFollowingTask(player, pet);
    }

    public void startFollowingTask(Player player, Entity pet) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!pet.isValid() || !player.isOnline()) {
                    this.cancel(); // Cancela a tarefa se o pet for removido ou o jogador sair
                    return;
                }

                double distance = player.getLocation().distance(pet.getLocation());

                if (distance > 10) {  // Se a distância for maior que 10 blocos
                    pet.teleport(player.getLocation());  // Teleporta o pet para o jogador
                } else if (distance > 2) {  // Se a distância for maior que 2 blocos mas menor que 10
                    pet.setVelocity(player.getLocation().toVector().subtract(pet.getLocation().toVector()).normalize().multiply(0.3));
                }
            }
        }.runTaskTimer(VexmosHub.get(), 0L, 20L); // Executa a cada segundo (20 ticks)
    }

    @EventHandler
    public void onPetDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.getCustomName() != null && entity.getCustomName().equals("§cApagar pet")) {
                event.setCancelled(true); // Cancela o dano ao pet
            }
        }
    }

    // Evento para remover o pet ao clicar nele
    @EventHandler
    public void onPetClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity pet = event.getRightClicked();

        if (pet.getCustomName() != null && pet.getCustomName().equals("§cApagar pet")
                && playerPets.get(player.getName()) == pet) {
            pet.remove();
            playerPets.remove(player.getName());
            player.sendMessage(ChatColor.GREEN + "Seu pet foi removido.");
        }
    }

    // Evento para remover o pet ao sair do jogo
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Entity pet = playerPets.get(player.getName());

        if (pet != null) {
            pet.remove();
            playerPets.remove(player.getName());
        }
    }

    // Função para criar pets diferentes
    public void givePet(Player player, String petType) {
        switch (petType.toLowerCase()) {
            case "cachorro":
                createPet(player, EntityType.WOLF);
                break;
            case "jaguatirica":  // Use "jaguatirica" como string, mas o tipo é "OCELOT"
                createPet(player, EntityType.OCELOT);
                break;
            case "irongolem":
                createPet(player, EntityType.IRON_GOLEM);
                break;
            case "bonecodeneve":
                createPet(player, EntityType.SNOWMAN);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Tipo de pet desconhecido!");
                break;
        }
    }

    public void removePet(Player player) {
        Entity pet = playerPets.get(player.getUniqueId());
        if (pet != null) {
            pet.remove();
            playerPets.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Seu pet foi removido.");
        }
    }



}
