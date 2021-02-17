package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;

/**
 * File <b>ArmAnimationInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * ArmAnimationInPacket is a part of islands.
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
 * Created the 05/02/2021 at 14:37
 * @since 0.1
 */
public class ArmAnimationInPacket extends GamePacket<PacketPlayInArmAnimation> {

	protected ArmAnimationInPacket(PacketPlayInArmAnimation handle) {
		super(handle);
	}

	/**
	 * @return which hand is used
	 */
	public EnumHand getHand() {
		return handle.b();
	}

	/**
	 * @param hand the hand to animate
	 */
	public void setEnumHand(EnumHand hand) {
		setHandleValue("a", hand);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.ARM_ANIMATION_IN;
	}
}
