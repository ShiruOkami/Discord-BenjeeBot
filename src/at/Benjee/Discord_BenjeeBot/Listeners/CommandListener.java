package at.Benjee.Discord_BenjeeBot.Listeners;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;
import at.Benjee.Discord_BenjeeBot.Commands.CommandStreamPingRole;
import at.Benjee.Discord_BenjeeBot.Commands.CommandIAgree;
import at.Benjee.Discord_BenjeeBot.Commands.CommandPing;
import at.Benjee.Discord_BenjeeBot.Commands.CommandSetInfo;
import at.Benjee.Discord_BenjeeBot.Commands.CommandSetVerify;
import at.Benjee.Discord_BenjeeBot.Util.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	private final BenjeeBot _bot;
	private HashMap<String, Class<?>> _commands; 
	
	public CommandListener(BenjeeBot bot) {
		_bot = bot;
		setupCommands();
	}
	
	private void setupCommands() {
		
		_commands = new HashMap<String, Class<?>>();
		
		_commands.put("iagree", CommandIAgree.class);
		_commands.put("ping", CommandPing.class);
		_commands.put("setinfo", CommandSetInfo.class);
		_commands.put("setverify", CommandSetVerify.class);
		_commands.put("streamping", CommandStreamPingRole.class);
		
	}
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String rawMsg = event.getMessage().getContentRaw();
		String[] args;
		
		if ((rawMsg.length() > 1) && (rawMsg.substring(0, _bot.getPrefix().length()).equals(_bot.getPrefix()))) {
			
			args = rawMsg.substring(_bot.getPrefix().length(), rawMsg.length()).split(" ");
			
			if (_commands.containsKey(args[0])) {
				
				try {
					final Class<?> commandClass = _commands.get(args[0]);
					final Constructor<?> commandConstructor = commandClass.getDeclaredConstructor(GenericEvent.class);
					commandConstructor.newInstance(new GenericEvent(_bot, event, args));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
		}
		
	}
	
}
