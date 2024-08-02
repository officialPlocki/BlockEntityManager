package de.plocki.blenma;

import de.plocki.blenma.easy.entityai.StupidCow;
import de.plocki.blenma.testing.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BlockEntityManager extends JavaPlugin {

    @Override
    public void onEnable() {

        Objects.requireNonNull(this.getCommand("test-blenma")).setExecutor(new TestCommand());
        Bukkit.getPluginManager().registerEvents(new StupidCow(), this);

    }
}
