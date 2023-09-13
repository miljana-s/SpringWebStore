package com.example.springproject.config;

import com.example.springproject.model.*;
import com.example.springproject.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductSizesRepository productSizesRepository;
    private final StockRepository stockRepository;

    public InitialDataLoader(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository, ProductSizesRepository productSizesRepository, StockRepository stockRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productSizesRepository = productSizesRepository;
        this.stockRepository = stockRepository;
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

        ProductSizesModel sizeSmall = new ProductSizesModel(SizeEnum.SMALL);
        ProductSizesModel sizeMedium = new ProductSizesModel(SizeEnum.MEDIUM);
        ProductSizesModel sizeLarge = new ProductSizesModel(SizeEnum.LARGE);

        productSizesRepository.saveAll(Arrays.asList(sizeSmall, sizeMedium, sizeLarge));

        CategoryModel jacketCat = categoryRepository.findByName(CategoryEnum.JACKETS);
        // Add products
        ProductModel jacket1 = new ProductModel(
                "Winter Jacket",
                "https://m.media-amazon.com/images/I/81rntI+0XHL._AC_UX679_.jpg",
                120,
                jacketCat,
                null,
                new HashSet<>(Arrays.asList(sizeSmall, sizeMedium))

        );
        productRepository.save(jacket1);

        ProductModel jacket2 = new ProductModel(
                "Jeans Jacket",
                "https://5.imimg.com/data5/SELLER/Default/2021/7/GN/SV/SO/21288657/denim-jacket-1000x1000.jpeg",
                50,
                jacketCat,
                null,
                new HashSet<>(Arrays.asList(sizeSmall, sizeMedium, sizeLarge))
        );
        productRepository.save(jacket2);

        CategoryModel shirtCat = categoryRepository.findByName(CategoryEnum.SHIRTS);
        ProductModel shirt = new ProductModel(
                "White shirt",
                "https://www.schoolwear.ie/wp-content/uploads/2016/04/White-Shirt.jpg",
                30,
                shirtCat,
                null,
                new HashSet<>(Arrays.asList(sizeSmall))
        );
        productRepository.save(shirt);

        CategoryModel tshirtCat = categoryRepository.findByName(CategoryEnum.TSHIRTS);
        ProductModel tshirt = new ProductModel(
                "Black T-shirt",
                "https://sonnenswim.com/cdn/shop/products/tshirtnegra1_1024x1024@2x.jpg?v=1656020034",
                20,
                tshirtCat,
                null,
                new HashSet<>(Arrays.asList(sizeSmall, sizeMedium, sizeLarge))
        );
        productRepository.save(tshirt);


        // Create stock entries for products and sizes
        createStockEntry(jacket1, sizeSmall, 50);
        createStockEntry(jacket1, sizeMedium, 30);
        createStockEntry(jacket2, sizeSmall, 20);
        createStockEntry(jacket2, sizeMedium, 15);
        createStockEntry(jacket2, sizeLarge, 10);
        createStockEntry(shirt, sizeSmall, 40);
        createStockEntry(tshirt, sizeSmall, 25);
        createStockEntry(tshirt, sizeMedium, 20);
        createStockEntry(tshirt, sizeLarge, 15);
    }

    private void createStockEntry(ProductModel product, ProductSizesModel size, int quantity) {
        StockModel stock = new StockModel(product, size, quantity);
        stockRepository.save(stock);
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
