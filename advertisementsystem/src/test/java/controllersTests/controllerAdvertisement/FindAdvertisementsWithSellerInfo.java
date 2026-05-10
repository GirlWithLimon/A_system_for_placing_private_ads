package controllersTests.controllerAdvertisement;

import com.example.dto.AdvertisementsDTO;
import com.example.dao.AdvertisementDAO;
import com.example.service.AdvertisementServiceSQL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAdvertisementsWithSellerInfo {

    @Mock
    private AdvertisementDAO advertisementDAO;

    @InjectMocks
    private AdvertisementServiceSQL advertisementServiceSQL;

    private AdvertisementsDTO soldAdDTO;
    private AdvertisementsDTO activeAdDTO;
    private AdvertisementsDTO adWithHighRatingDTO;
    private AdvertisementsDTO adWithZeroRatingDTO;

    @BeforeEach
    public void setUp() {
        // Проданное объявление (byeStatus = true)
        soldAdDTO = new AdvertisementsDTO(
                "Машина",
                "AUTO",
                300.0,
                "seller1",
                4.5
        );

        // Активное объявление (byeStatus = false)
        activeAdDTO = new AdvertisementsDTO(
                "Мебель",
                "FURNITURE",
                100.0,
                "seller2",
                3.8
        );

        // Объявление с высоким рейтингом продавца
        adWithHighRatingDTO = new AdvertisementsDTO(
                "Телефон",
                "ELECTRONICS",
                500.0,
                "topSeller",
                4.9
        );

        // Объявление с продавцом без оценок (рейтинг 0)
        adWithZeroRatingDTO = new AdvertisementsDTO(
                "Книга",
                "BOOKS",
                50.0,
                "newSeller",
                0.0
        );
    }

    /**
     * Тест 1: Успешное получение списка объявлений (проданные + активные)
     */
    @Test
    public void testFindAdvertisementsWithSellerInfo_Success() {
        // Arrange
        List<AdvertisementsDTO> expectedList = Arrays.asList(soldAdDTO, activeAdDTO);
        when(advertisementDAO.findAdvertisementsWithSellerInfo()).thenReturn(expectedList);

        // Act
        List<AdvertisementsDTO> result = advertisementServiceSQL.findAdvertisementsWithSellerInfo();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Машина", result.get(0).getTitle());
        assertEquals("Мебель", result.get(1).getTitle());
        assertEquals(300.0, result.get(0).getPrice());
        assertEquals(100.0, result.get(1).getPrice());

        verify(advertisementDAO).findAdvertisementsWithSellerInfo();
    }

    /**
     * Тест 2: Проверка порядка - сначала проданные, потом активные
     */
    @Test
    public void testFindAdvertisementsWithSellerInfo_OrderCorrect() {
        // Arrange
        List<AdvertisementsDTO> orderedList = Arrays.asList(
                new AdvertisementsDTO("Проданный товар 1", "AUTO", 1000.0, "seller1", 4.5),
                new AdvertisementsDTO("Проданный товар 2", "FURNITURE", 500.0, "seller2", 3.0),
                new AdvertisementsDTO("Активный товар 1", "ELECTRONICS", 200.0, "seller3", 5.0),
                new AdvertisementsDTO("Активный товар 2", "BOOKS", 50.0, "seller4", 4.0)
        );

        when(advertisementDAO.findAdvertisementsWithSellerInfo()).thenReturn(orderedList);

        // Act
        List<AdvertisementsDTO> result = advertisementServiceSQL.findAdvertisementsWithSellerInfo();

        // Assert
        assertNotNull(result);
        assertEquals(4, result.size());

        // Первые два должны быть проданными (byeStatus = true)
        // Последние два - активными (byeStatus = false)
        // Проверяем по названиям, что порядок сохранен
        assertTrue(result.get(0).getTitle().contains("Проданный"));
        assertTrue(result.get(1).getTitle().contains("Проданный"));
        assertTrue(result.get(2).getTitle().contains("Активный"));
        assertTrue(result.get(3).getTitle().contains("Активный"));

        verify(advertisementDAO).findAdvertisementsWithSellerInfo();
    }

    /**
     * Тест 3: Корректность расчета рейтинга продавца
     */
    @Test
    public void testFindAdvertisementsWithSellerInfo_RatingCalculation() {
        // Arrange
        List<AdvertisementsDTO> adsWithDifferentRatings = Arrays.asList(
                adWithHighRatingDTO,   // рейтинг 4.9
                activeAdDTO,           // рейтинг 3.8
                adWithZeroRatingDTO    // рейтинг 0.0 (нет оценок)
        );

        when(advertisementDAO.findAdvertisementsWithSellerInfo()).thenReturn(adsWithDifferentRatings);

        // Act
        List<AdvertisementsDTO> result = advertisementServiceSQL.findAdvertisementsWithSellerInfo();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        // Проверяем рейтинг топ-продавца
        assertEquals(4.9, result.get(0).getScore());

        // Проверяем рейтинг обычного продавца
        assertEquals(3.8, result.get(1).getScore());

        // Проверяем, что у продавца без оценок рейтинг 0.0, а не NULL
        assertEquals(0.0, result.get(2).getScore());

        verify(advertisementDAO).findAdvertisementsWithSellerInfo();
    }

    /**
     * Тест 4: Пустой результат (нет объявлений)
     */
    @Test
    public void testFindAdvertisementsWithSellerInfo_EmptyList() {
        // Arrange
        when(advertisementDAO.findAdvertisementsWithSellerInfo()).thenReturn(Collections.emptyList());

        // Act
        List<AdvertisementsDTO> result = advertisementServiceSQL.findAdvertisementsWithSellerInfo();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(advertisementDAO).findAdvertisementsWithSellerInfo();
    }

    /**
     * Тест 5: Только проданные объявления (нет активных)
     */
    @Test
    public void testFindAdvertisementsWithSellerInfo_OnlySoldAds() {
        // Arrange
        List<AdvertisementsDTO> onlySoldAds = Arrays.asList(soldAdDTO);
        when(advertisementDAO.findAdvertisementsWithSellerInfo()).thenReturn(onlySoldAds);

        // Act
        List<AdvertisementsDTO> result = advertisementServiceSQL.findAdvertisementsWithSellerInfo();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Машина", result.get(0).getTitle());
        assertTrue(result.get(0).getPrice() > 0);

        verify(advertisementDAO).findAdvertisementsWithSellerInfo();
    }

    /**
     * Тест 6: Проверка, что все поля DTO заполнены корректно
     */
    @Test
    public void testFindAdvertisementsWithSellerInfo_AllFieldsFilled() {
        // Arrange
        List<AdvertisementsDTO> fullAd = Arrays.asList(
                new AdvertisementsDTO(
                        "Полный товар",
                        "ELECTRONICS",
                        999.99,
                        "fullSeller",
                        4.7
                )
        );

        when(advertisementDAO.findAdvertisementsWithSellerInfo()).thenReturn(fullAd);

        // Act
        List<AdvertisementsDTO> result = advertisementServiceSQL.findAdvertisementsWithSellerInfo();
        AdvertisementsDTO ad = result.get(0);

        // Assert - проверяем все поля
        assertNotNull(ad.getTitle());
        assertNotNull(ad.getCategory());
        assertNotNull(ad.getSeller());
        assertNotNull(ad.getScore());
        assertNotNull(ad.getPrice());

        assertEquals("Полный товар", ad.getTitle());
        assertEquals("ELECTRONICS", ad.getCategory());
        assertEquals("fullSeller", ad.getSeller());
        assertEquals(4.7, ad.getScore());
        assertEquals(999.99, ad.getPrice());

        verify(advertisementDAO).findAdvertisementsWithSellerInfo();
    }
}