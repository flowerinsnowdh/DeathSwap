package pers.flowerinsnow.deathswap.runnable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.scheduler.BukkitRunnable;
import pers.flowerinsnow.deathswap.DeathSwap;

public class SwapRunnable extends BukkitRunnable {
    public int next = DeathSwap.maxTime;

    @Override
    public void run() {
        next--;
        handleNow();
    }

    private void handleNow() {
        if (next < DeathSwap.maxTime) {
            if (next > 120) {
                sendByColor(ChatColor.GREEN, BarColor.GREEN);
            } else if (next > 60) {
                sendByColor(ChatColor.YELLOW, BarColor.YELLOW);
            } else if (next > 10) {
                sendByColor(ChatColor.GOLD, BarColor.YELLOW);
            } else if (next < 0) {
                DeathSwap.swap();
                next = DeathSwap.maxTime + 3;
            } else if (next % 2 == 0) {
                sendByColor(ChatColor.RED, BarColor.RED);
            } else {
                sendByColor(ChatColor.DARK_RED, BarColor.RED);
            }
        }
    }

    private void sendByColor(ChatColor color, BarColor bc) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            DeathSwap.getBossBarManager().initPlayerBossBar(p);
            DeathSwap.getBossBarManager().setPlayerBossBarColor(p, bc);
            DeathSwap.getBossBarManager().setPlayerBossBarTitle(p, color + "将在 " + next + "s 后进行交换");
            DeathSwap.getBossBarManager().setPlayerBossBarProgress(p, (double) next / (double) DeathSwap.maxTime);
        });
    }

    public SwapRunnable start() {
        runTaskTimer(DeathSwap.getInstance(), 0L, 20L);
        return this;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        Bukkit.getOnlinePlayers().forEach(p -> DeathSwap.getBossBarManager().setPlayerBossBarVisible(p, false));
    }
}
