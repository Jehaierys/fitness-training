package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.session.GetCoachSessionDto;
import com.fitnesstraining.domain.dto.session.GetCoachSessionListRequest;
import com.fitnesstraining.domain.dto.session.SessionRegistrationRequest;
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

    private final SessionCreator sessionCreator;
    private final SessionSearcher searcher;


    @Transactional
    public void create(SessionRegistrationRequest request) {
        sessionCreator.create(request);
    }

    public List<GetCoachSessionDto> findSessionsByCoachAndCriteria(GetCoachSessionListRequest request) {
        return searcher.search(request);
    }
}
