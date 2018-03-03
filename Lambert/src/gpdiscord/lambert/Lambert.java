package gpdiscord.lambert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;

public final class Lambert implements EventListener
{
	private HashMap<Long, Integer> scores = new HashMap<Long, Integer>();
	
	@Override
    public void onEvent(Event event)
    {
        if (event instanceof ReadyEvent)
        {
            System.out.println("Bot is ready!");
        }
        else if (event instanceof MessageReceivedEvent)
        {
        	MessageReceivedEvent msg = (MessageReceivedEvent) event;
        	
        	if (msg.getMessage().getContentDisplay().startsWith("!level"))
        	{
        		int score = scores.getOrDefault(msg.getAuthor().getIdLong(), 0);
        		msg.getChannel().sendMessage(msg.getAuthor().getAsMention() + " you have " + score + " pixels!\nProgress to next level: " + score + "/2").complete();
        	}
        	else if (msg.getMessage().getContentDisplay().contains("thanks") || msg.getMessage().getContentDisplay().contains("thx") || msg.getMessage().getContentDisplay().contains("thank you"))
        	{
        		List<Member> thanked = msg.getMessage().getMentionedMembers();
        		for (Member member : thanked)
        		{
        			msg.getChannel().sendMessage(member.getUser().getAsMention() + " your helpfulness has earned you 1 pixels!").complete();
        			scores.put(member.getUser().getIdLong(), scores.getOrDefault(member.getUser().getIdLong(), 0) + 1);
        		}
        	}
        }
    }
	
	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, IOException
	{
		new JDABuilder(AccountType.BOT).setToken(
				Files.readAllLines(new File("bot-info.txt").toPath()).get(0).substring(7)
				).addEventListener(new Lambert()).buildBlocking();
	}
}
