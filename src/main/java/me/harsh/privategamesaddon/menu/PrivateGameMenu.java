package me.harsh.privategamesaddon.menu;
import me.harsh.privategamesaddon.buffs.ArenaBuff;
import me.harsh.privategamesaddon.menu.subBuffMenu.HealthBuffMenu;
import me.harsh.privategamesaddon.menu.subBuffMenu.RespawnBuffMenu;
import me.harsh.privategamesaddon.menu.subBuffMenu.SpawnRateBuffMenu;
import me.harsh.privategamesaddon.menu.subBuffMenu.SpeedBuffMenu;
import me.harsh.privategamesaddon.settings.Settings;
import me.harsh.privategamesaddon.utils.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class PrivateGameMenu extends Menu {
    private final Button healthBuff;
    private final Button oneHitBuff;
    private final Button gravityBuff;
    private final Button speedBuff;
    private final Button blockProtBuff;
    private final Button respawnTimeBuff;
    private final Button baseSpawnerBuff;

    public PrivateGameMenu(){
        setTitle(Settings.MENU_TITLE);
        setSize(9*4);
        this.respawnTimeBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                new RespawnBuffMenu().displayTo(player);
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.PAPER,
                        Settings.RESPAWN_TIME_BUFF,
                        "",
                        "Enables you to ",
                        "Change respawn time").build().make();
            }
        };
        this.blockProtBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                final ArenaBuff buff = Utility.getBuffSafe(player);
                if (!buff.isBlocksProtected()){
                    buff.setBlocksProtection(true);
                    restartMenu("&aEnabled Block Protection!");
                    return;
                }
                buff.setBlocksProtection(false);
                restartMenu("&cDisabled Block Protection!");
            }

            @Override
            public ItemStack getItem() {
                final ArenaBuff buff = Utility.getBuffSafe(getViewer());
                return ItemCreator.of(CompMaterial.GRASS_BLOCK,
                        Settings.DISABLE_BLOCK_PROTECTION_BUFF,
                        "",
                        "Enables you to stop the",
                        "Block protection in arenas")
                        .glow(buff.isBlocksProtected())
                        .build().make();
            }
        };
        this.baseSpawnerBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                new SpawnRateBuffMenu().displayTo(player);
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.IRON_INGOT,
                                Settings.SPAWN_RATE_MUTIPLIER_BUFF,
                                "",
                                "Enables people multiply",
                                "spawn rate of base spawners!")
                        .build().make();
            }
        };
        this.speedBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                final SpeedBuffMenu speedBuffMenu = new SpeedBuffMenu();
                speedBuffMenu.displayTo(player);
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.SUGAR,
                        Settings.SPEED_BUFF,
                        "",
                        "Enables you to play",
                        "in more speed than normal").build().make();
            }
        };
        this.gravityBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                final ArenaBuff buff = Utility.getBuffSafe(player);
                if (buff.isLowGravity()){
                    buff.setLowGravity(false);
                    restartMenu("&cLow Gravity Disabled");
                    return;
                }
                buff.setLowGravity(true);
                restartMenu("&aLow Gravity Enabled");
            }

            @Override
            public ItemStack getItem() {
                final ArenaBuff buff = Utility.getBuffSafe(getViewer());
                return ItemCreator.of(CompMaterial.RABBIT_FOOT,
                        Settings.LOW_GRAVITY_BUFF,
                        "",
                        "Enables you to play in",
                        "low gravity in bedwars")
                        .glow(buff.isLowGravity())
                        .build().make();
            }
        };
        this.oneHitBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                final ArenaBuff buff = Utility.getBuffSafe(player);
                if (buff.isOneHitKill()){
                    buff.setOneHitKill(false);
                    restartMenu("&cOne Hit Buff Disabled!");
                    return;
                }
                buff.setOneHitKill(true);
                restartMenu("&aOne Hit Buff Enabled!");
            }

            @Override
            public ItemStack getItem() {
                final ArenaBuff buff = Utility.getBuffSafe(getViewer());
                return ItemCreator.of(CompMaterial.DIAMOND_SWORD,
                        Settings.ONE_HIT_BUFF,
                        "",
                        "Enables to one hit anyone",
                        "in the bedwars game")
                        .glow(buff.isOneHitKill())
                        .build().make();
            }
        };
        this.healthBuff = new Button() {
            @Override
            public void onClickedInMenu(Player player, Menu menu, ClickType click) {
                final HealthBuffMenu menu1 = new HealthBuffMenu();
                menu1.displayTo(player);
            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(CompMaterial.GOLDEN_APPLE, Settings.HEALTH_BUFF,
                        "",
                        "Enables you to",
                        "increase custom health").build().make();

            }
        };

    }

    @Override
    public ItemStack getItemAt(int slot) {
        if (slot == 10){
            return oneHitBuff.getItem();
        }else if (slot == 12){
            return healthBuff.getItem();
        }else if (slot == 14){
            return gravityBuff.getItem();
        }else if (slot == 16){
            return speedBuff.getItem();
        }else if (slot == 29){
            return baseSpawnerBuff.getItem();
        }else if (slot == 33){
            return blockProtBuff.getItem();
        }else if (slot == 31){
            return respawnTimeBuff.getItem();
        }
        return ItemCreator.of(CompMaterial.CYAN_STAINED_GLASS_PANE).build().make();
    }
}
