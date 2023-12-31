package com.example.springproject.config;

import com.example.springproject.model.*;
import com.example.springproject.repository.CategoryRepository;
import com.example.springproject.repository.ProductRepository;
import com.example.springproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.springproject.repository.RoleRepository;

import java.util.stream.Stream;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public InitialDataLoader(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUser();
        loadProducts();
    }

    private void loadProducts() {
        // Add categories first
//        for (category : CategoryEnum.values()) {
//            CategoryModel categoryModel = new CategoryModel(category);
//            categoryRepository.save(categoryModel);
//        }
        Stream.of(CategoryEnum.values()).forEach(
                categoryEnum -> {
                    CategoryModel categoryModel = new CategoryModel(categoryEnum);
                    categoryRepository.save(categoryModel);
                }
        );
        CategoryModel jacketCat = categoryRepository.findByName(CategoryEnum.JACKETS);
        // Add products
        ProductModel jacket1 = new ProductModel(
                "Winter Jacket",
                "https://m.media-amazon.com/images/I/81rntI+0XHL._AC_UX679_.jpg",
                120,
                jacketCat,
                null
        );
        productRepository.save(jacket1);

        ProductModel jacket2 = new ProductModel(
                "Jeans Jacket",
                "https://5.imimg.com/data5/SELLER/Default/2021/7/GN/SV/SO/21288657/denim-jacket-1000x1000.jpeg",
                50,
                jacketCat,
                null
        );
        productRepository.save(jacket2);

        CategoryModel shirtCat = categoryRepository.findByName(CategoryEnum.SHIRTS);
        ProductModel shirt = new ProductModel(
                "White shirt",
                "https://www.schoolwear.ie/wp-content/uploads/2016/04/White-Shirt.jpg",
                30,
                shirtCat,
                null
        );
        productRepository.save(shirt);

        CategoryModel tshirtCat = categoryRepository.findByName(CategoryEnum.TSHIRTS);
        ProductModel tshirt = new ProductModel(
                "Black T-shirt",
                "https://sonnenswim.com/cdn/shop/products/tshirtnegra1_1024x1024@2x.jpg?v=1656020034",
                20,
                tshirtCat,
                null
        );
        productRepository.save(tshirt);
    }

    private void loadUser() {
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
        customer.setPassword(encoder.encode("123"));
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
        seller.setPassword(encoder.encode("123"));
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
