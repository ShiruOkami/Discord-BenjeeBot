package at.Benjee.Discord_BenjeeBot.Commands;

import java.util.HashMap;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSetPronouns {

	public CommandSetPronouns(GenericEvent genericEvent) {
		assignPronounRole(genericEvent);
	}
	
	private boolean assignPronounRole(GenericEvent genericEvent) {
		
		MessageReceivedEvent event = (MessageReceivedEvent) genericEvent.getEventObject();
		BenjeeBot bot = genericEvent.getBotInstance();
		
		Member senderMember = event.getMember();
		HashMap<String, Role> pronounsList = bot.getPronounList();
		String[] args = genericEvent.getArgs();
		
		// Arguments check
		if (args.length != 2) {
			
			StringBuilder strBuilder = new StringBuilder();
			for (String s : pronounsList.keySet()) {
				strBuilder.append(s + " , ");
			}
			
			event.getChannel().sendMessage("Wrong arguments: !pronouns <" + strBuilder.substring(0, strBuilder.length()-3) + ">").queue();
			return false;
			
		}
		
		// Check if role exists
		if (!pronounsList.containsKey(args[1])) {
			event.getChannel().sendMessage("This pronoun role doesn't exist (at least on this Discord)!").queue();
			return false;
		}
		
		for (Role r : senderMember.getRoles()) {
			if (r.getName().equalsIgnoreCase(args[1])) {
				event.getGuild().removeRoleFromMember(senderMember, r).complete();
				event.getChannel().sendMessage(senderMember.getAsMention() + " , you don't have **" + args[1] + "** role anymore.").queue();
				return true;
			}
		}
		
		event.getGuild().addRoleToMember(senderMember, event.getGuild().getRolesByName(args[1], true).get(0)).complete();
		event.getChannel().sendMessage(senderMember.getAsMention() + " , you now have the pronoun **" + args[1] + "**").queue();
		return true;
		
	}
	
}
