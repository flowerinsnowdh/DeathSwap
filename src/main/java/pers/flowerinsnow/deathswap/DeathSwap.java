package pers.flowerinsnow.deathswap;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pers.flowerinsnow.deathswap.command.CommandDeathSwap;
import pers.flowerinsnow.deathswap.manager.IBossBarManager;
import pers.flowerinsnow.deathswap.manager.impl.BossBarManagerImpl;
import pers.flowerinsnow.deathswap.runnable.SwapRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DeathSwap extends JavaPlugin {
    private static DeathSwap instance;
    public static SwapRunnable timer;
    public static int maxTime = 300;
    private static IBossBarManager bossBarManager;

    @Override
    public void onEnable() {
        instance = this;
        bossBarManager = new BossBarManagerImpl();
        CommandDeathSwap ds = new CommandDeathSwap();
        PluginCommand command = getCommand("deathswap");
        if (command == null) {
            getLogger().severe("错误: 找不到命令 deathswap");
            setEnabled(false);
        } else {
            command.setExecutor(ds);
            command.setTabCompleter(ds);
        }
    }

    public static void swap() {
        ArrayList<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        if (playerList.size() < 2) {
            Bukkit.getOnlinePlayers().forEach(p -> {
                getBossBarManager().setPlayerBossBarTitle(p, DeathSwap.parseColor("&7[&c×&7]&b没有足够的玩家"));
                getBossBarManager().setPlayerBossBarColor(p, BarColor.RED);
            });
            return;
        }
        HashMap<Player, Location> map = new HashMap<>();
        playerList.forEach(p -> map.put(p, p.getLocation()));
        Bukkit.getOnlinePlayers().forEach(p -> {
            @SuppressWarnings("unchecked")
            ArrayList<Player> list = (ArrayList<Player>) playerList.clone();
            list.remove(p);
            Player target = list.get(new Random().nextInt(list.size()));
            playerList.remove(target);
            Location selfLocation = p.getLocation();
            Location targetLocation = map.get(target);
            targetLocation.setYaw(selfLocation.getYaw());
            targetLocation.setPitch(selfLocation.getPitch());
            p.teleport(targetLocation);
            getBossBarManager().setPlayerBossBarTitle(p, DeathSwap.parseColor("&7[&b!&7]&b您传送到了 &e" + target.getName() + " &b处"));
            getBossBarManager().setPlayerBossBarColor(p, BarColor.BLUE);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent());
        });
    }

    public static String parseColor(String s) {
        return s.replace("&", "§")
                .replace("§§", "&");
    }

    public static DeathSwap getInstance() {
        return instance;
    }

    public static IBossBarManager getBossBarManager() {
        return bossBarManager;
    }
}
