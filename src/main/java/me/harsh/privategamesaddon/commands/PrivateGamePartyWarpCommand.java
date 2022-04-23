package me.harsh.privategamesaddon.commands;

import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import me.harsh.privategamesaddon.api.events.PrivateGameWarpEvent;
import me.harsh.privategamesaddon.settings.Settings;
import me.harsh.privategamesaddon.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.command.SimpleSubCommand;


public class PrivateGamePartyWarpCommand extends SimpleSubCommand {

    protected PrivateGamePartyWarpCommand(SimpleCommandGroup parent) {
        super(parent, "warp");
    }

    @Override
    protected void onCommand() {
        checkConsole();
        final Player p2 = getPlayer();
        if (!Utility.hasPermision(p2)){
            Common.tell(p2, Settings.PREFIX + " You Don't have permission to create private games!");
            return;
        }
        final Arena arena = GameAPI.get().getArenaByPlayer(p2);
        if (arena == null) {
            Common.tell(p2, Settings.PREFIX + " &cYou're not in an arena!");
            return;

        }
        if (!Utility.getManager().getMode(p2)){
            Common.tell(p2, Settings.PREFIX + " &cYou're not in private game creation mode!");
            return;
        }
        if (!Utility.getManager().privateArenas.contains(arena)){
            Common.tell(p2, Settings.PREFIX + "&cYou cannot warp players in a non-private game room!");
            return;
        }
        final PartyPlayer partyPlayer = Utility.getPlayer(p2);
        if (partyPlayer.isInParty()){
            final Party party = Utility.getParty(p2);
            if (party == null) Common.log("Party is null");
            party.getMembers().forEach(uuid -> {
                final Player p = Utility.getPlayerByUuid(uuid);
                if (p == null) Common.log("Player is Null!");
                if (p == p2) return;
                Common.tell(p2, Settings.PREFIX + "&aWarping " + p.getName());
                arena.addPlayer(p);
            });
            Bukkit.getServer().getPluginManager().callEvent(new PrivateGameWarpEvent(party.getMembers(), arena));
        }else {
            Common.tell(p2,Settings.PREFIX + "&cSorry you're not in a party to warp players");
        }

    }
}
