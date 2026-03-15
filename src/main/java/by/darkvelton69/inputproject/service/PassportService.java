package by.darkvelton69.inputproject.service;

import by.darkvelton69.inputproject.dto.PassportRequest;
import by.darkvelton69.inputproject.dto.PassportResponse;
import by.darkvelton69.inputproject.entity.Passport;
import by.darkvelton69.inputproject.entity.User;
import by.darkvelton69.inputproject.exception.NotFoundException;
import by.darkvelton69.inputproject.mapper.PassportMapper;
import by.darkvelton69.inputproject.repository.PassportRepository;
import by.darkvelton69.inputproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassportService {
    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;
    private final UserRepository userRepository;

    @Transactional
    public PassportResponse addPassport(PassportRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(()->
                new NotFoundException("Пациент не найден"));

        if(passportRepository.countByUser_Id(user.getId())>1){
            throw new RuntimeException("Пользователь не может иметь больше одного паспорта. Если вы всё таки имеете больше одного пасспорта советую пойти в полицию");
        }

        Passport passport = passportMapper.toEntity(request);

        passport.setUser(user);

        Passport savedPassport = passportRepository.save(passport);

        return passportMapper.toResponse(savedPassport);

    }

    @Transactional
    public PassportResponse editPassport(PassportRequest passportRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Passport passport = passportRepository.findByUserEmail(email);

        passportMapper.updateEntityFromDto(passportRequest, passport);

        Passport savedPassport = passportRepository.save(passport);

        return passportMapper.toResponse(savedPassport);


    }
}
