package dv.kinash.hw7_rest.controller;

import dv.kinash.hw7_rest.model.Shop;
import dv.kinash.hw7_rest.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/shops")
public class ShopController {
    @Autowired
    private ShopService service;

    @GetMapping(value = {"", "/", "/list"})
    public ResponseEntity<CollectionModel<Shop>> getList(){
        CollectionModel model = CollectionModel.of(service.getList());
        model.add(linkTo(methodOn(ShopController.class).getList()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @PostMapping(value = {"/", "/add"})
    public ResponseEntity add(@RequestBody Shop newShop){
        newShop = service.add(newShop);
        EntityModel<Shop> entityModel = EntityModel.of(newShop);
        entityModel.add(linkTo(methodOn(ShopController.class).getById(newShop.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ShopController.class).getList()).withRel("list"));
        entityModel.add(linkTo(methodOn(ShopController.class).deleteById(newShop.getId())).withRel("delete").withType("DELETE"));
        entityModel.add(linkTo(methodOn(ShopController.class).updateById(newShop.getId(), newShop)).withRel("update").withType("PUT"));
        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntityModel<Shop>> getById(@PathVariable("id") Integer id){
        Shop foundShop = service.getById(id);
        if (foundShop == null) {
            return ResponseEntity.notFound().build();
        } else {
            EntityModel<Shop> entityModel = EntityModel.of(foundShop);
            entityModel.add(linkTo(methodOn(ShopController.class).getById(id)).withSelfRel());
            entityModel.add(linkTo(methodOn(ShopController.class).getList()).withRel("list"));
            entityModel.add(linkTo(methodOn(ShopController.class).deleteById(id)).withRel("delete").withType("DELETE"));
            entityModel.add(linkTo(methodOn(ShopController.class).updateById(id, foundShop)).withRel("update").withType("PUT"));
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity deleteById(@PathVariable("id") Integer id){
        HttpStatus status = (service.deleteById(id)) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(CollectionModel.of(
                        Collections.EMPTY_LIST,
                        linkTo(methodOn(ShopController.class).getList()).withRel("list")));
    }

    @RequestMapping(value = {"/{id}", "/update/{id}"}, method = {RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<EntityModel<Shop>> updateById(@PathVariable("id") Integer id, @RequestBody Shop shop){
        Shop updatedShop = service.updateById(id, shop);
        if (updatedShop == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EntityModel<Shop> entityModel = EntityModel.of(updatedShop);
        entityModel.add(linkTo(methodOn(ShopController.class).getById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(ShopController.class).getList()).withRel("list"));
        entityModel.add(linkTo(methodOn(ShopController.class).deleteById(id)).withRel("delete").withType("DELETE"));
        entityModel.add(linkTo(methodOn(ShopController.class).updateById(id, updatedShop)).withRel("update").withType("PUT"));
        return new ResponseEntity<>(entityModel, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<EntityModel<Object>> test(){
        String answer;
        HttpStatus status;
        if (service.getList().size() > 0){
            answer = "Base already has data!";
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else {
            service.add(new Shop("All for home", "Kyiv", "Khreshchatyk 13", 5, true));
            service.add(new Shop("All for office", "Lviv", "Liberty Avenu 7", 8, false));
            answer = "Added test data!";
            status = HttpStatus.OK;
        }
        EntityModel<Object> entityModel = EntityModel.of(new Object(){public String value = answer;});
        entityModel.add(linkTo(methodOn(ShopController.class).getList()).withRel("list"));
        return ResponseEntity.status(status).body(entityModel);
    }

}
