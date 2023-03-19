import api.CategoryService;
import dto.GetCategoryResponse;
import api.CategoryService;
import dto.GetCategoryResponse;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import utils.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class GetCategoryTest {

    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();
        assert response.body() != null;
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product -> assertThat(product.getCategoryTitle(), equalTo("Food")));

    }

    @SneakyThrows
    @Test
    void getCategoryByIdNegativeTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(5).execute();
        assert response.body() == null;
    }

}
