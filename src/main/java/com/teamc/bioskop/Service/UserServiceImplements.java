package com.teamc.bioskop.Service;

import java.util.List;
import java.util.Optional;

import com.teamc.bioskop.Model.Films;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.teamc.bioskop.Repository.UserRepository;
import com.teamc.bioskop.Model.User;
import com.teamc.bioskop.Exception.ResourceNotFoundException;

@Service
@AllArgsConstructor
public class UserServiceImplements implements UserService {
    private final UserRepository userRepository;

    /***
     * Get All User
     * @return
     */
    public List<User> getAll() {
        List<User> user = userRepository.findAll();
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not exist with id :");
        }
        return this.userRepository.findAll();

    }

    /***
     * Get User By ID
     * @param users_Id
     * @return
     */
    public Optional<User> getUserById(Long users_Id) {
        Optional<User> optionalUser = userRepository.findById(users_Id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        return this.userRepository.findById(users_Id);
    }

    /***
     * Create User
     * @param user
     * @return
     */
    public User createUser(User user) {

        return this.userRepository.save(user);
    }

    /***
     * Delete User
     * @param users_Id
     */
    @Override
    public void deleteUserById(Long users_Id) {
        Optional<User> optionalUser = userRepository.findById(users_Id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        User user = userRepository.getReferenceById(users_Id);
        this.userRepository.delete(user);
    }

    /***
     * Update User
     * @param user
     * @return
     * @throws Exception
     */
    public User updateUser(User user) throws Exception {
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not exist with id :" + user.getUserId());
        }
        return this.userRepository.save(user);
    }

    @Override
    public User getReferenceById(Long Id) {
        return this.userRepository.getReferenceById(Id);
    }

    @Override
    public List<User> search(String keyword) {
        if (keyword != null) {
            return userRepository.search(keyword);
        }
        return userRepository.findAll();


    }
}