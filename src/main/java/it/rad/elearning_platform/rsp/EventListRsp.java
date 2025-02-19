package it.rad.elearning_platform.rsp;

import it.rad.elearning_platform.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventListRsp {

    List<Event> EvAppointment;
    List<Event> EvReminder;
}
