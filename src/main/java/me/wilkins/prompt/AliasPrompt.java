package me.wilkins.prompt;

import me.wilkins.menu.CommandSettings;
import me.wilkins.settings.SettingsSave;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;

public class AliasPrompt extends SimplePrompt {

	public AliasPrompt(){
		super(true);
	}

	@Override
	protected String getPrompt(ConversationContext ctx) {
		return "&8[&6&l!&8]&6 Enter the aliases for this command, separated by '/'. For example 'alias1/alias2/alias3'";
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input) {
		return !input.contains(" ");
	}

	@Override
	protected String getFailedValidationText(ConversationContext context, String invalidInput) {
		return "&8[&c&l!&8]&c Invalid Input!";
	}

	@Override
	protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String s) {
		CommandSettings.setAliases(s);
		new SettingsSave().setAliases(s);
		CommandSettings.needsRestart = true;
		return null;
	}

}
