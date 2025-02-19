package it.rad.elearning_platform.responseBody;

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
