package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R3.Vec3D;

/**
 * File <b>UseEntityInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * UseEntityInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:56
 * @since 0.1
 */
public class UseEntityInPacket extends GamePacket<PacketPlayInUseEntity> {

	protected UseEntityInPacket(PacketPlayInUseEntity handle) {
		super(handle);
	}

	/**
	 * @return target entity id
	 */
	public int getEntityID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param id set target id
	 */
	public void setEntity(int id) {
		setHandleValue("a", id);
	}

	/**
	 * @return action
	 */
	public PacketPlayInUseEntity.EnumEntityUseAction getAction() {
		return handle.b();
	}

	/**
	 * @param action player action
	 */
	public void setAction(PacketPlayInUseEntity.EnumEntityUseAction action) {
		setHandleValue("action", action);
	}

	/**
	 * @return target location only if it's Type interact at
	 */
	public Vec3D getTargetPos() {
		return handle.d();
	}

	/**
	 * @param pos target pos
	 */
	public void setTargetPos(Vec3D pos) {
		setHandleValue("c", pos);
	}

	/**
	 * @return hand
	 */
	public EnumHand getHand() {
		return handle.c();
	}

	/**
	 * @param hand player interact hand
	 */
	public void setHand(EnumHand hand) {
		setHandleValue("d", hand);
	}

	/**
	 * @return if the player is sneaking
	 */
	public boolean isSneaking() {
		return handle.e();
	}

	/**
	 * @param sneaking set the player sneaking
	 */
	public void setSneaking(boolean sneaking) {
		setHandleValue("e", sneaking);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.USE_ENTITY_IN;
	}
}
