package ru.tayviscon.mafia.master.bot.service.handler.control.command;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
public class RulesTelegramBotCommandHandler implements TelegramBotCommandHandler {

    private static final String NAME = "rules";

    @Value("${telegram.files.static.rules.path}")
    private Resource rulesResource;
    @Value("${telegram.files.static.rules.alternative-web-path}")
    private String alternativeWebResource;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getLabel() {
        return "Правила";
    }

    @Override
    @SneakyThrows
    public void handle(Update update, TelegramLongPollingBot telegramBot) {
        try (
            var inputStream = rulesResource.getInputStream();
        ) {
            var sendDocument = new SendDocument();
            sendDocument.setChatId(update.getMessage().getChatId());
            var rulesInputFile = new InputFile(inputStream, rulesResource.getFilename());
            sendDocument.setDocument(rulesInputFile);
            sendDocument.setCaption("Вот правила");
            telegramBot.execute(sendDocument);
        } catch (IOException e) {
            var sendMessage = new SendMessage();
            sendMessage.setText(
                String.format("Вы можете ознакомиться правилами на нашем сайте: %s", alternativeWebResource)
            );
            sendMessage.setChatId(update.getMessage().getChatId());
            telegramBot.execute(sendMessage);
        }
    }
}
