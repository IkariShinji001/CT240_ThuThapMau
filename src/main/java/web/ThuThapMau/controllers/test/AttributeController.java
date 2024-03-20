package web.ThuThapMau.controllers.test;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.ThuThapMau.dtos.AttributeDto;
import web.ThuThapMau.entities.CollectionAttribute;
import web.ThuThapMau.services.CollectionAttributeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AttributeController {
    private ModelMapper modelMapper;
    private CollectionAttributeService collectionAttributeService;


    @Autowired
    public AttributeController(ModelMapper modelMapper, CollectionAttributeService collectionAttributeService){
        this.collectionAttributeService = collectionAttributeService;
        this.modelMapper = modelMapper;
    }
    @GetMapping ("/api/v1/collection-attribute")
    public List<AttributeDto> getAllAttributes(){
        List<CollectionAttribute> collectionAttributes  =  collectionAttributeService.getAllAttributes();
        return collectionAttributes.stream()
                .map(collectionAttribute -> modelMapper.map(collectionAttribute, AttributeDto.class))
                .collect(Collectors.toList());
    }
}
