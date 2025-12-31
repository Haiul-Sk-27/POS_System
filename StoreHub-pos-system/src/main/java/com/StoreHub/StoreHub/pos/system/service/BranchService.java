package com.StoreHub.StoreHub.pos.system.service;

import com.StoreHub.StoreHub.pos.system.exceptions.UserException;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.BranchDto;

import java.util.List;

public interface BranchService {

    BranchDto createBranch(BranchDto branchDto) throws UserException;
    BranchDto updateBranch (Long id,BranchDto branchDto) throws Exception;
    void deleteBranch (Long id) throws Exception;
    List<BranchDto> getAllBranchesByStore(Long storeId);
    BranchDto getBranchById(Long id) throws Exception;
}
