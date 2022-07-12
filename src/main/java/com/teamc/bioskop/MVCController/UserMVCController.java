package com.teamc.bioskop.MVCController;



import com.teamc.bioskop.DTO.UserResponseDTO;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.User;
import com.teamc.bioskop.Service.UserService;
import com.teamc.bioskop.Service.UserServiceImplements;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

    private final UserServiceImplements userServiceImplements;

    @GetMapping("/MVC/User/Signup")
    public String showUserForm(User user) {
        return "User_AddNew";
    }


    @PostMapping("/MVC/User/add-user")
    public String showAddUSer(@Valid User user, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "redirect:/MVC/User";
        }

        userService.createUser(user);
        return "redirect:/MVC/User";
    }


    @GetMapping("/MVC/User")
    public String showUserlist(Model model, @Param("keyword")String keyword, @Param("page") String page) {

        Integer pageNumber = null;

        if (page != null){
            pageNumber = userServiceImplements.pageUpdate(page);
        }

        Page<User> result = userService.search(keyword, pageNumber);

//        List<User> User = userService.search(keyword);
        model.addAttribute("User_masuk", result);
        model.addAttribute("keyword", keyword);
        model.addAttribute("user_add", new User());
        return "User_Index"; //ngambil file htmm
    }


    @GetMapping("/MVC/User/{id}")
    public String showUserlist(@PathVariable("id") Long id, Model model){

        Optional<User> user = userService.getUserById(id);
        User userget = user.get();
        UserResponseDTO result = userget.convertToResponse();
        model.addAttribute("user_entry", result);
        return "User_GetById";
    }
    @GetMapping("/MVC/User/update/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model){
        Optional<User> user = userService.getUserById(id);
        User userget = user.get();
        model.addAttribute("user", userget);
        return "User_Update";
    }

    @PostMapping("/MVC/User/update-user/{id}")
    public String showUpdateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            user.setUserId(id);
            return "redirect:/MVC/User";
        }

        user.setUserId(id);
        userService.updateUser(user);
        return "redirect:/MVC/User";
    }


    @GetMapping("/MVC/User/delete/{id}")
    public String showDeleteUser(@PathVariable("id") Long id) {
;
        this.userService.deleteUserById(id);
        return "redirect:/MVC/User";
    }


}