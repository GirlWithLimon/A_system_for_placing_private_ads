package serviceTests;

import com.example.dao.AdvertisementDAO;
import com.example.model.Advertisement;
import com.example.model.AdvertisementStatus;
import com.example.service.AdvertisementServiceSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceTest {
    @Mock
    private AdvertisementDAO advertisementDAO;
    @InjectMocks
    private AdvertisementServiceSQL advertisementServiceSQL;

    Advertisement advertisement1;
    Advertisement advertisement2;
    Advertisement advertisementWithoutTitle;

    @BeforeEach
    public void advertisements(){
        advertisement1 = new Advertisement();
        advertisement1.setId(1);
        advertisement1.setTitle("Машина");
        advertisement1.setDescription("Хорошая");
        advertisement1.setPrice(300.0);
        advertisement1.setStatus(AdvertisementStatus.AСTIVE);
        advertisement1.setByeStatus(false);
        advertisement1.setPublicationDate(LocalDate.of(2000,11,21));

        advertisement2 = new Advertisement();
        advertisement2.setId(2);
        advertisement2.setTitle("Мебель");
        advertisement2.setDescription("Хорошая");
        advertisement2.setPrice(100.0);
        advertisement2.setStatus(AdvertisementStatus.AСTIVE);
        advertisement2.setByeStatus(true);
        advertisement2.setPublicationDate(LocalDate.of(2010,11,21));

        advertisementWithoutTitle = new Advertisement();
        advertisementWithoutTitle.setId(3);
        advertisementWithoutTitle.setDescription("Плохая");
        advertisementWithoutTitle.setPrice(300.0);
        advertisementWithoutTitle.setStatus(AdvertisementStatus.AСTIVE);
        advertisement1.setByeStatus(false);
        advertisement1.setPublicationDate(LocalDate.of(2000,12,21));
    }

    //Тестирование вывода всех объявлений
    @Test
    public void TestGetAdvertisement(){
        List<Advertisement> advertisementList = Arrays.asList(advertisement1,advertisement2);
        when(advertisementDAO.findAll()).thenReturn(advertisementList);

        List<Advertisement> result = advertisementServiceSQL.findAll();
        assertEquals(2, result.size());
        assertSame("Машина", result.getFirst().getTitle());
        assertSame("Мебель", result.get(1).getTitle());
        verify(advertisementDAO).findAll();
    }
    @Test
    public void TestGetAdvertisementBad(){
        List<Advertisement> advertisementList = new ArrayList<>();
        when(advertisementDAO.findAll()).thenReturn(advertisementList);

        List<Advertisement> result = advertisementServiceSQL.findAll();
        assertTrue(result.isEmpty());
        verify(advertisementDAO).findAll();
    }

    //Тестирование вывода одного объявления
}
