package serviceTest;

import com.epam.esm.dao.TagGiftRepository;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.*;
import com.epam.esm.service.*;
import com.epam.esm.service.impl.TagGiftServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagGiftServiceImplTest {

    @Mock
    private TagGiftRepository tagGiftRepository;

    @Mock
    private TagService tagService;

    @Mock
    private GiftCertificateService giftCertificateService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private TagGiftServiceImpl tagGiftService;


    @Test
    public void testGetCertificateDTOById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Test Certificate");

        when(giftCertificateService.getGiftCertificateById(1)).thenReturn(giftCertificate);

        GiftCertificateDTO result = tagGiftService.getCertificateDTOById(1);

        assertEquals(giftCertificate.getName(), result.getName());
    }

    @Test
    public void testGetTagsByCertificateId() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("Test Certificate");

        TagGift tagGift = new TagGift(giftCertificate, new Tag());
        when(giftCertificateService.getGiftCertificateById(1)).thenReturn(giftCertificate);
        when(tagGiftRepository.getTagGiftByGiftCertificateId(1)).thenReturn(Collections.singletonList(tagGift));

        List<TagDTO> result = tagGiftService.getTagsByCertificateId(1);

        assertEquals(1, result.size());
    }
    @Test
    public void testGetTagDTOById() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Test Tag");

        when(tagService.getTagById(1)).thenReturn(tag);

        TagDTO result = tagGiftService.getTagDTOById(1);

        assertEquals(tag.getName(), result.getName());
    }


}