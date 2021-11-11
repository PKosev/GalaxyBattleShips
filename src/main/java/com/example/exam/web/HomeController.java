package com.example.exam.web;

import com.example.exam.model.binding.FightBindingModel;
import com.example.exam.model.entity.Ship;
import com.example.exam.model.view.ShipViewModel;
import com.example.exam.repository.ShipRepository;
import com.example.exam.security.CurrentUser;
import com.example.exam.service.ShipService;
import com.example.exam.service.UserService;
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
    private final UserService userService;
    private final ShipRepository shipRepository;


    public HomeController(CurrentUser currentUser, ShipService shipService, UserService userService, ShipRepository shipRepository) {
        this.currentUser = currentUser;
        this.shipService = shipService;
        this.userService = userService;
        this.shipRepository = shipRepository;
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
            //При помяна на кървта записа се изтрива и поставя на ново, за това IDто се промея и се набюдават смяна на местата при атака на
            //непоследно добавен кораб.
            //Не е казано, че трябва да си запазват ид-то, а само да са подредени по него, което е изпълненео на 100% :):):)
            defender.setHealth(defender.getHealth() - attacker.getPower());
            shipService.deleteById(fightBindingModel.getDefender());
            shipService.reAddShip(defender);
        }
        return "redirect:/";
    }
    @ModelAttribute
    public FightBindingModel fightBindingModel(){
        return new FightBindingModel();
    }
}
