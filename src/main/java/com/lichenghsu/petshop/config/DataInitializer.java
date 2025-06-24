package com.lichenghsu.petshop.config;

import com.lichenghsu.petshop.entity.*;
import com.lichenghsu.petshop.repository.*;
import com.lichenghsu.petshop.service.ImageService;
import com.lichenghsu.petshop.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws IOException {
        // 初始化角色
        if (!roleRepository.existsById("ROLE_USER")) {
            roleRepository.save(new Role("ROLE_USER"));
            logger.info("ROLE_USER created.");
        }
        if (!roleRepository.existsById("ROLE_ADMIN")) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            logger.info("ROLE_ADMIN created.");
        }

        // 初始化管理員帳號
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findById("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("初始化失敗：找不到 ROLE_ADMIN"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));

            // 加入新欄位：啟用與建立時間
            admin.setEnabled(true); // 預設啟用
            admin.setCreatedAt(java.time.LocalDateTime.of(1999, 9, 26, 0, 9, 30));

            userRepository.save(admin);
            logger.info("預設管理員帳號建立完成：帳號 admin / 密碼 admin123");
        }

        if (userRepository.count() <= 1) { // 避免重複建立（admin 以外）
            Role userRole = roleRepository.findById("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("找不到 ROLE_USER"));

            for (int i = 1; i <= 4; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setPassword(passwordEncoder.encode("password" + i));
                user.setEmail("user" + i + "@example.com");
                user.setRoles(Collections.singleton(userRole));
                user.setEnabled(true); // 若你有設
                user.setCreatedAt(LocalDateTime.now());
                userRepository.save(user);
            }
            logger.info("4 位一般會員建立完成");
        }

        // 初始化分類
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "測試分類 1"));
            categoryRepository.save(new Category(null, "測試分類 2"));

            logger.info("測試分類建立成功");
        } else {
            logger.info("測試分類已存在，跳過建立。");
        }

        // 初始化標籤
        if (tagRepository.count() == 0) {
            tagRepository.save(new Tag(null, "測試標籤 1"));
            tagRepository.save(new Tag(null, "測試標籤 2"));

            logger.info("測試標籤建立成功");
        } else {
            logger.info("測試標籤已存在，跳過建立。");
        }

        // 初始化商品
        if (productRepository.count() == 0) {
            // 商品 A
            Product p1 = new Product();
            p1.setName("測試商品 A");
            p1.setPrice(499.0);
            p1.setCategory(categoryRepository.findAll().get(0));
            p1.setDescription("這是測試商品 A");

            List<ProductImage> images1 = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                String fileName = String.format("classpath:test-images/product1-%d.jpg", i);
                Resource img = resourceLoader.getResource(fileName);
                Long imageId = imageService.saveImage(img.getInputStream());

                Image image = imageRepository.findById(imageId)
                        .orElseThrow(() -> new RuntimeException("找不到圖片 ID：" + imageId));

                ProductImage pi = new ProductImage();
                pi.setImage(image);
                pi.setProduct(p1);
                images1.add(pi);
            }
            p1.setImages(images1);
            productRepository.save(p1);

            // 商品 B
            Product p2 = new Product();
            p2.setName("測試商品 B");
            p2.setPrice(888.0);
            p2.setCategory(categoryRepository.findAll().get(1));
            p2.setDescription("這是測試商品 B");

            List<ProductImage> images2 = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                String fileName = String.format("classpath:test-images/product2-%d.jpg", i);
                Resource img = resourceLoader.getResource(fileName);
                Long imageId = imageService.saveImage(img.getInputStream());

                Image image = imageRepository.findById(imageId)
                        .orElseThrow(() -> new RuntimeException("找不到圖片 ID：" + imageId));

                ProductImage pi = new ProductImage();
                pi.setImage(image);
                pi.setProduct(p2);
                images2.add(pi);
            }
            p2.setImages(images2);
            productRepository.save(p2);

            logger.info("2 筆測試商品建立完成");
        }

        // 建立測試用訂單
        List<Product> products = productRepository.findAll();
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getUsername().startsWith("user"))
                .toList();

        if (orderRepository.count() == 0 && !users.isEmpty() && !products.isEmpty()) {
            for (User user : users) {
                for (int i = 0; i < 2; i++) {
                    Order order = new Order();
                    order.setUser(user);
                    order.setCreatedAt(LocalDateTime.now().minusDays((long) (Math.random() * 30)));
                    order.setStatus(OrderStatus.PENDING);

                    List<OrderItem> items = new ArrayList<>();
                    for (Product product : products) {
                        OrderItem item = new OrderItem();
                        item.setProduct(product);
                        item.setQuantity((int) (Math.random() * 3) + 1);
                        item.setPrice(product.getPrice() * item.getQuantity());
                        item.setOrder(order);
                        items.add(item);
                    }

                    order.setItems(items);
                    orderRepository.save(order);
                }
            }

            logger.info("為每位會員建立 2 筆測試訂單，共 {} 筆", users.size() * 2);
        }
    }
}

