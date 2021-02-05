package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.utils.Preconditions;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction;

/**
 * File <b>EntityActionInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * EntityActionInPacket is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2021 Islands Wars.
 * <p>
 * islands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 05/02/2021 at 15:40
 * @since 0.1
 * TODO debug :/
 */
public class EntityActionInPacket extends GamePacket<PacketPlayInEntityAction> {

	protected EntityActionInPacket(PacketPlayInEntityAction handle) {
		super(handle);
	}

	/**
	 * @return the action used for this animation
	 */
	public PacketPlayInEntityAction.EnumPlayerAction getAction() {
		return handle.c();
	}

	/**
	 * @param action set the action to animate
	 */
	public void setAction(PacketPlayInEntityAction.EnumPlayerAction action) {
		setHandleValue("animation", action);
	}

	/**
	 * @return the target id
	 */
	public int getEntityID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param id the target of this effect
	 */
	public void setEntityID(int id) {
		setHandleValue("a", id);
	}

	/**
	 * Only used by the “start jump with horse” action, in which case it ranges from 0 to 100. In all other cases it is 0.
	 *
	 * @return horse jump boost
	 */
	public int getHorseBoost() {
		return handle.d();
	}

	/**
	 * Only used by the “start jump with horse” action, in which case it ranges from 0 to 100. In all other cases it is 0.
	 *
	 * @param boost hirse jump boost
	 */
	public void setHorseBoost(int boost) {
		Preconditions.checkState(boost, (b) -> b <= 100 && b >= 0);
		setHandleValue("c", boost);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.ENTITY_ACTION_IN;
	}
}
