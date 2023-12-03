package mainservice.event.dto.filter;

import lombok.Builder;
import lombok.Value;
import mainservice.event.dto.EventSort;

import java.time.LocalDateTime;
import java.util.List;
@Value
@Builder
public class PublicEventFilterQuery {

    String text;
    List<Long> categories;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    EventSort sort;
    Integer from;
    Integer size;

}
