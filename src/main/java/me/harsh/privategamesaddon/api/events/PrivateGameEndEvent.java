package me.harsh.privategamesaddon.api.events;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Collection;

public class PrivateGameEndEvent extends Event {
    private static final HandlerList list = new HandlerList();
    Arena arena;
    Collection<Player> winners;
    Team team;

    public PrivateGameEndEvent(Arena arena, Collection<Player> winners, Team team){
        this.arena = arena;
        this.team = team;
        this.winners = winners;
    }

    public Arena getArena() {
        return arena;
    }

    public Collection<Player> getWinners() {
        return winners;
    }

    public Team getWiningTeam() {
        return team;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}