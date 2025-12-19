package me.carlos.flynoclip;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class ComHand implements CommandExecutor {

    Map<String, String> gabarito = new HashMap<>(Map.of(
            "Pedra","Papel",
            "Papel","Tesoura",
            "Tesoura","Pedra"
    ));

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("jogar")){
            if (args.length == 0){
                sender.sendMessage("use dioreito porra");
                return true;
            }
            if (gabarito.containsKey(args[0])){
                sender.sendMessage(gabarito.get(args[0]) + ". ganhei porra se fuder");
                return true;
            }
            else{
                sender.sendMessage("Mas assim tambem qualquer um né");
                return true;
            }
        }
        return false;
    }

}
