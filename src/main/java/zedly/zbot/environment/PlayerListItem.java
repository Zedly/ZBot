/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.zbot.environment;

import zedly.zbot.environment.PlayerListItemProperty;
import java.util.UUID;

/**
 *
 * @author Dennis
 */
public class PlayerListItem {
    public UUID uuid;
    public String name;
    public PlayerListItemProperty[] properties;
    public int gameMode;
    public int ping;
    public boolean hasDisplayName;
    public String displayName;
}
