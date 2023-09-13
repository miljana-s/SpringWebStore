package com.example.springproject.service;

import com.example.springproject.model.RoleModel;
import com.example.springproject.model.UserModel;
import com.example.springproject.model.UserModelDTO;
import com.example.springproject.model.UserModelMapper;
import com.example.springproject.repository.RoleRepository;
import com.example.springproject.repository.UserRepository;
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
        public boolean updateUser(UserModelDTO updatedUserModel) {
        UserModel existingUser = userRepository.findById(updatedUserModel.getId()).orElseThrow(
                () -> new IllegalStateException("User with id " + updatedUserModel.getId() + " not exists!")
        );
        UserModelDTO existingUserDTO = UserModelMapper.toDTO(existingUser);
        if (!existingUserDTO.equals(updatedUserModel)){
                UserModelMapper.toEntity(updatedUserModel, existingUser);
                userRepository.save(existingUser);
                return true;
        }

        return false;
    }


    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id).get();
    }
}
