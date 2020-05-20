package usecase;


import com.soares.core.entity.Contact;
import com.soares.core.entity.TypeContact;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.usecase.contact.impl.AddContact;
import org.junit.Assert;
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
public class AddContactUseCaseTest {

    @InjectMocks
    private AddContact addContactUseCase;
    @Mock
    private IContactGateway contactGateway;


    @Test
    public void shouldAddContact(){
        final var contact = Contact
                .builder()
                .peopleId("ertretre-rytry")
                .typeContact(TypeContact.EMAIL)
                .build();

        final var contactResponse = Contact
                .builder()
                .id("1")
                .peopleId("ertretre-rytry")
                .typeContact(TypeContact.EMAIL)
                .build();

        Mockito.when(contactGateway.save(any(Contact.class)))
                .thenReturn(Mono.just(contactResponse));

        final var response =  addContactUseCase.execute(contact);

        Assert.assertNotNull(response);

        StepVerifier.create(response)
                .expectNextMatches(contactResp -> {
                    Assert.assertEquals("1", contactResp.getId());
                    return true;
                })
                .verifyComplete();

    }
}
