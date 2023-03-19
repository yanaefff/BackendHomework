import api.ProductService;
import com.github.javafaker.Faker;
import dto.Product;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.RetrofitUtils;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


public class GetProductTest {

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
    void setUp() throws IOException {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
        Response<Product> response = productService.createProduct(product).execute();
        id = response.body().getId();

    }

    @Test
    void getProductInFoodCategoryTest() throws IOException {
        Response<Product> getResponse = productService.getProductById(id).execute();
        assert getResponse.body() != null;
        assertThat(getResponse.isSuccessful(), CoreMatchers.is(true));

        assertThat(getResponse.body().getCategoryTitle(), CoreMatchers.equalTo(product.getCategoryTitle()));
        assertThat(getResponse.body().getTitle(), CoreMatchers.equalTo(product.getTitle()));
        assertThat(getResponse.body().getPrice(), CoreMatchers.equalTo(product.getPrice()));
    }


    @Test
    void getProductsInFoodCategoryTest() throws IOException {
        Response<ResponseBody> getAllResponse = productService.getProducts().execute();
        assert getAllResponse.body() != null;
        assertThat(getAllResponse.isSuccessful(), CoreMatchers.is(true));


    }


    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }



}
