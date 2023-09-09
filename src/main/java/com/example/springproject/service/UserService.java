package com.example.springproject.service;

import com.example.springproject.model.RoleModel;
import com.example.springproject.model.UserModel;
import com.example.springproject.repository.RoleRepository;
import com.example.springproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
            userModel.setPassword(encoder.encode(password));
            userModel.setEmail(email);
            userModel.setPhone(phone);
            userModel.setAddress(address);
            userModel.setCity(city);

            RoleModel customerRole = roleRepository.findByName("CUSTOMER");
            userModel.getRoles().add(customerRole);

            return userRepository.save(userModel);
        }
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findFirstByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public UserModel authenticate(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

//    @Transactional
    public UserModel updateUser(UserModel updatedUserModel) {
        UserModel existingUser = userRepository.findById(updatedUserModel.getId()).orElseThrow(
                () -> new IllegalStateException("User with id " + updatedUserModel.getId() + " not exists!")
        );
        boolean anyFieldChanged = false;
        if (!existingUser.getFirstName().equals(updatedUserModel.getFirstName())){
            existingUser.setFirstName(updatedUserModel.getFirstName());
            anyFieldChanged = true;
        }
        if (!existingUser.getLastName().equals(updatedUserModel.getLastName())){
            existingUser.setLastName(updatedUserModel.getLastName());
            anyFieldChanged = true;
        }
        if (!existingUser.getEmail().equals(updatedUserModel.getEmail())){
            existingUser.setEmail(updatedUserModel.getEmail());
            anyFieldChanged = true;
        }
        if (!existingUser.getPhone().equals(updatedUserModel.getPhone())){
            existingUser.setPhone(updatedUserModel.getPhone());
            anyFieldChanged = true;
        }
        if (!existingUser.getAddress().equals(updatedUserModel.getAddress())){
            existingUser.setAddress(updatedUserModel.getAddress());
            anyFieldChanged = true;
        }
        if (!existingUser.getCity().equals(updatedUserModel.getCity())){
            existingUser.setCity(updatedUserModel.getCity());
            anyFieldChanged = true;
        }
        userRepository.save(existingUser);
        return anyFieldChanged? existingUser : null;
    }
}
