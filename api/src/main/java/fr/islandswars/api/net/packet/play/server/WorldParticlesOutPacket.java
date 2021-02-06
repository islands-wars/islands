package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.Particle;
import net.minecraft.server.v1_16_R3.ParticleParam;

/**
 * File <b>WorldParticlesOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * WorldParticlesOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 19:22
 * @since 0.1
 * <p>
 * TODO proper builder
 */
public class WorldParticlesOutPacket extends GamePacket<PacketPlayOutWorldParticles> {

	protected WorldParticlesOutPacket(PacketPlayOutWorldParticles handle) {
		super(handle);
	}

	public float getX() {
		return (float) getHandleValue("a");
	}

	public void setX(float newX) {
		setHandleValue("a", newX);
	}

	public float getY() {
		return (float) getHandleValue("b");
	}

	public void setY(float newY) {
		setHandleValue("b", newY);
	}

	public float getZ() {
		return (float) getHandleValue("c");
	}

	public void setZ(float newZ) {
		setHandleValue("c", newZ);
	}

	public int getCount() {
		return (int) getHandleValue("h");
	}

	public void setCount(int count) {
		setHandleValue("h", count);
	}

	public boolean useLongDistance() {
		return (boolean) getHandleValue("i");
	}

	public void setLongDistance(boolean value) {
		setHandleValue("i", value);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.WORLD_PARTICLES_OUT;
	}
}
