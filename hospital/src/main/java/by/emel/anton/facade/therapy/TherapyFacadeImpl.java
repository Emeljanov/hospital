package by.emel.anton.facade.therapy;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.entity.therapy.Therapy;
import by.emel.anton.service.UserService;
import by.emel.anton.service.exception.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class TherapyFacadeImpl implements TherapyFacade {

    private UserService userService;
    private Converter<Therapy, ResponseTherapyDTO> converter;

    @Autowired
    public TherapyFacadeImpl(@Qualifier("SpringDataService") UserService userService, Converter<Therapy, ResponseTherapyDTO> converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @Override
    public ResponseTherapyDTO getTherapy(int id) {

        return userService
                .getTherapy(id)
                .map(converter::convert)
                .orElseThrow(() -> new UserServiceException("Didn't fint therapy with id :" + id));
    }
}
