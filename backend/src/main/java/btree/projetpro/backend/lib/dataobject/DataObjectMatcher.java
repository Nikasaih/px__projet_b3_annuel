package btree.projetpro.backend.lib.dataobject;

import btree.projetpro.backend.lib.dataobject.dto.Dto;
import btree.projetpro.backend.lib.dataobject.entity.Entities;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataObjectMatcher<T extends Entities> {
    final ModelMapper mapper = new ModelMapper();

    public Dto entityToDto(Entities entity, Dto dto) {
        return mapper.map(entity, dto.getClass());
    }

    public List<Dto> entitiesToDtos(List<Entities> entities, Dto dto) {
        return entities.stream().map(entity -> entityToDto(entity, dto)).collect(Collectors.toList());
    }

    public T dtoToEntity(Dto dto, T entity) {
        T map = (T) mapper.map(dto, entity.getClass());
        return map;
    }


    public List<Entities> dtosToEntities(List<Dto> dtos, T entity) {
        return dtos.stream().map(dto -> dtoToEntity(dto, entity)).collect(Collectors.toList());
    }
}
