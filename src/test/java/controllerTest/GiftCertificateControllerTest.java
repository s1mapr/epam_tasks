package controllerTest;


import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import com.epam.esm.dto.SearchParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private GiftCertificateController giftCertificateController;

    @Mock
    private TagGiftServiceImpl tagGiftService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(giftCertificateController).build();
    }

    @Test
    public void testGetAllGiftCertificates() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/certificate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testCreateGiftCertificate() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO();
        certificateDTO.setName("Test certificate");
        certificateDTO.setDescription("Test certificate description");
        certificateDTO.setPrice(100.0);

        when(tagGiftService.createGiftCertificate(any(GiftCertificateDTO.class))).thenReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/certificate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(certificateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Created gift certificate with id 1"))
                .andReturn();
    }

    @Test
    public void testDeleteGiftCertificate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/certificate/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateGiftCertificateById() throws Exception {
        GiftCertificateDTO certificateDTO = new GiftCertificateDTO();
        certificateDTO.setName("Updated certificate");
        certificateDTO.setDescription("Updated certificate description");
        certificateDTO.setPrice(100.0);

        mockMvc.perform(MockMvcRequestBuilders.patch("/certificate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(certificateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Updated gift certificate with id 1"));
    }

    @Test
    public void testGetCertificatesByTagName() throws Exception {
        List<GiftCertificateDTO> certificates = Arrays.asList(new GiftCertificateDTO(), new GiftCertificateDTO());
        when(tagGiftService.getCertificatesBySearchParams(any(SearchParams.class))).thenReturn(certificates);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificate/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetCertificateById() throws Exception {
        GiftCertificateDTO certificate = new GiftCertificateDTO();
        when(tagGiftService.getCertificateDTOById(1)).thenReturn(certificate);

        mockMvc.perform(MockMvcRequestBuilders.get("/certificate/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

}
