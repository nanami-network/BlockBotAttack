package xyz.n7mn.dev.blockbotattack;

import io.github.waterfallmc.waterfall.event.ProxyExceptionEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ClientConnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class EventListener implements Listener {

    private Map<String, Integer> list;
    private Map<String, Date> blockList;

    private Plugin plugin;

    public EventListener(Map<String, Integer> list, Map<String, Date> blockList, Plugin plugin){
        this.list = list;
        this.blockList = blockList;
        this.plugin = plugin;
    }

    @EventHandler
    public void PreLoginEvent (PreLoginEvent e){
        String address = e.getConnection().getSocketAddress().toString();
        System.out.println(address);

        if (blockList.get(address) != null){
            e.setCancelled(true);
            return;
        }

        //System.out.println(address);
        int count = -1;
        Integer integer = list.get(address);
        if (integer != null){
            //System.out.println("te1");
            count = integer;
            list.replace(address, integer + 1);
        } else {
            //System.out.println("te2");
            list.put(address, 1);
        }

        //System.out.println(count);
        if (count < 5){
            return;
        }

        blockList.put(address, new Date());

        e.setCancelled(true);
        plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatColor.YELLOW + "[BlockBotAttack] "+address+"からの攻撃をブロックしました。"));
        Collection<ProxiedPlayer> players = plugin.getProxy().getPlayers();
        new Thread(()->{
            for (ProxiedPlayer player : players){
                player.sendMessage(new TextComponent(ChatColor.YELLOW + "[BlockBotAttack] 攻撃を検知しました。 しばらく接続が不安定になる可能性があります。"));
            }
        }).start();

    }
}
