package com.example.springproject.service;

import com.example.springproject.model.RoleModel;
import com.example.springproject.model.UserModel;
import com.example.springproject.repository.RoleRepository;
import com.example.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserModel registerUser(String firstName, String lastName,String username, String password, String email, String phone, String address, String city  ){

        if (username == null || password == null) {
            return null;
        } else {
            if (userRepository.findFirstByUsername(username).isPresent()){
//                System.out.println("Duplicate login");
                return null;
            }
            UserModel userModel = new UserModel();
            userModel.setFirstName(firstName);
            userModel.setLastName(lastName);
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.setEmail(email);
            userModel.setPhone(phone);
            userModel.setAddress(address);
            userModel.setCity(city);

            RoleModel customerRole = roleRepository.findByName("CUSTOMER");
            userModel.getRoles().add(customerRole);

            return userRepository.save(userModel);
        }
    }

    public UserModel authenticate(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}
