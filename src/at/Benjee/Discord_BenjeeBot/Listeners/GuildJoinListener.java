package at.Benjee.Discord_BenjeeBot.Listeners;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoinListener extends ListenerAdapter {

	private BenjeeBot _bot;
	
	public GuildJoinListener(BenjeeBot bot) {
		_bot = bot;
	}
	
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		
		StringBuilder strBuilder = new StringBuilder();
		String messageLine;
		
		try (BufferedReader br = new BufferedReader(new FileReader(getClass().getResource("/resources/welcome_message.txt").getFile()))) {
			while ((messageLine = br.readLine()) != null) {
				strBuilder.append(messageLine + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		event.getUser().openPrivateChannel().queue(channel -> {
			channel.sendMessage(strBuilder).queue();
		});
		
	}

}
