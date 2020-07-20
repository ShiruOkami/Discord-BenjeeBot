package at.Benjee.Discord_BenjeeBot.Commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandCreatePronoun {

	public CommandCreatePronoun(GenericEvent genericEvent) {
		createNewPronoun(genericEvent);
	}
	
	private boolean createNewPronoun(GenericEvent genericEvent) {
		
		MessageReceivedEvent event = (MessageReceivedEvent) genericEvent.getEventObject();
		BenjeeBot bot = genericEvent.getBotInstance();
		
		Guild guild = event.getGuild();
		HashMap<String, Role> pronounsList = bot.getPronounList();
		String[] args = genericEvent.getArgs();
		
		// Check if it's owner who executes command
		if (!event.getAuthor().getId().equals(event.getGuild().getOwner().getId())) {
			return false;
		}
		
		// Check for arguments
		if (args.length != 2) {
			event.getChannel().sendMessage("Wrong syntax: !createpronoun <pronoun>").queue();
			return false;
		}
		
		// Check if role already exists
		if (pronounsList.containsKey(args[1])) {
			event.getChannel().sendMessage("This role already exists").queue();
			return false;
		}
		
		// Else, create role & update _pronounsList & pronouns.txt file
		long permissionsLong = 104120897;
		Role newRole = guild.createRole().setName(args[1]).setPermissions(permissionsLong).complete();
		bot.getPronounList().put(args[1], newRole);
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(bot.getFileFolder() + "/" + bot.getPronounsFile())))) {
			
			for (String s : pronounsList.keySet()) {
				bw.write(s + "\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		event.getChannel().sendMessage(event.getMember().getAsMention() + " , I have created the role **" + args[1] + "**").queue();
		return true;
		
	}
	
}
