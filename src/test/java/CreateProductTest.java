import api.ProductService;
import com.github.javafaker.Faker;
import api.ProductService;
import dto.Product;
import utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


public class CreateProductTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product).execute(); //создаем продукт
        id = response.body().getId(); //получаем id
        assert response.body() != null;
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        Response<Product> getResponse = productService.getProductById(id).execute();  //находим созданную запись
        assertThat(getResponse.isSuccessful(), CoreMatchers.is(true));
        assertThat(getResponse.body().getCategoryTitle(), CoreMatchers.equalTo(product.getCategoryTitle()));
        assertThat(getResponse.body().getTitle(), CoreMatchers.equalTo(product.getTitle()));
        assertThat(getResponse.body().getPrice(), CoreMatchers.equalTo(product.getPrice()));
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }



}
