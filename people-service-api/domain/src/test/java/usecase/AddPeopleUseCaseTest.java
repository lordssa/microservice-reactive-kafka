package usecase;


import com.soares.core.entity.People;
import com.soares.core.gateway.IPeopleGateway;
import com.soares.core.usecase.people.AddPeopleUseCase;
import com.soares.core.usecase.people.impl.AddPeople;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AddPeopleUseCaseTest {

    @InjectMocks
    private AddPeople addPeopleUseCase;
    @Mock
    private IPeopleGateway peopleGateway;


    @Test
    public void shouldAddPeople(){
        final var people = People
                .builder()
                .firstName("João")
                .lastName("Oliveira")
                .build();

        final var peopleResponse = People
                .builder()
                .id("1")
                .firstName("João")
                .lastName("Oliveira")
                .build();

        Mockito.when(peopleGateway.save(any(People.class)))
                .thenReturn(Mono.just(peopleResponse));

        final var response =  addPeopleUseCase.execute(people);

        Assert.assertNotNull(response);

        StepVerifier.create(response)
                .expectNextMatches(peopleResp -> {
                    Assert.assertEquals("1", peopleResp.getId());
                    return true;
                })
                .verifyComplete();

    }
}
