package me.carlos.flynoclip;

import java.awt.*;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;

public class Chat implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Component c = e.message();
        String message = PlainTextComponentSerializer.plainText().serialize(c);

        int i = message.indexOf("[item]");
        if (i >= 0){
            Player player = (Player)e.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();


            Component name;
            if (meta != null && meta.hasDisplayName()) {
                name = meta.displayName();
            }
            else if (meta != null && meta.hasItemName()){
                name = meta.itemName();
            }
            else{
                name = Component.translatable(item.getType().translationKey());
            }
            String strItemName = PlainTextComponentSerializer.plainText().serialize(name);

            Component before = Component.text(message.substring(0, i));

            Component middle = Component.text("[" + strItemName + "]", NamedTextColor.AQUA);

            if (item.getAmount() > 0 && item.getAmount() <= 99) {
                middle = middle.hoverEvent(item.asHoverEvent());
            }
            Component after = Component.text(message.substring(i+6), NamedTextColor.WHITE);


            e.message(before.append(middle.append(after)));
        }

    }
}
