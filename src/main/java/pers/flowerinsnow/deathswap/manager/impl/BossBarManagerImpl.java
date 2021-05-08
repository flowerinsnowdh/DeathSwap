package pers.flowerinsnow.deathswap.manager.impl;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pers.flowerinsnow.deathswap.manager.IBossBarManager;

import java.util.HashMap;
import java.util.Map;

public class BossBarManagerImpl implements IBossBarManager {
    private final Map<Player, BossBar> bossBarMap = new HashMap<>();
    @Override
    public void initPlayerBossBar(Player player) {
        BossBar bossBar = bossBarMap.get(player);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar("§r", BarColor.WHITE, BarStyle.SOLID);
            bossBar.addPlayer(player);
            bossBar.setVisible(true);
            bossBarMap.put(player, bossBar);
        } else if (!bossBar.isVisible()) {
            bossBar.setVisible(true);
        }
    }

    @Override
    public void setPlayerBossBarProgress(Player player, double value) {
        bossBarMap.get(player).setProgress(value);
    }

    @Override
    public void setPlayerBossBarColor(Player player, BarColor color) {
        bossBarMap.get(player).setColor(color);
    }

    @Override
    public void setPlayerBossBarVisible(Player player, boolean visible) {
        bossBarMap.get(player).setVisible(visible);
    }

    @Override
    public void setPlayerBossBarTitle(Player player, String title) {
        bossBarMap.get(player).setTitle(title);
    }

    @Override
    public BossBar getPlayerBossBar(Player player) {
        return bossBarMap.get(player);
    }
}
