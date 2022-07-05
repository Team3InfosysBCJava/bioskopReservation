package com.teamc.bioskop.MVCController;



import com.teamc.bioskop.DTO.UserResponseDTO;
import com.teamc.bioskop.Model.User;
import com.teamc.bioskop.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class UserMVCController {

    private final UserService userService;

    @GetMapping("/MVC/User/Signup")
    public String showUserForm(User user) {
        return "User_AddNew";
    }


    @PostMapping("/MVC/User/add-user")
    public String showAddUSer(@Valid User user, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "User_AddNew";
        }

        userService.createUser(user);
        return "redirect:/MVC/User";
    }


    @GetMapping("/MVC/User")
    public String showUserlist(Model model) {
        List<User> User = userService.getAll();
        //alias masuk ke file html
        model.addAttribute("User_masuk", User);
        return "User_Index"; //ngambil file html
    }


    @GetMapping("/MVC/User/{id}")
    public String showUserlist(@PathVariable("id") Long id, Model model){

        Optional<User> user = userService.getUserById(id);
        User userget = user.get();
        UserResponseDTO result = userget.convertToResponse();
        model.addAttribute("user_entry", result);
        return "User_GetById";
    }


    @PostMapping("/MVC/User/update-user/{id}")
    public String showUpdateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            user.setUserId(id);
            return "User_Update";
        }

        user.setUserId(id);
        userService.updateUser(user);
        return "redirect:/MVC/User";
    }


    @GetMapping("/MVC/User/delete/{id}")
    public String showDeleteUser(@PathVariable("id") Long id, Model model) {
;
        userService.deleteUserById(id);
        return "redirect:/MVC/User";
    }


}
