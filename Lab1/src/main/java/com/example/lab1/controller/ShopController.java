package com.example.lab1.controller;


import com.example.lab1.DAO.ShopDao;
import com.example.lab1.models.Car;
import com.example.lab1.models.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/shop")
public class ShopController {
    @EJB
    private ShopDao shopDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // localhost:8080/shop
    public Response getClients() throws JsonProcessingException {
        List<Shop> shops = shopDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(shops))
                .build();
    }

    @GET
    @Path("/{shopId}") // localhost:8080/driver
    public Response getShopById(@PathParam("shopId") String shopId) throws JsonProcessingException {
        Shop shop = shopDao.get(Integer.valueOf(shopId));

        if (shop == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Car with id %s not found", shopId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(shop))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewShop(
            @FormParam("address") String address) {
        Shop shop = new Shop();
        shop.setAddress(address);

        shopDao.save(shop);
        return Response.ok().build();
    }

    @PUT
    @Path("/{shopId}")
    public Response updateShop(
            @PathParam("shopId") String shopId,
            @DefaultValue("") @FormParam("address") String address
    ) {
        Shop shop = shopDao.get(Integer.valueOf(shopId));
        if (shop == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Shop with id %s not found", shopId)).build();

        if (!address.isEmpty())
            shop.setAddress(address);

        shopDao.update(shop);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{shopId}")
    public Response deleteShop(@PathParam("shopId") String shopId) {
        Shop shop = shopDao.get(Integer.valueOf(shopId));
        if (shop == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Shop with id %s not found", shopId)).build();

        shopDao.delete(shop);
        return Response.ok().build();
    }
}
