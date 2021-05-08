package pers.flowerinsnow.deathswap.manager;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public interface IBossBarManager {
    void initPlayerBossBar(Player player);
    void setPlayerBossBarProgress(Player player, double value);
    void setPlayerBossBarColor(Player player, BarColor color);
    void setPlayerBossBarVisible(Player player, boolean visible);
    void setPlayerBossBarTitle(Player player, String title);
    BossBar getPlayerBossBar(Player player);
}
