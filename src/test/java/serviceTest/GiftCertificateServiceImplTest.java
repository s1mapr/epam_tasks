package serviceTest;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Test
    public void testCreateGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1); // Assuming you set the ID when saving

        when(giftCertificateRepository.save(any(GiftCertificate.class))).thenReturn(giftCertificate);

        int createdId = giftCertificateService.createGiftCertificate(giftCertificate);

        assertEquals(1, createdId);
    }

    @Test
    public void testGetAllGiftCertificates() {
        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(new GiftCertificate());

        when(giftCertificateRepository.findAll()).thenReturn(certificates);

        List<GiftCertificate> result = giftCertificateService.getAllGiftCertificates();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllGiftCertificatesWithPagination() {
        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(new GiftCertificate());

        Page<GiftCertificate> page = new PageImpl<>(certificates);

        when(giftCertificateRepository.findAll(any(PageRequest.class))).thenReturn(page);

        List<GiftCertificate> result = giftCertificateService.getAllGiftCertificatesWithPagination(0);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetGiftCertificateById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        when(giftCertificateRepository.getGiftCertificateById(1)).thenReturn(Optional.of(giftCertificate));

        GiftCertificate result = giftCertificateService.getGiftCertificateById(1);

        assertNotNull(result);
    }

    @Test
    public void testDeleteGiftCertificateById() {
        assertDoesNotThrow(() -> giftCertificateService.deleteGiftCertificateById(1));
        verify(giftCertificateRepository).deleteGiftCertificateById(1);
    }

    @Test
    public void testUpdateGiftCertificate() {
        GiftCertificate oldGiftCertificate = new GiftCertificate();
        when(giftCertificateRepository.getGiftCertificateById(1)).thenReturn(Optional.of(oldGiftCertificate));

        GiftCertificate newGiftCertificate = new GiftCertificate();
        newGiftCertificate.setName("New Name");

        giftCertificateService.updateGiftCertificate(newGiftCertificate, 1);

        verify(giftCertificateRepository).save(any(GiftCertificate.class));
    }
}