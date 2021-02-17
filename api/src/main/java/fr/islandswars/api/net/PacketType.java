package fr.islandswars.api.net;

import fr.islandswars.api.net.packet.handshake.client.HandShakePacket;
import fr.islandswars.api.net.packet.login.client.EncryptionResponsePacket;
import fr.islandswars.api.net.packet.login.client.LoginStartPacket;
import fr.islandswars.api.net.packet.login.server.DisconnectPacket;
import fr.islandswars.api.net.packet.login.server.EncryptionRequestPacket;
import fr.islandswars.api.net.packet.login.server.SetCompressionPacket;
import fr.islandswars.api.net.packet.login.server.SuccessPacket;
import fr.islandswars.api.net.packet.play.client.*;
import fr.islandswars.api.net.packet.play.server.*;
import fr.islandswars.api.net.packet.status.client.PingPacket;
import fr.islandswars.api.net.packet.status.client.StartPacket;
import fr.islandswars.api.net.packet.status.server.PongPacket;
import fr.islandswars.api.net.packet.status.server.ServerInfoPacket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_16_R3.*;
import static fr.islandswars.api.net.PacketType.Bound.IN;
import static fr.islandswars.api.net.PacketType.Bound.OUT;

/**
 * File <b>PacketType</b> located on fr.islandswars.api.net
 * PacketType is a part of islands.
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
 * @author Gogume1er
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:12
 * @since 0.1
 */
public class PacketType<T extends GamePacket> {

	private static final Map<Class<? extends Packet>, Class<? extends GamePacket>> packetList = new HashMap<>();
	private static       Map<Class<? extends Packet>, Class<? extends GamePacket>> unmodifiablePacketList;
	private final        Class<T>                                                  packet;
	private final        Class<? extends Packet>                                   nmsPacket;
	private final        Protocol                                                  protocol;
	private final        Bound                                                     bound;

	public PacketType(Class<T> packet, Class<? extends Packet> nmsPacket, Protocol protocol, Bound bound) {
		this.protocol = protocol;
		this.nmsPacket = nmsPacket;
		this.packet = packet;
		this.bound = bound;
		packetList.put(nmsPacket, packet);
	}

	/**
	 * @return an immutable Map access (read only)
	 * @see Collections#unmodifiableMap(Map)
	 */
	public static Map<Class<? extends Packet>, Class<? extends GamePacket>> getPacketList() {
		return unmodifiablePacketList == null ? unmodifiablePacketList = Collections.unmodifiableMap(packetList) : unmodifiablePacketList;
	}

	/**
	 * @return the packet's bound
	 */
	public Bound getBound() {
		return bound;
	}

	/**
	 * @return this packet name
	 */
	public String getID() {
		return nmsPacket.getSimpleName();
	}

	/**
	 * @return the packet wrapper's class
	 */
	public Class<T> getPacket() {
		return packet;
	}

	/**
	 * @return the packet wrapper's protocol
	 */
	public Protocol getProtocol() {
		return protocol;
	}

	public enum Protocol {
		/**
		 * Id: -1
		 */
		HANDSHAKE,
		/**
		 * Id: 0
		 */
		PLAY,
		/**
		 * Id: 1
		 */
		STATUS,
		/**
		 * Id: 2
		 */
		LOGIN
	}

	public enum Bound {
		/**
		 * Incomming buffer
		 */
		IN,

		/**
		 * Outcomming buffer
		 */
		OUT
	}

	public static final class Handshake {

		public static final class Client {

			public static final PacketType<HandShakePacket> HANDSHAKE = new PacketType<>(HandShakePacket.class, PacketHandshakingInSetProtocol.class, Protocol.HANDSHAKE, OUT);
		}

	}

	public static final class Login {

		public static final class Client {

			public static final PacketType<EncryptionResponsePacket> ENCRYPION_RESPONSE = new PacketType<>(EncryptionResponsePacket.class, PacketLoginInEncryptionBegin.class, Protocol.LOGIN, IN);
			public static final PacketType<LoginStartPacket>         LOGIN_START        = new PacketType<>(LoginStartPacket.class, PacketLoginInStart.class, Protocol.LOGIN, IN);
		}

		public static final class Server {

			public static final PacketType<DisconnectPacket>        DISCONNECT         = new PacketType<>(DisconnectPacket.class, PacketLoginOutDisconnect.class, Protocol.LOGIN, OUT);
			public static final PacketType<EncryptionRequestPacket> ENCRYPTION_REQUEST = new PacketType<>(EncryptionRequestPacket.class, PacketLoginOutEncryptionBegin.class, Protocol.LOGIN, OUT);
			public static final PacketType<SetCompressionPacket>    COMPRESSION        = new PacketType<>(SetCompressionPacket.class, PacketLoginOutSetCompression.class, Protocol.LOGIN, OUT);
			public static final PacketType<SuccessPacket>           SUCCESS            = new PacketType<>(SuccessPacket.class, PacketLoginOutSuccess.class, Protocol.LOGIN, OUT);
		}
	}

	public static final class Status {

		public static final class Client {

			public static final PacketType<PingPacket>  PING  = new PacketType<>(PingPacket.class, PacketStatusInPing.class, Protocol.STATUS, IN);
			public static final PacketType<StartPacket> START = new PacketType<>(StartPacket.class, PacketStatusInStart.class, Protocol.STATUS, IN);
		}

		public static final class Server {

			public static final PacketType<PongPacket>       PONG        = new PacketType<>(PongPacket.class, PacketStatusOutPong.class, Protocol.STATUS, OUT);
			public static final PacketType<ServerInfoPacket> SERVER_INFO = new PacketType<>(ServerInfoPacket.class, PacketStatusOutServerInfo.class, Protocol.STATUS, OUT);
		}

	}

	public static final class Play {

		public static final class Client {

			public static final PacketType<AbilitiesInPacket>           ABILITIES_IN             = new PacketType<>(AbilitiesInPacket.class, PacketPlayInAbilities.class, Protocol.PLAY, IN);
			public static final PacketType<AdvancementsInPacket>        ADVANCEMENTS_IN          = new PacketType<>(AdvancementsInPacket.class, PacketPlayInAdvancements.class, Protocol.PLAY, IN);
			public static final PacketType<ArmAnimationInPacket>        ARM_ANIMATION_IN         = new PacketType<>(ArmAnimationInPacket.class, PacketPlayInArmAnimation.class, Protocol.PLAY, IN);
			public static final PacketType<AutoRecipeInPacket>          AUTO_RECIPE_IN           = new PacketType<>(AutoRecipeInPacket.class, PacketPlayInAutoRecipe.class, Protocol.PLAY, IN);
			public static final PacketType<BeaconInPacket>              BEACON_IN                = new PacketType<>(BeaconInPacket.class, PacketPlayInBeacon.class, Protocol.PLAY, IN);
			public static final PacketType<BEditInPacket>               BOOK_EDIT_IN             = new PacketType<>(BEditInPacket.class, PacketPlayInBEdit.class, Protocol.PLAY, IN);
			public static final PacketType<BlockDigInPacket>            BLOCK_DIG_IN             = new PacketType<>(BlockDigInPacket.class, PacketPlayInBlockDig.class, Protocol.PLAY, IN);
			public static final PacketType<BlockPlaceInPacket>          BLOCK_PLACE_IN           = new PacketType<>(BlockPlaceInPacket.class, PacketPlayInBlockPlace.class, Protocol.PLAY, IN);
			public static final PacketType<BoatMoveInPacket>            BOAT_MOVE_IN             = new PacketType<>(BoatMoveInPacket.class, PacketPlayInBoatMove.class, Protocol.PLAY, IN);
			public static final PacketType<ChatInPacket>                CHAT_IN                  = new PacketType<>(ChatInPacket.class, PacketPlayInChat.class, Protocol.PLAY, IN);
			public static final PacketType<ClientCommandInPacket>       CLIENT_COMMAND_IN        = new PacketType<>(ClientCommandInPacket.class, PacketPlayInClientCommand.class, Protocol.PLAY, IN);
			public static final PacketType<CloseWindowsInPacket>        CLOSE_WINDOWS_IN         = new PacketType<>(CloseWindowsInPacket.class, PacketPlayInCloseWindow.class, Protocol.PLAY, IN);
			public static final PacketType<CustomPayloadInPacket>       CUSTOM_PAYLOAD_IN        = new PacketType<>(CustomPayloadInPacket.class, PacketPlayInCustomPayload.class, Protocol.PLAY, IN);
			public static final PacketType<DifficultyChangeInPacket>    DIFFICULTY_CHANGE_IN     = new PacketType<>(DifficultyChangeInPacket.class, PacketPlayInDifficultyChange.class, Protocol.PLAY, IN);
			public static final PacketType<DifficultyLockInPacket>      DIFFICULTY_LOCK_IN       = new PacketType<>(DifficultyLockInPacket.class, PacketPlayInDifficultyLock.class, Protocol.PLAY, IN);
			public static final PacketType<EnchantItemInPacket>         ENCHANT_ITEM_IN          = new PacketType<>(EnchantItemInPacket.class, PacketPlayInEnchantItem.class, Protocol.PLAY, IN);
			public static final PacketType<EntityActionInPacket>        ENTITY_ACTION_IN         = new PacketType<>(EntityActionInPacket.class, PacketPlayInEntityAction.class, Protocol.PLAY, IN);
			public static final PacketType<EntityNBTQueryInPacket>      ENTITY_NBT_QUERY_IN      = new PacketType<>(EntityNBTQueryInPacket.class, PacketPlayInEntityNBTQuery.class, Protocol.PLAY, IN);
			public static final PacketType<FlyingInPacket>              FLYING_IN                = new PacketType<>(FlyingInPacket.class, PacketPlayInFlying.class, Protocol.PLAY, IN);
			public static final PacketType<HeldItemSlotInPacket>        HELD_ITEM_SLOT_IN        = new PacketType<>(HeldItemSlotInPacket.class, PacketPlayInHeldItemSlot.class, Protocol.PLAY, IN);
			public static final PacketType<ItemNameInPacket>            ITEM_NAME_IN             = new PacketType<>(ItemNameInPacket.class, PacketPlayInItemName.class, Protocol.PLAY, IN);
			public static final PacketType<JigsawGenerateInPacket>      JIGSAW_GENERATE_IN       = new PacketType<>(JigsawGenerateInPacket.class, PacketPlayInJigsawGenerate.class, Protocol.PLAY, IN);
			public static final PacketType<KeepAliveInPacket>           KEEP_ALIVE_IN            = new PacketType<>(KeepAliveInPacket.class, PacketPlayInKeepAlive.class, Protocol.PLAY, IN);
			public static final PacketType<PickItemInPacket>            PICK_ITEM_IN             = new PacketType<>(PickItemInPacket.class, PacketPlayInPickItem.class, Protocol.PLAY, IN);
			public static final PacketType<RecipeDisplayedInPacket>     RECIPE_DISPLAYED_IN      = new PacketType<>(RecipeDisplayedInPacket.class, PacketPlayInRecipeDisplayed.class, Protocol.PLAY, IN);
			public static final PacketType<RecipeSettingsInPacket>      RECIPE_SETTINGS_IN       = new PacketType<>(RecipeSettingsInPacket.class, PacketPlayInRecipeSettings.class, Protocol.PLAY, IN);
			public static final PacketType<RessourcePackStatusInPacket> RESSOURCE_PACK_STATUS_IN = new PacketType<>(RessourcePackStatusInPacket.class, PacketPlayInResourcePackStatus.class, Protocol.PLAY, IN);
			public static final PacketType<SetCommandBlockInPacket>     SET_COMMAND_BLOCK_IN     = new PacketType<>(SetCommandBlockInPacket.class, PacketPlayInSetCommandBlock.class, Protocol.PLAY, IN);
			public static final PacketType<SetCommandMinecartInPacket>  SET_COMMAND_MINECART_IN  = new PacketType<>(SetCommandMinecartInPacket.class, PacketPlayInSetCommandMinecart.class, Protocol.PLAY, IN);
			public static final PacketType<SetCreativeSlotInPacket>     SET_CREATIVE_SLOT_IN     = new PacketType<>(SetCreativeSlotInPacket.class, PacketPlayInSetCreativeSlot.class, Protocol.PLAY, IN);
			public static final PacketType<SetJigsawInPacket>           SET_JIGSAW_IN            = new PacketType<>(SetJigsawInPacket.class, PacketPlayInSetJigsaw.class, Protocol.PLAY, IN);
			public static final PacketType<SettingsInPacket>            SETTINGS_IN              = new PacketType<>(SettingsInPacket.class, PacketPlayInSettings.class, Protocol.PLAY, IN);
			public static final PacketType<SpectateInPacket>            SPECTATE_IN              = new PacketType<>(SpectateInPacket.class, PacketPlayInSpectate.class, Protocol.PLAY, IN);
			public static final PacketType<SteerVehicleInPacket>        STEER_VEHICLE_IN         = new PacketType<>(SteerVehicleInPacket.class, PacketPlayInSteerVehicle.class, Protocol.PLAY, IN);
			public static final PacketType<StructInPacket>              STRUCT_IN                = new PacketType<>(StructInPacket.class, PacketPlayInStruct.class, Protocol.PLAY, IN);
			public static final PacketType<TabCompleteInPacket>         TAB_COMPLETE_IN          = new PacketType<>(TabCompleteInPacket.class, PacketPlayInTabComplete.class, Protocol.PLAY, IN);
			public static final PacketType<TeleportAcceptInPacket>      TELEPORT_ACCEPT_IN       = new PacketType<>(TeleportAcceptInPacket.class, PacketPlayInTeleportAccept.class, Protocol.PLAY, IN);
			public static final PacketType<TileNBTQueryInPacket>        TILE_NBT_QUERY_IN        = new PacketType<>(TileNBTQueryInPacket.class, PacketPlayInTileNBTQuery.class, Protocol.PLAY, IN);
			public static final PacketType<TransactionInPacket>         TRANSACTION_IN           = new PacketType<>(TransactionInPacket.class, PacketPlayInTransaction.class, Protocol.PLAY, IN);
			public static final PacketType<TrSelInPacket>               TR_SEL_IN                = new PacketType<>(TrSelInPacket.class, PacketPlayInTrSel.class, Protocol.PLAY, IN);
			public static final PacketType<UpdateSignInPacket>          UPDATE_SIGN_IN           = new PacketType<>(UpdateSignInPacket.class, PacketPlayInUpdateSign.class, Protocol.PLAY, IN);
			public static final PacketType<UseEntityInPacket>           USE_ENTITY_IN            = new PacketType<>(UseEntityInPacket.class, PacketPlayInUseEntity.class, Protocol.PLAY, IN);
			public static final PacketType<UseItemInPacket>             USE_ITEM_IN              = new PacketType<>(UseItemInPacket.class, PacketPlayInUseItem.class, Protocol.PLAY, IN);
			public static final PacketType<WindowsClickInPacket>        WINDOWS_CLICK_IN         = new PacketType<>(WindowsClickInPacket.class, PacketPlayInWindowClick.class, Protocol.PLAY, IN);
		}

		public static final class Server {

			public static final PacketType<BossOutPacket>                       BOSS_OUT                         = new PacketType<>(BossOutPacket.class, PacketPlayOutBoss.class, Protocol.PLAY, OUT);
			public static final PacketType<ChatOutPacket>                       CHAT_OUT                         = new PacketType<>(ChatOutPacket.class, PacketPlayOutChat.class, Protocol.PLAY, OUT);
			public static final PacketType<OpenWindowOutPacket>                 OPEN_WINDOW_OUT                  = new PacketType<>(OpenWindowOutPacket.class, PacketPlayOutOpenWindow.class, Protocol.PLAY, OUT);
			public static final PacketType<ScoreboardDisplayObjectiveOutPacket> SCOREBOARD_DISPLAY_OBJECTIVE_OUT = new PacketType<>(ScoreboardDisplayObjectiveOutPacket.class, PacketPlayOutScoreboardDisplayObjective.class, Protocol.PLAY, OUT);
			public static final PacketType<ScoreboardObjectiveOutPacket>        SCOREBOARD_OBJECTIVE_OUT         = new PacketType<>(ScoreboardObjectiveOutPacket.class, PacketPlayOutScoreboardObjective.class, Protocol.PLAY, OUT);
			public static final PacketType<ScoreboardScoreOutPacket>            SCOREBOARD_SCORE_OUT             = new PacketType<>(ScoreboardScoreOutPacket.class, PacketPlayOutScoreboardScore.class, Protocol.PLAY, OUT);
			public static final PacketType<ScoreboardTeamOutPacket>             SCOREBOARD_TEAM_OUT              = new PacketType<>(ScoreboardTeamOutPacket.class, PacketPlayOutScoreboardTeam.class, Protocol.PLAY, OUT);
			public static final PacketType<SetSlotOutPacket>                    SET_SLOT_OUT                     = new PacketType<>(SetSlotOutPacket.class, PacketPlayOutSetSlot.class, Protocol.PLAY, OUT);
			public static final PacketType<TabCompleteOutPacket>                TAB_COMPLETE_OUT                 = new PacketType<>(TabCompleteOutPacket.class, PacketPlayOutTabComplete.class, Protocol.PLAY, OUT);
			public static final PacketType<TitleOutPacket>                      TITLE_OUT                        = new PacketType<>(TitleOutPacket.class, PacketPlayOutTitle.class, Protocol.PLAY, OUT);
			public static final PacketType<WindowItemsOutPacket>                WINDOW_ITEMS_OUT                 = new PacketType<>(WindowItemsOutPacket.class, PacketPlayOutWindowItems.class, Protocol.PLAY, OUT);
			public static final PacketType<WorldParticlesOutPacket>             WORLD_PARTICLES_OUT              = new PacketType<>(WorldParticlesOutPacket.class, PacketPlayOutWorldParticles.class, Protocol.PLAY, OUT);
		}
	}
}
