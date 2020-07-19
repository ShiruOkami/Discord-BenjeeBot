package at.Benjee.Discord_BenjeeBot.Util;

import at.Benjee.Discord_BenjeeBot.BenjeeBot;

public class GenericEvent {

	private BenjeeBot _bot;
	private Object _eventObject;
	private String[] _args;
	
	public GenericEvent(BenjeeBot bot, Object eventObject, String[] args) {
		_bot = bot;
		_eventObject = eventObject;
		_args = args;
	}
	
	public BenjeeBot getBotInstance() {
		return _bot;
	}
	
	public Object getEventObject() {
		return _eventObject;
	}
	
	public String[] getArgs() {
		return _args;
	}
	
}
