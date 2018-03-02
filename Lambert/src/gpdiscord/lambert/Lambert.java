package gpdiscord.lambert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;

public final class Lambert implements EventListener
{
	@Override
    public void onEvent(Event event)
    {
        if (event instanceof ReadyEvent)
            System.out.println("Bot is ready...");
    }
	
	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, IOException
	{
		new JDABuilder(AccountType.BOT).setToken(
				Files.readAllLines(new File("bot-info.txt").toPath()).get(0).substring(7)
				).addEventListener(new Lambert()).buildBlocking();
	}
}
