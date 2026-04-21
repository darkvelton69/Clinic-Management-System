package by.darkvelton69.inputproject.tgBot.handler;

import by.darkvelton69.inputproject.entity.Doctor;
import by.darkvelton69.inputproject.repository.DoctorRepository;
import by.darkvelton69.inputproject.tgBot.bot.AppointmentDraft;
import by.darkvelton69.inputproject.tgBot.bot.BotState;
import by.darkvelton69.inputproject.tgBot.bot.UserSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecialtyCallbackHandler implements TelegramCallbackHandler{

    private final UserSessionManager sessionManager;
    private final DoctorRepository doctorRepository;

    public SpecialtyCallbackHandler(UserSessionManager sessionManager, DoctorRepository doctorRepository) {
        this.sessionManager = sessionManager;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public String getCallbackData() {
        return "SPEC_";
    }

    @Override
    @Transactional
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String callbackData = callbackQuery.getData();

        String specialty = callbackData.replace("SPEC_", "");

        AppointmentDraft draft = sessionManager.getDraft(chatId);
        draft.setSpecialty(specialty);

        sessionManager.setUserState(chatId, BotState.WAITING_FOR_DOCTOR);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        String specialtyName = specialty.equals("THERAPIST") ? "Терапевт" : "Хирург";
        message.setText("Шаг 2/4: Специальность ("+ specialtyName +") выбрана.\nВыберите лучащего врача:");

        message.setReplyMarkup(getDoctorsKeyboard(specialty));

        return message;

    }

    private InlineKeyboardMarkup getDoctorsKeyboard(String specialty){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<Doctor> doctors = doctorRepository.findByJobTitle(specialty);


        for(Doctor doctor : doctors) {

            if (specialty.equals("THERAPIST")) {
                rows.add(List.of(createDoctorButton(doctor.getUser().getMiddleName()+" "+doctor.getUser().getFirstName().charAt(0)+"."+doctor.getUser().getLastName().charAt(0)+".", doctor.getId())));
            } else if (specialty.equals("SURGEON")) {
                rows.add(List.of(createDoctorButton(doctor.getUser().getMiddleName()+" "+doctor.getUser().getFirstName().charAt(0)+"."+doctor.getUser().getLastName().charAt(0)+".", doctor.getId())));
            }else if (specialty.equals("UROLOGIST")){
                rows.add(List.of(createDoctorButton(doctor.getUser().getMiddleName()+" "+doctor.getUser().getFirstName().charAt(0)+"."+doctor.getUser().getLastName().charAt(0)+".", doctor.getId())));
            }
        }
        markup.setKeyboard(rows);
        return markup;

    }

    private InlineKeyboardButton createDoctorButton(String name, Long doctorId){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(name);
        button.setCallbackData("DOC_"+doctorId);
        return button;
    }
}
