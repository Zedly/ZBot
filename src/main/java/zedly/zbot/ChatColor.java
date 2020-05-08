/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot;

/**
 *
 * @author Dennis
 */
public enum ChatColor {

    BLACK("\u00A70", "\033[30m"),
    DARK_BLUE("\u00A71", "\033[34m"),
    DARK_GREEN("\u00A72", "\033[32m"),
    DARK_AQUA("\u00A73", "\033[36m"),
    DARK_RED("\u00A74", "\033[31m"),
    DARK_PURPLE("\u00A75", "\033[35m"),
    GOLD("\u00A76", "\033[33m"),
    GRAY("\u00A77", "\033[37m"),
    DARK_GRAY("\u00A78", "\033[90m"),
    BLUE("\u00A79", "\033[94m"),
    GREEN("\u00A7a", "\033[92m"),
    AQUA("\u00A7b", "\033[96m"),
    RED("\u00A7c", "\033[91m"),
    LIGHT_PURPLE("\u00A7d", "\033[95m"),
    YELLOW("\u00A7e", "\033[93m"),
    WHITE("\u00A7f", "\033[97m"),
    RESET("\u00A7r", "\033[0m");

    private final String ansiCode, legacyCode;

    private ChatColor(String legacyCode, String ansiCode) {
        this.legacyCode = legacyCode;
        this.ansiCode = ansiCode;
    }

    public String getANSICode() {
        return ansiCode;
    }

    public String getLegacyCode() {
        return legacyCode;
    }

    public static String legacyToANSI(String legacy) {
        for (ChatColor cc : values()) { 
            legacy = legacy.replace(cc.legacyCode, cc.ansiCode);
        }
        return legacy;
    }

}
