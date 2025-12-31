package com.StoreHub.StoreHub.pos.system.service.impl;

import com.StoreHub.StoreHub.pos.system.exceptions.UserException;
import com.StoreHub.StoreHub.pos.system.mapper.BranchMapper;
import com.StoreHub.StoreHub.pos.system.model.Branch;
import com.StoreHub.StoreHub.pos.system.model.Store;
import com.StoreHub.StoreHub.pos.system.model.User;
import com.StoreHub.StoreHub.pos.system.payload.response.dto.BranchDto;
import com.StoreHub.StoreHub.pos.system.repository.BranchRepository;
import com.StoreHub.StoreHub.pos.system.repository.StoreRepository;
import com.StoreHub.StoreHub.pos.system.service.BranchService;
import com.StoreHub.StoreHub.pos.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private  final  BranchRepository branchRepository;
    private  final  StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDto createBranch(BranchDto branchDto) throws UserException {

        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(branchDto,store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDto updateBranch(Long id, BranchDto branchDto) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                ()-> new Exception("Branch not exists")
        );

        existing.setName(branchDto.getName());
        existing.setWorkingDays(branchDto.getWorkingDays());
        existing.setEmail(branchDto.getEmail());
        existing.setPhone(branchDto.getPhone());
        existing.setAddress(branchDto.getAddress());
        existing.setOpenTime(branchDto.getOpenTime());
        existing.setCloseTime(branchDto.getCloseTime());
        existing.setUpdateAt(LocalDateTime.now());

        Branch updateBranch = branchRepository.save(existing);
        return BranchMapper.toDTO(updateBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {

        Branch existing = branchRepository.findById(id).orElseThrow(
                ()-> new Exception("Branch not exists")
        );

        branchRepository.delete(existing);
    }

    @Override
    public List<BranchDto> getAllBranchesByStore(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public BranchDto getBranchById(Long id) throws Exception {
        Branch existing = branchRepository.findById(id).orElseThrow(
                ()-> new Exception("Branch not exists")
        );
        return BranchMapper.toDTO(existing);
    }
}
