package com.abhinav.ConsumerFleetHub.ResponseDTOs;

import com.abhinav.ConsumerFleetHub.DTOs.VehicleAndTransporterDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleAndTransporterDetailsResponseDto {
    private VehicleAndTransporterDetails vehicleAndTransporterDetails;
    private String msg;
}
