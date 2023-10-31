package com.example.officebuilding.service.serviceContract;

import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IServiceContractService extends IGeneralService<ServiceContractDTO> {
    ServiceContractDTO update(ServiceContractDTO serviceContractDTO);

    List<ServiceContractDTO> findAllByStatus(Integer scStatus);
    List<ServiceContractDTO> findServiceContractsWithinDateRange(Integer month, Integer year);

    List<ServiceContractDTO> findAllByRoomId(Integer roomId);
}
