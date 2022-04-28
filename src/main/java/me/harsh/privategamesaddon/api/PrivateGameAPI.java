package me.harsh.privategamesaddon.api;

import de.marcely.bedwars.api.arena.Arena;
import lombok.experimental.UtilityClass;
import me.harsh.privategamesaddon.settings.Settings;
import me.harsh.privategamesaddon.utils.Utility;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class PrivateGameAPI {
    public Boolean hasPermision(@NotNull Player player){
        return player.hasPermission(Settings.GLOBAL_PERM) || player.hasPermission(Settings.CREATE_PERM) || player.hasPermission("*");
    }

    public Boolean isPrivateGame(@NotNull Arena arena){
        return Utility.getManager().privateArenas.contains(arena);
    }
}