package common.event.changeorder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LabelChangeReadyForChangeOrderEvent(
    Integer version,
    UUID labelChangeId,
    String labelChangeNumber,
    String dispatchEventId,
    LocalDateTime dispatchDate,
    List<RegistrationSnapshot> registrations) {}
