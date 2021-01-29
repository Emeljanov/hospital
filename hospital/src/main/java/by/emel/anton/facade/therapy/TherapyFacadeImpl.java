package by.emel.anton.facade.therapy;

import by.emel.anton.facade.converter.Converter;
import by.emel.anton.model.beans.therapy.Therapy;
import by.emel.anton.model.dao.exceptions.TherapyDaoUncheckedException;
import by.emel.anton.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TherapyFacadeImpl implements TherapyFacade {

    @Autowired
    @Qualifier("SpringDataService")
    private UserService userService;

    @Autowired
    Converter<Therapy, ResponseTherapyDTO> converter;

    @Override
    public ResponseTherapyDTO getTherapy(int id) {

        return userService
                .getTherapy(id)
                .map(converter::convert)
                .orElseThrow(() -> new TherapyDaoUncheckedException("Didn't fint therapy with id :" + id));
    }
}
