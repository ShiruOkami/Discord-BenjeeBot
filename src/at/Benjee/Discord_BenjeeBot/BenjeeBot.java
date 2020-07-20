package at.Benjee.Discord_BenjeeBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import at.Benjee.Discord_BenjeeBot.Listeners.CommandListener;
import at.Benjee.Discord_BenjeeBot.Listeners.GuildJoinListener;
import at.Benjee.Discord_BenjeeBot.Listeners.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BenjeeBot {

	private static final String _guildID = "";
	private static final String _botToken = "";
	private static final String _botPrefix = "!";
	
	private static final String _streamPingRoleName = "StreamPing";
	private static final String _verifiedRoleName = "Sparks";
	
	private static final String _fileFolder = "resources";
	
	private static final String _channelIDsFile = "channelIDs.txt";
	private static final String _welcomeMessageFile = "welcome_message.txt";
	private static final String _pronounsFile = "pronouns.txt";
	
	private JDABuilder _builder;
	private JDA _jda;
	
	private String _verifyChannelID = "0";
	private String _infoChannelID = "0";
	
	private HashMap<String, Role> _rolePronouns;
	
	public BenjeeBot() {
		_builder = JDABuilder.createDefault(_botToken);
	}
	
	
	public boolean connectBot() throws LoginException {
		
		// Create files if not existant
		createFiles();
		
		// Load the channel IDs
		fetchChannelIDs();
		
		// Allow to track guild members
		_builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		
		// Activate listeners
		_builder.addEventListeners(new CommandListener(this));
		_builder.addEventListeners(new GuildJoinListener(this));
		_builder.addEventListeners(new ReadyListener(this));
		
		// Set bot activity
		_builder.setActivity(Activity.playing("with Raichus -w-"));
		
		// Connect to Discord
		 _jda = _builder.build();
		
		return true;
		
	}
	
	public String getGuildID() {
		return _guildID;
	}
	
	public String getPrefix() {
		return _botPrefix;
	}
	
	public String getStreamPingRoleName() {
		return _streamPingRoleName;
	}
	
	public String getVerifiedRoleName() {
		return _verifiedRoleName;
	}
	
	public String getFileFolder() {
		return _fileFolder;
	}
	
	public String getChannelIDsFile() {
		return _channelIDsFile;
	}
	
	public String getWelcomeMessageFile() {
		return _welcomeMessageFile;
	}
	
	public String getVerifyChannelID() {
		return _verifyChannelID;
	}
	
	public String getPronounsFile() {
		return _pronounsFile;
	}
	
	public HashMap<String, Role> getPronounList() {
		return _rolePronouns;
	}
	
	public String getInfoChannelID() {
		return _infoChannelID;
	}
	
	public void setVerifyChannelID(String newID) {
		_verifyChannelID = newID;
	}

	public void setInfoChannelID(String newID) {
		_infoChannelID = newID;
	}
	
	public void sendWelcomeMessageToChannel(TextChannel tc) {
		
		StringBuilder strBuilder = new StringBuilder();
		String messageLine;
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(_fileFolder + "/" + _welcomeMessageFile)))) {
			while ((messageLine = br.readLine()) != null) {
				strBuilder.append(messageLine + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tc.sendMessage(strBuilder).queue();
		tc.sendMessage("**By typing the !iagree command, you agree to the following:**\n\n" + 
				"**a)** I am aware of the StreamPing role (to get notified when Benjee goes live) & can acquire it with !streamping\n" + 
				"**b)** I agree with the rules\n\nIf you have trouble w/ verification, shoot Benjee a DM! UwU").queue();

	}
	
	public boolean doesUserHaveVerifiedRole(Member member) {
		
		Role verifiedRole = member.getGuild().getRolesByName(getVerifiedRoleName(), true).get(0);
		
		for (Role r : member.getRoles()) {
			if (r.getName().equalsIgnoreCase(verifiedRole.getName())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	
	private void createFiles() {
		
		File resourceDirectory = new File(_fileFolder);
		File channelIDsFile = new File(_fileFolder + "/" + _channelIDsFile);
		File welcomeMessageFile = new File(_fileFolder + "/" + _welcomeMessageFile);
		File pronounsFile = new File (_fileFolder + "/" + _pronounsFile);
		
		try {	
			
			if (!resourceDirectory.exists()) {
				resourceDirectory.mkdir();
			}
			
			if (!channelIDsFile.exists()) {
				
				try (InputStream in = getClass().getResourceAsStream("/" + _fileFolder + "/" +  _channelIDsFile); BufferedReader br = new BufferedReader(new InputStreamReader(in));
						BufferedWriter bw = new BufferedWriter(new FileWriter(channelIDsFile))) {
					
					bw.write(br.readLine() + "\n");
					bw.write(br.readLine());
					
				}
				
			}
			
			if (!welcomeMessageFile.exists()) {
				
				try (InputStream in = getClass().getResourceAsStream("/" + _fileFolder + "/" + _welcomeMessageFile); BufferedReader br = new BufferedReader(new InputStreamReader(in));
						BufferedWriter bw = new BufferedWriter(new FileWriter(welcomeMessageFile))) {
					
					String line;
					while ((line = br.readLine()) != null) {
						bw.write(line + "\n");
					}
					
				}
				
			}
			
			if (!pronounsFile.exists()) {
				
				try (InputStream in = getClass().getResourceAsStream("/" + _fileFolder + "/" + _pronounsFile); BufferedReader br = new BufferedReader(new InputStreamReader(in));
						BufferedWriter bw = new BufferedWriter(new FileWriter(pronounsFile))) {
					
					String line;
					while ((line = br.readLine()) != null) {
						bw.write(line + "\n");
					}
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void fetchChannelIDs() {
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(_fileFolder + "/" + _channelIDsFile)))) {
			_verifyChannelID = br.readLine();
			_infoChannelID = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadPronounsRoles() {
		
		try (BufferedReader br = new BufferedReader(new FileReader(new File(_fileFolder + "/" + _pronounsFile)))) {
			
			Guild myGuild = _jda.getGuildById(_guildID);
			String line = null;
			
			_rolePronouns = new HashMap<String, Role>();
			
			Role pronounRole = null;
			List<Role> tempRoleList = null;
			
			while ((line = br.readLine()) != null) {
				
				tempRoleList = myGuild.getRolesByName(line, true);
				
				if (tempRoleList.size() < 1) {
					pronounRole = myGuild.createRole().setName(line).complete();
				} else {
					pronounRole = tempRoleList.get(0);
				}
				
				_rolePronouns.put(line, pronounRole);
				
			}
			
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
}
