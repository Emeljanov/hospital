package by.emel.anton.facade.converter;

import by.emel.anton.facade.therapy.ResponseTherapyDTO;
import by.emel.anton.model.entity.therapy.Therapy;
import org.springframework.stereotype.Component;

@Component
public class TherapyConverter implements Converter<Therapy, ResponseTherapyDTO> {
    @Override
    public ResponseTherapyDTO convert(Therapy from) {

        return ResponseTherapyDTO.builder()
                .therapyId(from.getId())
                .description(from.getDescription())
                .endDate(from.getEndDate())
                .startDate(from.getStartDate())
                .patientId(from.getPatient().getId())
                .build();
    }
}
