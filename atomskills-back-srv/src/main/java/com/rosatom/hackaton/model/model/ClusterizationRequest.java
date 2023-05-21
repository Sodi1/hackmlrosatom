package com.rosatom.hackaton.model.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClusterizationRequest {
    private List<Long> file_id;
}
