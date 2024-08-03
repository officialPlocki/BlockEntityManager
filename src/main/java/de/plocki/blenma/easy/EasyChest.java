package de.plocki.blenma.easy;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class EasyChest {

    public Inventory getLiveChestInventory(Block block) {
        return ((Chest)block).getBlockInventory();
    }

    public boolean isBlocked(Block block) {
        return ((Chest)block).isBlocked();
    }

    /***
     *
     * @param inventory inventory of a chest or player
     * @param searchFor must be amount 1
     * @param searchMaterial set to true if only the material should be searched
     * @return data at which slot is which item that corresponded to the filter
     */
    public HashMap<Integer, ItemStack> scanInventoryContent(Inventory inventory, ItemStack searchFor, boolean searchMaterial){
        HashMap<Integer, ItemStack> map = new HashMap<>();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (!(itemStack == null)) {
                if (searchMaterial) {
                    if (itemStack.getType() == searchFor.getType()) {
                        map.put(i, itemStack);
                    }
                } else {
                    ItemStack clone = itemStack.clone();
                    clone.setAmount(1);
                    if (clone == searchFor) {
                        map.put(i, itemStack);
                    }
                }
            }
        }

        return map;
    }

}
