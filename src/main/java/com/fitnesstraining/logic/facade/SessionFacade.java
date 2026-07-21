package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.dto.request.session.SessionSearchCriteria;
import com.fitnesstraining.domain.dto.response.SessionDto;
import com.fitnesstraining.logic.processor.SessionCreator;
import com.fitnesstraining.logic.processor.SessionSearcher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionFacade {

    private final SessionCreator creator;
    private final SessionSearcher searcher;


    @Transactional
    public void create(SessionRegistrationRequest request) {
        creator.create(request);
    }

    public List<SessionDto> findSessionsByCriteria(SessionSearchCriteria criteria) {
        return searcher.searchByCriteria(criteria);
    }
}
