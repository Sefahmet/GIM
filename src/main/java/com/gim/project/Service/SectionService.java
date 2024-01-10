package com.gim.project.Service;

import com.gim.project.Entity.Section;
import com.gim.project.Entity.Street;
import com.gim.project.Repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SectionService {
    List<Section> getAllSections();

}
