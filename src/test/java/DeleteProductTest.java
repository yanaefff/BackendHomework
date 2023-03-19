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


public class DeleteProductTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    int id;
    int newId;

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
    void deleteProductInFoodCategoryTest() throws IOException {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assert response.body() != null;
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        Response<Product> getResponse = productService.getProductById(id).execute();
        assert getResponse.body() == null;
        assertThat(getResponse.isSuccessful(), CoreMatchers.is(false));
    }


}
