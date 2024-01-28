package com.gim.project.Service;

import java.util.HashMap;

public interface SparQLService {
    String translateDe2En(String deLabel);
    String getImage(String deLabel);
    String getDescription(String deLabel);
    HashMap<String,String> getAllEnglishVersions();
}
