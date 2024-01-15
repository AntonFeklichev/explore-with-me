package mainservice.event.repository;

import mainservice.event.dto.EventSort;
import mainservice.event.dto.filter.AdminEventFilterQuery;
import mainservice.event.dto.filter.PublicEventFilterQuery;
import mainservice.event.entity.Event;
import mainservice.event.entity.EventStateEnum;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

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

        List<EventStateEnum> states = filterQuery.getStates();
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
                .setFirstResult(filterQuery.getFrom())
                .setMaxResults(filterQuery.getSize());

        return query.getResultList();

    }

    @Override
    public List<Event> getFilteredEventForPublic(PublicEventFilterQuery filterQuery) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);

        Root<Event> eventRoot = criteriaQuery.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        String text = filterQuery.getText();

        if (text != null && !text.isBlank()) {

            text = text.toLowerCase();

            Predicate doAnnotationPred = criteriaBuilder.like(criteriaBuilder
                    .lower(eventRoot.get("annotation")), "%" + text + "%");
            Predicate doDescriptionPred = criteriaBuilder.like(criteriaBuilder
                    .lower(eventRoot.get("description")), "%" + text + "%");
            Predicate doAnnotOrDescrPred = criteriaBuilder.or(doAnnotationPred, doDescriptionPred);

            predicates.add(doAnnotOrDescrPred);
        }

        List<Long> categories = filterQuery.getCategories();
        if (categories != null && !categories.isEmpty()) {
            predicates.add(eventRoot.get("category").in(categories));
        }

        Boolean paid = filterQuery.getPaid();
        if (paid != null) {
            predicates.add(eventRoot.get("paid").in(paid));
        }

        LocalDateTime rangeStart = filterQuery.getRangeStart();
        LocalDateTime rangeEnd = filterQuery.getRangeEnd();
        if (rangeStart == null || rangeEnd == null) {
            predicates.add(criteriaBuilder
                    .greaterThanOrEqualTo(eventRoot.get("eventDate"), now()));
        }

        if (rangeStart != null) {
            predicates.add(criteriaBuilder
                    .greaterThanOrEqualTo(eventRoot.get("eventDate"), rangeStart));
        }

        if (rangeEnd != null) {
            predicates.add(criteriaBuilder
                    .lessThanOrEqualTo(eventRoot.get("eventDate"), rangeEnd));
        }

        Predicate onlyPublished = criteriaBuilder
                .equal(eventRoot.get("state"), EventStateEnum.PUBLISHED);
        predicates.add(onlyPublished);

        Order sortingOrder;
        EventSort sort = filterQuery.getSort();
        switch (sort) {
            case EVENT_DATE: {
                sortingOrder = criteriaBuilder.asc(eventRoot.get("eventDate"));
                break;
            }
            case VIEWS: {
                sortingOrder = criteriaBuilder.desc(eventRoot.get("views"));
                break;
            }
            default: {
                sortingOrder = criteriaBuilder.desc(eventRoot.get("views"));
            }

        }

        criteriaQuery
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(sortingOrder);

        TypedQuery<Event> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(filterQuery.getFrom())
                .setMaxResults(filterQuery.getSize());

        return query.getResultList();
    }

}


