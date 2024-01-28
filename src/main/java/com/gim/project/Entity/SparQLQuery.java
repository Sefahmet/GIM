package com.gim.project.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SparQLQuery {
    @Getter private String select;
    @Getter private List<String> from;
    @Getter private List<String > where;

}
