package com.example.springproject.config;

import com.example.springproject.model.UserModel;
import com.example.springproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.springproject.model.RoleModel;
import com.example.springproject.repository.RoleRepository;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public InitialDataLoader(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        RoleModel customerRole = new RoleModel();
        customerRole.setName("CUSTOMER");
        roleRepository.save(customerRole);

        RoleModel sellerRole = new RoleModel();
        sellerRole.setName("SELLER");
        roleRepository.save(sellerRole);

        // Create and insert a customer
        UserModel customer = new UserModel();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setUsername("user");
        customer.setPassword("123");
        customer.setEmail("customer@example.com");
        customer.setPhone("064581154");
        customer.setAddress("StreetCust");
        customer.setCity("CityCust");

        // Assign the CUSTOMER role to the customer
        RoleModel custRole = roleRepository.findByName("CUSTOMER");
        customer.getRoles().add(custRole);

        userRepository.save(customer);

        // Create and insert a seller
        UserModel seller = new UserModel();
        seller.setFirstName("Jane");
        seller.setLastName("Smith");
        seller.setUsername("seller");
        seller.setPassword("123");
        seller.setEmail("seller@example.com");
        seller.setPhone("0626897145");
        seller.setAddress("StreetSell");
        seller.setCity("CitySell");

        // Assign the SELLER role to the seller
        RoleModel sellRole = roleRepository.findByName("SELLER");
        seller.getRoles().add(sellRole);

        userRepository.save(seller);
    }
}
