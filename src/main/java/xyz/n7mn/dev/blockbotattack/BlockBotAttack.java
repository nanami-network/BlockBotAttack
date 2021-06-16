package xyz.n7mn.dev.blockbotattack;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Time;
import java.util.*;

public final class BlockBotAttack extends Plugin {

    private Map<String, Integer> list = new HashMap<>();
    private Map<String, Date> blockList = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getProxy().getPluginManager().registerListener(this, new EventListener(list, blockList, this));

        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getProxy().getConsole().sendMessage(new TextComponent("[BlockBotAttack] 定期リセット"));
                blockList.clear();
            }
        }, 0L, 60000L);
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //getProxy().getConsole().sendMessage(new TextComponent("[BlockBotAttack] 定期リセット (B)"));
                list.clear();
            }
        }, 0L, 1000L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
