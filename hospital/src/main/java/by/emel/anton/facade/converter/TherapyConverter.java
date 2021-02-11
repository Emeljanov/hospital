package by.emel.anton.facade.converter;

import by.emel.anton.facade.therapy.ResponseTherapyDTO;
import by.emel.anton.model.beans.therapy.Therapy;
import org.springframework.stereotype.Component;

@Component
public class TherapyConverter implements Converter<Therapy, ResponseTherapyDTO> {
    @Override
    public ResponseTherapyDTO convert(Therapy from) {

        ResponseTherapyDTO responseTherapyDTO = new ResponseTherapyDTO();
        responseTherapyDTO.setTherapyId(from.getId());
        responseTherapyDTO.setDescription(from.getDescription());
        responseTherapyDTO.setEndDate(from.getEndDate());
        responseTherapyDTO.setStartDate(from.getStartDate());
        responseTherapyDTO.setPatientId(from.getPatient().getId());

        return responseTherapyDTO;
    }
}
