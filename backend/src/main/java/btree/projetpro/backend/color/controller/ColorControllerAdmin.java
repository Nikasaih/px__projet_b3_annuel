package btree.projetpro.backend.color.controller;

import btree.projetpro.backend.color.ColorDto;
import btree.projetpro.backend.color.ColorEntity;
import btree.projetpro.backend.color.ColorRepository;
import btree.projetpro.backend.util.hateoas.ReqControllerAdmin;
import btree.projetpro.backend.util.persistenceservice.DtoEntityConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/admin/colors")
@RestController
public class ColorControllerAdmin implements ReqControllerAdmin<ColorEntity> {
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    DtoEntityConverterService dtoEntityConverter;
    ColorEntity uselessEntityForDtoConversion = new ColorEntity();

    @PostMapping
    public ResponseEntity<ColorEntity> createColor(@RequestBody ColorDto colorDto) {
        ColorEntity entityToSave = (ColorEntity) dtoEntityConverter.dtoToEntity(colorDto, uselessEntityForDtoConversion);

        colorRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ColorEntity> deleteOne(@PathVariable("id") Long id) {
        colorRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
