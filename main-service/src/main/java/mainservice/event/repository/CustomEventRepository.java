package mainservice.event.repository;

import mainservice.event.dto.EventFullDto;
import mainservice.event.dto.filter.AdminEventFilterQuery;
import mainservice.event.dto.filter.PublicEventFilterQuery;
import mainservice.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomEventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventsListForAdmin(AdminEventFilterQuery filterQuery);
    List<Event> getFilteredEventForPublic(PublicEventFilterQuery filterQuery);

}
