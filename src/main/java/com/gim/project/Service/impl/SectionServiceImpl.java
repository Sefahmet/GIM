package com.gim.project.Service.impl;

import com.gim.project.Entity.Section;
import com.gim.project.Repository.SectionRepository;
import com.gim.project.Service.SectionService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    @Override
    public List<Section> getAllSections() {
        try {
            return sectionRepository.findAllGeometryAsText();
        }catch (Exception e){
            throw e;
        }
    }
}
