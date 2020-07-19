package at.Benjee.Discord_BenjeeBot.Commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandAssignRole {

	private static final String _streamPingRole = "StreamPing";
	
	private MessageChannel _channel;
	private Guild _guild;
	private Member _senderMember;
	
	public CommandAssignRole(MessageReceivedEvent event) {
		
		_senderMember = event.getMember();
		_guild = event.getGuild();
		_channel = event.getChannel();
		
		assignRole();
		
	}
	
	
	private boolean assignRole() {
		
		Role streamPingRole = _guild.getRolesByName(_streamPingRole, true).get(0);
		
		for (Role r : _senderMember.getRoles()) {
			if (r.getName().equalsIgnoreCase(_streamPingRole)) {
				_guild.removeRoleFromMember(_senderMember, streamPingRole).complete();
				_channel.sendMessage(_senderMember.getAsMention() + " , you don't have **StreamPing** role anymore.").queue();
				return true;
			}
		}
		
		_guild.addRoleToMember(_senderMember, streamPingRole).complete();
		_channel.sendMessage(_senderMember.getAsMention() + " , you now have the **StreamPing** role.").queue();
		return true;
		
	}
	
}
