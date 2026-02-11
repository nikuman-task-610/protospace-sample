package in.tech_camp.protospace_sample.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({ ValidationPriority1.class, ValidationPriority2.class})
public interface ValidationOrder {

}