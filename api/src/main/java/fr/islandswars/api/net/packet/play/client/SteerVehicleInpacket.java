package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;

/**
 * File <b>SteerVehicleInpacket</b> located on fr.islandswars.api.net.packet.play.client
 * SteerVehicleInpacket is a part of islands.
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
 * Created the 05/02/2021 at 17:18
 * @since 0.1
 * <p>
 * TODO debug
 */
public class SteerVehicleInpacket extends GamePacket<PacketPlayInSteerVehicle> {

	protected SteerVehicleInpacket(PacketPlayInSteerVehicle handle) {
		super(handle);
	}

	/**
	 * @return positive to the left of the player
	 */
	public float getSideways() {
		return handle.b();
	}

	/**
	 * @param sideways positive to the left of the player
	 */
	public void setSideways(float sideways) {
		setHandleValue("a", sideways);
	}

	/**
	 * @return positive forward
	 */
	public float getForward() {
		return handle.c();
	}

	/**
	 * @param forwards positive forward
	 */
	public void setForwards(float forwards) {
		setHandleValue("b", forwards);
	}

	/**
	 * @return if the player is jumping
	 */
	public boolean getJump() {
		return handle.d();
	}

	/**
	 * @param value if the player is jumping
	 */
	public void setJump(boolean value) {
		setHandleValue("c", value);
	}

	/**
	 * @return if the player is unmounting
	 */
	public boolean getUnmount() {
		return handle.e();
	}

	/**
	 * @param value set player unmounting
	 */
	public void setUnmount(boolean value) {
		setHandleValue("d", value);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
