package com.test.combatsystems.DataManager;

import org.bukkit.entity.Player;

public class PlayerData {
    private Long time;
    private Long cooldown;
    private boolean against;
    private Integer shield;
    private Integer sword;
    private Integer bow;
    private Integer axp;
    private Integer Block;
    private boolean handSword;

    private boolean rightAction;
    private boolean COOLDOWN;
    private final Player player;

    public PlayerData(Player player) {
        this.player = player;
        this.against = false;
        this.time = DataManager.time;
        this.cooldown = DataManager.cooldown;
        this.shield = 0;
        this.sword = 0;
        this.bow = 0;
        this.axp = 0;
        this.Block = 0;
        this.handSword = false;
        this.COOLDOWN = true;
        this.rightAction = false;
    }

    public boolean isCOOLDOWN() {
        return COOLDOWN;
    }
    public void setCOOLDOWN(boolean COOLDOWN) {
        this.COOLDOWN = COOLDOWN;
    }

    public boolean isHandSword() {
        return handSword;
    }

    public void setHandSword(boolean sword) {
        handSword = sword;
    }

    public Integer getSword() {
        return sword;
    }

    public Integer getAxp() {
        return axp;
    }

    public void setAxp(Integer axp) {
        this.axp = axp;
    }

    public Integer getBow() {
        return bow;
    }

    public void setBow(Integer bow) {
        this.bow = bow;
    }

    public boolean isAgainst() {
        return against;
    }

    public void setAgainst(boolean against) {
        this.against = against;
    }

    public Integer getBlock() {
        return Block;
    }

    public void setBlock(Integer Block) {
        this.Block = Block;
    }
    public void setSword(Integer sword) {
        this.sword = sword;
    }
    public Integer getShield() {
        return shield;
    }
    public void setShield(Integer shield) {
        this.shield = shield;
    }
    public Long getCooldown() {
        return cooldown;
    }
    public void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }
    public Long getTime() {
        return time;
    }

    public boolean isRightAction() {
        return rightAction;
    }

    public void setRightAction(boolean rightAction) {
        this.rightAction = rightAction;
    }
}