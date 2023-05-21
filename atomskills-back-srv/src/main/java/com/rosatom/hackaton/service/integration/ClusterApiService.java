package com.rosatom.hackaton.service.integration;

import com.rosatom.hackaton.model.model.ClusterizationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "clusterApiCommunicationService", url = "${rest.clusterApiCommunicationService.url}")
public interface ClusterApiService {

    @PostMapping("/clusterization")
    void sendClusterization(ClusterizationRequest request);

}
