package mainservice.event.repository;

import mainservice.event.dto.filter.AdminEventFilterQuery;
import mainservice.event.entity.Event;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomEventRepositoryImpl extends SimpleJpaRepository<Event, Long> implements CustomEventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomEventRepositoryImpl(JpaEntityInformation<Event, ?> entityInformation,
                                     EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
   public List<Event> getEventsListForAdmin(AdminEventFilterQuery filterQuery) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> rootEvent = criteriaQuery.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        List<Long> usersIds = filterQuery.getUsersIds();
        if (usersIds != null && !usersIds.isEmpty()) {
            predicates.add(rootEvent.get("initiator").in(usersIds));
        }

        List<String> states = filterQuery.getStates();
        if (states != null && !states.isEmpty()) {
            predicates.add(rootEvent.get("state").in(states));
        }

        List<Long> categories = filterQuery.getCategories();
        if (categories != null && !categories.isEmpty()) {
            predicates.add(rootEvent.get("category").in(categories));
        }

        LocalDateTime rangeStart = filterQuery.getRangeStart();
        if (rangeStart != null) {
            predicates.add(criteriaBuilder
                    .greaterThanOrEqualTo(rootEvent.get("eventDate"), rangeStart));
        }

        LocalDateTime rangeEnd = filterQuery.getRangeEnd();
        if (rangeEnd != null) {
            predicates.add((criteriaBuilder
                    .lessThanOrEqualTo(rootEvent.get("eventDate"), rangeEnd)));
        }

        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        TypedQuery<Event> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(filterQuery.getSize())
                .setMaxResults(filterQuery.getSize());

        return query.getResultList();

    }


}
