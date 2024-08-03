package de.plocki.blenma.easy;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EasyFurnace {

    public Inventory getFurnaceInventory(Block block) {
        BlockState state = block.getState();
        if (state instanceof Furnace ) {
            return ((Furnace) state).getInventory();
        }
        return null;
    }

    public void setFurnaceInventory(Block furnaceBlock, ItemStack smeltingItem, ItemStack fuelItem, ItemStack resultItem) {
        Inventory inventory = getFurnaceInventory(furnaceBlock);
        if (inventory != null) {
            inventory.setItem(0, smeltingItem);
            inventory.setItem(1, fuelItem);
            inventory.setItem(2, resultItem);
        }
    }

    public void startSmelting(Block block) {
        Furnace furnace = (Furnace) block;
        Inventory inventory = furnace.getInventory();
        ItemStack smelting = inventory.getItem(0);
        ItemStack fuel = inventory.getItem(1);
        if (smelting != null && fuel != null && furnace.getCookTimeTotal() > 0) {
            furnace.setBurnTime((short) 200); //ticks
            furnace.setCookTime((short) 0);
            furnace.update();
        }
    }

}
