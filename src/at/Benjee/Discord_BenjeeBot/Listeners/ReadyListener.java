package at.Benjee.Discord_BenjeeBot.Listeners;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

	private final BenjeeBot _bot;
	
	public ReadyListener(BenjeeBot bot) {
		_bot = bot;
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		
		Guild guild = event.getJDA().getGuildById(_bot.getGuildID());
		
		TextChannel tcVerify = guild.getTextChannelById(_bot.getVerifyChannelID());
		TextChannel tcInfo   = guild.getTextChannelById(_bot.getInfoChannelID());
		
		if ((tcVerify != null) && (tcVerify.getHistoryFromBeginning(1).complete().size() < 1)) {
			_bot.sendWelcomeMessageToChannel(tcVerify);
		}
		
		if ((tcInfo != null) && (tcInfo.getHistoryFromBeginning(1).complete().size() < 1)) {
			_bot.sendWelcomeMessageToChannel(tcInfo);
		}
		
	}
	
}
