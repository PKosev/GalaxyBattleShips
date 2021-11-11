package com.example.exam.service;

import com.example.exam.model.entity.Ship;
import com.example.exam.model.service.ShipServiceModel;
import com.example.exam.model.view.ShipViewModel;

import java.util.List;

public interface ShipService {

    void addShip(ShipServiceModel shipServiceModel);

    List<ShipViewModel> findAllShips();

    List<ShipViewModel> findAllShipsByUser(Long id);

    List<ShipViewModel> findAllShipsNotByUser(Long id);

    Ship findById(Long id);

    void deleteById(Long id);

    void reAddShip(Ship ship);

    List<ShipViewModel> findAllShipsOrderById();
}
