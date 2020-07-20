package at.Benjee.Discord_BenjeeBot.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSetVerify {
	
	public CommandSetVerify(GenericEvent genericEvent) {		
		setVerificationChannel(genericEvent);
	}
	
	private boolean setVerificationChannel(GenericEvent genericEvent) {
		
		String[] args = genericEvent.getArgs();
		BenjeeBot bot = genericEvent.getBotInstance();
		MessageReceivedEvent event = (MessageReceivedEvent) genericEvent.getEventObject();
		
		// Check if person is an admin
		if (!event.getAuthor().getId().equals(event.getGuild().getOwner().getId())) {
			return false;
		}
		
		// Interrupt when no channel is specified
		if (args.length < 2) {
			event.getChannel().sendMessage("You need to specify a channel!").queue();
			return false;
		}
		
		// Check if channel exists
		List <GuildChannel> guildChannels = event.getGuild().getChannels();
		String channelID = "-1";
		
		for (GuildChannel gc : guildChannels) {
			if (gc.getName().equalsIgnoreCase(args[1])) {
				channelID = gc.getId();
			}
		}
		
		if (channelID.equals("-1")) {
			event.getChannel().sendMessage("I couldn't find the channel #" + args[1]).queue();
			return false;
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(bot.getFileFolder() + "/" + bot.getChannelIDsFile())))) {
			
			bw.write(channelID + "\n");
			bw.write(genericEvent.getBotInstance().getInfoChannelID());
			
			bot.setVerifyChannelID(channelID);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		genericEvent.getBotInstance().sendWelcomeMessageToChannel(event.getGuild().getTextChannelById(channelID));
		event.getChannel().sendMessage("Updated channel for verification to " + args[1]).queue();
		
		return true;
		
	}
	
}
