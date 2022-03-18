package spd.backend.controller;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
public class TestVerifyController {
    /*public MockMvc mockMvc;
    @InjectMocks
    private VerifyController verifyController;

    @Before
    public void setup() {
        verifyController = new VerifyController();
        mockMvc = MockMvcBuilders.standaloneSetup(verifyController).build();
    }

    @Test
    public void test_VerifyRoutes() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/verify");
        MvcResult result = mockMvc.perform(request).andReturn();
        MatcherAssert.assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));
    }*/
}
