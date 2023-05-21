package com.rosatom.hackaton.model.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationPrizesDto {
    private Integer first_places;
    private Integer second_places;
    private Integer third_places;
}
