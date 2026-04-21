package by.darkvelton69.inputproject.tgBot.bot;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionManager {
    private final Map<Long, BotState> userStates = new ConcurrentHashMap<>();

    private final Map<Long, AppointmentDraft> drafts = new ConcurrentHashMap<>();

    public AppointmentDraft getDraft(long chatId){
        return drafts.computeIfAbsent(chatId, k-> new AppointmentDraft());
    }

    public void clearDraft(long chatId){
        drafts.remove(chatId);
    }

    public BotState getUserState(long chatId){
        return userStates.getOrDefault(chatId, BotState.DEFAULT);
    }

    public void setUserState(long chatId, BotState state){
        userStates.put(chatId, state);
    }
}
