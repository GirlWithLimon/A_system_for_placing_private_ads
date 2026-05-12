package com.example.service;


import com.example.dto.AdvertisementItemDTO;
import com.example.dto.AdvertisementUsersDTO;
import com.example.dto.AdvertisementsDTO;
import com.example.dto.AdvertisementsUsersDTO;
import com.example.model.Advertisement;
import com.example.model.ProductsCategory;

import java.util.List;

public interface IAdvertisementService extends GenericService<Advertisement, Integer> {
    List<AdvertisementsDTO> findAdvertisementsWithSellerInfo();
    List<AdvertisementsUsersDTO> findAdvertisementsUsers(int idSeller);
    AdvertisementItemDTO findAdvertisementItem(int id);
    AdvertisementUsersDTO findAdvertisementUsersItem(int id);
    List<AdvertisementsDTO> findAdvertisementWithCategory (ProductsCategory category);
}