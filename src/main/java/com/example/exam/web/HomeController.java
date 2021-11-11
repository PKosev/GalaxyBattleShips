package com.example.exam.web;

import com.example.exam.model.binding.FightBindingModel;
import com.example.exam.model.entity.Ship;
import com.example.exam.model.view.ShipViewModel;
import com.example.exam.security.CurrentUser;
import com.example.exam.service.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {
    private final CurrentUser currentUser;
    private final ShipService shipService;


    public HomeController(CurrentUser currentUser, ShipService shipService) {
        this.currentUser = currentUser;
        this.shipService = shipService;
    }

    @GetMapping()
    public String index(Model model){
        if (currentUser.getId() == null){
            return "index";
        }

        List<ShipViewModel> ships = shipService.findAllShipsOrderById();
        List<ShipViewModel> attackers = shipService.findAllShipsByUser(currentUser.getId());
        List<ShipViewModel> defenders = shipService.findAllShipsNotByUser(currentUser.getId());


        model.addAttribute("ships",ships);
        model.addAttribute("attackers", attackers);
        model.addAttribute("defenders", defenders);

        return "home";
    }
    @PostMapping("/fight")
    public String fight(@Valid FightBindingModel fightBindingModel,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("fightBindingModel", fightBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.fightBindingModel",bindingResult);
            return "redirect:/";
        }
        Ship attacker = shipService.findById(fightBindingModel.getAttacker());
        Ship defender = shipService.findById(fightBindingModel.getDefender());
        if (attacker.getPower() >= defender.getHealth()){
            shipService.deleteById(fightBindingModel.getDefender());
        }else {
            defender.setHealth(defender.getHealth() - attacker.getPower());
            shipService.reAddShip(defender);
        }
        return "redirect:/";
    }
    @ModelAttribute
    public FightBindingModel fightBindingModel(){
        return new FightBindingModel();
    }
}
