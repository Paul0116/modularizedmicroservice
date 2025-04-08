import com.modularizedmicroservice.productservice.application.dto.request.CreateProductRequest;
import com.modularizedmicroservice.productservice.application.dto.response.ProductResponse;
import com.modularizedmicroservice.productservice.application.dtoConverter.ProductResponseDtoConverter;
import com.modularizedmicroservice.productservice.application.usecase.CreateSingleProductUseCase;
import com.modularizedmicroservice.productservice.domain.model.Products;
import com.modularizedmicroservice.productservice.infrastructure.respository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // This enables Mockito in JUnit 5 tests
public class CreateSingleProductUseCaseTest {

    @Mock
    private ProductsRepository productsRepository;  // Mock the repository
    @Mock
    private ProductResponseDtoConverter productResponseDtoConverter;  // Mock the converter

    @InjectMocks
    private CreateSingleProductUseCase createSingleProductUseCase;  // Inject mocks into the use case

    @BeforeEach
    public void setUp() {
        // Set up any necessary mock behavior before each test
    }

    @Test
    public void testCreateProductIsNonBlocking() {
        // Given a valid CreateProductRequest
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Test Product");
        request.setSku("SKU123");
        request.setDescription("Test product description");
        request.setTags("test");
        request.setCategory_id(1);
        request.setStatus(1);  // Assuming status 1 is valid

        ProductResponse mockResponse = new ProductResponse();  // Mock response from the use case
        when(productsRepository.save(any())).thenReturn(Mono.just(new Products()));  // Mock save
        when(productResponseDtoConverter.convert(any(), any())).thenReturn(mockResponse);  // Mock conversion

        // When calling execute
        Mono<ProductResponse> responseMono = createSingleProductUseCase.execute(request);

        // Then use StepVerifier to verify the reactive flow is non-blocking
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response != null)  // Check the response is not null
                .verifyComplete();  // Ensure the Mono completes successfully

        // Verify that the repository and converter were called
        verify(productsRepository, times(1)).save(any());
        verify(productResponseDtoConverter, times(1)).convert(any(), any());
    }
}
