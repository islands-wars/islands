package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying;

/**
 * File <b>FlyingInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * FlyingInPacket is a part of islands.
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
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 05/02/2021 at 15:53
 * @since 0.1
 * <p>
 * If the distance between the last known position of the player on the server and the new position set by this packet is greater than 100 meters, the client will be kicked for "You moved too
 * quickly :( (Hacking?)".
 * <p>
 * Also if the fixed-point number of X or Z is set greater than 3.2E7D the client will be kicked for "Illegal position".
 */
public class FlyingInPacket extends GamePacket<PacketPlayInFlying> {

	protected FlyingInPacket(PacketPlayInFlying handle) {
		super(handle);
	}

	/**
	 * @return absolute X position
	 */
	public double getX() {
		return (double) getHandleValue("x");
	}

	/**
	 * @return absolute Y position
	 */
	public double getY() {
		return (double) getHandleValue("y");
	}

	/**
	 * @return absolute Z position
	 */
	public double getZ() {
		return (double) getHandleValue("z");
	}

	/**
	 * @return absolute rotation on the X axis
	 */
	public float getPitch() {
		return (float) getHandleValue("pitch");
	}

	/**
	 * @return absolute rotation on the Y axis
	 */
	public float getYaw() {
		return (float) getHandleValue("yaw");
	}

	/**
	 * @return if the player is on ground
	 */
	public boolean isOnGround() {
		return handle.b();
	}

	/**
	 * @return update only position
	 */
	public boolean hasPos() {
		return (boolean) getHandleValue("hasPos");
	}

	/**
	 * @return update only look
	 */
	public boolean hasLook() {
		return (boolean) getHandleValue("hasLook");
	}

	/**
	 * @param X absolute X position
	 */
	public void setX(double X) {
		setHandleValue("x", X);
	}

	/**
	 * @param Y absolute Y position
	 */
	public void setY(double Y) {
		setHandleValue("y", Y);
	}

	/**
	 * @param Z absolute Z position
	 */
	public void setZ(double Z) {
		setHandleValue("z", Z);
	}

	/**
	 * @param yaw absolute rotation on the X axis
	 */
	public void setYaw(float yaw) {
		setHandleValue("yaw", yaw);
	}

	/**
	 * @param pitch absolute rotation on the Y axis
	 */
	public void setPitch(float pitch) {
		setHandleValue("pitch", pitch);
	}

	/**
	 * @param hasPos position
	 */
	public void setHasPos(boolean hasPos) {
		setHandleValue("hasPos", hasPos);
	}

	/**
	 * @param hasLook rotation
	 */
	public void setHasLook(boolean hasLook) {
		setHandleValue("hasLook", hasLook);
	}

	/**
	 * @param onGround flying or not
	 */
	public void setOnGround(boolean onGround) {
		setHandleValue("f", onGround);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.FLYING_IN;
	}
}
