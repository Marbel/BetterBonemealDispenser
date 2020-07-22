package de.j_moebis;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

import de.j_moebis.Plants.IGrower;
import de.j_moebis.Plants.SugarCaneGrower;


public final class DispenserListener implements Listener {

    final BetterBonemealDispenser plugin;

    final IGrower sugarCaneWorker;

    DispenserListener(BetterBonemealDispenser plugin) {
        this.plugin = plugin;
        this.sugarCaneWorker = new SugarCaneGrower(plugin);
    }

    @EventHandler
    public void DispenserUseItem(BlockDispenseEvent dispenseEvent) {
        Material mat = dispenseEvent.getBlock().getBlockData().getMaterial();
        Material item = dispenseEvent.getItem().getType();

        if (mat != Material.DISPENSER && item != Material.BONE_MEAL) {
            return;
        }

        Block dispenserBlock = dispenseEvent.getBlock();
        if (!(dispenserBlock.getBlockData() instanceof Directional)) {
            return;
        }

        Directional directional = (Directional) dispenserBlock.getBlockData();
        BlockFace face = directional.getFacing();
        Location loc = dispenserBlock.getLocation().add(face.getDirection());
        Block targetBlock = loc.getBlock();
        switch (targetBlock.getBlockData().getMaterial()) {
            case SUGAR_CANE:
                if(plugin.getConfig().getBoolean("allowed-plants-grow.sugarcane.enabled")){
                    this.sugarCaneWorker.Grow(targetBlock, dispenserBlock);
                }
            default:
                return;
        }
    }

}