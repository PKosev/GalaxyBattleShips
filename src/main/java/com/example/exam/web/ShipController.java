package com.example.exam.web;

import com.example.exam.model.binding.ShipBindingModel;
import com.example.exam.model.service.ShipServiceModel;
import com.example.exam.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/ships")
public class ShipController {
    private final ShipService shipService;
    private final ModelMapper modelMapper;

    public ShipController(ShipService shipService, ModelMapper modelMapper) {
        this.shipService = shipService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String add(){
        return "ship-add";
    }
    @PostMapping("/add")
    public String addConfirm(@Valid ShipBindingModel shipBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("shipBindingModel", shipBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shipBindingModel",bindingResult);
            return "redirect:add";
        }
        shipService
                .addShip(modelMapper.map(shipBindingModel, ShipServiceModel.class));
        return "redirect:/";
    }


    @ModelAttribute
    public ShipBindingModel orderBindingModel(){
        return new ShipBindingModel();
    }
}
