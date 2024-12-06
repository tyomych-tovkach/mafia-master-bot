package ru.tayviscon.mafia.master.bot.service.consumer;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;


public interface TelegramUpdateConsumer extends Consumer<Update> {


}
