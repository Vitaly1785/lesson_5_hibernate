package ru.geekbrains;

import ru.geekbrains.product.Product;
import ru.geekbrains.productDao.ProductDao;

public class Application {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        System.out.println(productDao.findById(1L).toString());
        productDao.findAll().forEach(product -> System.out.println(product.toString()));
//        productDao.delete(8L);
        productDao.saveOrUpdate(new Product("Samsung", 500));
    }
}
