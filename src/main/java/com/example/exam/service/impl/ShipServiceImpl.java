package com.example.exam.service.impl;

import com.example.exam.model.entity.Ship;
import com.example.exam.model.service.ShipServiceModel;
import com.example.exam.model.view.ShipViewModel;
import com.example.exam.repository.ShipRepository;
import com.example.exam.security.CurrentUser;
import com.example.exam.service.CategoryService;
import com.example.exam.service.ShipService;
import com.example.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserService userService;
    private final CategoryService categoryService;

    public ShipServiceImpl(ShipRepository shipRepository, ModelMapper modelMapper, CurrentUser currentUser, UserService userService, CategoryService categoryService) {
        this.shipRepository = shipRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public List<ShipViewModel> findAllShips() {
        return shipRepository.findAll()
                .stream()
                .map(ship -> modelMapper.map(ship, ShipViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShipViewModel> findAllShipsByUser(Long id) {
        return shipRepository.findAllByUserID(id)
                .stream()
                .map(ship -> modelMapper.map(ship, ShipViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ShipViewModel> findAllShipsNotByUser(Long id) {
        return shipRepository.findAllByNotUserID(id)
                .stream()
                .map(ship -> modelMapper.map(ship, ShipViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Ship findById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public void reAddShip(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public List<ShipViewModel> findAllShipsOrderById() {
        return shipRepository.findAllByOrderById()
                .stream()
                .map(ship -> modelMapper.map(ship,ShipViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addShip(ShipServiceModel shipServiceModel) {
        Ship ship = modelMapper.map(shipServiceModel, Ship.class);
        ship.setCategory(categoryService.findByCategoryNameEnum(shipServiceModel.getCategory()));
        ship.setUser(userService.findById(currentUser.getId()));
        shipRepository.save(ship);
    }
}
