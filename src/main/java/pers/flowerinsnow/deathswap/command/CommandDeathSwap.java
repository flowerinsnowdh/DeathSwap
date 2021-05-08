package pers.flowerinsnow.deathswap.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import pers.flowerinsnow.deathswap.DeathSwap;
import pers.flowerinsnow.deathswap.runnable.SwapRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandDeathSwap implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "start":
                    if (sender.hasPermission("deathswap.start")) {
                        if (DeathSwap.timer != null) {
                            sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c计时已经开启"));
                            return true;
                        }
                        DeathSwap.timer = new SwapRunnable().start();
                        sender.sendMessage(DeathSwap.parseColor("&7[&b!&7]&b已开始计时"));
                    } else sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c您没有权限 &edeathswap.start"));
                    break;
                case "stop":
                    if (sender.hasPermission("deathswap.stop")) {
                        if (DeathSwap.timer != null) {
                            DeathSwap.timer.cancel();
                            DeathSwap.timer = null;
                            sender.sendMessage(DeathSwap.parseColor("&7[&b!&7]&b已停止计时"));
                        } else sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c计时没有开始"));
                    } else sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c您没有权限 &edeathswap.stop"));
                    break;
                default:
                    sendUsage(sender);
                    break;
            }
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "time":
                    if (sender.hasPermission("deathswap.time")) {
                        if (DeathSwap.timer != null) {
                            try {
                                DeathSwap.timer.next = Integer.parseInt(args[1]);
                                sender.sendMessage(DeathSwap.parseColor("&7[&b!&7]&b已成功设置时间"));
                            } catch (NumberFormatException e) {
                                sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c请输入一个正确的数字"));
                            }
                        } else sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c计时没有开始"));
                    } else sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c您没有权限 &edeathswap.time"));
                    break;
                case "maxtime":
                    if (sender.hasPermission("deathswap.maxtime")) {
                        try {
                            DeathSwap.maxTime = Integer.parseInt(args[1]);
                            sender.sendMessage(DeathSwap.parseColor("&7[&b!&7]&b已成功设置循环时间"));
                        } catch (NumberFormatException e) {
                            sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c请输入一个正确的数字"));
                        }
                    } else sender.sendMessage(DeathSwap.parseColor("&7[&c×&7]&c您没有权限 &edeathswap.maxtime"));
                    break;
                default:
                    sendUsage(sender);
            }
        } else {
            sendUsage(sender);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>(Arrays.asList("start", "stop", "time", "maxtime"));
            list.removeIf(s -> !s.toLowerCase().startsWith(args[0]));
            return list;
        } else return new ArrayList<>();
    }
    private void sendUsage(CommandSender sender) {
        sender.sendMessage(DeathSwap.parseColor("&7[&a?&7]&b用法: &e/deathswap(ds) start\n" +
                "&e/deathswap(ds) stop\n" +
                "&e/deathswap(ds) time <下一次时间>\n" +
                "&e/deathswap(ds) maxtime <每一次时间>"));
    }
}
