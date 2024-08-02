package de.plocki.blenma.testing;

import de.plocki.blenma.easy.EasyBlocks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        sender.sendMessage(new EasyBlocks().getBlockInformation(Objects.requireNonNull(((Player) sender).getTargetBlockExact(10)).getLocation()).toString());

        return false;
    }
}
