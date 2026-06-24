package com.abhinav.ConsumerFleetHub.ResponseDTOs;

import com.abhinav.ConsumerFleetHub.DTOs.VehicleAndTransporterDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CreateLoadQueryResponseDto {
    List<VehicleAndTransporterDetails> vehicleAndTransporterDetailsList;
    String msg;
    String status;
}
