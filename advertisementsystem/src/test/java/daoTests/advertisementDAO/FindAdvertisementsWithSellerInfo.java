package daoTests.advertisementDAO;

import com.example.dao.AdvertisementDAO;
import com.example.dto.AdvertisementsDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAdvertisementsWithSellerInfo {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<AdvertisementsDTO> query;

    @InjectMocks
    private AdvertisementDAO advertisementDAO;

    private AdvertisementsDTO byeAdvertisementDTO;
    private AdvertisementsDTO notByeAdvertisementDTO;

    @BeforeEach
    public void setUp() {
        byeAdvertisementDTO = new AdvertisementsDTO(
                "Машина",
                "AUTO",
                300.0,
                "seller1",
                4.5
        );

        notByeAdvertisementDTO = new AdvertisementsDTO(
                "Мебель",
                "FURNITURE",
                100.0,
                "seller2",
                3.8
        );
    }

    // Тест 1. Успешное получение списка объявлений
    @Test
    public void testFindAdvertisementsWithSellerInfo_Success() {

        List<AdvertisementsDTO> expectedList = Arrays.asList(byeAdvertisementDTO, notByeAdvertisementDTO);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(AdvertisementsDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedList);

        List<AdvertisementsDTO> result = advertisementDAO.findAdvertisementsWithSellerInfo();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Машина", result.get(0).getTitle());
        assertEquals("Мебель", result.get(1).getTitle());
        assertEquals(300.0, result.get(0).getPrice());
        assertEquals(100.0, result.get(1).getPrice());
        assertEquals("seller1", result.get(0).getSeller());
        assertEquals("seller2", result.get(1).getSeller());
        assertEquals(4.5, result.get(0).getScore());
        assertEquals(3.8, result.get(1).getScore());

        verify(sessionFactory).getCurrentSession();
        verify(session).createQuery(anyString(), eq(AdvertisementsDTO.class));
        verify(query).getResultList();
    }

   // Тест 2. Пустой результат
    @Test
    public void testFindAdvertisementsWithSellerInfo_EmptyList() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(AdvertisementsDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<AdvertisementsDTO> result = advertisementDAO.findAdvertisementsWithSellerInfo();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(sessionFactory).getCurrentSession();
        verify(session).createQuery(anyString(), eq(AdvertisementsDTO.class));
        verify(query).getResultList();
    }

    //Тест 3. Проверка, что HQL запрос содержит все необходимые части
    @Test
    public void testFindAdvertisementsWithSellerInfo_HqlQueryStructure() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(AdvertisementsDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        advertisementDAO.findAdvertisementsWithSellerInfo();

        ArgumentCaptor<String> hqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(session).createQuery(hqlCaptor.capture(), eq(AdvertisementsDTO.class));

        String actualHql = hqlCaptor.getValue();

        assertNotNull(actualHql);
        assertTrue(actualHql.contains("SELECT new com.example.dto.AdvertisementsDTO"));
        assertTrue(actualHql.contains("a.title"));
        assertTrue(actualHql.contains("a.category"));
        assertTrue(actualHql.contains("a.price"));
        assertTrue(actualHql.contains("u.login"));
        assertTrue(actualHql.contains("FROM Advertisement a"));
        assertTrue(actualHql.contains("JOIN a.seller u"));
        assertTrue(actualHql.contains("UNION ALL"));
        assertTrue(actualHql.contains("a.byeStatus = true"));
        assertTrue(actualHql.contains("a.byeStatus = false"));
        assertTrue(actualHql.contains("a.status = 'ACTIVE'"));
        assertTrue(actualHql.contains("COALESCE"));
        assertTrue(actualHql.contains("(SELECT AVG(s.score) FROM Score s WHERE s.seller = u)"));
    }

    // Тест 4. Проверка последовательности отображения(сначала проплаченные, потом обычные)
    @Test
    public void testFindAdvertisementsWithSellerInfo_CheckUnionOrder() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(AdvertisementsDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(byeAdvertisementDTO, notByeAdvertisementDTO));

        List<AdvertisementsDTO> result = advertisementDAO.findAdvertisementsWithSellerInfo();

        assertNotNull(result);
        assertEquals("Машина", result.get(0).getTitle());
        assertEquals("Мебель", result.get(1).getTitle());
    }


    // Тест 5. Проверка, что заменяется NULL на 0.0
    @Test
    public void testFindAdvertisementsWithSellerInfo_CoalesceUsed() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString(), eq(AdvertisementsDTO.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        advertisementDAO.findAdvertisementsWithSellerInfo();

        ArgumentCaptor<String> hqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(session).createQuery(hqlCaptor.capture(), eq(AdvertisementsDTO.class));

        String hql = hqlCaptor.getValue();

        assertTrue(hql.contains("COALESCE"));
        assertTrue(hql.contains("SELECT AVG(s.score)"));
        assertTrue(hql.contains("0.0"));
    }

    // Тест 6. Проверка при возникновении исключения
    @Test
    public void testFindAdvertisementsWithSellerInfo_WhenExceptionThrown() {
        RuntimeException expectedException = new RuntimeException("Database error");
        when(sessionFactory.getCurrentSession()).thenThrow(expectedException);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            advertisementDAO.findAdvertisementsWithSellerInfo();
        });

        assertEquals("Database error", thrown.getMessage());
        verify(sessionFactory).getCurrentSession();
        verify(session, never()).createQuery(anyString(), any());
    }
}