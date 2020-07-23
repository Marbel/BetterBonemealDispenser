# BetterBonemealDispenser

A Minecraft Bukkit/Spigot/Paper plugin to enhance dispenser for better bonemeal functionality.

Compatible from 1.13 to 1.16.

![](sugarcane.gif)

## How to use

Fill bone meal in a dispenser and place a sugar cane in front of it.  
Activate the dispenser via redstone and the sugar cane will grow if it has 1 Block free above it.  
Sugar cane will grow to a maximum length of 3 and stop to grow if a block other than air is in its way.

## Configuration

    ###########################
    # BetterBonemealDispenser #
    ###########################

    allowed-plants-grow:
        sugarcane:
            enabled: true
            # chance to proc 2 grow steps instead of 1
            # 1 = 100% , 0.5 = 50% , 0 = 0%
            proc-chance: 0

## Permission

    # User with this permission can reload the plugin configuration
    betterbonemealdispenser
