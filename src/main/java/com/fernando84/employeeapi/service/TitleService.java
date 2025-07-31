package com.fernando84.employeeapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fernando84.employeeapi.DTO.TitleDTO;
import com.fernando84.employeeapi.model.Title;
import com.fernando84.employeeapi.projections.TitleHistoryDTOProjection;
import com.fernando84.employeeapi.repository.TitlesRepository;

@Service
public class TitleService {

    private TitlesRepository titlesRepository;

    public TitleService(TitlesRepository tr) {
        this.titlesRepository = tr;
    }

    public ResponseEntity<List<TitleDTO>> getTitlesHistory(Long id) {
        List<Title> titles = titlesRepository.findByIdEmployeeId(id);

        List<TitleDTO> dtoList = titles.stream()
                .map(t -> new TitleDTO(
                        t.getId().getTitle(),
                        t.getId().getFromDate(),
                        t.getToDate()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    public List<TitleHistoryDTOProjection> getTitleHistory() {
        return titlesRepository.getTitleHistory();
    }

}
