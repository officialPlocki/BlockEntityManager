package de.plocki.blenma.easy;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

public class EasyChest {

    public Inventory getLiveChestInventory(Block block) {
        return ((Chest)block).getBlockInventory();
    }

    public boolean isBlocked(Block block) {
        return ((Chest)block).isBlocked();
    }

}
