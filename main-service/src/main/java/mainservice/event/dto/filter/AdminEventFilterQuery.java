package mainservice.event.dto.filter;

import lombok.Builder;
import lombok.Value;
import mainservice.event.entity.EventStateEnum;

import java.time.LocalDateTime;
import java.util.List;
@Value
@Builder
public class AdminEventFilterQuery {

    List<Long> usersIds;
    List<EventStateEnum> states;
    List<Long> categories;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Integer from;
    Integer size;


}
