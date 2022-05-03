package me.harsh.privategamesaddon.commands;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.Party;
import com.alessiodp.parties.api.interfaces.PartyPlayer;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.simonsator.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.party.PartyManager;
import de.simonsator.partyandfriends.api.party.PlayerParty;
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
        setPermission(null);
    }

    @Override
    protected void onCommand() {
        checkConsole();
        final Player p2 = getPlayer();
        if (!Utility.hasPermision(p2)){
            Common.tell(p2, Settings.PREFIX + Settings.NO_PERM_EROR);
            return;
        }
        final Arena arena = GameAPI.get().getArenaByPlayer(p2);
        if (arena == null) {
            Common.tell(p2, Settings.PREFIX + " " + Settings.NOT_IN_ARENA);
            return;

        }
        if (!Utility.getManager().getMode(p2)){
            Common.tell(p2, Settings.PREFIX + " " + Settings.NOT_IN_PRIVATE_GAME_MODE);
            return;
        }
        if (!Utility.getManager().privateArenas.contains(arena)){
            Common.tell(p2, Settings.PREFIX + " " + Settings.NOT_PRIVATE_ROOM_WARP);
            return;
        }
        if (Utility.isParty){
            final PartyPlayer partyPlayer = Parties.getApi().getPartyPlayer(p2.getUniqueId());
            if (partyPlayer.isInParty()){
                final Party party = Parties.getApi().getParty(partyPlayer.getPartyId());
                if (party == null) Common.tell(p2, Settings.PREFIX + "&c Party not found!");
                party.getMembers().forEach(uuid -> {
                    final Player p = Utility.getPlayerByUuid(uuid);
                    if (p == null) Common.log("Player is Null!");
                    if (p == p2) return;
                    Common.tell(p2, Settings.PREFIX + "&aWarping " + p.getName());
                    arena.addPlayer(p);
                });
                Bukkit.getServer().getPluginManager().callEvent(new PrivateGameWarpEvent(party.getMembers(), arena));
            }else {
                Common.tell(p2,Settings.PREFIX + " " + Settings.NOT_IN_PARTY);
            }
        }else if (Utility.isPfa){
            final OnlinePAFPlayer pafPlayer = PAFPlayerManager.getInstance().getPlayer(p2);
            if (pafPlayer.getParty() != null){
                final PlayerParty party = PartyManager.getInstance().getParty(pafPlayer);
                party.getPlayers().forEach(player -> {
                    Common.tell(p2, Settings.PREFIX + "&aWarping " + player.getName());
                    arena.addPlayer(player.getPlayer());
                });
            }

        }

    }
}
