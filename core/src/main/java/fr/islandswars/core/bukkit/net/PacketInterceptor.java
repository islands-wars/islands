package fr.islandswars.core.bukkit.net;

import fr.islandswars.api.net.PacketHandler;
import fr.islandswars.api.utils.NMSReflectionUtil;
import io.netty.channel.*;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * File <b>PacketInterceptor</b> located on fr.islandswars.core.bukkit.net
 * PacketInterceptor is a part of islands.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:50
 * @since TODO edit
 */
public class PacketInterceptor {

	private static final AtomicInteger                     ID                   = new AtomicInteger(0);
	private static final Map<SocketAddress, PacketHandler> HANDLERS             = new ConcurrentHashMap<>();
	private static final ChannelFutureHandler              channelFutureHandler = new ChannelFutureHandler();
	private static       List<ChannelFuture>               channelFutures;
	private static       PacketHandlerManager              manager;

	PacketInterceptor(PacketHandlerManager packetHandlerManager) {
		manager = packetHandlerManager;
		ID.incrementAndGet();
	}

	public static void clean() {
		if (channelFutures == null)
			return;

		for (ChannelFuture o : channelFutures) {
			ChannelPipeline pp = o.channel().pipeline();
			if (pp.get(ChannelFutureHandler.ID) != null)
				pp.remove(ChannelFutureHandler.ID);
		}

		for (PacketHandler handler : HANDLERS.values()) {
			handler.channel.pipeline().remove(handler); //Remove all HANDLERS
		}
	}

	public static void inject() {

		var mcServer      = NMSReflectionUtil.getValue(Bukkit.getServer(), "console");
		var srvConnection = NMSReflectionUtil.getFirstValueOfType(mcServer, "{nms}.ServerConnection");
		channelFutures = NMSReflectionUtil.getFirstValueOfType(srvConnection, List.class); //Steal channelFutures list

		for (ChannelFuture o : channelFutures)
			if (o.channel().pipeline().get(ChannelFutureHandler.ID) == null)
				o.channel().pipeline().addFirst(ChannelFutureHandler.ID, ChannelFutureHandler.INSTANCE);

		for (var player : Bukkit.getOnlinePlayers()) // /reload support
			injectPlayer(player);                       // (inject to already connected players)
	}

	private static void injectPlayer(Player player) {
		Channel       ch      = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		PacketHandler handler = new PacketHandler(ch);
		ch.pipeline().addBefore("packet_handler", PacketHandler.ID, handler);
		HANDLERS.put(ch.remoteAddress(), handler);
	}

	public static final class ChannelFutureHandler extends ChannelDuplexHandler {

		private static final ChannelFutureHandler INSTANCE = new ChannelFutureHandler();
		private static final String               ID       = "NMSProtocol-ChannelFuture";

		private ChannelFutureHandler() {
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			Channel channel = ((Channel) msg);
			channel.pipeline().addFirst(ChannelInitHandler.ID, new ChannelInitHandler(channel));

			super.channelRead(ctx, msg);
		}
	}

	@ChannelHandler.Sharable
	public static final class ChannelInitHandler extends ChannelDuplexHandler {

		private static final String  ID = "NMSProtocol-Init";
		private final        Channel channel;

		ChannelInitHandler(Channel channel) {
			this.channel = channel;
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			PacketHandler handler = new PacketHandler(channel);
			HANDLERS.put(channel.remoteAddress(), handler);

			ChannelPipeline pipeline = ctx.channel().pipeline();
			pipeline.addBefore("packet_handler", PacketHandler.ID, handler);
			pipeline.remove(this); //Auto-remove

			super.channelRead(ctx, msg);
		}
	}

	public static final class PacketHandler extends ChannelDuplexHandler {

		private static final String  ID = "NMSProtocol-PacketHandler";
		private final        Channel channel;

		PacketHandler(Channel channel) {
			this.channel = channel;
		}

		@Override
		public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
			HANDLERS.remove(channel.remoteAddress());
			ctx.fireChannelUnregistered();
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			if (!manager.handlePacket((Packet) msg, channel)) //if event not cancelled
				super.write(ctx, msg, promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			if (!manager.handlePacket((Packet) msg, channel)) //if event not cancelled
				super.channelRead(ctx, msg);
		}
	}
}
